def kwj = [
    'scmvars': null,
]

node {
    stage('BigStage1'){
        sh 'echo ok'
    }
    stage('BigStage2'){
        parallel (
            'cfg1': {
                stage('fok1'){
                    sh 'echo fok1'
                }
                stage('fok2'){
                    sh 'echo fok2'
                }
            },
            'cfg2': {
                stage('h'){
                    echo 'NN'
                }
                stage('s'){
                    echo 'NS'
                    // parallel (
                    //     'g': {
                    //             sh 'echo gsfok1'
                    //     },
                    //     'k':{
                    //             sh 'echo skfok2'
                    //     }
                    // )
                }
            },
            'cfg3': {
                stage('h234234'){
                    echo 'NN'
                }
                stage('s23423'){
                    echo 'NS'
                    // parallel (
                    //     'g': {
                    //             sh 'echo gsfok1'
                    //     },
                    //     'k':{
                    //             sh 'echo skfok2'
                    //     }
                    // )
                }
            }
        )
    }
}