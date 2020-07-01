def pipe_text = '''
node{
    stage('info'){
        echo 'hello'
    }
}
'''

def def_text = '''
pipelineJob('example') {
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

def jp = [
    text: env.text ?: def_text
]

node(){
    stage('info'){
        sh '''#!/bin/bash
        pwd
        ls
        df -h
        '''
    }
    stage('feedback'){
        print jp.text
    }
    stage('job-creation'){
        jobDsl scriptText: jp.text
    }
}