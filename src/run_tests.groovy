
@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

import lib.Lib
import org.jenslib.JenApi
import org.jenslib.Jenapi
import tests.unit.TestsBasic


def get_jenapi(){
    // TODO: move to file
    def org_ast_holder= [root:[:], path: '.', cur: null]
    JenApi.metaClass.ast_holder = org_ast_holder

    JenApi.metaClass.reset_ast = {
        println "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
        ast_holder.root = [
            stages: []
        ]
        ast_holder.cur = ast_holder.root.stages
    }

    JenApi.metaClass.sh ={
        name -> println "JEN_SH ${name}"
        def step = [
            step: "sh",
            mparams:[
                script: name
            ]
        ]
        ast_holder.cur.add(step)
    }

    JenApi.metaClass.stage ={
        name, body -> println "JEN_STAGE ${name}"
        def stage = [
            stage: name,
            steps:[]
        ]
        def pcur = ast_holder.cur
        ast_holder.cur.add(stage)
        ast_holder.cur = stage.steps
        body()
        ast_holder.cur = pcur
    }


    def jenapi_obj = new JenApi()
    return jenapi_obj

}

def get_data4test_dir(){
    def scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
    def repo_root = new File(scriptDir).parent
    def data4test_dir = "${repo_root}/data4test/jenlib_pure"
    return data4test_dir

    // //One more solution. It works perfect even you run the script using GrovyConsole

    // File getScriptFile(){
    //     new File(this.class.classLoader.getResourceLoader().loadGroovySource(this.class.name).toURI())
    // }

    // println getScriptFile()

}

def tenv = [
    lib: new Lib(),
    jenapi: get_jenapi(),
    jenapi_alt: new Jenapi(),
    test_suits: [
        basic: new TestsBasic()
    ],
    data4test_dir: get_data4test_dir()
]

def test_sample(def tenv){
    tenv.lib.saySomething();
    println tenv.lib.sum(37,5)
    println tenv.jenapi.reverse_string("it")
    println tenv.jenapi_alt.reverse_string("on")

    println get_data4test_dir()
}

def _print_usage(String err=""){
    println "Usage: groovy run_tests.groovy tests=all"
    println "Usage: groovy run_tests.groovy tests=test1,test2"
    if (err){
        println err
    }
    return
}

def discover_tests(kwargs, tenv){
    def holder = tenv.test_suits.basic
    def api = tenv.jenapi

    if (kwargs.length == 0){
        return {_print_usage()}
    }
    if (kwargs.length == 1) {
        def first_arg = kwargs[0]

        def methods = holder.getClass().declaredMethods.findAll {
            !it.synthetic && it.name.startsWith("test")
        }.name

        methods = methods.unique()
        def mc = methods.getClass()

        if (first_arg=='list-tests'){
            println "Abailable Tests"
            return {
                for( m in methods ){
                    println m
                }
            }
        }
        if (first_arg=='test-sample'){
            println "Running Smaple Test"
            return {
                test_sample(tenv)
            }
        }

        def pair_list = first_arg.split('=') as List
        if (pair_list.size() != 2){
            def err = "Args should be in X=Y pairs, but got: ${first_arg}"
            return {_print_usage(err)}
        }

        assert pair_list[0] == 'tests'

        def tests_to_run = pair_list[1].split(',')
        if (tests_to_run.length == 0){
            def err = """
                Args should be in tests=t1,t2 format,
                t1,t2 is list of test and = it should be coma separated
                got ${first_arg}
                """
            return {_print_usage(err)}
        } else {
            if (tests_to_run[0] == 'all'){

                tests_to_run = methods
            }

            return {run_many_tests(api, tenv,holder, tests_to_run)}
        }
        // api: tenv.jenapi
    }
}

def run_many_tests(api, tenv, holder, methods){
    println "api=${api}. methods=${methods} "
    def mc = methods.getClass()

    println("methods:  ${mc}")

    for (var i = 0; i < methods.size(); i++){
        def the_method = methods[i]
        println "SDFSD=${the_method}"
            // throw new Exception("sadf")
        run_one_test(api, tenv, holder, the_method)
    }
}

def run_one_test(api, tenv, holder, method, argstr=''){
    // argstr - not in use
    api.reset_ast()

    def now_ts= new Date().format( 'yyyyMMdd-hhmm-ss00' )
    def uid = UUID.randomUUID().toString()
    def suid = uid[19..-1]
    def runid = "${now_ts}-${suid}"
    println "${method} ${argstr}"

    println "@@act=prep target=test runid=${runid} name=${method} argstr=${argstr} agent=root.groovy.test.runner"
    def result = holder."${method}"(
        api: api,
        tenv: tenv
    )
    println "@@act=post target=test runid=${runid} name=${method} result=${result} agent=root.groovy.test.runner"
}

def runner(def kwargs, def tenv){
    println "conf: ${kwargs}"
    def func = discover_tests(kwargs, tenv)
    return func()
}

// args.each{println it}
runner(args, tenv)
