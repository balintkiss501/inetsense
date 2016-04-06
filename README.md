# iNETSense
iNETSense

How to run:

```bash
mvn clean install -P memdb
java -jar target/inetsense-server-0.0.1-SNAPSHOT
curl -X POST -H "Content-Type: application/json" -d @valid-testdata.json http://localhost:8080/message-endpoint
```
