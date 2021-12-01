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
	        agent { label 'Docker' }
	        parallel {
                stage ('Build PersistentXSS') {
                    sh 'echo Building PersistentXSS stage'
                }
                stage ('Build Insecure') {
                    sh 'echo Building Insecure stage'
                }
            }

	    }
    }
    post {
    	always {
        	junit '**/target/surefire-reports/*.xml'
        }
    }
}