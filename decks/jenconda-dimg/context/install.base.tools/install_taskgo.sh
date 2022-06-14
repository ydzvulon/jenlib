#! /bin/bash

TASKGO_VERSION=v3.0.0
TASKGO_ZIPNAME=task_linux_amd64.tar.gz

echo install task executable
pushd /tmp
    rm -rf task_linux_amd64.tar.gz
    curl -L https://github.com/go-task/task/releases/download/${TASKGO_VERSION}/${TASKGO_ZIPNAME}  -o ${TASKGO_ZIPNAME}
    tar xzvf ${TASKGO_ZIPNAME}
    mv task /bin/
    rm -rf task_linux_amd64.tar.gz
popd
