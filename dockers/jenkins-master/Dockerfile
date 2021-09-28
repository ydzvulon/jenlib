FROM jenkins/jenkins:2.272

ENV JENKINS_USER admin
ENV JENKINS_PASS admin

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

USER root
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

# added ny yairvd
COPY install.scripts/install_taskgo.sh install.scripts/install_taskgo.sh
RUN chmod +x install.scripts/install_taskgo.sh
RUN  install.scripts/install_taskgo.sh

# or 3.4.1
RUN cd /tmp \
    && wget https://github.com/mikefarah/yq/releases/download/v4.2.0/yq_linux_amd64 \
    && mv yq_linux_amd64 /usr/bin/yq

RUN cd /tmp \
    && wget https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64 \
    && mv jq-linux64 /usr/bin/jq

USER jenkins

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/plugins.txt

# COPY jenkins.yaml /usr/share/jenkins/jenkins.yaml
# COPY jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml
# COPY jenkins.yaml /usr/share/stuff/jenkins.yaml


# COPY plugins_manual.txt /usr/share/jenkins/ref/plugins_manual.txt

# ENV JENKINS_UC_DOWNLOAD=${JENKINS_UC_EXPERIMENTAL}/download
# RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins_manual.txt



COPY default-newci-job.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY jobs_sys /usr/share/jenkins/job_sys
# COPY custom.groovy /usr/share/jenkins/ref/init.groovy.d/custom.groovy

# https://medium.com/the-devops-ship/custom-jenkins-dockerfile-jenkins-docker-image-with-pre-installed-plugins-default-admin-user-d0107b582577
