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

# set_fec_for_speed - Set FEC encoding matching the link speed
# @1: interface
# @2: speed in Mb/s (25000 -> RS-FEC, 10000 -> no FEC)
set_fec_for_speed() {
    case "$2" in
        25000) ethtool --set-fec "$1" encoding rs ;;
        10000) ethtool --set-fec "$1" encoding off ;;
    esac
}

# config_iface - Configure msglvl/speed/autoneg/FEC based on the module type.
# A link that is already up is never touched.
# @1: interface
config_iface() {
    iface="$1"

    [ -d "/sys/class/net/$iface" ] || return 0

    if ethtool "$iface" 2>/dev/null | grep -q "Link detected: yes"; then
        echo "mtipcfm: $iface link already up, not touching it"
        return 0
    fi

    # module EEPROM may only be readable with the netdev up
    ifconfig "$iface" up
    sleep 2
    module_info="$(ethtool -m "$iface" 2>&1)"
    module_rc=$?
    if [ "$module_rc" -ne 0 ]; then
        echo "mtipcfm: ethtool -m failed on $iface rc=$module_rc: $module_info"
        echo "mtipcfm: error line cable is not connected to $iface"
        return 1
    fi

    # down -> set msglvl/speed/FEC -> up
    ifconfig "$iface" down
    if echo "$module_info" | grep -q "Transmitter technology.*Copper"; then
        echo "mtipcfm: Port type Direct Attach Copper detected on $iface"
        ethtool -s "$iface" msglvl 1
    else
        echo "mtipcfm: Port type FIBRE detected on $iface"
        ethtool -s "$iface" msglvl 2
    fi
    ethtool -s "$iface" speed 25000 autoneg off duplex full
    set_fec_for_speed "$iface" 25000
    ifconfig "$iface" up
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
            config_iface "$iface"
        done
    fi
    #ethtool -s eth00 speed 25000 autoneg off
    #echo 0,0,0,55,0,0,0,1 > /sys/kernel/debug/qcom_aw_phy_test/tx_eq_val
fi

exit 0
