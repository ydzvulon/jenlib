
# autoremove FROM statement
FROM ubuntu:18.04

# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=start]
USER root
RUN curl -sL https://taskfile.dev/install.sh | sh && mv ./bin/task /usr/local/bin/
USER $USER
# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=stop]
