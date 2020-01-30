## openapi api.client
_Kazan QA meetup_

## presentation

## how to run

#### run SUT

0. Start test server 
```
docker run  --name swaggerapi-petstore2 -d -p 8080:8080 swaggerapi/petstore3:unstable java -jar -DswaggerUrl=openapi.yaml /swagger-petstore/jetty-runner.jar /swagger-petstore/server.war
```
1. Check server http://localhost:8080/

#### generate client

0. Generate client from openapi.json
```
docker run --rm -v ${PWD}:/depot openapitools/openapi-generator-cli generate \
     -i depot/openapi.json \
     -g java \
     -o /depot/client/
```
1. Follow steps in client's README.md
2. Run tests in "petstore"

## Links
0. https://github.com/OpenAPITools/openapi-generator
1. https://openapi-generator.tech/

