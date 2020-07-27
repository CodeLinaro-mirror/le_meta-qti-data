#!/bin/sh
# Copyright (c) 2020, The Linux Foundation. All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#     * Redistributions of source code must retain the above copyright
#       notice, this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above
#       copyright notice, this list of conditions and the following
#       disclaimer in the documentation and/or other materials provided
#       with the distribution.
#     * Neither the name of The Linux Foundation nor the names of its
#       contributors may be used to endorse or promote products derived
#       from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
# WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
# ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
# BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
# BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
# WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
# OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
# IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

DUMP_TO_KMSG=/dev/kmsg

file=$1
. $1

echo $qlocal_ip $qremote_ip $qlocal_port $qremote_port $qvlan_id $qlocal_macid $qremote_macid $qip_type $qinterface $qprotocol
echo $cvlan_id $clocal_macid $cremote_macid $cip_type $cinterface $cprotocol
echo "script loaded from eth-adaption-layer" > $DUMP_TO_KMSG
netmask=`echo ${qlocal_ip:P:18}`
echo $netmask

if [ -f /sys/devices/soc0/soc_id ]; then
    soc_id=`cat /sys/devices/soc0/soc_id`
else
    soc_id=`cat /sys/devices/system/soc/soc0/id`
fi

if [ -f /sys/devices/soc0/hw_platform ]; then
    hw_platform=`cat /sys/devices/soc0/hw_platform`
else
    hw_platform=`cat /sys/devices/system/soc/soc0/hw_platform`
fi

if [ -f /sys/devices/soc0/platform_subtype_id ]; then
    platform_subtype_id=`cat /sys/devices/soc0/platform_subtype_id`
fi

case "$qlocal_ip" in
    *:*)
        qip_type="IPv6"
        echo $qip_type
    ;;
    *.*)
        qip_type="IPv4"
        echo $qip_type
    ;;
esac

case "$soc_id" in
    "418" | "352")
        case "$hw_platform" in
            "ADP" | "TTP" | "MTP")

            case "$platform_subtype_id" in
                "0" | "1" | "2" | "3" | "4" | "5")
                #QMI over ethernet configuration
                    if [[ "$qip_type" == "IPv4" ]]
                    then
                        echo "\n QMI add vlan to eth start" > $DUMP_TO_KMSG
                        vconfig add eth0 $qvlan_id
                        ifconfig eth0.$qvlan_id hw ether $qlocal_macid
                        ifconfig eth0.$qvlan_id $qlocal_ip up
                        if [ -e /sys/class/net/bridge0 ]; then
                            ebtables -t broute -A BROUTING -p 802_1q --vlan-id $qvlan_id -i eth0 -j DROP
                        fi
                        echo qvlanid=$qvlan_id > /dev/emac
                        echo qmac_id=$qlocal_macid > /dev/emac
                        echo qoe=$qprotocol > /dev/emac
                        echo "\n QMI add vlan to eth stop" > $DUMP_TO_KMSG
                    elif [[ "$qip_type" == "IPv6" ]]
                    then
                        echo "\n QMI add vlan to eth start" > $DUMP_TO_KMSG
                        vconfig add eth0 $qvlan_id
                        ifconfig eth0.$qvlan_id hw ether $qlocal_macid
                        ifconfig eth0.$qvlan_id inet6 add $qlocal_ip
                        ifconfig eth0.$qvlan_id up
                        if [ -e /sys/class/net/bridge0 ]; then
                            ebtables -t broute -A BROUTING -p 802_1q --vlan-id $qvlan_id -i eth0 -j DROP
                        fi
                        ip -6 r a $netmask/64 dev eth0.$qvlan_id
                        echo qvlanid=$qvlan_id > /dev/emac
                        echo qmac_id=$qlocal_macid > /dev/emac
                        echo qoe=$qprotocol > /dev/emac
                        echo "\n QMI add vlan to eth stop" > $DUMP_TO_KMSG
                    else
                        echo "\n QMI add vlan to eth start" > $DUMP_TO_KMSG
                        echo "\n specify ip type correctly in config" > $DUMP_TO_KMSG
                        echo "\n QMI add vlan to eth stop" > $DUMP_TO_KMSG
                    fi

                #CV2X over ethernet configuration
                    if [[ "$cprotocol" == "Cv2X" ]]
                    then
                        echo "\n CV2X add vlan to eth start" > $DUMP_TO_KMSG
                        if [[ "$soc_id" == "352" ]]
                        then
                            vconfig add $cinterface $cvlan_id
                            ifconfig $cinterface.$cvlan_id hw ether $clocal_macid
                            ifconfig $cinterface.$cvlan_id up
                            if [ -e /sys/class/net/bridge0 ]; then
                                vconfig add bridge0 $cvlan_id
                                ifconfig bridge0.$cvlan_id hw ether $clocal_macid
                                ifconfig bridge0.$cvlan_id up
                                ebtables -t broute -A BROUTING -p 802_1q --vlan-id $cvlan_id -i $cinterface -j DROP
                            fi
                        fi
                        echo cvlanid=$cvlan_id > /dev/emac
                        echo cmac_id=$clocal_macid > /dev/emac
                        echo cv2x=$cprotocol > /dev/emac
                        echo "\n CV2X add vlan to eth stop" > $DUMP_TO_KMSG
                    else
                        echo "\n CV2X add vlan to eth start" > $DUMP_TO_KMSG
                        echo "\n No Cv2X specifyied in config" > $DUMP_TO_KMSG
                        echo "\n CV2X add vlan to eth stop" > $DUMP_TO_KMSG
                    fi
                ;;
        #Normal ethernet configuration
                "0")
                    echo $qlocal_macid > /dev/emac
                    echo $clocal_macid > /dev/emac
                ;;
            esac
            ;;
        esac
    ;;
esac

exit 0
