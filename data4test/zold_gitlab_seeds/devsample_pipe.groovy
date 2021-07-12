@Library(value="Jenkins_Jenlib",changelog=false)

// pipeline {
//   agent any
//   stages {
//     stage('Source') {
//       steps {
//         sh 'ls'
//         sh 'pwd'
//       }
//     }
//     stage('MULTI') {
//       steps {
//         jen.step_stages_from_tasks(
//             kwj, '.' ,'Taskfile.yml', 'ci-flow'
//         )
//       }
//     }
//   }
// }

def kwj = [
    'scmvars': null,
    'task_parse_result': [:],
]

node {

    stage('start'){
        sh 'echo START'
        kwj.scmvars = checkout scm
        sh 'task --version'
        sh 'task -l'

    }

    dir('data4test/pipe_samples'){
        stage('test'){
            sh 'task start'
        }
        jen.step_stages_from_tasks(
            kwj, '.' ,'Taskfile.yml', 'ci-flow'
        )
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
