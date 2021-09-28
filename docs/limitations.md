# Limitations

Not all taskfiles suported by jenlib

## Restrictions

> ci-flow

- supported:
  - clean task call
    - `task TASK_NAME`
    - `task: TASK_NAME`

- unsupported:
  - no variable are allowed
    - `task TASK_NAME VAR=val`
    - `task: TASK_NAME, vars: VAR:val`
