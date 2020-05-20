
# autoremove FROM statement
FROM ubuntu:18.04

# keep 4 lines

# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=start]
RUN curl -sL https://taskfile.dev/install.sh | sh
# --- @@[kind=dockerfile.part][name=install-dtask][os=ubuntu][act=stop]
