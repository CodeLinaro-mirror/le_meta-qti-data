#************************************************************************************
#  @FILE     mtipcfm.sh
#
#  @BRIEF    Script to install/configure any additional configuration to MTIP driver
#            enumerated interfaces.
#
#  Copyright (c) 2023-2024 Qualcomm Innovation Center, Inc. All rights reserved.
#  SPDX-License-Identifier: BSD-3-Clause-Clear
#************************************************************************************
#!/bin/sh

x100_dir="/etc/csm/x100"
curr_dir=$(dirname $0)
build_conf_path="/firmware/image/build_conf/build_conf.txt"

is_ntn() {
    [ -f "$build_conf_path" ] || return 0
    #grep -q '^build_type:NTN$' "$build_conf_path" 2>/dev/null && return 1
    return 1
}

#Execute mtipcfm.sh from host if this is x100 device and the current script is
#not running at host location
if [ -d "$x100_dir" ] && [ "$curr_dir" != "$x100_dir" ]
then
    sh $x100_dir/mtipcfm.sh
    exit 0
else
    echo "Placeholder to issue Ethernet configuration commands"
    is_ntn
    if [ $? -eq 1 ]; then
        touch /tmp/is_ntn
        for iface in eth00 eth10 eth20; do
            ethtool -s "$iface" speed 25000 autoneg off duplex full
        done
    fi
    #ethtool -s eth00 speed 25000 autoneg off
    #echo 0,0,0,55,0,0,0,1 > /sys/kernel/debug/qcom_aw_phy_test/tx_eq_val
fi

exit 0
