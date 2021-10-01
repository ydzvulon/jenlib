organizationFolder('Org-ydzvulon') {
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

	// configure github-pr-comment-build-plugin
	configure { organizationFolder -> 
		def strategy = organizationFolder << strategy(class: 'jenkins.branch.DefaultBranchPropertyStrategy') {
			properties(class: 'java.util.Arrays$ArrayList') {
				a(class: 'jenkins.branch.BranchProperty-array') {
					"com.adobe.jenkins.github__pr__comment__build.TriggerPRCommentBranchProperty"(plugin :'github-pr-comment-build') {
						commentBody('.*Do: Build.*')
					}
				}
			}
		}
	}

}