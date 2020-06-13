# Mall ![build](https://travis-ci.com/uuhnaut69/mall-sample.svg?branch=master)

Sample mall project with Spring boot 2.x

## Project Structure

- [x] Common Service

- [x] Capture Data Change Service

- [x] Core Service

- [x] File Service

- [x] Payment Service

- [x] Search Service

- [x] Security Service

- [x] Web Service

## Prerequisites

- Java 8+

- Docker

- Docker-Compose

## Start Environment

1. Go to infrastructure folder
    ```shell script
    cd infrastructure/
    ```

2. Run command:

    ```shell script
    docker-compose up -d
    ```

3. Check environment health:

    ```shell script
    docker-compose ps
    ```
4. Install json_lines plugin for logstash

```shell script
# Open terminal in logstash container
docker exec -it mall-logtash /bin/bash

# Go to bin directory
cd /bin/

# Install plugin
logstash-plugin install logstash-codec-json_lines

# Exit container after install successfully
exit

# Restart logtash container
docker restart mall-logtash
```
