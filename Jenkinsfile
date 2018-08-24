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
        stage('save env') {
          steps {
            bat 'set > env.txt'
            bat 'echo env.txt'
          }
        }
        stage('show dir') {
          steps {
            bat(script: 'dir', encoding: 'utf-8')
          }
        }
      }
    }
    stage('Test') {
      agent any
      steps {
        dir(path: env.WORKSPACE+'/ete-core') {
          bat 'mvn test -Dtest=RepositoryTest'
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