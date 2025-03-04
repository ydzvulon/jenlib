# About Jenlib

Jenlib is a bridge to top-level automation

> Develop on [![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/from-referrer/) 

## Content

Get Started With Local Development

```
# minimalistic run with ui
docker run -d -p 8080:8080 yairdar/jenconda:0.8.4-1633116582 

docker run -d yairdar/jenconda:0.8.4-1633116582 


```

### Links

- [see more live](./links.html)
- [see more stale](./docs/links.md)

## Layout

- `src` - jenkins shared lib 'jenlib'
- `jenpy` (jenlib) - python cli for jenkins, jenlib, gitlab, s3/http access

## Command Groups

This repo is multi product repo
Each product have it's own taskfile
Taskfiles are copied to tasks dir from different ci-libs
Each taskfile express one group

> Available Groups

- `jenlib` - jenkins shared lib tasks
- `jenconda` - jenkins with conda docker build
- `mkdocs`  - documentation
- `deps` - dependancy resolution


## More Distributions

- https://github.com/odavid/my-bloody-jenkins
- https://hub.docker.com/r/eeacms/jenkins-slave
- https://github.com/ydtools/eea.docker.jenkins.slave-dind
- https://github.com/arminc/clair-scanner

## Security

- https://github.com/arminc/clair-scanner
