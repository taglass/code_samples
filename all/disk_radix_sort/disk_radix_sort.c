/*
 *  Author
 *    Terry Glass <taglass@gmail.com>
 *  Objective
 *    Sorts a textfile consisting of strings representing integers listed one
 *    per line using a radix sort.  Only one integer from the input is allowed
 *    in main memory at any given time. The inspiration for this was a mistated
 *    homework assignment from a student on reddit.
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>

/* Radix > 10 has not been tested, and almost certainly doesn't work. */
static const int RADIX = 10;
static const char *inpath = "input.txt";
static const char *outpath = "output.txt";

/* returns the nth character from the end of s
 * lsb_nth_char("1234", 0) would return 0;
 * returns -1 upon error
 */
char lsb_nth_char(const char *s, int pos)
{
    int index = strlen(s) - pos - 1;
    return (index >= 0) ? s[index] : '0';
}



/*
 * Returns the number of passes required to sort infile
 */
int passes_required(FILE *infile)
{
    long initial_pos = ftell(infile);
    char *buf;
    int len, maxlen = 0;

    if(initial_pos == -1) {
        return - 1;
    }
    rewind(infile);
    
    /*
     * %m is a GNUism that dynamically allocates a string of sufficient
     * size.  We are responsible for calling free() on the returned
     * string.
     */
    while (fscanf(infile, "%ms\n", &buf) != EOF) {
        len = strlen(buf);
        if(buf[0] == '+' || buf[0] == '-') {
            len--;
        }
        if(len > maxlen) {
            maxlen = len;
        }
        free(buf);
    }
    fseek(infile, initial_pos, SEEK_SET);
    return maxlen;
}


/* 
 * Splits a file of strings that represent integers into buckets based upon
 * the value of the nth from the right digit.
 */
void split(FILE *infile, FILE *buckets[], int pos)
{
    int i;
    char digit;
    char *s;
    while(fscanf(infile, "%ms\n", &s) != EOF) {
        digit = lsb_nth_char(s, pos);
        fprintf(buckets[digit - 48], "%s\n", s);
        free(s);
    }
    for(i = 0; i < 10; i++) {
        fflush(buckets[i]);
    }
}


/*
 * Merges a set of buckets into one output file.
 */
void merge(FILE *outfile, FILE *buckets[])
{
    int i;
    char *s;

    for(i = 0; i < 10; i++) {
        rewind(buckets[i]);
        while(fscanf(buckets[i], "%ms\n", &s) != EOF) {
            fprintf(outfile, "%s\n", s);
            free(s);
        }
    }
    fflush(outfile);
}

/*
 *
 * Creates n buckets that are backed by temporary files.  The buckets act as
 * queues for the radix sort.
 */
void create_buckets(FILE *buckets[], int n)
{
    int i;

    for(i = 0; i < n; i++) {
        buckets[i] = tmpfile();
        if(buckets[i] == NULL) {
            perror("tmpfile()");
            exit(EXIT_FAILURE);
        }
    }
}


/*
 * Destroy the files representing our buckets.
 */
void destroy_buckets(FILE *buckets[], int n)
{
    int i;
    for(i = 0; i < n; i++) {
        fclose(buckets[i]);
        buckets[i] = NULL;
    }
}


/*
 * Perform a disk based radix sort of file input, which contains strings
 * representing integers in radix.  Results are stored in file output.
 */
void disk_radix_sort(FILE *input, FILE *output, int radix)
{
    int pass, num_passes;
    FILE *buckets[radix];
    FILE *in, *out;


    num_passes = passes_required(input);
    in = input;
    out = tmpfile();

    for(pass = 0; pass < num_passes; pass++) {
        create_buckets(buckets, radix);
        split(in, buckets, pass);
        /* merge to output on final pass */
        merge((pass == num_passes -1) ? output : out, buckets);
        destroy_buckets(buckets, radix);

        fclose(in);
        in = out;
        out = tmpfile();
        rewind(in);
    }

}



int main(int argc, char *argv[])
{
    long i;
    FILE *input;
    FILE *output;

    input = fopen(inpath, "r");
    if(input == NULL)
    {
        // input.txt doesn't exist.  Create it and fill with random data
        if(errno == ENOENT) {
            input = fopen(inpath, "w+");
            if(input) {
                for(i = 0; i < 1000; i++) {
                    fprintf(input, "%ld\n", random());
                }
                input = freopen(inpath, "r", input);
                if(!input) {
                    perror("freopen()");
                    exit(EXIT_FAILURE);
                }
            } else {
                perror("fopen()");
                exit(EXIT_FAILURE);
            }

        } else {
            perror("fopen");
            exit(EXIT_FAILURE);
        }
    }

    output = fopen(outpath, "w");
    if(!output) {
        perror("fopen()");
        exit(EXIT_FAILURE);
    }

    disk_radix_sort(input, output, RADIX); 
    return EXIT_SUCCESS;
}
