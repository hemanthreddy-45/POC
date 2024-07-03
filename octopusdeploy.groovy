import com.octopusdeploy.api.OctopusServer
import com.octopusdeploy.api.Deployment

def octopusServerUrl = "https://octopus-test.opsera.io/"
def apiKey = "JMLK3JPHLP5YPMHIGGFZKJEO8G"

def projectName = "Demo-H"
def environmentName = "Dev"
def releaseVersion = "1.0.0"

OctopusServer server = new OctopusServer(octopusServerUrl, apiKey)
Deployment deployment = server.deploy(projectName, environmentName, releaseVersion)

if (deployment.isSuccess()) {
    println("Deployment successful!")
} else {
    println("Deployment failed: " + deployment.getError())
}
