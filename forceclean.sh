#!/bin/sh
find `dirname $0` -type d -name 'target' | xargs -I% rm -rfv %
