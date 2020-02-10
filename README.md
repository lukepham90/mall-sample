# Mall
![Java CI](https://github.com/uuhnaut69/mall/workflows/Java%20CI/badge.svg)
![Travis CI](https://travis-ci.com/uuhnaut69/mall-sample.svg?branch=master)

Sample mall project with Spring boot 2.x

<h3>Project Structure</h3>

- [x] Core Service - Contain common classes

- [x] File Service - Upload file to Aws S3

- [x] Mall Service - Core business

- [x] Message Queue Service - Message queue with rabbitmq

- [x] Payment Service - Integrate payment with Stripe

- [x] Search Service - Elasticsearch service support autocomplete, search product, simple content-based recommendation

- [x] Web Service - Endpoint for user interaction

<h3>Debezium Postgres Installation</h3>

1. Pull docker image:

  ``` bash
  docker pull debezium/postgres
  ```
2. Run docker image with username-password: "postgres-postgres"

  ``` bash
  docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD="postgres" debezium/postgres
  ```
