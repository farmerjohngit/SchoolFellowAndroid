#!/bin/sh
buildType=''
other=''
for arg in "$@"
do
    if  [[ $arg == -* ]];then
            other+=$arg
            other+=" "
        else
            buildType=$arg
    fi
done

if [ "$buildType"x = ""x ]; then
    buildType="debug"
fi
gradle patch -Pdy=$buildType $other