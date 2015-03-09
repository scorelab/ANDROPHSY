#!/bin/bash

# mount data partition read only
mount -t ext4 -o ro /home/indeewari/Sample_Phone/data.img /home/indeewari/Test/

# change owner recursively
currentuser = $USER
chown -R currentuser:currentuser /home/indeewari/TEST/
 
# change owner
chmod 677 /home/indeewari/Test/ 
