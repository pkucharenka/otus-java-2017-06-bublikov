#!/usr/bin/env bash

#-XX:+UseSerialGC
#-XX:+UseParallelGC
#-XX:+UseParNewGC
#-XX:+UseG1GC

java -Xdebug -XX:+UseSerialGC -Xmx512m -Xms512m -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=n -jar target/L2.2gcstat.jar