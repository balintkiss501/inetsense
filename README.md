# iNETSense
iNETSense

How to run:

```bash
mvn clean install -P memdb
java -jar target/inetsense-server-0.0.1-SNAPSHOT
curl -X POST -H "Content-Type: application/json" -d @valid-testdata.json http://localhost:8080/message-endpoint
```

For developers: for valid JSON format, follow this schema

http://157.181.161.108/wiki/doku.php?id=jsonschema
