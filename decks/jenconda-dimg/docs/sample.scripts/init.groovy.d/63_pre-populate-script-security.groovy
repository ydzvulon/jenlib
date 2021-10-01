import java.lang.reflect.*;
import jenkins.model.Jenkins;
import jenkins.model.*;
import org.jenkinsci.plugins.scriptsecurity.scripts.*;
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.*;

scriptApproval = ScriptApproval.get()
alreadyApproved = new HashSet<>(Arrays.asList(scriptApproval.getApprovedSignatures()))


// add all manual whitelist methods here.

approveSignature('method groovy.json.JsonBuilder call java.util.List')
approveSignature('method groovy.json.JsonSlurper parseText java.lang.String')
approveSignature('method groovy.json.JsonSlurperClassic parseText')
approveSignature('method groovy.lang.Binding getVariables')
approveSignature('method groovy.lang.Binding getVariable java.lang.String')
approveSignature('method groovy.lang.Binding hasVariable java.lang.String')
approveSignature('method groovy.lang.Closure getMaximumNumberOfParameters')
approveSignature('method groovy.lang.GString plus java.lang.String')
approveSignature('method groovy.lang.GroovyObject invokeMethod java.lang.String java.lang.Object')
approveSignature('method hudson.model.Actionable getAction java.lang.Class')
approveSignature('method hudson.model.Actionable getActions')
approveSignature('method hudson.model.Cause$UpstreamCause getUpstreamProject')
approveSignature('method hudson.model.Cause$UserIdCause getUserId')
approveSignature('method hudson.model.ItemGroup getItem java.lang.String')
approveSignature('method hudson.model.Item getUrl')
approveSignature('method hudson.model.Job getBuildByNumber int')
approveSignature('method hudson.model.Job getLastBuild')
approveSignature('method hudson.model.Job getLastSuccessfulBuild')
approveSignature('method hudson.model.Job isBuilding')
approveSignature('method hudson.model.Run getCauses')
approveSignature('method hudson.model.Run getEnvironment hudson.model.TaskListener')
approveSignature('method hudson.model.Run getParent')
approveSignature('method hudson.model.Run getNumber')
approveSignature('method hudson.model.Run getResult')
approveSignature('method hudson.model.Run getUrl')
approveSignature('method hudson.model.Run getLogFile')
approveSignature('method java.util.Map containsKey java.lang.Object')
approveSignature('method java.util.Map entrySet')
approveSignature('method java.util.Map get java.lang.Object')
approveSignature('method java.util.Map keySet')
approveSignature('method java.util.Map putAll java.util.Map')
approveSignature('method java.util.Map remove java.lang.Object')
approveSignature('method java.util.Map size')
approveSignature('method java.util.Map values')
// ... your list here ...

scriptApproval.save()

void approveSignature(String signature) {
    if (!alreadyApproved.contains(signature)) {
       scriptApproval.approveSignature(signature)
    }
}

// Utility methods
String printArgumentTypes(Object[] args) {
    StringBuilder b = new StringBuilder();
    for (Object arg : args) {
        b.append(' ');
        b.append(EnumeratingWhitelist.getName(arg));
    }
    return b.toString();
}
