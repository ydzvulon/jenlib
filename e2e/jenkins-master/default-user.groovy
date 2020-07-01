import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement


def workspace = new File('.')
def jobManagement = new JenkinsJobManagement(System.out, [:], workspace)

def jobDslScript = new File('/usr/share/jenkins/job_sys/_new_ci/_new_ci.jdsl.groovy')

def jobDslScript_text = jobDslScript.text

new DslScriptLoader(jobManagement).runScript(jobDslScript_text)
