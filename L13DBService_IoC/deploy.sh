#!/usr/bin/env bash

mvn clean package

cp target/L13DBService_IoC.war ~/apps/jetty/webapps/root.war
