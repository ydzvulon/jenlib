def kwj = [:]

def LBUILD_DIR='__localbuild__/sambuild'
def LART_NAME='art.tar'

node {
    stage('start'){
        sh 'echo START'
        kwj.scmvars = checkout scm
    }

    dir('data4test/gitlab_seeds/batches/a_simple_batch'){
        stage('task prepare'){
            sh "rm -rf ${LBUILD_DIR}"
            sh "mkdir -p ${LBUILD_DIR}/bdir"
            sh "cp *.yml *.groovy ${LBUILD_DIR}/bdir"
        }

        stage('task build'){
            dir("${LBUILD_DIR}/bdir"){
                sh "tar -cvf ../${LART_NAME} ."
            }
        }

        stage('task test'){
            sh "tar -tvf ${LBUILD_DIR}/${LART_NAME}"
        }
    }

    stage('finis'){
        sh 'echo FINSH'
    }
}
