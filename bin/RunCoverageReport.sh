#!/bin/bash
sbt common_util/test common_util/coverageReport < common_util/testdata/formated-text.txt
open common_util/target/scala-2.11/scoverage-report/index.html