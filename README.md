Assignment Report: Jenkins CI/CD Pipeline Implementation1. ObjectiveTo design and implement an automated Declarative CI/CD pipeline using Jenkins for a Java Maven application hosted on GitHub, covering checkout, build, automated testing, packaging, and deployment.   2. Architecture & Pipeline WorkflowThe pipeline automates the software delivery lifecycle through the following workflow:   PlaintextGitHub Repo (Push/Commit)
       │
       ▼
1. Checkout (Jenkins SCM)
       │
       ▼
2. Build (mvn clean compile)
       │
       ▼
3. Unit Test (mvn test & JUnit reporting)
       │
       ▼
4. Package (mvn package -DskipTests)
       │
       ▼
5. Deploy (Local deployment to /tmp/deployed-app.jar)
3. Pipeline Stages BreakdownStageCommand / DirectivePurpose & ExplanationToolstools { jdk 'JDK-17'; maven 'Maven-3.9' }Injects the configured JDK 17 and Maven 3.9 paths dynamically into the execution PATH.   1. Checkout Codecheckout scmClones the latest commit from the GitHub repository (main branch).   2. Buildmvn clean compileCleans previous artifacts and compiles the Java source code (App.java).   3. Unit Testmvn test & junit '**/target/surefire-reports/*.xml'Executes automated unit tests (AppTest.java) and publishes JUnit visual test reports.   4. Packagemvn package -DskipTestsPackages the compiled application into an executable JAR file inside the target/ directory.   5. Deploycopy /Y target\*.jar C:\tmp\deployed-app.jarSimulates target server deployment by copying the generated artifact to the destination directory.   Post Actionspost { success { ... } failure { ... } }Displays notifications depending on whether the pipeline succeeded or encountered an error.   4. Technical Challenges & ResolutionsPlatform Compatibility: The initial pipeline configuration used Unix shell commands (sh), which failed on Windows with a CreateProcess error=2 (cannot run program "sh"). This was resolved by transitioning the commands to Windows batch scripts (bat).   Local Tool Binding: Automatically installed JDKs were not directly available via the controller interface; configuring the exact local JDK 17 home directory (C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot) ensured seamless integration with Maven.   5. Submission Checklist (Screenshots Attached)[x] GitHub Repository: Showing directory structure (pom.xml, Jenkinsfile, and src/).   [x] Global Tools Configuration: JDK-17 and Maven-3.9 saved under Jenkins Tools.   [x] Jenkins Stage View: Green execution pipeline for all 5 stages on Build #3.   [x] Test Results Trend: Graph and report confirming com.example.AppTest passed.   [x] Deployment Verification: Output proving C:\tmp\deployed-app.jar was created. 
