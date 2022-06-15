FROM jupyter/pyspark-notebook
# ARG BASE_CONTAINER=jupyter/pyspark-notebook
# FROM $BASE_CONTAINER
# Install task rclone yq jq
# https://taskfile.dev/#/installation
USER root
RUN apt update && apt install -y curl
RUN sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d -b /usr/local/bin
RUN echo "${NB_USER}   ALL=(ALL:ALL) NOPASSWD:ALL" >> /etc/sudoers

USER $NB_USER
ENV IN_DOCKER_BUILD 1
# __dbuild_install
# ------@@init:stage=prephook section=task.docker.cmds task=mxsh:install:zsh:kubeflow
COPY deps/tasks.vscode.deps.yml deps/tasks.vscode.deps.yml
RUN task -t deps/tasks.vscode.deps.yml install

# Install jupyter proxies for VS Code and RStudio
RUN pip install --no-cache-dir jupyter-server-proxy && pip install --no-cache-dir jupyter-vscode-proxy

RUN jupyter labextension install @jupyterlab/server-proxy
# ------@@over:stage=prephook section=task.docker.cmds task=mxsh:install:zsh:kubeflow
ENV PATH /home/${NB_USER}/.asdf/bin:/home/${NB_USER}/.asdf/shims:$PATH
RUN /bin/bash -c "git clone https://github.com/asdf-vm/asdf.git ~/.asdf --branch v0.8.0 &&                   asdf plugin-add groovy https://github.com/weibemoura/asdf-groovy.git &&                   asdf install groovy 3.0.7 &&                   asdf global groovy 3.0.7"
RUN     echo -e '\n. $HOME/.asdf/asdf.sh' >> ~/.bashrc     && echo -e '\n. $HOME/.asdf/completions/asdf.bash' >> ~/.bashrc  
    
COPY deps/pip.deps.list.txt deps/pip.deps.list.txt
RUN pip install -r deps/pip.deps.list.txt
RUN pip freeze | grep waiting
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64

EXPOSE 8888

WORKDIR /home/ubuntu
# mount point for kubeflow
VOLUME /home/jovyan

# Configure container startup
ENV NB_PREFIX /
CMD ["sh","-c", "jupyter lab --notebook-dir=/home/jovyan --ip=0.0.0.0 --no-browser --allow-root --port=8888 --NotebookApp.token='' --NotebookApp.password='' --NotebookApp.allow_origin='*' --NotebookApp.base_url="]
