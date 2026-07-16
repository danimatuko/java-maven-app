@Library('shared-lib') _  // 'shared-lib' matches the name in Jenkins config

pipeline {
    agent any
    tools { maven 'maven-3.9' }
    stages {

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
          buildApp("danimatuko/java-maven-app:latest")
        }
      }
    }

    stage('deploy app') {
      when {
        expression { env.BRANCH_NAME == 'main' }
      }
      steps {
        script {
          deployApp()
        }
      }
    }
  }
}
