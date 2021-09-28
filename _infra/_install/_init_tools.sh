#! /bin/bash
set -x
set -e

_SUDO=$(which sudo &> /dev/null && echo "sudo" || echo "")

$_SUDO apt-get update -y \
&& $_SUDO apt-get install -y \
    wget curl tar unzip git tig vim tmux

if [[ "$IN_DOCKER" != "" ]]; then
    $_SUDO apt-get clean 
    $_SUDO rm -rf /var/lib/apt/lists/*
fi

echo '
pack: yd-init-tools
installed:
  transfer: [wget, curl, git]
  archive: [tar, unzip]
  interface: [vim, tmux, tig]
'

if [[ "$1" == "test-all" ]]; then
    mkdir /tmp/test-all
    cd /tmp/test-all
fi