#!/bin/bash
DIR=`basename $PWD`

for tag in $@
do
	mkdir -p ../../$tag
	ln -s ../all/$DIR ../../$tag/$DIR
done


