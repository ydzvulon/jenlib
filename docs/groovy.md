# Groovy Essentials

## Basic

### Variables

### Methods

## Difference from Python

### kwargs and args

### method call

## Imports and Dymanic loading

### Load Class (import) from custom groovy file

> url_from=<https://stackoverflow.com/questions/9136328/including-a-groovy-script-in-another-groovy>

```groovy
File sourceFile = new File("/full/path/to/./src/org/jenslib/jentools.groovy");
Class groovyClass = new GroovyClassLoader(getClass().getClassLoader()).parseClass(sourceFile);
GroovyObject myObject = (GroovyObject) groovyClass.newInstance();
```

### Evaluate code ad-hoc

> Evaluate text url_from=<http://www.groovy-lang.org/metaprogramming.html#_dynamic_method_names>

```groovy
// evaluate implicitly creates a class based on the filename specified
evaluate(new File("./Testutils.groovy"))
// Safer to use 'def' here as Groovy seems fussy about whether the filename (and therefore implicit class name) has a capital first letter
def tu = new Testutils()
tu.myUtilityMethod("hello world")
```
