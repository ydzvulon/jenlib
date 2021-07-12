FROM ubuntu:18.04


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
