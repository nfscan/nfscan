#!/bin/sh
cd $CI_HOME/02-Sourcecode/nfscan-server
mvn clean compile package -Dtarget.env=des
