# Jen Groovy - Jenkins Task Execution Library

[Jen](./jen.groovy) implemented as a step for scripted pipelines,
allowing to run Taskfile as it were native jenkins stage

## Usage

Include in your scripted pipeline next block:

```groovy
    catchError {
        dir('tutorials/pyprjs/01-app-pydeps/repo_pydeps'){
            jen.step_stages_from_tasks(jg, '.', 'Taskfile.yml', 'ci-build')
        }
    }
```

## Stage (In Place) Generation From TaskFile

It will create stage from each command in list of task ci-build in Taskfile:

```yaml
  ci-build:
    desc: build as ci cd
    cmds:
      - task venv-install
      - task test-ci
      - task run
```

Generated stages will be something like:

```groovy
  stage('task venv-install'){
      sh 'task venv-install'
  }
  stage('task test-ci'){
      sh 'task test-ci'
  }
  stage('task run'){
      sh 'task run'
  }
```

## Roadmap

> [JEN-01](.#) create parallel stages execution for deps calculation

- not for root tasks (why? better not now)
- only for stage task (not for steps)
- dependencies will be calculated using parallel jenkins execution
    - on same node?
    - different node?
    - docker agent?
    - aggegation stage will include steps as commands (after [JEN-02])

> [JEN-02](.#) break each stage commands to steps 

- in root task each command is a stage in jenkins
- currently with one step
- however if stage is task call it can be broken to steps
- each step will reperesent a command from a stage-task