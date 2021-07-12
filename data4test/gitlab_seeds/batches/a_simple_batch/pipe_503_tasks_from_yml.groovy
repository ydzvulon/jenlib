
library identifier: 'jenlib@0.7.5', retriever: http(
credentialsId: 'asd',
httpURL: 'http://moneytime.yairdar.com/buildx/jenlib/jenlib.zip')

def kwj = [
    'scmvars': null,
    'task_parse_result': [:],
]

node {

    stage('start'){
        sh 'echo START'
        kwj.scmvars = checkout scm
    }

    dir('data4test/gitlab_seeds/batches/a_simple_batch'){
        jen.step_stages_from_tasks(
            kwj, '.' ,'Taskfile.yml', 'ci-flow'
        )
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
