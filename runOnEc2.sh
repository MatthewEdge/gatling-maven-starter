#!/usr/bin/env bash

# Before running:
#   1. Insert IAM keys into aws.properties file

# Download Gatling locally

GATLING_VERSION=$1
GATLING_DIR=./target/gatlingAws
GATLING_ARCHIVE=gatling-charts-highcharts-bundle-${GATLING_VERSION}-bundle.zip

# Short circuit download if alread present
if [ -f "${GATLING_DIR}/${GATLING_ARCHIVE}" ]; then
    echo "Gatling already downloaded"
    exit 0
fi

mkdir -p ${GATLING_DIR}
cd ${GATLING_DIR}
curl https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/${GATLING_VERSION}/${GATLING_ARCHIVE} -o ${GATLING_ARCHIVE}
unzip ${GATLING_ARCHIVE}

# Build EC2 instances and execute simulation
mvn \
    -Dgatling.skip=true  \
    -DskipTests          \
    clean package        \
    assembly:single      \
    com.ea.gatling:gatling-aws-maven-plugin:execute