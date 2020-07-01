pipelineJob("new_project") {
	description()
	keepDependencies(false)
	parameters {
		stringParam("region", "regionTESTING", "The target environment")
	}
	definition {
		cpsScm {
"""
"""		}
	}
	disabled(false)
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}
