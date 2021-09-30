properties([
    parameters([
        string(name: 'region', defaultValue: 'regionTESTING', description: 'The target environment',
        )])
])

//parameters {
//    string(defaultValue: "TEST", description: 'What environment?', name: 'userFlag')
//    choice(choices: ['US-EAST-1', 'US-WEST-2'], description: 'What AWS region?', name: 'region')
//}

def pipe_text = '''
node{
    stage('info'){
        echo 'hello wor'
    }
}
'''

node(){
    stage('info'){
        sh '''#!/bin/bash
        pwd
        ls
        df -h
        '''
    }
    stage('feedback'){
        print pipe_text
        sh "echo ${env.region}"
    }
    stage('job-creation'){
        jobDsl scriptText: '''
pipelineJob('notexample') {
    definition {
        cps {
            def pipeline_script="""''' + pipe_text +
            '''"""
            script(pipeline_script)
            sandbox()
        }
    }
}
'''
    }
}