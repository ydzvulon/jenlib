// @Library("jenlib@master") _
// ===== /SHARED PART ====

// --- globas vars ---
def jg = [
    scmvars: null,
    cmds: [],
    tstages: [:],
    prj_dir: '.',
    commits: [],
    params: [
        samle: 'yes'
    ]
]

node() {timestamps {

    stage('fetch'){
        echo 'Fetch source'
        def scmvars = checkout scm
        def more = "repo_validate"
        jen.set_build_name(currentBuild, scmvars, more)
        jg['scmvars'] = scmvars
    }

    stage('commits'){
        jen.desc_from_commits(currentBuild, jg)
    }

    catchError { dir('e2e/data4test/parallel_tasks_jen'){
        jen.step_stages_from_tasks(
            jg, jg.prj_dir,
            'Taskfile.yml', 'ci-flow')
    }}

    stage('finish'){
        echo "done"
    }
}}
