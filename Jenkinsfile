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
        string(name: 'IMAGE_NAME', defaultValue: 'danimatuko/java-maven-app', description: 'Docker image name')
    }
    stages {
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
            buildJar()
          }
        }
      }
      
      stage('Build Docker Image') {
        steps {
          script {
            buildImage("${params.IMAGE_NAME}:${env.IMAGE_TAG}")
          }
        }
      }
      
      stage('Docker Login') {
        steps {
          script {
            dockerLogin()
          }
        }
      }
      
      stage('Push Docker Image') {
        steps {
          script {
            dockerPush("${params.IMAGE_NAME}:${env.IMAGE_TAG}")
          }
        }
      }
      
      stage('Deploy') {
        steps {
          script {
            deployApp()
          }
        }
      }
    }
}
