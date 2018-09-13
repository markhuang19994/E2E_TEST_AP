pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Install') {
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
      parallel {
        stage('TestService') {
          steps {
            dir(path: env.WORKSPACE+'/ete-core') {
              bat 'mvn test -Dtest=ServiceTest'
            }

          }
        }
        stage('TestResp') {
          steps {
            dir(path: env.WORKSPACE+'/ete-core') {
              bat 'mvn test -Dtest=RepositoryTest'
            }

          }
        }
      }
    }
    stage('Archive') {
      parallel {
        stage('Archive') {
          steps {
            archiveArtifacts(allowEmptyArchive: true, artifacts: '*/**/*.zip', onlyIfSuccessful: true)
          }
        }
        stage('') {
          steps {
            bat 'mvn -f impl/workspace/XSELL/pom.xml -Dmaven.test.failure.ignore clean package'
          }
        }
      }
    }
  }
}