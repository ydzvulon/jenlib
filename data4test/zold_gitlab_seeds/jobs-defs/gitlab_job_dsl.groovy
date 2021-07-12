private boolean isSandbox() {
  def locationConfig = jenkins.model.JenkinsLocationConfiguration.get()
  if (locationConfig != null && locationConfig.getUrl() != null) {
    locationConfig.getUrl().contains("staging")
  } else {
    System.getenv("ENVIRONMENT") == "sandbox"
  }
}

List<Map> gitlab = []
if (isSandbox()) {
  gitlab = [
    [name: 'tests', displayName: 'Jenkins Tests', group: 'jenkins/tests'],
  ]
} else {

  gitlab = [
    [ group: 'jenlib_sample',
      name: 'jenlib_sample_name',
      displayName: 'jenlib_sample_dname',
      folder: 'GitlabFolders']
  ]
}

def folders = gitlab.collect { it.folder }.unique() - null

folders.each {
  folder(it) {
  }
}

gitlab.each { Map org ->
  gitlabOrgs(org).call()
}

Closure gitlabOrgs(Map args = [:]) {
  def config = [
    displayName: args.name,
    group: args.name,
    suppressDefaultJenkinsfile: false,
  ] << args
  def name = config.folder ? "${config.folder}/${config.name}" : config.name
  GString orgDescription = "<br>${config.displayName} group projects"

  return {
    organizationFolder(name) {
      organizations {
        displayName(config.displayName)
        description(orgDescription)
        gitLabSCMNavigator {
          projectOwner(config.group)
        //   credentialsId('gitlab_ssh_key')
          serverName('gitlab-9313')
          traits {
            subGroupProjectDiscoveryTrait() // discover projects inside subgroups
            gitLabBranchDiscovery {
              strategyId(3) // discover all branches
            }
            originMergeRequestDiscoveryTrait {
              strategyId(1) // discover MRs and merge them with target branch
            }
            gitLabTagDiscovery() // discover tags
            gitLFSPullTrait()
            // gitLocalBranchTrait {
            //     localBranch("**")
            // }
          }
        }
      }

        // "Traits" ("Behaviours" in the GUI) that are NOT "declarative-compatible"
        // For some 'traits, we need to configure this stuff by hand until JobDSL handles it
        // https://issues.jenkins.io/browse/JENKINS-45504
        configure {
            def traits = it / navigators / 'io.jenkins.plugins.gitlabbranchsource.GitLabSCMNavigator' / traits
            traits << 'io.jenkins.plugins.gitlabbranchsource.ForkMergeRequestDiscoveryTrait' {
                strategyId 2
                trust(class: 'io.jenkins.plugins.gitlabbranchsource.ForkMergeRequestDiscoveryTrait$TrustPermission')
            }
        }
        // configure {
        //     def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
        //     traits << 'jenkins.plugins.git.traits.LocalBranchTrait' {
        //         localBranch('**')
        //     }
        // configure {
        //     def traits = it / navigators / 'org.jenkinsci.plugins.github__branch__source.GitHubSCMNavigator' / traits
        //     traits << 'org.jenkinsci.plugins.github_branch_source.BranchDiscoveryTrait' {
        //         strategyId 1
        //     }
        //     traits << 'org.jenkinsci.plugins.github_branch_source.ForkPullRequestDiscoveryTrait' {
        //         strategyId 2
        //         trust(class: 'org.jenkinsci.plugins.github_branch_source.ForkPullRequestDiscoveryTrait$TrustEveryone')
        //     }
        //     traits << 'org.jenkinsci.plugins.github__branch__source.OriginPullRequestDiscoveryTrait' {
        //         strategyId 2
        //     }
        // }
      orphanedItemStrategy {
        discardOldItems {
          daysToKeep(7)
          numToKeep(10)
        }
      }
      if (!isSandbox()) {
        triggers {
          periodicFolderTrigger {
            interval('1d')
          }
        }
      }
      projectFactories {
        workflowMultiBranchProjectFactory {
          scriptPath('build.pipe.groovy')
        }
      }
    }
  }
}
