#!/bin/bash

javac ex_$1.java && java ex_$1 ${@: 2};
rm ex_$1.class;