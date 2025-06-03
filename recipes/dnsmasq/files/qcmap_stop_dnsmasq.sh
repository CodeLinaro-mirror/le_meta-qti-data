#!/bin/sh
#Copyright (c) 2024 Qualcomm Innovation Center, Inc. All rights reserved.
#SPDX-License-Identifier: BSD-3-Clause-Clear

if [ $1 == 0 ]
then
    #Kill pid for default bridge
    kill -9 `cat /var/run/data/dnsmasq.pid`
else
    #Kill pid for on-demand bridges
    kill -9 `cat /var/run/data/dnsmasq.pid.bridge$1`
fi
echo "stop dnsmasq for bridge"$1 > /dev/kmsg

