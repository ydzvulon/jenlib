// --- docs ---

// https://itnext.io/jenkins-shared-libraries-part-1-5ba3d072536a
// http://gitlab.mobileye.com/objm/jenlib
// https://tomd.xyz/jenkins-shared-library/
// https://www.jenkins.io/doc/book/pipeline/shared-libraries/
// https://groovy-playground.appspot.com/
// https://groovy-lang.gitlab.io/101-scripts/docker/basico-en.html
// https://github.com/groovy/docker-groovy


// --- yaml sample ---
// @Grab('org.yaml:snakeyaml:1.17')
// import org.yaml.snakeyaml.Yaml

// --- private ---

def remove_quotes(txt){
    if (txt[0] == txt[-1]){
        // println "same"
        if (txt[0] == "'" || txt[0] == '"') {
            // println txt.substring(1,txt.length()-1)
            return txt.substring(1,txt.length()-1)
        }
    }
    return txt
}

def parse_groups(env_file_conf, line){
    def full_var_name = line.takeWhile { it != '=' }
    def var_content_raw = line.substring(full_var_name.length()+1)
    def var_content_clean = remove_quotes(var_content_raw)

    def group_name = full_var_name.takeWhile { it != '_' }
    def pack_name_long = full_var_name.substring(group_name.length()+1)
    println ( [group_name, pack_name_long] )

    def pack_name = pack_name_long.split('__')[0]
    def var_name_clean = pack_name_long.split('__')[1]

    println ( [pack_name, var_name_clean] )

    env_file_conf.full <<  ["${full_var_name}": "${var_content_clean}"]
    env_file_conf.vars << ["${var_name_clean}": "${var_content_clean}"]
    if(! env_file_conf.packs[pack_name]){
        env_file_conf.packs[pack_name] = [:]
    }
    env_file_conf.packs[pack_name] << ["${var_name_clean}": "${var_content_clean}"]
    if(! env_file_conf.groups){
        env_file_conf.groups[group_name] = [:]
    }
    env_file_conf.groups[group_name] << ["${pack_name_long}": "${var_content_clean}"]
}

// --- public ---

def parse_env_file_text(multiline_text){
    def lines = multiline_text.readLines()
    def mxpack_lines = lines.collectMany {
        (it.trim() =~ /^MXPACK_.*/)
        ? [it.trim()]
        : []
    }
    def env_file_conf = [
        full: [:],
        groups: [
            MXPACK: [:]
        ],
        vars: [:],
        packs: [:]
    ]
    mxpack_lines.each { it -> parse_groups(env_file_conf, it)}
    return env_file_conf
}


