@Library(value="Jenkins_Jenlib@",changelog=false) _

pipeline {
    agent any
    stages {
        stage('Example Build') {
            steps {
                echo 'Hello, Maven'
            }
        }
        // stage('Example Test') {
        //     steps {
        //       jen.step_stages_from_tasks(
        //           kwj, '.' ,'Taskfile.yml', 'ci-flow'
        //       )
        //     }
        // }
    }
}