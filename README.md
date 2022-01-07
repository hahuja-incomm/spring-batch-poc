# spring-batch-poc

```
**To create docker image** 
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=hahuja12/sbatch-poc

**Setup mysql**
docker-compose -f src/docker/docker-compose.yml up

**Run application on docker**
docker run -e SPRING_DATASOURCE_URL=jdbc:mysql://192.168.86.28:3306/test -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=root -e SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver hahuja12/sbatch-poc:latest fileName=https://github.com/hahuja12/spring-batch-demo/blob/master/sample-data1.csv?raw=true

**Expose Mysql as a service on K8s**
kubectl apply -f src/kubernetes/database-service.yaml

**Execute the K8s Job**
kubectl apply -f src/kubernetes/job.yaml

JOB_NAME=sample1 \
  FILE_NAME="https://github.com/hahuja12/spring-batch-demo/blob/master/sample-data1.csv?raw=true" \
  envsubst < src/kubernetes/job.yaml | kubectl apply -f -

**Check K8s job**
kubectl get jobs

**Get K8s pod**
kubectl get pods

**Check application logs**
kubectl logs <name-of-pod>



```
