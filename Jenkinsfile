#!/usr/bin/env groovy

@Library('shared-lib') _  // 'shared-lib' = name from Jenkins Global Libraries config

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

        stage('Build Image') {
            steps {
                buildImage("${params.IMAGE_NAME}:${params.IMAGE_TAG}")  // full image name from parameters
            }
        }

        stage('Deploy') {
            steps {
                deployApp()
            }
        }
    }
}
