Usage

1. Build the app with "mvn clean install"
2. Build docker image with "docker build -t pre-interview-assignment:0.0.1-SNAPSHOT ."
3. Start minikube with "minikube start"
4. Check minikube status with "minikube status"
5. Execute "kubectl -apply -f kube"
6. To view the deployment run "kubectl get deployments"
7. Get the url of the app running with "minikube service knote --url"
8. To access minikube dashboard execute "minikube dashboard --url"
9. Enable horizontal autoscaling with "kubectl autoscale deployment pre-interview-assignment --cpu-percent=50 --min=1 --max=10"
10. To clean up execute "kubectl delete service pre-interview-assignment", "kubectl delete deployment pre-interview-assignment"
11. Stop the vm using "minikube stop" and then to delete the vm execute "minikube delete"
