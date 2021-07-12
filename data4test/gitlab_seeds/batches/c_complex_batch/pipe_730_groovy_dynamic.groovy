def kwj = [:]

def LBUILD_DIR='__localbuild__/sambuild'
def LART_NAME='art.tar'

node {
    stage('start'){
        sh 'echo START'
        kwj.scmvars = checkout scm
    }

    stage('finis'){
        sh 'echo FINSH'
        def res = evaluate 'def test() { "eval EVALTEST" }; return test()'
        echo res
    }
    evaluate """
    stage('ini'){
        sh 'echo INI'
        def res = evaluate 'def test() { "eval INCEPTION" }; return test()'
        echo res
    }
    """
}
