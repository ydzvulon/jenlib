

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
                stage('wewill'){
                    echo 'NN'
                }
                stage('wedo'){
                    echo 'NS'
                    parallel (
                        'wedo:honne': {
                            stage('wedo:hhhhh'){
                                sh 'echo gsfok1'
                            }
                            stage('wedo:bbbbbb'){
                                sh 'echo gsfok1'
                            }
                        },
                        'wedo:twiter':{
                            stage('wedo:ggaaaggg'){
                                sh 'echo skfok2'
                            }
                        }
                    )
                }
                stage('wedone'){
                    echo 'NN'
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
    stage('middle'){
        echo 'NN'
    }
    stage('more'){
        echo 'NS'
        parallel (
            'honne1': {
                stage('dsdfh12'){
                    sh 'echo gsfok1'
                }
                stage('dsfffj12'){
                    sh 'echo gsfok1'
                }
            },
            'twiter1':{
                stage('hjkhju12'){
                    sh 'echo skfok2'
                }
            }
        )
    }
    stage('report'){
        echo 'NN'
    }
}