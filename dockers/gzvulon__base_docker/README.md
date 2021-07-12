# DTask warpper

If we want to use task in thin docker oriented os
we should wrap this tool as a docker file
as docker-compose was warppen onec

## Wrapper

Heavily based on [Conda Docker](https://towardsdatascience.com/conda-pip-and-docker-ftw-d64fe638dc45) Article


## Tricks

### Running docker build from stdin
```bash
docker build -<<EOF
FROM busybox
RUN echo "hello world"
EOF
```

### Writing to files with cat
```bash
cat <<EOF > print.sh
#!/bin/bash
echo \$PWD
echo $PWD
EOF
```