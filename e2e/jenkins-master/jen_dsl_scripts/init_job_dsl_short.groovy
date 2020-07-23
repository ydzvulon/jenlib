import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob

WorkflowJob job = Jenkins.instance.createProject(WorkflowJob, 'my-pipeline')
