text="""
# http://objx-stattic.sddc.mobileye.com/docs/files/project_settings.env
# This is .env file with all settings for all packages
# notation:
# MXPACK_{PACKGROUP}__{VARIABLE}

# --- version ---
MXPACK_MXPACK__VERSION_SCHEMA="0.14.1331"

# --- mxpack global config ---
MXPACK_MXPACK__LOCAL_BUILD_DIR=__localbuild__
MXPACK_MXPACK__LOCAL_BUILD_INFO=__build_info

# --- mxpack project pack config ---
MXPACK_PROJECT__PROJECT_NAME=objx-homepage

# --- mxpack gitdata pack config ---
MXPACK_GITDATA__GIT_TAG_PREFIX=objx-homepage-

# --- mxpack mkdocs pack config ---
MXPACK_MKDOCS__VENV_NAME="objx-homepage-venv-py37"
MXPACK_MKDOCS__PY_VERSION="3.7"

# --- mxpack jenlib pack config ---
MXPACK_MKDOCS__JENLIB_SETTINGS_YML=version/recipes/jenlib.pack.settings.yml

"""

// println text
println text[0]

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
println( remove_quotes('"noqouetes"') )

def parse_flat(line){
    def var_name = line.takeWhile { it != '=' }
    def var_content_raw = line.substring(var_name.length()+1)
    def var_content_clean = remove_quotes(var_content_raw)
    return
}
println parse_flat("""MXPACK_MXPACK__VERSION_SCHEMA='0.14.1331'""")
println parse_flat("""MXPACK_MXPACK__LOCAL_BUILD_DIR=__localbuild__""")


def parse_groups(kw, line){
    def full_var_name = line.takeWhile { it != '=' }
    def var_content_raw = line.substring(full_var_name.length()+1)
    def var_content_clean = remove_quotes(var_content_raw)

    def group_name = full_var_name.takeWhile { it != '_' }
    def pack_name_long = full_var_name.substring(group_name.length()+1)
    println ( [group_name, pack_name_long] )

    def pack_name = pack_name_long.split('__')[0]
    def var_name_clean = pack_name_long.split('__')[1]

    println ( [pack_name, var_name_clean] )

    kw.full <<  ["${full_var_name}": "${var_content_clean}"]
    kw.vars << ["${var_name_clean}": "${var_content_clean}"]
    if(! kw.packs[pack_name]){
        kw.packs[pack_name] = [:]
    }
    kw.packs[pack_name] << ["${var_name_clean}": "${var_content_clean}"]
    if(! kw.groups){
        kw.groups[group_name] = [:]
    }
    kw.groups[group_name] << ["${pack_name_long}": "${var_content_clean}"]


}

def parse_env_file(multiline_text){
    def lines = multiline_text.readLines()
    def mxpack_lines = lines.collectMany {
        (it.trim() =~ /^MXPACK_.*/)
        ? [it.trim()]
        : []
    }
    def kw = [
        full: [:],
        groups: [
            MXPACK: [:]
        ],
        vars: [:],
        packs: [:]
    ]
    mxpack_lines.each { it -> parse_groups(kw, it)}

    return kw

}
import static groovy.json.JsonOutput.*

def config = parse_env_file(text)

println prettyPrint(toJson(config))

