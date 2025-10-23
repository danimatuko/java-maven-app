#!/usr/bin/env groovy

// Reference to shared library (kept for later)
// library identifier: 'shared-lib@main', retriever: modernSCM(
//   [$class: 'GitSCMSource',
//    remote: 'https://github.com/danimatuko/jenkins-shared-library.git',
//    credentialsId: 'github-credentials'])
//
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
            // Call external Groovy function
            def utils = load 'jenkins-utils.groovy'
            utils.buildJar(this) // pass pipeline context
          }
        }
      }

      stage('Build Docker Image') {
        steps {
          script {
            def utils = load 'jenkins-utils.groovy'
            utils.buildImage(this, "${params.IMAGE_NAME}:${env.IMAGE_TAG}")
          }
        }
      }

      stage('Docker Login') {
        steps {
          script {
            def utils = load 'jenkins-utils.groovy'
            utils.login(this)
          }
        }
      }

      stage('Push Docker Image') {
        steps {
          script {
            def utils = load 'jenkins-utils.groovy'
            utils.push(this, "${params.IMAGE_NAME}:${env.IMAGE_TAG}")
          }
        }
      }

      stage('Deploy') {
        steps {
          script {
            def utils = load 'jenkins-utils.groovy'
            utils.deployApp(this) // if you have a deploy function in your script
          }
        }
      }
    }
}
