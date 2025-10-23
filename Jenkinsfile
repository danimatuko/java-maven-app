#!/usr/bin/env groovy

def utils

pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }
    parameters {
        string(name: 'IMAGE_NAME', defaultValue: 'danimatuko/demo-app', description: 'Docker image name')
    }
    stages {
      stage('Initialize') {
        steps {
          script {
            // Load the external script once and store it in the pipeline variable
            utils = load 'jenkins-utils.groovy'
          }
        }
      }
      
      stage('Increment Version') {
        steps {
          script {
            sh 'mvn build-helper:parse-version versions:set \
              -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
              versions:commit'
            env.NEW_VERSION = sh(
                script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
                returnStdout: true
            ).trim()
            if (!env.NEW_VERSION) {
              error("Failed to read project.version from pom.xml")
            }
            env.IMAGE_TAG = "${env.NEW_VERSION}-${env.BUILD_NUMBER}"
            echo "📦 New project version: ${env.NEW_VERSION}"
            echo "🐳 Docker image tag: ${env.IMAGE_TAG}"
          }
        }
      }
      
      stage('Build JAR') {
        steps {
          script {
            utils.buildJar(this)
          }
        }
      }
      
      stage('Build Docker Image') {
        steps {
          script {
            utils.buildImage(this, "${params.IMAGE_NAME}:${env.IMAGE_TAG}")
          }
        }
      }
      
      stage('Docker Login') {
        steps {
          script {
            utils.login(this)
          }
        }
      }
      
      stage('Push Docker Image') {
        steps {
          script {
            utils.push(this, "${params.IMAGE_NAME}:${env.IMAGE_TAG}")
          }
        }
      }
      
      stage('Deploy') {
        steps {
          script {
            utils.deployApp(this)
          }
        }
      }
    }
}
