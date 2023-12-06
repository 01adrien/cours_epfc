#!/usr/bin/bash

# arping on all machine on local network

for i in {1..254}; do
    echo -ne "\n 192.168.1.$i : "
    arping -c 1 -I wlp2s0 192.168.1$i | grep reply
done
