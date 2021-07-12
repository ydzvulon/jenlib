


def kws = [
	seed_job_branch: 'yairda.presentaion',
	seed_job_pipe: 'data4test/gitlab_seeds/devsample_pipe.groovy',
	repo_path: "file:///repo"
]

pipelineJob("devsample") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						url(kws.repo_path)
					}
					branch("*/${kws.seed_job_branch}")
				}
			}
			scriptPath("${kws.seed_job_pipe}")
		}
	}
	// environmentVariables {
	// 	envs(jobs_def_dir: "\${jobs_def_dir}")
	// 	keepBuildVariables(true)
	// }
	disabled(false)
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}