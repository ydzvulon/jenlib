#!/usr/bin/env groovy

// @@[from_url=https://github.com/gzvulon/mvp_jin_sys_jobs]

//  jinMakeParallel.groovy
def call(cfgs, Closure clsr) {
    jinEchoMark("jinMakeParallel:${cfgs}")
    def actors = [:]
    if (cfgs instanceof List){
        for(def i = 0; i < cfgs.size(); ++i){
            // Need to bind the label variable before the closure
            def cfg = cfgs[i]
            def name = "${i}" + ''
            if (cfg instanceof String){
               name = cfg
            }
            else {
                name = cfg.ref_id ?: cfg.name
            }
            name = name ?: "tread#${i}"
            actors[name] = clsr.curry(cfg)
        }
    } else {
        for(def x in cfgs){
            actors[x.key] = clsr.curry(x.value)
        }
    }
    return actors
}