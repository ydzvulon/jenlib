FROM jenkins/jenkins:2.237

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
RUN chmod +x install.scripts/install_taskgo.sh \
 && install.scripts/install_taskgo.sh

USER jenkins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt
COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/

# https://medium.com/the-devops-ship/custom-jenkins-dockerfile-jenkins-docker-image-with-pre-installed-plugins-default-admin-user-d0107b582577
