// Este es el primer primer borrador del Jenkinsfile. Se tiene que mirar todo la sintaxis
// Ver como poner coretament los comandos bash, multi-linea, variables, path, etc
// Lo mejor es trocear el pipeline e ir testeando cada 'stage' en cadena
pipeline {
    agent master
    stages {
        stage('Hola Mundo') {
            steps {
                sh 'echo HOLA MUNDO'
            }
        }
        stage('Ansible Module') {
            steps {
                sh 'bash -c ./scripts/hola_ansible.sh'
            }
        }
        stage('Entorno virtual') {
            steps {
                sh 'bash -c source entorno_virtual/bin/activate'
            }
        }
        stage('Requirements') {
            steps {
                sh 'bash -c /usr/local/bin/pip install -r requirements.txt'
            }
        }
        stage('Flask App') {
            steps {
                sh 'bash -c /usr/bin/python src/main.py &'
            }
        }
        stage('Testing') {
            steps {
                sh 'bash -c cd src && pytest && cd .. '
            }
        }
        stage('Docker IMAGE') {
            steps {
                sh 'bash -c docker build -t apptest:latest . '
            }
        }
        stage('Docker PUSH') {
            steps {
                sh 'bash -c docker tag apptest:latest dcmikki/apptest:latest'
                sh 'bash -c docker push dcmikki/apptest:latest '
                sh 'bash -c docker rmi apptest:latest && docker rmi dcmikki/apptest:latest && docker rmi ubuntu:latest '
            }
        }
    }
}
