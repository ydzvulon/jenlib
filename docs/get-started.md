# Get Started

## Build And Test

```bash
# clone this repo from github
git clone https://github.com/ydzvulon/jenlib.git
# set working dir to jenlib
cd jenlib

# build Jenkins Docker Image
task -d decks/jenconda-dimg docker:build

# run build and tests
task ci-flow
```

