
@Library('JenkinsLib_jenlib') _

// library identifier: 'jenlib@0.7.5', retriever: http(
// credentialsId: 'asd',
// httpURL: 'http://httpit:8043/__localbuild__/jenbuild/jenlib.zip')

def kwj = [
    'scmvars': null,
    'task_parse_result': [:],
]

node {

    stage('start'){
        sh 'echo START'
        kwj.scmvars = checkout scm
    }

    dir('data4test/gitlab_seeds/batches/c_complex_batch'){
        jen.step_stages_from_tasks(
            kwj, '.' ,'Taskfile.yml', 'ci-flow'
        )
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
