@Library("jenlib@master") _

def kws = [:]

LoadConfig(
    kws,
    dest: 'jenlib_config'
    config_path:  "version/jenlib_config.yml",
    config_entry: "jenbuild_flow"
)

JenFlowTopLevel(
    kws,
    taskfile: kws.jenlib_config.jenflow,
    taskname: kws.jenlib_config.taskname
)
