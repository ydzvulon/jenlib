FROM jenkins/jenkins:2.332
# https://medium.com/the-devops-ship/custom-jenkins-dockerfile-jenkins-docker-image-with-pre-installed-plugins-default-admin-user-d0107b582577

ENV JENKINS_USER admin
ENV JENKINS_PASS admin

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false -Dhudson.plugins.git.GitSCM.ALLOW_LOCAL_CHECKOUT=true

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
COPY context/install.base.tools /context/install.base.tools
RUN chmod +x /context/install.base.tools/*.sh; chmod +x /context/install.base.tools/*.yml || true;
RUN  /context/install.base.tools/install_taskgo.sh

# or 3.4.1
RUN cd /tmp \
    && curl -L https://github.com/mikefarah/yq/releases/download/v4.2.0/yq_linux_amd64 -o yq_linux_amd64 \
    && mv yq_linux_amd64 /usr/bin/yq

RUN cd /tmp \
    && curl -L https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64 -o jq-linux64 \
    && mv jq-linux64 /usr/bin/jq

USER jenkins

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/plugins.txt

# COPY plugins_manual.txt /usr/share/jenkins/ref/plugins_manual.txt

# ENV JENKINS_UC_DOWNLOAD=${JENKINS_UC_EXPERIMENTAL}/download

# create new jobs
COPY context/init.groovy.d/default-newci-job.groovy /usr/share/jenkins/ref/init.groovy.d/

# disable security
COPY context/init.groovy.d/configure-job-dsl-security.groovy /usr/share/jenkins/ref/init.groovy.d/

# tell the jenkins config-as-code plugin where to find the yaml file
COPY context/jenkins-casc.yaml /usr/local/jenkins-casc.yaml
ENV CASC_JENKINS_CONFIG /usr/local/jenkins-casc.yaml

COPY context/jobs_sys /usr/share/jenkins/job_sys

