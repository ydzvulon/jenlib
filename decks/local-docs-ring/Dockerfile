FROM jenconda
COPY deps.setup.conda-env.exe.yml /deps/deps.setup.conda-env.exe.yml
RUN task install -t /deps/deps.setup.conda-env.exe.yml
RUN mkdocs --version
