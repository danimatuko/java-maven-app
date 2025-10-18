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
                    echo 'Test webhook for multibranch pipeline'
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
