### Create new meal:

  ```` 
  curl --location --request POST 'http://localhost:8080/topjava/rest/meals' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "dateTime": "2015-05-30T13:00:15",
  "description": "NEW Обед",
  "calories": 777,
  "user": null
  }'
  ````

### Update meal with ID 100008:

  ````
  curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100008' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "id": 100008,
  "dateTime": "2015-05-30T13:00:00",
  "description": "Edit Обед",
  "calories": 555,
  "user": null
  }'
  ````

### Get meal with ID 100008:
`curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100008'`

### Delete meal with ID 100008:
`curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100008'`

### Get all meals: 
`curl --location --request GET 'http://localhost:8080/topjava/rest/meals'`

### Get between with empty fields 
`curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=&startTime=11:00'`
