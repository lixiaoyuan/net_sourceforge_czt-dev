#!/bin/bash


for file in `find -name pom.xml`
do
    sed -i '1,$s/1.6-SNAPSHOT/1.6-cdh/g' $file
done
