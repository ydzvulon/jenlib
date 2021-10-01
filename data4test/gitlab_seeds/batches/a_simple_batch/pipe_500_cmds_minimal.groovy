node {
    stage('start'){
        sh 'echo START'
    }

    stage('body'){
        sh 'find -name "*.groovy"'
        sh 'curl http://localhost:8080/userContent/readme.txt'
        sh 'curl http://localhost:8080/userContent/readme2.txt'
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
