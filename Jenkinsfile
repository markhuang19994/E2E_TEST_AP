pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat 'mvn clean install -Dmaven.test.skip=true'
      }
    }
    stage('Test') {
      agent any
      environment {
        coreDir = 'env.WORKSPACE'
      }
      steps {
        ws(dir: '${coreDir}/ete/ete-core') {
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
