#!/bin/sh
#Copyright (c) 2022 Qualcomm Innovation Center, Inc. All rights reserved.
#SPDX-License-Identifier: BSD-3-Clause-Clear

kill -9 `cat /var/run/data/dnsmasq.pid.bridge$1`
echo "stop dnsmasq "$1 > /dev/kmsg
