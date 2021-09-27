
FROM jenmaster

USER root
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
RUN chown jenkins:jenkins -R /root/miniconda3
# ENV CONDA_DEFAULT_ENV 
USER jenkins
ENV PATH="/root/miniconda3/bin:${PATH}"
