# Mall
![Java CI](https://github.com/uuhnaut69/mall/workflows/Java%20CI/badge.svg)
![Travis CI](https://travis-ci.com/uuhnaut69/mall-sample.svg?branch=master)

Sample mall project with Spring boot 2.x

<h3>Project Structure</h3>

- [x] Core Service - Contain common classes

- [x] File Service - Upload file to Aws S3

- [x] Mall Service - Core business

- [x] Payment Service - Integrate payment with Stripe

- [x] Search Service - Elasticsearch service support autocomplete, search product, simple content-based recommendation

- [x] Web Service - Endpoint for user interaction

<details>
 <summary> List technology stacks used in this project </summary> 
 <p>
  
   - Spring Boot Framework 2.x (JPA, Security, ...)
   
   - Postgres 10

   - Mapstruct (Bean mapping)

   - Lombok

   - Java stripe (Payment method)

   - Jwt

   - Spring boot mail

   - CDC Embedded Debezium Postgres

   - Elasticsearch (Jest client + ES v6.8.1)

   - Spring cache caffeine
   
   - AWS S3
   
   - Swagger 2
   
   - Rabbitmq
 </p>
</details> 

<h3>Run Environment</h3>

1. Run command:

  ``` bash
  
  docker-compose up -d
  ```

2. Check environment health:

  ```bash
    
  docker-compose ps
```

