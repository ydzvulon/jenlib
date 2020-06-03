FROM ubuntu:18.04


# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-python23][os=ubuntu][act=start]
# | xargs -P12 -I {} \

# ENV PY_VERSIONS "2.7 3.4 3.5 3.6 3.7"
ENV PY_VERSIONS "2.7 3.6"
RUN echo ${PY_VERSIONS} \
    | xargs -n 1 \
    | xargs -I {} \
    conda create -y --name py{} python={} && echo "OK"

# RUN conda init bash

# --- @@[kind=dockerfile.part][name=install-python23][os=ubuntu][act=end]
