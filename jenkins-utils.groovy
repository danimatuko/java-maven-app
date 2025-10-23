#!/usr/bin/env groovy


def buildImage(String imageName) {
    echo "Building Docker image: ${imageName}"
    sh "docker build -t ${imageName} ."
}

def login() {
    withCredentials([usernamePassword(
        credentialsId: 'docker-hub-repo',
        usernameVariable: 'USER',
        passwordVariable: 'PASS'
    )]) {
        sh 'echo $PASS | docker login -u $USER --password-stdin'
    }
}

def push(String imageName) {
    sh "docker push ${imageName}"
}

def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}


return this
