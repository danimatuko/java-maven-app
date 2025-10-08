#!/usr/bin/env groovy

def utils // variable to load helper script with build and deploy functions

pipeline {
    agent any

    tools {
        // Use the Maven tool named 'maven-3.9' configured in Jenkins
        maven 'maven-3.9'
    }

    stages {
        stage('Initialize') {
            steps {
                script {
                    // Load external Groovy functions (buildJar, buildImage, deployApp, etc.)
                    utils = load 'script.groovy'
                }
            }
        }

        stage('Build JAR') {
            steps {
                script {
                    utils.buildJar()
                }
            }
        }

        stage('Build Image') {
            steps {
                script {
                    utils.buildImage()
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    utils.deployApp()
                }
            }
        }
    }
}
