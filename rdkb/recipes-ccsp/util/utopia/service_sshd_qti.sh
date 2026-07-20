#!/bin/sh
# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: BSD-3-Clause-Clear

#--------------------------------------------------------------
# start_dropbear_echo
# Starts dropbear SSH on WAN IPv4, WAN IPv6 and LAN interfaces
# for echo BOX_TYPE
#--------------------------------------------------------------
start_dropbear_echo() {
    mkdir -p /tmp/.dropbear
    CM_IP4=`ip -4 addr show dev $CMINTERFACE scope global | awk '/inet/{print $2}' | cut -d '/' -f1`
    CM_IP6=`ip -6 addr show dev $CMINTERFACE scope global | awk '/inet/{print $2}' | cut -d '/' -f1 | head -n1`
    PID_FILE1=/var/run/dropbearv4.pid
    PID_FILE2=/var/run/dropbearv6.pid
    lan_ipaddr=$(syscfg get lan_ipaddr)
    lan_ssh=$(syscfg get mgmt_lan_sshaccess)
    if [ -n "$CM_IP4" ]; then
        # Start dropbear on WAN IPv4
        dropbear -R -E -a -r $DROPBEAR_PARAMS_1 -r $DROPBEAR_PARAMS_2 -p [$CM_IP4]:22 -P $PID_FILE1
    fi

    if [ -n "$CM_IP6" ]; then
        # Start dropbear on WAN IPv6
        dropbear -R -E -a -r $DROPBEAR_PARAMS_1 -r $DROPBEAR_PARAMS_2 -p [$CM_IP6]:22 -P $PID_FILE2
    fi

    if [ -n "$lan_ipaddr" ] && [ "$lan_ssh" = "1" ]; then
        # Start dropbear on LAN
        dropbear -R -E  -a -r $DROPBEAR_PARAMS_1 -r $DROPBEAR_PARAMS_2 -p [$lan_ipaddr]:22 -P $PID_FILE1
    fi
}
stop_dropbear_echo() {
    local tmp_filename=$1
    lan_ipaddr=$(syscfg get lan_ipaddr)
    LAN_SSHACCESS=$(syscfg get mgmt_lan_sshaccess)

    if [ "$LAN_SSHACCESS" = "1" ] && [ -n "$lan_ipaddr" ]; then
        ps | grep 'dropbear -R -E -a' | grep -v "$lan_ipaddr" | sed '/grep/d' > $tmp_filename
    else
        ps | grep 'dropbear -R -E -a' | sed '/grep/d' > $tmp_filename
    fi
}
