FROM continuumio/miniconda3
RUN apt update -y && apt install -y curl git
# https://taskfile.dev/#/installation?id=get-the-binary
RUN sh -c "$(curl --location https://taskfile.dev/install.sh)" -- -d -b /usr/local/bin
COPY deps.setup.conda-env.exe.yml /deps/deps.setup.conda-env.exe.yml
RUN task install -t /deps/deps.setup.conda-env.exe.yml
ENV PATH /opt/conda/envs/local-docs-env/bin:$PATH
RUN bash -c "source activate local-docs-env"
ENV CONDA_DEFAULT_ENV local-docs-env
RUN mkdocs --version
