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
    }

    stages {

      stage('Increment Version') {
        steps {
          script {
            // Use bash instead of sh
            sh '''#!/bin/bash
              mvn build-helper:parse-version versions:set \
              -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} \
              -DgenerateBackupPoms=false'''

              def newVersion = sh(
                  script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
                  returnStdout: true
                  ).trim()

              if (!newVersion) {
                error("Failed to read project.version from pom.xml")
              }

            env.NEW_VERSION = newVersion
              env.IMAGE_TAG = "${env.NEW_VERSION}-${env.BUILD_NUMBER}"

              echo "📦 New project version: ${env.NEW_VERSION}"
              echo "🐳 Docker image tag: ${env.IMAGE_TAG}"
          }
        }
      }

      stage('Build JAR') {
        steps {
          buildJar()
        }
      }

      stage('Build Docker Image') {
        steps {
          dockerBuildImage("${params.IMAGE_NAME}:${env.IMAGE_TAG}")
        }
      }

      stage('Docker Login') {
        steps {
          dockerLogin()
        }
      }

      stage('Push Docker Image') {
        steps {
          dockerPush("${params.IMAGE_NAME}:${env.IMAGE_TAG}")
        }
      }

      stage('Deploy') {
        steps {
          deployApp()
        }
      }
    }
}
