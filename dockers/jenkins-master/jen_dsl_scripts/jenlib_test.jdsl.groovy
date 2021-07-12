def jobname=

pipelineJob("jenlib_pipe_master_ci") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github("gzvulon/jenlib", "https")
					}
					branch("*/master")
				}
			}
			scriptPath("data4test/parallel_tasks_jen/build.groovy")
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

def ooo='''
cp dsls || true
task \
-d dsls \
add-prj jenfile_uri=jenfile_uri


task \
-d dsls \
add \
jobname=jenlib_pipe_master_ci \
github_p=gzvulon/jenlib
script_p=data4test/parallel_tasks_jen/build.groovy

mkdir -p _projects_dsl
'''