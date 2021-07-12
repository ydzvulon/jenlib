node {
    stage('start'){
        sh 'echo START'
    }

    stage('body'){
        sh 'find -name "*.groovy"'
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
