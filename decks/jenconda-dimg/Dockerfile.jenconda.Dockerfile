
FROM jenmaster:latest

ENV IN_DOCKER 1

USER root
ENV IN_DOCKER 1
RUN apt update && apt install -y git sudo curl wget zip unzip \
 && rm -rf /var/lib/apt/lists/*

# install basics
RUN apt-get update -y \
 && apt-get install -y apt-utils git curl ca-certificates bzip2 cmake tree htop bmon iotop g++ \
 && apt-get install -y libglib2.0-0 libsm6 libxext6 libxrender-dev \
 && rm -rf /var/lib/apt/lists/*

RUN curl -s https://packagecloud.io/install/repositories/github/git-lfs/script.deb.sh | bash \
    && apt-get update && apt-get install git-lfs

ENV PATH="/opt/jenkins_tools/miniconda3/bin:${PATH}"
RUN mkdir -p /opt/jenkins_tools && chown jenkins:jenkins -R /opt/jenkins_tools
RUN sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d -b /usr/local/bin
RUN echo 'jenkins ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && echo "REMOVE IT !!! "
USER jenkins

RUN git clone https://github.com/ydzvulon/setup-ubuntu-dev.git /opt/jenkins_tools/setup-ubuntu-dev
RUN /opt/jenkins_tools/setup-ubuntu-dev/vparts/install_os_base_tools.sh         \
    install-all \
    && ls /opt/jenkins_tools/setup-ubuntu-dev/vparts/ \
    && ls -alh /opt/jenkins_tools/setup-ubuntu-dev/vparts/install_yd_base_tools.tasks.yml
RUN chmod +x /opt/jenkins_tools/setup-ubuntu-dev/vparts/*.yml

RUN which task && task -t /opt/jenkins_tools/setup-ubuntu-dev/vparts/install_yd_base_tools.tasks.yml  \
    install-all
RUN task -t /opt/jenkins_tools/setup-ubuntu-dev/vparts/install_conda_mini.tasks.yml     \
    install-all \
    local_source=/tmp/miniconda.sh \
    target_dest=/opt/jenkins_tools/miniconda3

ENV PATH="/opt/jenkins_tools/miniconda3/bin:${PATH}"
RUN conda --version

# Create a default pythonenvironment
RUN conda install -y conda-build \
 && conda create -y --name jencenv python=3.9 ipython \
 && conda clean -ya

ENV CONDA_DEFAULT_ENV=jencenv
ENV CONDA_PREFIX=/opt/jenkins_tools/miniconda3/envs/$CONDA_DEFAULT_ENV
ENV PATH=$CONDA_PREFIX/bin:$PATH
ENV CONDA_AUTO_UPDATE_CONDA=false

