def buildProject() {
    echo "Building ${env.APP_NAME} (Build #${env.BUILD_NUMBER})"
    sh "echo Building ${env.APP_NAME}"
}

def deployProject(target) {
    echo "Deploying ${env.APP_NAME} to ${target}"
    sh "echo Deploying ${env.APP_NAME} to ${target}"
}

return this
