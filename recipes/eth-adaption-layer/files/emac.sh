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

echo "script loaded from eth-adaption-layer" > $DUMP_TO_KMSG
netmask=`echo ${qlocal_ip:P:18}`

platform_v1=10000
platform_v2=20000
platform_v3=30000
sa515m=418
QCS405=352
sa2150p=452

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

if [ -f /sys/devices/soc0/platform_version ]; then
  platform_version=`cat /sys/devices/soc0/platform_version`
fi

platform_version_hex=`printf '%x\n' $platform_version`

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


eam_supported_targets () {

#for external AP uncomment below line of code
#current_target=EAP
#echo $current_target
#return 1

case "$soc_id" in
  "$sa515m")
    case "$hw_platform" in
      "ADP")
      case "$platform_subtype_id" in
        "5")
        case "$platform_version_hex" in
          "$platform_v1" | "$platform_v2" | "$platform_v3")
          echo -n "support for Ethernet Adaptation Module Enabled" > /dev/kmsg
          current_target=NAD
          echo $current_target
          return 1
        ;;
        esac
      ;;
      esac
    ;;
    esac
;;
esac

case "$soc_id" in
  "$sa2150p" | "$QCS405")
    case "$hw_platform" in
      "ADP")
      case "$platform_subtype_id" in
        "0")
        case "$platform_version_hex" in
          "$platform_v2" | "$platform_v3")
          echo -n "support for Ethernet Adaptation Module Enabled" > /dev/kmsg
          current_target=EAP
          echo $current_target
          return 1
        ;;
        esac
      ;;
      esac
    ;;
    esac
;;
esac

return 0
}

current_target=$(eam_supported_targets)
is_target_supported=$?

if [[ $is_target_supported -eq 1 ]]
then
  #QMI over ethernet configuration
  if [[ "$qip_type" == "IPv4" ]]
  then
    echo "\n QMI add vlan to eth start" > $DUMP_TO_KMSG
	echo $target > /dev/kmsg
    vconfig add $qinterface $qvlan_id
    ifconfig $qinterface.$qvlan_id hw ether $qlocal_macid
    ifconfig $qinterface.$qvlan_id $qlocal_ip up
    if [ -e /sys/class/net/bridge0 ]; then
     ebtables -t broute -A BROUTING -p 802_1q --vlan-id $qvlan_id -i $qinterface -j DROP
    fi
  echo qvlanid=$qvlan_id > /dev/emac
  echo qmac_id=$qlocal_macid > /dev/emac
  echo qoe=$qprotocol > /dev/emac
  echo "\n QMI add vlan to eth stop" > $DUMP_TO_KMSG
  elif [[ "$qip_type" == "IPv6" ]]
  then
    echo "\n QMI add vlan to eth start" > $DUMP_TO_KMSG
    vconfig add $qinterface $qvlan_id
    ifconfig $qinterface.$qvlan_id hw ether $qlocal_macid
    ifconfig $qinterface.$qvlan_id inet6 add $qlocal_ip
    ifconfig $qinterface.$qvlan_id up
    if [ -e /sys/class/net/bridge0 ]; then
     ebtables -t broute -A BROUTING -p 802_1q --vlan-id $qvlan_id -i $qinterface -j DROP
    fi
    ip -6 r a $netmask/64 dev $qinterface.$qvlan_id
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
    if [[ "$current_target" == "EAP" ]]
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
else
  echo -n "No support for Ethernet Adaptation Module Enabled" > /dev/kmsg
fi
exit 0
