#!/bin/bash

MYHOST__MYPORT=$1

attempt_counter=0
max_attempts=${max_attempts:-5}
timeout_s=${timeout_s:-5}

until $(curl --output /dev/null --silent --head --fail http://${MYHOST__MYPORT}); do
    if [ ${attempt_counter} -eq ${max_attempts} ];then
      echo "Max attempts reached"
      echo "Failed to reach ${MYHOST__MYPORT} after ${max_attempts}"
      exit 1
    fi

    echo "... no response. will retry in ${timeout_s} sec."
    attempt_counter=$(($attempt_counter+1))
    sleep $timeout_s
done
echo "http://${MYHOST__MYPORT} is accessable"