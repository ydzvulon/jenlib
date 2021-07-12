// vars/jen_flow.groovy
import org.jenkinsci.plugins.pipeline.utility.steps.shaded.org.yaml.snakeyaml.Yaml


def __get_real_cfg(config_agent_env_settings_yaml, pipe_type=null){
    
    println "@@act=info-block title=agent_env_settings_yaml"
    println config_agent_env_settings_yaml

    println "@@act=init kind=read title='${config_agent_env_settings_yaml}'"
    def agent_env_settings_text = readTrusted(config_agent_env_settings_yaml)
    println "@@act=over kind=read title='${config_agent_env_settings_yaml}'"
    println agent_env_settings_text

    def yaml_parser = new Yaml()
    def parsed_file = yaml_parser.load(agent_env_settings_text)
    def spec_agents = parsed_file.spec.agents

    def actual_type = pipe_type ?: spec_agents.default
    println "@@act=over step=resolve-config follow=mutli-line-block"
    println actual_type

    def real_cfg = spec_agents.options[actual_type]

    println "@@act=over step=resolve-pipe_type follow=mutli-line-block"
    println real_cfg.pipe_type
    return real_cfg
}

def call(Map config=[:], Closure body) {
    // https://www.jenkins.io/blog/2020/10/21/a-sustainable-pattern-with-shared-library/
    // https://blog.ippon.tech/setting-up-a-shared-library-and-seed-job-in-jenkins-part-2/
    // https://kimsereylam.com/jenkins/2019/12/27/jenkins-shared-libraries.html
    def jenlib_pipe_conf_yml = config.kws._layout.jenlib_pipe_conf_yml
    def real_cfg = __get_real_cfg(jenlib_pipe_conf_yml)
    if (real_cfg['pipe_type'] == 'native'){
        ///// --- schema.by.sample ----
        // native:
        //     pipe_type: native
        //     node_label: algo-ci-generic-deb8-x86_64        
        node(real_cfg['node_label']) {
            properties([
                disableConcurrentBuilds()
            ])

            timestamps {
            ansiColor('xterm') {
                stage_fetch(config.kws, jenlib_pipe_conf_yml)
                body()
            }}
        }
    }
    if (real_cfg.pipe_type == 'k8s'){
        println real_cfg
        ///// --- schema.by.sample ----
        // k8s:
        //     pipe_type: k8s
        //     pod_template_yaml: _infra/jenlib_lib/cicd/mkdocs.deliver.pipe.pod.tmpl.yaml
        //     cloud: k8s-objx-prod-28102020
        //     container: mxcc-prod
        //     # FIX: change to objx
        //     ssh-agent: yairda-ssh-gitlab

        podTemplate(
            cloud: real_cfg['cloud'],
            yaml: readTrusted(real_cfg['pod_template_yaml'])
        ){
            node(POD_LABEL) { 
                timestamps {
                ansiColor('xterm') {
                    container(real_cfg['container']){
                    sshagent([real_cfg['ssh-agent']]) {
                        stage_fetch(config.kws, jenlib_pipe_conf_yml)
                        body()
                    }}
                }}
            }
        }
    }
}

def stage_fetch(kws, kws_config_file){
    stage('fetch') {
        // k8s support ssh keys mapping
        sh "SSH_AUTH_SOCK=${env.SSH_AUTH_SOCK} ssh-add -l"

        if (kws.params.delete_work_dir) {
            echo "@jenkins: deleting working dir"
            deleteDir()
        }

        echo 'Fetch source'

        def scmvars = checkout scm

        echo 'Fetched source'

        def pipe_config_cont = readYaml file: kws_config_file
        kws << pipe_config_cont.spec.kws

        dir(kws.const.subpath) {
            def version_prefix = readFile("version/version_prefix.txt")
            def project_prefix_txt = "version/project_prefix.txt"
            def project_prefix = null
            if (fileExists(project_prefix_txt)){
                project_prefix = readFile(project_prefix_txt)
            }
            else {
                project_prefix = readFile('version/project_name.txt')
            }
            def build_name_suffix = "${kws.const.build_type}:${project_prefix}:${version_prefix}"
            jen.set_build_name(currentBuild, scmvars, build_name_suffix)
            kws['scmvars'] = scmvars
            jen.desc_from_commits(currentBuild, kws)
        }
    }
}
