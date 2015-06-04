#!/bin/sh
cd 02-Sourcecode/nfscan-server
mvn clean compile package -Dtarget.env=prod
