# Get Started

## Build And Test

```bash
# clone this repo from github
git clone https://github.com/ydzvulon/jenlib.git
# set working dir to jenlib
cd jenlib

# build Jenkins Docker Image
task -d dockers/jenkins-master docker:build:jenconda

# run tests
task e2e:ci-flow
```

