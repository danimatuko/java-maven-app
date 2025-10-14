#!/usr/bin/env groovy

library identifier: 'shared-lib@main', retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'https://github.com/danimatuko/jenkins-shared-library.git',
   credentialsId: 'github-credentials'])

pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    parameters {
        string(name: 'IMAGE_NAME', defaultValue: 'danimatuko/demo-app', description: 'Docker image name')
        string(name: 'IMAGE_TAG', defaultValue: 'latest', description: 'Docker image tag')
    }

    stages {
        stage('Build JAR') {
            steps {
                buildJar()
            }
        }

        stage('Build Docker Image') {
            steps {
                dockerBuildImage("${params.IMAGE_NAME}:${params.IMAGE_TAG}")
            }
        }

        stage('Docker Login') {
            steps {
                dockerLogin()
            }
        }

        stage('Push Docker Image') {
            steps {
                dockerPush("${params.IMAGE_NAME}:${params.IMAGE_TAG}")
            }
        }

        stage('Deploy') {
            steps {
                deployApp()
            }
        }
    }
}
