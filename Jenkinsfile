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
                }
            }
        }

        stage('deploy') {
            steps {
                script {
                    echo 'Deploying the application....'
                    withCredentials([usernamePassword(
                        credentialsId: 'docker-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh 'docker build -t "$DOCKER_USER/java-maven-app:latest" .'
                        sh 'docker login -u "$DOCKER_USER" -p "$DOCKER_PASS"'
                        sh 'docker push "$DOCKER_USER/java-maven-app:latest"'
                    }
                }
            }
        }
    }
}
