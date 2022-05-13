# spring-boot-template 

This demo deploys a simple Spring Boot web application that connects to Postgres onto a Kubernetes cluster.

You can watch this demo along with an introduction to Kubernetes concepts [here](https://www.youtube.com/watch?v=OsWXtVbTnv0).

## Prerequisites

- Kubernetes cluster with kubectl installed and configured to use your cluster
- docker cli installed, you must be signed into your Docker Hub account

## Deploy Spring Boot app and Postgres on Kubernetes
1. Deploy postgres with a persistent volume claim
   ```
   kubectl create -f k8s/db-deployment.yaml
   ```

2. Create a config map with the hostname of Postgres
   ```
   kubectl create configmap hostname-config --from-literal=postgres_host=$(kubectl get svc postgres -o jsonpath="{.spec.clusterIP}")
   ```

3. Replace `<your Docker Hub account>` with your account name in `build_and_push.bat` and start the process:

   **Build the Spring Boot app -> Build a Docker image -> Push the image to Docker Hub**
   
   ```
    ./build_and_push.bat
   ```

5. Replace `<your Docker Hub account>` with your account name in `specs/spring-boot-app.yml`, then deploy the app
   ```
   kubectl create -f k8s/app-deployment.yaml
   ```

6. Create an external load balancer for your app
   ```
   kubectl expose deployment spring-boot-template --type=LoadBalancer --port=8080
   ```

7. Get the External IP address of Service, then the app will be accessible at `http://<External IP Address>:8080`
   ```
   kubectl get svc spring-boot-template
   ```
   > **Note:** It may take a few minutes for the load balancer to be created
   
   > **Note:** If you have External IP Address long time in status Pending, then read [this](https://stackoverflow.com/questions/44110876/kubernetes-service-external-ip-pending)

8. Scale your application
   ```
   kubectl scale deployment spring-boot-template --replicas=3
   ```

## Updating your application
1. Update the image that the containers in your deployment are using
   ```
   kubectl set image deployment/spring-boot-template spring-boot-template=<your Docker Hub account>/spring-boot-template:v2
   ```

## Deleting the Resources
1. Delete the Spring Boot app deployment
   ```
   kubectl delete -f k8s/app-deployment.yaml
   ```

1. Delete the service for the app
   ```
   kubectl delete svc spring-boot-template
   ```

1. Delete the hostname config map
   ```
   kubectl delete cm hostname-config
   ```

1. Delete Postgres
   ```
   kubectl delete -f k8s/db-deployment.yaml
   ```
