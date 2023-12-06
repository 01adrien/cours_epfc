#!/bin/bash

javac funcs.java && java funcs ${@: 2};
rm funcs.class;