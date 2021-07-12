package tests.unit

@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml


def test_reverse_string(Map cfg=[:]){
    assert cfg.api
    def api = cfg.api

    the_string = "someone"
    expected = "enoemos"
    actual = api.reverse_string(the_string)
    assert actual == expected
    return "state:success"
}

def sample(){return """
# https://taskfile.dev

version: '3'

GREETING: Hello, World!

tasks:
  default:
    cmds:
      - echo "{{.GREETING}}"
    silent: true

  prepare:
    cmds:
      - pwd

  report:
    cmds:
    - ls -alh

  ci-flow:
    cmds:
      - echo ci-flow init
      - echo ci-flow over

  top-level:
    desc: _
    cmds:
      - task prepare
      - task ci-flow
      - task report
"""
}

def test_read_task(Map cfg=[:]){
    assert cfg.api
    def api = cfg.api

    def text = sample()
    def yaml_map = api.text_yaml_to_map(text)
    assert yaml_map.version == '3'
    println yaml_map
}

def test_stages_from_task_inline(Map cfg=[:]){
    assert cfg.api
    def api = cfg.api
    def text = sample()
    api.reset_ast()
    def yaml_map = api.text_yaml_to_map(text)
    def the_task = yaml_map.tasks['top-level']
    def stages = api.stages_from_task(the_task)
    println stages
    println api.text_yaml_from_map  (api.ast_holder)
    def actual = api.ast_holder.root.stages
    def expected_txt = """
- stage: task prepare
  steps:
  - step: sh
    mparams: {script: task prepare}
- stage: task ci-flow
  steps:
  - step: sh
    mparams: {script: task ci-flow}
- stage: task report
  steps:
  - step: sh
    mparams: {script: task report}"""

    def expected = api.text_yaml_to_map(expected_txt)
    assert actual == expected
}


def test_this_and_owner(Map cfg=[:]){
  def x = { def y = { println "this=";println this; println "owner="; println owner }; y() }
  x()
}


def test_stages_from_task(Map cfg=[:]){
    assert cfg.api
    def api = cfg.api
    def tenv = cfg.tenv


    def taskfile_path = "${tenv.data4test_dir}/test_basic_task/Taskfile.yml"
    def jenflow_path ="${tenv.data4test_dir}/test_basic_task/Taskfile.jenflow.yml"

    def taskfile_text = new File(taskfile_path).text
    def jenflow_text = new File(jenflow_path).text


    def yaml_map = api.text_yaml_to_map(taskfile_text)
    def the_task = yaml_map.tasks['top-level']
    def stages = api.stages_from_task(the_task)

    def actual = api.ast_holder.root.stages


    def flow_full = api.text_yaml_to_map(jenflow_text)
    def flow_map = flow_full.entries.stages_from_task

    def expected = flow_map.stages

    println api.text_yaml_from_map(actual)

    assert actual == expected
}


def stage_with_steps_from_task(Map cfg=[:]){
    assert cfg.api
    def api = cfg.api
    def tenv = cfg.tenv


    def taskfile_path = "${tenv.data4test_dir}/test_basic_task/Taskfile.yml"

    def taskfile_text = new File(taskfile_path).text



    def yaml_map = api.text_yaml_to_map(taskfile_text)
    def the_task = yaml_map.tasks['top-level']
    def stages = api.stage_with_steps_from_task(
      taskmap: the_task,
      taskname: 'top-level'
    )

    def actual = api.ast_holder.root.stages
    println api.text_yaml_from_map(actual)

    def jenflow_path ="${tenv.data4test_dir}/test_basic_task/Taskfile.jenflow.yml"
    def jenflow_text = new File(jenflow_path).text
    def flow_full = api.text_yaml_to_map(jenflow_text)
    def flow_map = flow_full.entries.stage_with_steps_from_task
    def expected = flow_map.stages


    assert actual == expected
}


return this