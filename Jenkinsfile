pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build') {
          steps {
            bat 'mvn clean install -Dmaven.test.skip=true'
          }
        }
        stage('error') {
          steps {
            bat 'set > env.txt'
          }
        }
      }
    }
    stage('Test') {
      agent any
      steps {
        dir(path: env.WORKSPACE+'/ete/ete-core') {
          bat 'mvn test'
        }

      }
    }
    stage('Archive') {
      steps {
        archiveArtifacts(allowEmptyArchive: true, artifacts: '*')
      }
    }
  }
}