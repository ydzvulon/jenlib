  organizationFolder('ydzvulon-org') {
    description('This contains branch source jobs for example-org GitHub')
    displayName('ydzvulon-org')
    triggers {
      periodic(2400)
    }
    organizations {
      github {
        repoOwner("ydzvulon")
        apiUri("https://api.github.com")
        // credentialsId('jenkins-token')
        traits {
          publicRepoPullRequestFilterTrait()
        }
      }
    }
    // configure {
    //   def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
    //   traits << 'org.jenkinsci.plugins.github__branch__source.BranchDiscoveryTrait' {
    //     strategyId(2)
    //   }
    //   traits << 'org.jenkinsci.plugins.github__branch__source.OriginPullRequestDiscoveryTrait' {
    //    strategyId(2)
    //  }
    // }
	configure {
		def traits = it / navigators / 'org.jenkinsci.plugins.github__branch__source.GitHubSCMNavigator' / traits
		traits << 'org.jenkinsci.plugins.github_branch_source.BranchDiscoveryTrait' {
			strategyId 1
		}
		traits << 'org.jenkinsci.plugins.github_branch_source.ForkPullRequestDiscoveryTrait' {
			strategyId 2
			trust(class: 'org.jenkinsci.plugins.github_branch_source.ForkPullRequestDiscoveryTrait$TrustEveryone')
		}
		traits << 'org.jenkinsci.plugins.github__branch__source.OriginPullRequestDiscoveryTrait' {
			strategyId 2
		}
	}

    projectFactories {
      workflowMultiBranchProjectFactory {
        // Relative location within the checkout of your Pipeline script.
        scriptPath("_cicd/build.pipe.groovy")
      }
    }
  }