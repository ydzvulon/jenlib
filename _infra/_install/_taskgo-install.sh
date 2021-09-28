#! /bin/bash
set -x
set -e

TASKGO_VERSION=3.0.0
TASKGO_ZIPNAME=task_linux_amd64.tar.gz

echo install task executable
pushd /tmp
    rm -rf task_linux_amd64.tar.gz
    wget https://github.com/go-task/task/releases/download/v${TASKGO_VERSION}/${TASKGO_ZIPNAME}
    tar xzvf ${TASKGO_ZIPNAME}
    mv task /bin/
    rm -rf task_linux_amd64.tar.gz
popd

# --- testing --
cd /tmp 
task --version | grep ${TASKGO_VERSION}
# --- /testing

echo "task go installed succeffully"
