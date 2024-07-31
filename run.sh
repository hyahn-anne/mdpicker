#!/bin/bash

gradle clean build bootJar -x test

java -jar ./build/libs/mdpicker-0.0.1-SNAPSHOT.jar
