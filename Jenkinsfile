@Library('shared-lib') _  // 'shared-lib' matches the name in Jenkins config

pipeline {
    agent any
    tools { maven 'maven-3.9' }
    stages {

    stage('test webhook') {
      steps {
        echo "Webhook triggered pipeline for ${env.BRANCH_NAME} at ${env.GIT_COMMIT}"
      }
    }

    stage('test app') {
      steps {
        script {
          testApp()
        }
      }
    }

    stage('build app') {
      steps {
        script {
          buildApp()
        }
      }
    }

    stage('deploy app') {
      when {
        expression { env.BRANCH_NAME == 'main' }
      }
      steps {
        script {
          deployApp("danimatuko/java-maven-app:latest")
        }
      }
    }
  }
}
