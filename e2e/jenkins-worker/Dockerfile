FROM continuumio/conda-ci-linux-64-python3.7

USER root

ENV JENKINS_USER test_user
ENV JENKINS_PASS test_user

USER root
RUN curl -sL https://taskfile.dev/install.sh | sh
RUN chmod +x ./bin/task && mv ./bin/task /usr/bin

RUN apt-get update \
    && apt-get install -qqy apt-transport-https ca-certificates curl gnupg2 software-properties-common
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"
RUN apt-get update  -qq \
    && apt-get install docker-ce -y
RUN usermod -aG docker jenkins
RUN apt-get clean
RUN curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose

RUN /opt/conda/bin/conda install -c anaconda openjdk  -y

RUN   - mkdir -p /tmp
      - cd /tmp && wget https://repo.jenkins-ci.org/releases/org/jenkins-ci/plugins/swarm-client/3.23/swarm-client-3.23.jar
      - mkdir -p /data/tools/
      - mv /tmp/swarm-client-3.23.jar /data/tools/