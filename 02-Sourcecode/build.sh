#!/bin/sh
cd $CI_HOME/02-Sourcecode/nfscan-server
pwd
mvn clean compile package -Dtarget.env=prod
