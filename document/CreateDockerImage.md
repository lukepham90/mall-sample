# Create Docker Image

Jib builds optimized Docker and OCI images for Java applications without a Docker daemon - and without deep mastery of Docker best-practices.

## Goals

- Fast - Deploy your changes fast. Jib separates your application into multiple layers, splitting dependencies from classes. Now you don’t have to wait for Docker to rebuild your entire Java application - just deploy the layers that changed.

- Reproducible - Rebuilding your container image with the same contents always generates the same image. Never trigger an unnecessary update again.

- Daemonless - Reduce your CLI dependencies. Build your Docker image from within Maven or Gradle and push to any registry of your choice. No more writing Dockerfiles and calling docker build/push.

## Build && push docker image to Docker Hub

```shell script
 mvn package jib:build 
```

## Preferences

[GoogleContainerTools - Jib](https://github.com/GoogleContainerTools/jib)
