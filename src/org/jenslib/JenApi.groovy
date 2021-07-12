package org.jenslib

@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

def reverse_string(String s){
    return s.reverse()
}

def checkOutFrom(repo) {
  sh(" echo git@github.com:jenkinsci/${repo}")
}

def text_yaml_to_map(text){
    def yaml_parser = new Yaml()
    def parsed = yaml_parser.load(text)
    return parsed
}

def text_yaml_from_map(the_map){
    def yaml_parser = new Yaml()
    def text = yaml_parser.dump(the_map)
    return text
}

def stages_from_task(task_map){
  for (cmd in task_map.cmds){
    stage(cmd){
      sh(cmd)
    }
  }
}

def stage_with_steps_from_task(Map cfg =[:]){
  def taskmap = cfg.taskmap; assert taskmap
  def taskname = cfg.taskname; assert taskname
  // end of header schema

  stage(taskname){
    for (cmd in taskmap.cmds){
        sh(cmd)
    }
  }
}

return this
