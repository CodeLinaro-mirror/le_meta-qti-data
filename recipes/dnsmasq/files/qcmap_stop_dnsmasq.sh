#!/bin/sh
#Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
#SPDX-License-Identifier: BSD-3-Clause-Clear

kill -9 `cat /var/run/data/dnsmasq.pid.bridge$1`
echo "stop dnsmasq "$1 > /dev/kmsg
