# Jobs
- <https://devops.datenkollektiv.de/from-plain-groovy-to-jenkins-job-dsl-a-quantum-jump.html>

## Docker file example

```docker
FROM jenkinsci/jenkins:2.61

....
ADD jobs.groovy /var/jenkins_home/

RUN /usr/local/bin/install-plugins.sh job-dsl:1.63 git:3.3.0 workflow-aggregator:2.5

COPY create-initial-jobs-with-dsl.groovy /var/jenkins_home/init.groovy.d/
```

```groovy
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement

def jobDslScript = new File('/var/jenkins_home/jobs.groovy')
def workspace = new File('.')

def jobManagement = new JenkinsJobManagement(System.out, [:], workspace)

new DslScriptLoader(jobManagement).runScript(jobDslScript.text)
```

```groovy
pipelineJob('planets-homepage') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            credentials('planets-homepage_id_rsa')
            url('git@github.com:datenkollektiv/planets-homepage.git')
          }
        }
      }
      scriptPath('Jenkinsfile')
    }
  }
  scm {
    git {
      remote {
        credentials('planets-homepage_id_rsa')
        url('git@github.com:datenkollektiv/planets-homepage.git')
      }
    }
  }
  triggers {
      cron('@midnight')
  }
}
```