# About Jenlib

Jenlib is a bridge to top-level automation

## Content

Get Started With Local Development

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
