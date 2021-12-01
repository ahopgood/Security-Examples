pipeline {
	agent  { label 'Java11' }
	stages {
	    stage('build') {
	        steps {
                git credentialsId: 'github_token', url: 'https://github.com/ahopgood/Security-Examples.git', branch: '${BRANCH_NAME}'
                sh 'mvn --version'
                sh 'mvn clean install'
            }
	    }
	    stage('docker build') {
	        parallel {
                stage ('Build PersistentXSS') {
                    agent { label 'Docker' }
                    steps {
                        sh 'echo Building PersistentXSS stage'
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