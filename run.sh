#!/bin/bash

./gradlew clean build -x test
java -jar ./build/libs/mdpicker-0.0.1-SNAPSHOT.jar