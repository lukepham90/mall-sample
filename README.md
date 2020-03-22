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

<h3>Run Environment</h3>

1. Run command:

  ``` bash
  
  docker-compose up -d
  ```

2. Check environment health:

  ```bash
    
  docker-compose ps
```

<h3>Recommendation Using Elasticsearch (Content-based)</h3>

```bash
curl -X GET "127.0.0.1:9200/productindex/_search" -H 'Content-Type: application/json' -d'

{
  "query": {
    "function_score": {
      "query":  {"match_all": {}},
      "functions": [
        {
          "filter": { 
            "bool": {
              "must_not": {
                 "ids": { "values": ["productId1","productId2","productId3"] }
              }
            }
          },
          "weight": 8
        },
        {
          "filter": { "terms": { "tags": ["trendy", "revolution"] } },
          "weight": 4
        },
        {
          "filter": { "term": { "trending": true } },
          "weight": 2
        }
      ],
      "score_mode": "sum"
    }
  }
}
```

**Notes**

 - Storing user's activities when user view a product => mark as read => save product id corresponding user in ES.
 - Building query for fetch list product: 
 
   - Using function score:
   
     - If result return doesn't match user's viewed product's ids => Give score 8.
     
     - If user's tags match in product's tags => Give score 4.
     
     - If product's tags marked trending => Give score 2.
     
     - Using **SUM SCORE MODE**.
     
     - Sorting by score. 
 
 - Why using score is number that is powers of 2 ? 
 
   => We can decode score to get information what match in query by bitwise operations.
