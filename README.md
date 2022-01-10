# spring-batch-poc

```
**Steps for building the application and execution on local Server**

Prerequisite: JDK, Maven, Docker Desktop already installed.

Step 1: package the application - 
        mvn package
Step 2: Create docker image, change image name if required
        mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hahuja12/sbatch-poc

Step 3: Setup mysql         
        Using docker-compose to download and install MySQL DB and create test database from schema file /sql folder
        docker-compose -f src/docker/docker-compose.yml up
        
        To check DB installation is succesful and check records in table
        docker exec -it mysql bash
        
        Below will prompt for DB passowrd - root
        mysql -u root -p test
        
        Once connected to MySQL prompt
        select * from PEOPLE;

Step 4: Execute application on docker, replace IP address with your local IP address, also any input accessible fileName URL can be provided

        docker run -e SPRING_DATASOURCE_URL=jdbc:mysql://192.168.86.28:3306/test -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=root -e SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver hahuja12/sbatch-poc:latest fileName=https://github.com/hahuja12/spring-batch-demo/blob/master/sample-data1.csv?raw=true        

Optional Steps
if want to excecute on Kubernetes on local server, make sure docker-desktop Kubenetes is running and have kubectl CLI installed

Step 5: Expose MYSQL DB as a service on Kubernetes
        kubectl apply -f src/kubernetes/database-service.yaml

Step 6: Execute application as Job object on Kubernetes, Note need to run below on Bash shell 
        JOB_NAME=sample3 \
  FILE_NAME="https://github.com/hahuja12/spring-batch-demo/blob/master/sample-data3.csv?raw=true" \
  envsubst < src/kubernetes/job.yaml | kubectl apply -f -

        if bash shell is not available, then use following - Filename and jobname has been hardcoded in .yaml file 
        kubectl apply -f src/kubernetes/job-windows.yaml

```

```
**Steps for building the application and execution on AWS EKS and AWS RDS MySQL DB**

Prerequisite: JDK, Maven, Kubectl, AWS CLI eksctl (optional)

Step 1: package the application
        mvn package

Step 2: Create docker image, change image name if required
        mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hahuja12/sbatch-poc

Step 3: Push the image to DockerHub or any other container registery
        docker push <name-of-image-repo>/<name-of-image>

Step 4: Create AWS EKS cluster, any of below options can be used
        1. Create cluster using AWS console
        2. Using Cloudformation template available in src/kubernetes/eks-cluster-template.yaml
        3. Using Eksctl - eksctl create cluster --name=<name-of-cluster> --nodes=2 (https://eksctl.io/introduction/)

Step 5: Create AWS RDS Database, any of the below option can be used
        1. Using AWS console (https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/USER_CreateDBInstance.html)
        2. Using cloudformation template src/kubernetes/rds-mysql-template.yaml
        3. Using AWS CLI (https://dev.to/bensooraj/accessing-amazon-rds-from-aws-eks-2pc3)

Step 6: Connect MySQL workbench to RDS DB and exceute DDL script in /src/sql/database.sql to create test  database with Spring batch related tables and PEOPLE table where we will insert data after processing each record in input file.

Step 7: Connect EKS with RDS which are hosted in different VPC
        VPC peering and updating Route table and Security
        Follow Step 4. Let's build the bridge! in blog (https://dev.to/bensooraj/accessing-amazon-rds-from-aws-eks-2pc3)

Step 8: Expose RDS MySql as Kubernetes Service, 
        
        *Make sure Kubectl is pointing to EKS cluster by updating kubeconfig (https://docs.aws.amazon.com/eks/latest/userguide/create-kubeconfig.html)
        
        *Make sure to update database-service-aws.yaml with your RDS DB hostname at .spec.externalName
        
        kubectl apply -f src/kubernetes/database-service-aws.yaml         

Step 9: Executing Spring Batch job on EKS
        
        *Make sure to update Docker image URL to your image URL in job.yaml or job-windows.yaml. Line 11 image: in .yaml file

        JOB_NAME=sample3 \
  FILE_NAME="https://github.com/hahuja12/spring-batch-demo/blob/master/sample-data3.csv?raw=true" \
  envsubst < src/kubernetes/job.yaml | kubectl apply -f -

        *Use below command if shell is not available, woudld need to update job-windows.yaml if testing with different input fileName.  
        
        Kubectl apply -f job-windows.yaml


```

```
General commands

Optional Step:
Download any MySQL client e.g. MySQL Workbench (https://dev.mysql.com/downloads/workbench/) to connect to MySQL hosted on local docker or RDS, to query People table.

**Check K8s job**
kubectl get jobs

**Get K8s pod**
kubectl get pods

**Check application logs**
kubectl logs -f <name-of-pod>

**Delete K8s job**
kubectl delete job <name-of-job>

```
