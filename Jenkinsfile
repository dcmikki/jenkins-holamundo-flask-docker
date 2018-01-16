pipeline {
    agent {
        label 'slave'
    }
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
                sh '''
                        bash -c "/usr/local/bin/pip install -r requirements.txt"
                '''
            }
        }
        stage('Run Flask Application') {
            steps {
                sh '''
                        bash -c "/usr/bin/python src/main.py &"
                '''
            }
        }
        stage('Test Application') {
            steps {
                sh '''
                        bash -c "cd src && pytest && cd .. "
                '''
            }
        }
        stage('Build Docker Image') {
            steps {
                sh '''
                        docker build -t apptest:latest .
                '''
            }
        }
        stage('Push to DockerHub') {
            steps {
                sh '''
                        docker tag apptest:latest dcmikki/apptest:latest
                        docker push dcmikki/apptest:latest 
                '''
            }
        }
        stage('Delete local images') {
            steps {
                sh '''
                        docker rmi apptest:latest
                        docker rmi dcmikki/apptest:latest
                '''
            }
        }
    post {
        success {
            emailext(

                subject: "Ejecucion del trabajo ${env.JOB_NAME}[${env.BUILD_NUMBER}] sin problemas. Imagen Docker Creada ",
                body:""" '${env.JOB_NAME}, el build numero '[${env.BUILD_NUMBER}]'' se ha ejecutado correctamente".
                          Imagen Docker creada y subida al DockerHub !!!! 
                          Verifica la salida de ${env.JOB_NAME} [${env.BUILD_NUMBER}] para consulta """,
                to: "diegoytess@gmail.com"
                )
        }	
    }
}
