#! /bin/bash
if [[ "$(uname)" == "Darwin" ]]; then
    FLAVOUR=darwin-amd64
else
    FLAVOUR=linux-amd64
fi

pushd /tmp
    rm -rf mc
    wget https://dl.minio.io/client/mc/release/${FLAVOUR}/mc
    chmod +x mc
    mv mc /bin/
    rm -rf mc
popd
