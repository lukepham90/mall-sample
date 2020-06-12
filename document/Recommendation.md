# Recommendation Using Elasticsearch (Content-based)

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
