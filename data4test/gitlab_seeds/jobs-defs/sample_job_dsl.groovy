
def kws = [
	seed_job_branch: 'nextrelease.v0.7.6',
	root_dir: "samples",
	seed_job_pipes: [
		'data4test/gitlab_seeds/batches/a_simple_batch/pipe_500_cmds_minimal.groovy',
		'data4test/gitlab_seeds/batches/a_simple_batch/pipe_501_cmds_explicit.groovy',
		'data4test/gitlab_seeds/batches/a_simple_batch/pipe_502_tasks_explicit.groovy',
		'data4test/gitlab_seeds/batches/a_simple_batch/pipe_503_tasks_from_yml.groovy',
		'data4test/gitlab_seeds/batches/c_complex_batch/pipe_701_complex_pipes.groovy',
		'data4test/gitlab_seeds/batches/c_complex_batch/pipe_703_tasks_from_yml.groovy',
		'data4test/gitlab_seeds/batches/c_complex_batch/pipe_730_groovy_dynamic.groovy',
	],
	repo_path: "file:///repo"
]


folder(kws.root_dir){

}

for (seed_job_pipe in kws.seed_job_pipes)
{
	def arr = seed_job_pipe.split('/')
	def jname = arr[-1]
	def jfname = "${kws.root_dir}/${jname}"
	pipelineJob(jfname) {
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
				scriptPath("${seed_job_pipe}")
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
}