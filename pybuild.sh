#!/bin/bash

mkdir -p pyclient/sliceGen
slice2py slice/Home.ice --output-dir=pyclient/sliceGen
