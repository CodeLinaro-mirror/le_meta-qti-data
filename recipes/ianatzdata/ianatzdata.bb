require ianatimezone.inc

DEPENDS = "ianatzdata-native"

S = "${WORKDIR}"

TZONES= "africa antarctica asia australasia europe northamerica southamerica \
         factory etcetera backward systemv"

do_compile () {
  # Compile timezones using zic
  for zone in ${TZONES}; do \
    ${STAGING_BINDIR_NATIVE}/zic -d ${WORKDIR}${datadir}/zoneinfo -L /dev/null \
      -y ${S}/yearistype.sh ${S}/${zone} ; \
  done

  # Create an iana database file with timezones and respective posix code
  pushd ${WORKDIR}${datadir}/zoneinfo
  timezones=$(find . -type f \( ! -iname "*.list" ! -iname "*.tab" \));
  delim=":"
  for timezone in $timezones; do \
    if ! ( [[ $timezone == *"posix"* ]] || [[ $timezone == *"right"* ]] ); then
      pcode=$(tail -1 $timezone);
      echo "${timezone:2}${delim}[$pcode]" >> ${WORKDIR}/iana_tzdb;
    fi
  done;
  popd
}

