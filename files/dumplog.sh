#!/bin/sh

adb shell "su -c dumpsys $1 " > $2
