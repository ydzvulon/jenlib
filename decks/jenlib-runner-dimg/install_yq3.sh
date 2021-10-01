#! /bin/bash

version="3.0.1"
url=https://github.com/mikefarah/yq/releases/download/${version}/yq_linux_amd64

echo install yq3 executable
pushd /tmp
    rm -rf yq_linux_amd64
    wget ${url}
    chmod +x yq_linux_amd64
    mv yq_linux_amd64 /bin/yq
    rm -rf yq_linux_amd64
popd
