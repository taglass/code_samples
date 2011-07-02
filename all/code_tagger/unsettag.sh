#!/bin/bash
DIR=`basename $PWD`

for TAG in $@
do
#	mkdir -p ../../$tag
#	ln -s ../all/$DIR ../../$tag/$DIR
	rm -f ../../$TAG/$DIR
	if [ `ls -1 ../../$TAG/ | wc -l` -eq 0 ]; then
		echo "Removing empty category '$TAG'."
		rmdir ../../$TAG
	fi
done


