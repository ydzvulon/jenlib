FROM ubuntu:18.04
# keep 4 lines

# --- @@[kind=dockerfile.part][name=setup-default-shell][os=ubuntu][act=start]
SHELL [ "/bin/bash", "--login", "-c" ]
# --- @@[kind=dockerfile.part][name=setup-default-shell][os=ubuntu][act=end]
# keep 4 lines

# --- @@[kind=dockerfile.part][name=Create a non-root user][os=ubuntu][act=start]
# Create a non-root user
ARG username=pyconda
ARG uid=1000
ARG gid=100
ENV USER $username
ENV UID $uid
ENV GID $gid
ENV HOME /home/$USER
RUN adduser --disabled-password \
    --gecos "Non-root user" \
    --uid $UID \
    --gid $GID \
    --home $HOME \
    $USER
# --- @@[kind=dockerfile.part][name=Create a non-root user][os=ubuntu][act=end]
# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-docker-client][os=ubuntu][act=start]
RUN apt-get update \
    && apt-get install -qqy apt-transport-https ca-certificates curl gnupg2 software-properties-common \
    sudo git curl wget
RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
RUN apt-get update  -qq \
    && apt-get install docker-ce -y

RUN usermod -aG docker root
# use non-rootuser
RUN usermod -aG docker $USER

RUN apt-get clean
RUN curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose
# --- @@[kind=dockerfile.part][name=install-docker-client][os=ubuntu][act=stop]


# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-miniconda][os=ubuntu][act=start]
# install basics
RUN apt-get update -y \
 && apt-get install -y apt-utils git curl ca-certificates bzip2 cmake tree htop bmon iotop g++ \
 && apt-get install -y libglib2.0-0 libsm6 libxext6 libxrender-dev

RUN apt-get update

RUN apt-get install -y wget && rm -rf /var/lib/apt/lists/*

USER $USER
# install miniconda
# ENV MINICONDA_VERSION 4.8.2
ENV CONDA_DIR $HOME/miniconda3

RUN cd /tmp && wget \
    https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh \
    && bash Miniconda3-latest-Linux-x86_64.sh -b -p $CONDA_DIR \
    && rm -f Miniconda3-latest-Linux-x86_64.sh

# RUN wget --quiet https://repo.anaconda.com/miniconda/Miniconda3-$MINICONDA_VERSION-Linux-x86_64.sh -O ~/miniconda.sh && \
#     chmod +x ~/miniconda.sh && \
#     ~/miniconda.sh -b -p $CONDA_DIR && \
#     rm ~/miniconda.sh

# make non-activate conda commands available
ENV PATH=$CONDA_DIR/bin:$PATH
RUN conda --version

# make conda activate command available from /bin/bash --login shells
RUN echo ". $CONDA_DIR/etc/profile.d/conda.sh" >> ~/.profile
# make conda activate command available from /bin/bash --interative shells
RUN conda init bash
# --- @@[kind=dockerfile.part][name=install-miniconda][os=ubuntu][act=stop]

# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=start]
USER root
RUN curl -sL https://taskfile.dev/install.sh | sh && mv ./bin/task /usr/local/bin/
USER $USER
# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=stop]
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
# keep 4 lines

# --- @@[kind=dockerfile.part][name=default-build-cmd][os=ubuntu][act=start]
# #!/bin/bash
# @@@build# docker build -f auto.Dockerfile -t dtaskimg .
# --- @@[kind=dockerfile.part][name=default-build-cmd][os=ubuntu][act=end]

# --- @@[kind=dockerfile.part][name=set-entrypoint-pyenv-arg][os=ubuntu][act=start]
USER root
COPY python_23_conda__env_arg.part.run.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
USER $USER
RUN echo USER=$USER
ENTRYPOINT [ "/entrypoint.sh" ]

# default command will launch JupyterLab server for development
CMD [ "jupyter", "lab", "--no-browser", "--ip", "0.0.0.0" ]

# --- @@[kind=dockerfile.part][name=set-entrypoint-pyenv-arg][os=ubuntu][act=end]
