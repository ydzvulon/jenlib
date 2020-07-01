// def jobname_ci = '{{.JOB_NAME}}' // jenlib_pipe_master_ci
// def github_p = '{{.GITHUB_PATH}}' // "gzvulon/jenlib"
// def jengroovy_p = '{{.JENKINS_FILE_PATH}}' // "e2e/data4test/parallel_tasks_jen/build.groovy"

pipelineJob("${jobname_ci}") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github("${github_p}", "https")
					}
					branch("*/master")
				}
			}
			scriptPath("${jengroovy_p}")
		}
	}
	disabled(false)
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}
