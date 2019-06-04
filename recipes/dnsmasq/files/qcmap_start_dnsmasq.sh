#!/bin/sh
DUMP_TO_KMSG=/dev/kmsg

echo "Starting DNSMASQ SERVICE arg1: $1 agr2: ${type_inst} " > $DUMP_TO_KMSG

if [ ${type_inst} == "vlan" ]; then
    echo "launching dnsmasq for tpie vlan inst" > $DUMP_TO_KMSG
    /usr/bin/dnsmasq --conf-file=${conf_file} \
    --dhcp-leasefile=${dhcp_leasefile} \
    --addn-hosts=${addn_hosts} \
    --pid-file=${pid_file} \
    --interface=${interface} \
    --except-interface=lo \
    -z \
    --dhcp-range=${dhcp_range} \
    --dhcp-hostsfile=${dhcp_hostsfile} \
    --dhcp-option-force=${dhcp_option_force} \
    --dhcp-script=${dhcp_script}
elif [ ${type_inst} == "sip_server" ]; then
    /usr/bin/dnsmasq --conf-file=${conf_file} \
    --interface=${interface} \
    --except-interface=lo \
    -z \
    --resolv-file=${resolv_file} \
    --dhcp-range=${dhcp_range} \
    --dhcp-option=${dhcp_option} \
    --dhcp-option-force=${dhcp_option_force}
elif [ ${type_inst} == "relay" ]; then
    /usr/bin/dnsmasq \
    --interface=${interface} \
    --except-interface=lo \
    -z \
    --dhcp-relay=${dhcp_relay} \
    --dhcp-proxy
elif [ ${type_inst} == "ipv6_nat" ]; then
    /usr/bin/dnsmasq --conf-file=${conf_file} \
    --dhcp-leasefile=${dhcp_leasefile} \
    --addn-hosts=${addn_hosts} \
    --pid-file=${pid_file} \
    --interface=${interface} \
    --except-interface=lo \
    -z \
    --dhcp-range=${dhcp_range} \
    --dhcp-range=${dhcp_range1} \
    --dhcp-option=${dhcp_option} \
    --dhcp-option-force=${dhcp_option_force} \
    --dhcp-script=${dhcp_script}
fi
exit 0
