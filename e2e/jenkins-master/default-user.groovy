import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement


def workspace = new File('.')

def jobManagement = new JenkinsJobManagement(System.out, [:], workspace)
def jobDslScript_text = '''
pipeline
    params: scm dir jenkinsfile
'''
new DslScriptLoader(jobManagement).runScript(jobDslScript_text)
