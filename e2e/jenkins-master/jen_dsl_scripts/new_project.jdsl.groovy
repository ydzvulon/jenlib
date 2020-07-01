pipelineJob("new_project") {
	description()
	keepDependencies(false)
	parameters {
		stringParam("region", "regionTESTING", "The target environment")
	}
	definition {
		cpsScm {
"""
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
        sh "echo \${env.region}"
    }
    stage('job-creation'){
        jobDsl scriptText: '''
pipelineJob('notexample') {
    definition {
        cps {
            def pipeline_script=\"\"\"''' + pipe_text +
            '''\"\"\"
            script(pipeline_script)
            sandbox()
        }
    }
}
'''
    }
}"""		}
	}
	disabled(false)
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}
