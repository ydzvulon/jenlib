#!/bin/bash

$(cat auto.Dockerfile | grep @@@build | head -n 1 | cut -d'#' -f3)
