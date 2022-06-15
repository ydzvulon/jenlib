FROM continuumio/miniconda3
RUN apt update -y && apt install -y curl git
# https://taskfile.dev/#/installation?id=get-the-binary
RUN sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d -b /usr/local/bin
COPY deps.setup.conda-env.exe.yml /deps/deps.setup.conda-env.exe.yml
RUN task install -t /deps/deps.setup.conda-env.exe.yml CVENV_NAME=dev-docs-cenv
ENV PATH /opt/conda/envs/dev-docs-cenv/bin:$PATH
RUN bash -c "source activate dev-docs-cenv"
ENV CONDA_DEFAULT_ENV dev-docs-cenv
RUN mkdocs --version
COPY ctrl /ctrl
WORKDIR /ctrl

RUN which task | grep /usr/local/bin/task

ENTRYPOINT ["/usr/local/bin/task", "-d", "/ctrl"]
CMD ["-l"]
