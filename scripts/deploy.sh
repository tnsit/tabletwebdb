#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/tabletwebdb2.pem \
    target/tabletwebdb-1.0-SNAPSHOT.jar \
    ec2-user@ec2-35-157-146-242.eu-central-1.compute.amazonaws.com:/home/ec2-user/

echo 'Restart server...'

ssh -i ~/.ssh/tabletwebdb2.pem ec2-user@ec2-35-157-146-242.eu-central-1.compute.amazonaws.com << EOF
pgrep java | xargs kill -9
nohup java -jar tabletwebdb-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'