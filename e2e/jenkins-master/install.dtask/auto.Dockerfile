FROM ubuntu:18.04
# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-docker-client][os=ubuntu][act=start]
RUN apt-get update \
    && apt-get install -qqy apt-transport-https ca-certificates curl gnupg2 software-properties-common
RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
RUN apt-get update  -qq \
    && apt-get install docker-ce -y
RUN usermod -aG docker root
RUN apt-get clean
RUN curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose
# --- @@[kind=dockerfile.part][name=install-docker-client][os=ubuntu][act=stop]


# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=start]
RUN curl -sL https://taskfile.dev/install.sh | sh && mv ./bin/task /usr/local/bin/
# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=stop]

# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-miniconda][os=ubuntu][act=start]
# install basics
RUN apt-get update -y \
 && apt-get install -y apt-utils git curl ca-certificates bzip2 cmake tree htop bmon iotop g++ \
 && apt-get install -y libglib2.0-0 libsm6 libxext6 libxrender-dev

ENV PATH="/root/miniconda3/bin:${PATH}"
ARG PATH="/root/miniconda3/bin:${PATH}"
RUN apt-get update

RUN apt-get install -y wget && rm -rf /var/lib/apt/lists/*

RUN wget \
    https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh \
    && mkdir /root/.conda \
    && bash Miniconda3-latest-Linux-x86_64.sh -b \
    && rm -f Miniconda3-latest-Linux-x86_64.sh
RUN conda --version

# Create a Python 3.6 environment
RUN conda install -y conda-build \
 && conda create -y --name py36 python=3.6.10 \
 && conda clean -ya

# ENV CONDA_DEFAULT_ENV=py36
# ENV CONDA_PREFIX=/miniconda/envs/$CONDA_DEFAULT_ENV
# ENV PATH=$CONDA_PREFIX/bin:$PATH
# ENV CONDA_AUTO_UPDATE_CONDA=false

RUN conda install -y ipython

# --- @@[kind=dockerfile.part][name=install-miniconda][os=ubuntu][act=stop]
