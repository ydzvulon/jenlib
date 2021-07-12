# Data Layout

```bash
run_id/
clip1/0001/Wheels/
                - p1
                - p2


*/*/Wheels/

*/*/Wheels/


clip1/*/*
- unifyed struct top-level

tech/clip/frame

read()


partition per clip
ordered by frame
https://github.com/argoproj/argo-workflows/blob/master/examples/daemon-step.yaml
```