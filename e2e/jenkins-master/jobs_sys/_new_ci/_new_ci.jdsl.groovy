job("_new_ci") {
	description()
	keepDependencies(false)
	parameters {
		choiceParam("jenfile_uri", ["__custom__", "https://github.com/gzvulon/jenlib.git;e2e/data4test/parallel_tasks_jen/build.groovy"], "repo;path_jenkinsfile")
		stringParam("__custom__", "", "")
	}
	disabled(false)
	concurrentBuild(false)
	steps {
		shell(
"""
echo \$jenfile_uri
echo cus \$__custom__
""")
	}
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}