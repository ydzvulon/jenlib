#!/usr/bin/env groovy

//  jen.groovy

def set_build_name(_currentBuild, scmvars, more){
    // set build name from scmvars
    _currentBuild.displayName = _currentBuild.displayName + "-" + [
        scmvars.GIT_BRANCH,
        scmvars.GIT_COMMIT.substring(0, 7),
        more].join('-#')
}


def add_stages(jg, filename, prefix){
    // echo "Load tasks from ${filename}"
    // filename='Taskfile.yml'
    def taskfile = readYaml file: filename
    for ( e in taskfile['tasks'] ) {
        jg.tstages[prefix + e.key] = e.value
        // echo "adding ${prefix}${e.key}"
    }
    echo "Loaded tasks from ${filename}"
    return taskfile
}

def step_stages_from_tasks(jg, wd, filename, root_job){
    // create piple stages from task commands
    jg.tstages  = jg.tstages ?: [:]
    def taskfile = add_stages(jg, filename, '')
    echo 'create stages from tasks'
    stage_names = []
    jg.cmds = taskfile['tasks'][root_job]['cmds']
    for (int i = 0; i < jg.cmds.size(); i++) {
        def _cmd = jg.cmds[i]
        def name = _cmd
        def org_cmd = _cmd
        def deps = []
        def dp_cmds = []
        def task_dir = '.'
        // echo "FFF ${_cmd}"
        // assume task:taskname syntax
        try {
            def try_some_name = _cmd['task']
            // cmd = "task ${name}"
            def content = taskfile['tasks'][name]
            if (content.deps){
                task_dir = content.dir ?: '.'
                deps = content.deps
                dp_cmds = content.cmds
            }
        }
        //  try task taskname syntax
        catch(Exception e) {
            try {
                def arr = _cmd.split()
                if (arr[0]=='task' && arr.size()==2){
                    name = arr[1]
                    def content = taskfile['tasks'][name]
                    if (content.deps){
                        deps = content.deps
                        dp_cmds = content.cmds
                    }
                }
            }
            catch(Exception e2) {

            }
        }

        if (deps) {
            jinEchoMark("creating parallel part from ${org_cmd} => ${deps}")
            // create parallel stages from cmds in deps task if exists
            stage("$name:deps"){
                dir(wd){
                    def actors = jinMakeParallel(deps, {dname -> sh "task $dname"})
                    parallel actors
                }
            }
            // create stage from cmds in deps task if exists
            if (dp_cmds){
                jinEchoMark("creating sequential part from ${org_cmd}")
                stage("$name:cmds"){
                    dir(wd){
                    dir(task_dir){
                        for (int j = 0; j < dp_cmds.size(); j++) {
                            def s_cmd = dp_cmds[j]
                            sh s_cmd
                        }
                    }}
                }
            }
        } else {
            jinEchoMark("creating original from ${org_cmd}")
            stage(name){
                dir(wd){
                    sh org_cmd
                }
            }
        }
    }
}

def desc_from_commits(currentBuild, jg){
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                echo "${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg}"
                jg.commits.add("${entry.author}: ${entry.msg}".toString())

                def files = new ArrayList(entry.affectedFiles)
                for (int k = 0; k < files.size(); k++) {
                def file = files[k]
                echo "  ${file.editType.name} ${file.path}"
                }
        }
    }
    currentBuild.description = jg.commits.join(', ')
}
