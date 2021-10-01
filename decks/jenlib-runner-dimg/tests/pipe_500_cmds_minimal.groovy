node {
    stage('start'){
        sh 'echo START'
    }

    stage('body'){
        sh 'find -name "*.groovy"'
        sh 'curl http://localhost:8080/userContent/readme.txt'
        sh 'git -C /usr/share/shared_libs/jenlibrepo status'
        // sh 'curl http://localhost:8080/userContent/jenkins.yaml'
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
