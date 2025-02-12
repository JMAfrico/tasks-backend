pipeline{
    agent any
    stages{  
        stage ('Build Backend'){
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests'){
            steps{
                bat 'mvn test'
            }
        }
        stage ('Sonar Analysis'){
            environment{
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=a336bd9a7696a4076b995e970e2b12594301e284 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**src/test/**,**/model/**,**Application.java"
                }
            }
        }
        stage('Quality Gate'){
            steps{
                sleep(5) 
                timeout(time:1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true

                }
            }
        }
        stage('Deploy Backend'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'Tomcat', path: '', url: 'http://localhost:8080')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }

        stage('API Test'){
            steps{
                dir('tasks-api-test'){
                    git credentialsId: 'GIthub', url: 'https://github.com/JMAfrico/tasks-api-test.git'
                    bat 'mvn test'
                }
            }
        }

        stage('Deploy Frontend'){
            steps{
                dir('tasks-frontend'){
                    git credentialsId: 'GIthub', url: 'https://github.com/JMAfrico/tasks-frontend.git'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'Tomcat', path: '', url: 'http://localhost:8080')], contextPath: 'tasks-frontend', war: 'target/tasks.war'           
                }
            }
        }

        stage('Functional Test'){
            steps{
                dir('tasks-functional-test'){
                    git credentialsId: 'GIthub', url: 'https://github.com/JMAfrico/tasks-functional-test.git'
                    bat 'mvn test'
                }
            }
        }
    }
}

