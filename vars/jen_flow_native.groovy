def call(Map config=[:], Closure body) {
    println config
    def node_label = config.node_label;
    def kws_config_file = config.kws_config_file
    println "kws_config_file=${kws_config_file}"
    def kws =  config.kws
    kws.params = kws.params ?: [:]


    node(node_label) {
        properties([
            disableConcurrentBuilds()
        ])

    timestamps {

        stage('fetch') {
            if (kws.params.delete_work_dir) {
                echo "@jenkins: deleting working dir"
                deleteDir()
            }

            echo 'Fetch source'
            def scmvars = checkout scm
            def pipe_config = readYaml file: kws_config_file
            kws << pipe_config

            dir(kws.const.subpath) {
                def version_prefix = readFile("version/version_prefix.txt")
                def project_prefix_txt = "version/project_prefix.txt"
                if (fileExists(project_prefix_txt)){
                    def project_prefix = readFile(project_prefix_txt)
                }
                else {
                    def project_prefix = readFile('version/project_name.txt')
                }
                def build_name_suffix = "${kws.const.build_type}:${project_prefix}:${version_prefix}"
                jen.set_build_name(currentBuild, scmvars, build_name_suffix)
                kws['scmvars'] = scmvars
                jen.desc_from_commits(currentBuild, kws)
            }
        }

        body()

    }}

}