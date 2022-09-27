#!/bin/sh

client_id=`ps -ef | grep vsockclient | grep -v "grep" | awk '{print $2}'`
echo $client_id
for id in $client_id
do
    kill -9 $id
    echo "killed $id"
done

java -jar ./target/vsockclient-0.0.1-SNAPSHOT.jar > client.log &