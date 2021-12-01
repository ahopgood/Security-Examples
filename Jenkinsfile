pipeline {
	agent  { label 'Java11' }
	stages {
	    stage('build') {
	        steps {
                git credentialsId: 'github_token', url: 'https://github.com/ahopgood/Security-Examples.git', branch: '${BRANCH_NAME}'
                sh 'mvn --version'
                sh 'mvn clean install'
                stash name: 'PersistentXSS', includes: 'PersistentXSS/target/PersistentXSS-*.jar'
            }
	    }
	    stage('Build & Publish Docker Images') {
	        parallel {
                stage ('Build PersistentXSS') {
                    agent { label 'Docker' }
                    stages {
                        stage ('Docker Build') {
                            steps {
                                sh 'echo Building PersistentXSS docker image'
                                unstash name: 'PersistentXSS'
                                sh '''
                                cp PersistentXSS-*.jar PersistentXSS/target/
                                cd PersistentXSS
                                TAG=$(date "+%Y%m%d-%H%M")
                                docker build -t persistent-xss:$TAG -t persistent-xss:latest .
                                '''
                            }
                        }
                        stage ('Docker Tag'){
                            steps {
                                sh 'echo Creating docker hub tags'
                                sh 'echo Pushing to docker hub'
                            }
                        }
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