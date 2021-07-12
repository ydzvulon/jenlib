def kwj = [:]

node {
    stage('start'){
        sh 'echo START'
        kwj.scmvars = checkout scm
    }
    dir('data4test/gitlab_seeds/batches/a_simple_batch'){
        stage('task prepare'){
            sh 'task prepare'
        }

        stage('task build'){
            sh 'task build'
        }

        stage('task test'){
            sh 'task test'
        }
    }
    stage('finis'){
        sh 'echo FINSH'
    }
}
