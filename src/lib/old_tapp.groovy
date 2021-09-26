
@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

import lib.Lib
import org.jenslib.JenApi
import org.jenslib.Jenapib
import tests.unit.TestsBasic

def apis = [
    lib: new Lib(),
    jenapi: new JenApi(),
    jenapi_alt: new Jenapi()
]

def test_sample(def apis){
    Lib.saySomething();
    println new Lib().sum(37,5)
    println new JenApi().reverse_string("it")
    def jap = new Jenapib()
    println jap.reverse_string("on")
    def tb = new TestsBasic()

    tb.test_read_task()
    def methods = TestsBasic.declaredMethods.findAll { !it.synthetic && it.name.startsWith("test")}.name
    println methods
}


test_sample(apis)



// def runner(def kwargs){
//     if (kwargs.length < 2){
//         println "Usage: groovy-run [method]:req METHOD_NAME"
//         return
//     }
//     File sourceFile = new File("/homes/yairda/_wd/mx-infra/jenlib/src/org/jenslib/jentools.groovy");
//     Class groovyClass = new GroovyClassLoader(getClass().getClassLoader()).parseClass(sourceFile);
//     GroovyObject myObject = (GroovyObject) groovyClass.newInstance();
//     // StaticApi
//     // https://stackoverflow.com/questions/9136328/including-a-groovy-script-in-another-groovy
//     // evaluate implicitly creates a class based on the filename specified

//     // evaluate(new File("./Testutils.groovy"))
//     // // Safer to use 'def' here as Groovy seems fussy about whether the filename (and therefore implicit class name) has a capital first letter
//     // def tu = new Testutils()
//     // tu.myUtilityMethod("hello world")

//     // if kwargs[0] == 'method'
//     // from=http://www.groovy-lang.org/metaprogramming.html#_dynamic_method_names
//     // def api = myObject //new StaticApi()
//     def api = myObject // new .StaticApi() //new StaticApi()
//     def method = kwargs[1]
//     def argstr = kwargs[2]
//     def now_ts= new Date().format( 'yyyyMMdd-hhmm-ss00' )
//     def uid = UUID.randomUUID().toString()
//     def suid = uid[19..-1]
//     def runid = "${now_ts}-${suid}"
//     println "${method} ${argstr}"

//     println "@@act=prep target=test runid=${runid} name=${method} argstr=${argstr} agent=root.groovy.test.runner"
//     def result = api."${method}"(argstr)
//     println "@@act=post target=test runid=${runid} name=${method} result=${result} agent=root.groovy.test.runner"
// }

// // args.each{println it}
// runner(args)
