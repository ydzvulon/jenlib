#!/bin/bash --login

set -e
conda activate $PYVER
exec "$@"
