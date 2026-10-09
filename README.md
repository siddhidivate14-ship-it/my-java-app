# Java Maven Jenkins CI/CD Pipeline

An end-to-end automated Continuous Integration and Continuous Deployment (CI/CD) pipeline built with **Jenkins Declarative Pipeline**, **GitHub**, **Apache Maven**, and **Java 17**[cite: 1, 3].

---

## Architecture & Workflow

```text
GitHub Repo (Push/Commit)
         │
         ▼
 1. Checkout Code (SCM)
         │
         ▼
 2. Build (mvn clean compile)
         │
         ▼
 3. Unit Test (mvn test + JUnit Reporting)
         │
         ▼
 4. Package (mvn package -DskipTests)
         │
         ▼
 5. Deploy (Local Destination / Artifact Store)
```[cite: 1, 3]

---

## Project Structure

```text
my-java-app/
├── pom.xml
├── Jenkinsfile
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── example/
    │               └── App.java
    └── test/
        └── java/
            └── com/
                └── example/
                    └── AppTest.java
```[cite: 1, 3]

---

## Prerequisites & Tool Configuration

### 1. Global Tool Configuration in Jenkins
Navigate to **Manage Jenkins** > **Tools** and configure the runtime environments[cite: 2]:

* **JDK Installation**:
  * **Name**: `JDK-17`[cite: 2]
  * **JAVA_HOME**: Local JDK path (e.g., `C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot`)[cite: 2]
* **Maven Installation**:
  * **Name**: `Maven-3.9`[cite: 2]
  * **Install automatically**: Checked (Version: Apache 3.9.x)[cite: 2]
* **Git Installation**:
  * **Name**: `Default`[cite: 6, 7]
  * **Path to Git executable**: `git` (or system Git path)

---

## Jenkinsfile Implementation

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    stages {
        stage('1. Checkout Code') {
            steps {
                echo 'Fetching source code from GitHub...'
                checkout scm
            }
        }

        stage('2. Build') {
            steps {
                echo 'Compiling the Java application...'
                bat 'mvn clean compile'
            }
        }

        stage('3. Unit Test') {
            steps {
                echo 'Running unit tests...'
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('4. Package') {
            steps {
                echo 'Packaging application into JAR/WAR...'
                bat 'mvn package -DskipTests'
            }
        }

        stage('5. Deploy') {
            steps {
                echo 'Deploying application artifact...'
                bat '''
                    echo Deploying target file to destination server...
                    if not exist C:\\tmp mkdir C:\\tmp
                    copy /Y target\\*.jar C:\\tmp\\deployed-app.jar || exit 0
                    echo Deployment completed successfully.
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the logs for details.'
        }
    }
}
```[cite: 3, 4]

> **Note**: For Linux-based Jenkins nodes or agents, change the `bat` execution directives to `sh` and adjust path separators accordingly[cite: 3].

---

## Pipeline Stages Breakdown

| Stage | Command / Directive | Description |
| :--- | :--- | :--- |
| **1. Checkout Code** | `checkout scm` | Clones the latest commit from the configured repository branch (`*/main`)[cite: 3, 4, 5]. |
| **2. Build** | `mvn clean compile` | Cleans previous build artifacts and compiles the source code into bytecode[cite: 1, 3]. |
| **3. Unit Test** | `mvn test` | Executes unit test suites and parses XML outputs via `junit` for test reports[cite: 3, 5]. |
| **4. Package** | `mvn package -DskipTests` | Packages the application into an executable `.jar` file under the `target/` directory[cite: 3]. |
| **5. Deploy** | Windows file copy command | Copies the packaged JAR file to `C:\tmp\deployed-app.jar` as a target server deployment[cite: 3]. |

---

## Jenkins Job Setup

1. Go to **Jenkins Dashboard** > **New Item**[cite: 4].
2. Name the project `java-mvn-cicd-pipeline` and choose **Pipeline**[cite: 4].
3. In the **Pipeline** section[cite: 4]:
   * **Definition**: `Pipeline script from SCM`[cite: 4]
   * **SCM**: `Git`[cite: 4]
   * **Repository URL**: `https://github.com/siddhidivate14-ship-it/my-java-app.git`[cite: 4]
   * **Branch Specifier**: `*/main`[cite: 4]
   * **Script Path**: `Jenkinsfile`[cite: 4]
4. Click **Save** and select **Build Now** to trigger the build[cite: 2, 4].

---

## Verification & Results

* **Stage View**: All 5 execution stages (`Checkout`, `Build`, `Unit Test`, `Package`, `Deploy`) passed with green status[cite: 3, 4].
* **Automated Test Results**: JUnit test results captured and tracked under **Test Result Trend** with zero failures[cite: 4, 5, 11].
* **Deployment Output**: Artifact successfully verified at destination path `C:\tmp\deployed-app.jar`[cite: 3].
