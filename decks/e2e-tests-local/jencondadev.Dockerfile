ARG BASE_CONTAINER=jenconda
FROM $BASE_CONTAINER

# COPY deps/jenkins.yaml /usr/share/jenkins/jenkins.yaml
# COPY deps/jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml

# COPY deps/default-test-user.groovy /usr/share/jenkins/ref/init.groovy.d/


# RUN cd /tmp \
#     && curl -O http://127.0.0.1:8080/jnlpJars/jenkins-cli.jar \
#     && echo 'jenkins.model.Jenkins.instance.securityRealm.createAccount("user1", "password123")' \
#     | java -jar jenkins-cli.jar -s http://localhost:8080 groovy =
