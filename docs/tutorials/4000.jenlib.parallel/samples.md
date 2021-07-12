# samples

## Groovy to task

> compare:stage.get-submodules:groovy

```groovy
stage("get-submodules") {
    steps {
        script {
        dir("pack-dir") {
            sh "git submodule update --recursive --init --jobs 16"
            sh "du -sh ."
            dir ("the-dir") {
                if (env.GITLAB_OBJECT_KIND == 'push') {
                    echo "it's from push"
                    sh "git checkout ${BRANCH_NAME}"
                    // sh "git clean -fdx && git reset --hard && git pull"
                } else {
                    echo "it's from MR"
                    sh "rsync -zrvh ${env.WORKSPACE}/subdir/* ."
                }
              }
            }
        }
    }
}
```

> compare:stage.get-submodules:task.yml

```yaml
get-submodules:
  dir: pack-dir
  cmds:
    - git submodule update --recursive --init --jobs 16
    - du -sh .
    - |
      pushd the-dir
        if [[ "${GITLAB_OBJECT_KIND}" == 'push' ]]; then
          echo "it's from push"
          git checkout ${BRANCH_NAME}
          # git clean -fdx && git reset --hard && git pull
        else
          echo "it's from MR"
          rsync -zrvh ${env.WORKSPACE}/subdir/* .
        fi
      popd
```