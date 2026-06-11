def utils //Global utils variable → accessible in all stages.

pipeline {
    agent any
    tools { maven 'maven-3.9' }
    stages {
        stage('init') {
            steps {
                script {
                    utils = load 'jenkins/utils.groovy'
                }
            }
        }
        stage('test') {
            steps {
                script {
                    utils.testApp()
                }
            }
        }

        stage('build') {
            steps {
                script {
                    utils.buildApp()
                }
            }
        }

        stage('deploy') {
            when {
                expression { env.BRANCH_NAME == 'main' }
            }
            steps {
                script {
                    utils.deploy()
                }
            }
        }
    }
}
