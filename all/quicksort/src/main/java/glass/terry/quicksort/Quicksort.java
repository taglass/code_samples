/*
 *Author: Terry Glass
 *Objective: Implement quicksort.  Quicksort is superior to the mergesort
 *provided by Collections.sort() for arrays of objects in memory if a stable
 *sort is not required.
 * O(nlogn) average performance with O(n^2) worst-case performance
 */
package glass.terry.quicksort;
 
public class Quicksort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        sort(array, 0, array.length-1);
    }


    private static <T extends Comparable<? super T>> void sort(T[] array, int leftIndex, int rightIndex) {
        //We could further optimize by using an insertion sort for small partitions
        if (rightIndex <= leftIndex) {
            return; 
        }

        String s = "";
        for (int i = 0; i < array.length; ++i) {
            s +=  "," + array[i];
        }
        int pivotIndex = medianOfThree(array, leftIndex, rightIndex);
        int pivotNewIndex = partition(array, leftIndex, rightIndex, pivotIndex);
        sort(array, leftIndex, pivotNewIndex-1);
        sort(array, pivotNewIndex+1, rightIndex);
    }

    private static <T extends Comparable<? super T>> int partition(T[] array, int leftIndex, int rightIndex, int pivotIndex) {
        String s = "";
        for (int i = leftIndex; i <= rightIndex; i++) {
            s += "," + array[i];
        }
        logger.log(Level.DEBUG, "partition " + s);
        T pivotValue = array[pivotIndex];
        swap(array, pivotIndex, rightIndex);
        int storeIndex = leftIndex;
        for (int i = leftIndex; i < rightIndex; ++i) {
            if(array[i].compareTo(pivotValue) < 0) {
                if(i != storeIndex)
                    swap(array, i, storeIndex);
                storeIndex++;
            }
        }
        swap(array, storeIndex, rightIndex);
        logger.log(Level.DEBUG, "end parttion");
        return storeIndex;

    }
    
    private static <T extends Comparable<? super T>> int medianOfThree(T[] array, int leftIndex, int rightIndex) {
        int centerIndex = (leftIndex + rightIndex) / 2;
        if(array[leftIndex].compareTo(array[centerIndex]) > 0) {
            swap(array, leftIndex, centerIndex);
        }

        if(array[leftIndex].compareTo(array[rightIndex]) > 0) {
            swap(array, leftIndex, rightIndex);
        }

        if(array[centerIndex].compareTo(array[rightIndex]) > 0) {
            swap(array, centerIndex, rightIndex);
        }
        return centerIndex;
    }

    private static <T> void swap(T[] array, int index1, int index2) {
        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

}
