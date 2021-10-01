# jenkins
Custom jenkins images with docker-compose,docker and all the plugins pre-installed

## Support on Beerpay
Hey dude! Help me out for a couple of :beers:!

[![Beerpay](https://beerpay.io/rubiin/jenkins/badge.svg?style=beer-square)](https://beerpay.io/rubiin/jenkins)  [![Beerpay](https://beerpay.io/rubiin/jenkins/make-wish.svg?style=flat-square)](https://beerpay.io/rubiin/jenkins?focus=wish)

## Remarks:

- jenkins can restarted without container being killed

## Usage:

```
{{task usage}}
```

## Spin up Tutorial

http://localhost:18808/script
http://localhost:18808/manage


```yaml
cat <<EOF > __build_info/docker-build/jenconda/{{.fulltag}}
kind: docker.build.info
version: "3"
vars:
  schema: |-
    dict: str
  spec: |-
    build_id: fdasdf
    git_src: url@commit
    docker_dst: repo:tag
includes:
  docker-info: ../../_infra/
EOF
```
