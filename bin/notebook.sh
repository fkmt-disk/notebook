#!/bin/sh

nbhome=~/notebook
port=9999

case "$1" in
	"start")
		cd $nbhome
		sbt run > $nbhome/log &
		echo "pid=$!"
		;;
	"stop")
		curl -v http://localhost:$port/shutdown
		;;
	*)
		if [ `ps aux | grep notebook.Launcher | grep -v grep | wc -l` -ne 0 ] ; then
			echo "server is running"
		else
			echo "server is dead"
		fi
		;;
esac
