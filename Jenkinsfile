pipeline {
    environment {
	    TAG = sh(script: 'date "+%Y%m%d-%H%M"', returnStdout: true)
    }
	agent  { label 'Java11' }
	stages {
	    stage('build') {
	        steps {
                git credentialsId: 'github_token', url: 'https://github.com/ahopgood/Security-Examples.git', branch: '${BRANCH_NAME}'
                sh 'mvn --version'
                sh 'mvn clean install -s settings.xml'
                stash name: 'PersistentXSS', includes: 'PersistentXSS/target/PersistentXSS-*.jar'
            }
	    }
	    stage('Build & Publish Docker Images') {
	        parallel {
                stage ('Build PersistentXSS') {
                    environment {
                        IMAGE_NAME="persistent-xss"
                        NAMESPACE="reclusive/"
                    }
                    agent { label 'Docker' }
                    stages {
                        stage ('Docker Build') {
                            steps {
                                sh 'echo Building PersistentXSS docker image'
                                unstash name: 'PersistentXSS'
                                sh '''
                                cd PersistentXSS
                                docker build -t persistent-xss:$TAG -t persistent-xss:latest .
                                '''
                            }
                        }
                        stage('Docker Image Vulnerability Scan') {
                            agent { label 'Docker && Grype' }
                            steps {
                                git credentialsId: 'github_token', url: 'https://github.com/ahopgood/Security-Examples.git', branch: '${BRANCH_NAME}'
                                sh'''
                                    grype version
                                    grype ${IMAGE_NAME}:${TAG} -c .grype.yaml
                                '''
                            }
                        } //End Vulnerability Scan Stage
                        stage ('Docker Hub Tag & Push'){
                            when {
                                branch 'master'
                            }
                            steps {
                                sh 'echo Pushing tags to docker hub'
                                withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                    sh '''
                                    echo ${PASSWORD} | docker login --username ${USERNAME} --password-stdin

                                    docker tag ${IMAGE_NAME}:${TAG} altairbob/${IMAGE_NAME}:${TAG}
                                    docker tag ${IMAGE_NAME}:latest altairbob/${IMAGE_NAME}:latest

                                    docker push altairbob/${IMAGE_NAME}:${TAG}
                                    docker push altairbob/${IMAGE_NAME}:latest
                                    docker logout
                                    '''
                                }
                            }
                        } //Docker hub end
                        stage ('Reclusive Regsistry Tag & Push') {
                            steps {
                                sh 'echo Pushing tags to Reclusive Registry'
                                withCredentials([usernamePassword(credentialsId: 'docker', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                    sh '''
                                    echo ${PASSWORD} | docker login --username ${USERNAME} --password-stdin https://${DOCKER_REGISTRY}

                                    docker tag ${IMAGE_NAME}:${TAG} ${DOCKER_REGISTRY}/${NAMESPACE}${IMAGE_NAME}:${TAG}
                                    docker tag ${IMAGE_NAME}:latest ${DOCKER_REGISTRY}/${NAMESPACE}${IMAGE_NAME}:latest

                                    docker push ${DOCKER_REGISTRY}/${NAMESPACE}${IMAGE_NAME}:${TAG}
                                    docker push ${DOCKER_REGISTRY}/${NAMESPACE}${IMAGE_NAME}:latest
                                    docker logout https://${DOCKER_REGISTRY}
                                    '''
                                }
                            }
                        }//end Reclusive registry tag & push
                    }
                }
                stage ('Build Insecure') {
                    agent { label 'Docker' }
                    steps {
                        sh 'echo Building Insecure stage'
                    }
                }
            }
	    } // end parallel docker build stage
    }
    post {
    	always {
        	junit '**/target/surefire-reports/*.xml'
        }
    }
}