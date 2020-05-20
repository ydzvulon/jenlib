def set_build_name(_currentBuild, scmvars, more){
    // set build name from scmvars
    _currentBuild.displayName = _currentBuild.displayName + "-" + [
        scmvars.GIT_BRANCH,
        scmvars.GIT_COMMIT.substring(0, 7),
        more].join('#')
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
    def taskfile = add_stages(jg, filename, '')
    echo 'create stages from tasks'
    stage_names = []
    jg.cmds = taskfile['tasks'][root_job]['cmds']
    for (int i = 0; i < jg.cmds.size(); i++) {
        def _cmd = jg.cmds[i]
        def name = ""
        def cmd = ""
        def deps = []
        try {
            name = _cmd['task']
            cmd = "task ${name}"
            def content = taskfile['tasks'][name]
            if (content.deps){
                deps = content.deps
            }
        }
        catch(Exception e) {
            name = _cmd
            cmd = _cmd
        }
        if (deps) {
            stage("$name-deps"){
                dir(wd){
                    def actors = jinMakeParallel(deps, {dname -> sh "task $dname"})
                    parallel actors
                }
            }
            stage(name){
                dir(wd){
                    sh cmd
                }
            }
        } else {
            stage(name){
                dir(wd){
                    sh cmd
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
