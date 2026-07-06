def testApp() {
    echo 'Testing the application....'
}

def buildApp() {
    echo 'Building the application....'
    sh 'mvn package'
}

def deploy() {
    echo 'Deploying the application....'
    withCredentials([usernamePassword(
        credentialsId: 'docker-creds',
        usernameVariable: 'DOCKER_USER',
        passwordVariable: 'DOCKER_PASS'
    )]) {
        sh 'docker build -t "$DOCKER_USER/java-maven-app:latest" .'
        sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
        sh 'docker push "$DOCKER_USER/java-maven-app:latest"'
    }
}

return this
