job("_new_ci") {
	description()
	keepDependencies(false)
	parameters {
		stringParam("seed_job_name", "_seed_job", "seed_job_name")
		stringParam("seed_job_path", "file:///repo", "seed_job_path")
		stringParam("seed_job_pipe", "data4test/gitlab_seeds/jobs-registrator.pipe.groovy", "seed_job_pipe")
		stringParam("seed_job_branch", "master", "seed_job_branch")
		stringParam("jobs_def_dir", "data4test/gitlab_seeds/jobs-defs", "jobs_def_dir")
	}
	disabled(false)
	concurrentBuild(false)
	steps {
		dsl {
			text("""// def seed_job_name = '{{.JOB_NAME}}' // jenlib_pipe_master_ci
// def seed_job_path = '{{.seed_job_pathATH}}' // "file:///repo"
// def seed_job_pipe = '{{.JENKINS_FILE_PATH}}' // "data4test/gitlab_seeds/jobs-registrator.pipe.groovy"
// https://code-maven.com/jenkins-pipeline-environment-variables
// https://www.iditect.com/how-to/51308400.html

path_parts = "\${seed_job_name}".split('/')
println(path_parts)
nparts = path_parts.size()
if( nparts == 1 ){
	println("Should Provide Jobname")
} else {
	folder("\${path_parts[0]}"){
	if ( nparts > 2 ){
		folder("\${path_parts[1]}"){
		if ( nparts > 3 ){	
	   		folder("\${path_parts[2]}"){
			if ( nparts > 4 ){	
	   			folder("\${path_parts[3]}"){
				}
			}
			}
		}
		}
	}
	}
}

pipelineJob("\${seed_job_name}") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						url("\${seed_job_path}")
					}
					branch("*/\${seed_job_branch}")
				}
			}
			scriptPath("\${seed_job_pipe}")
		}
	}
	parameters {
		stringParam('seed_job_branch',"\${seed_job_branch}", 'seed_job_branch')
	}
	environmentVariables {
		envs(jobs_def_dir: "\${jobs_def_dir}")
		keepBuildVariables(true)
	}
	disabled(false)
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}""")
			ignoreExisting(false)
			removeAction("IGNORE")
			removeViewAction("IGNORE")
			lookupStrategy("JENKINS_ROOT")
		}
	}
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}
