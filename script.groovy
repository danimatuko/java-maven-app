#!/usr/bin/env groovy
// NOTE: The logic in this file has been moved to the Jenkins shared library
// Functions like buildJar(), buildImage(), and deployApp() are now available as global steps
// This file is kept here only for reference

def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t danimatuko/demo-app:jma-1.3 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push danimatuko/demo-app:jma-1.3'
    }
}

def deployApp() {
    echo 'deploying the application...'
}

return this
