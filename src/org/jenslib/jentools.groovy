package org.jenslib.jentools

class StaticApi{
    public static String reverse_string(String s){
        return s.reverse()
    }
}

def reverse_string(String s){
    return s.reverse()
}

def checkOutFrom(repo) {
  sh(" echo git@github.com:jenkinsci/${repo}")
}

class Utilities implements Serializable {
  def steps
  Utilities(steps) {this.steps = steps}
  def mvn(args) {
    steps.sh "${steps.tool 'Maven'}/bin/mvn -o ${args}"
  }
}

// @Library('utils') import org.foo.Utilities
// def utils = new Utilities(this)
// node {
//   utils.mvn 'clean package'
// }
return this
