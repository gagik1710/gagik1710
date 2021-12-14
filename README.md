# Run application

## Installation Instructions

**Package Installer**

Make sure you have a docker engine.

* Execute ```mvn clean package && docker-compose up -d```

## Usage

You can find [requests.http](/src/main/resources/requests.http) file in resources folder for api testing

Please use the login request to get the access token.

Make sure you replace the access token on each request.

For job testing, you need to log in as admin Use these credentials

```json
    {
      "email": "admin@admin.com",
      "password": "1234"
    }
```

After a job request call, you can check and verify that files were created by executing this script
```
docker exec -it app ls /demo/app/output/parquet/
```
For Json files
```
docker exec -it app ls /demo/app/output/json/
```