pipeline {
    agent any
    stages {
        stage('Creando Virtual Enviroment') {
            steps {
                sh 'virtualenv entorno_virtual'
                sh '''
                      bash -c "source entorno_virtual/bin/activate"
                '''
            }
        }
        stage('Installando Requirements') {
            steps {
                sh 'bash -c /usr/local/bin/pip install -r requirements.txt'
            }
        }
        stage('Run Flask Application') {
            steps {
                sh 'bash -c /usr/bin/python src/main.py &'
            }
        }
        stage('Test Application') {
            steps {
                sh 'bash -c cd src && pytest && cd .. '
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'bash -c docker build -t apptest:latest . '
            }
        }
        stage('Push to DockerHub') {
            steps {
                sh 'bash -c docker tag apptest:latest dcmikki/apptest:latest'
                sh 'bash -c docker push dcmikki/apptest:latest '
            }
        }
        stage('Delete local images') {
            steps {
                sh 'bash -c docker rmi apptest:latest && docker rmi dcmikki/apptest:latest && docker rmi ubuntu:latest '
            }
        }
    }
}
