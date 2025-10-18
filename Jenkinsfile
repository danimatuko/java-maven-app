pipeline {
    agent any
    environment {
        APP_NAME = 'my-app'
    }
    stages {
        stage('Setup') {
            steps {
                script {
                    utils = load 'scripts/utils.groovy'
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    utils.buildProject()
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    utils.deployProject('staging')
                }
            }
        }
    }
}
