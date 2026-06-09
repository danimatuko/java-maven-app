pipeline {
    agent any
    tools { maven 'maven-3.9' }
    stages {
        stage('test') {
            steps {
                script {
                    echo 'Testing the application....'
                }
            }
        }

        stage('build') {
            steps {
                script {
                    echo 'Building the application....'
                    sh 'mvn clean package'
                    withCredentials([usernamePassword(
                        credentialsId: 'docker-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                            sh 'docker login -u "$DOCKER_USER" -p "$DOCKER_PASS"'
                        }
                }
            }
        }

        stage('deploy') {
            steps {
                script {
                    echo 'Deploying the application....'
                }
            }
        }
    }
}
