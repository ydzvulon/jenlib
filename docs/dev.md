# Dev Information

## Misc

### Shared libs

- <https://vzurczak.wordpress.com/2020/04/17/combining-jenkins-job-dsl-and-shared-libraries-for-docker-images-p
- https://github.com/zhaoterryy/mkdocs-pdf-export-plugin

### docs

- <https://pypi.org/project/mkdocs-drawio-exporter/>

```bash
▶ cat ../spec-file.txt | grep -v '#' | grep -v '@' | sed -e 's/http....//g' | xargs -I{}  bash -c 'mkdir -p $(dirname {}) && wget http://{} -o {}'
```