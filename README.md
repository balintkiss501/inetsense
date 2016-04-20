# iNETSense

## iNETSense Server:

How to run:

```bash
mvn clean install -P memdb
java -jar target/inetsense-server-0.0.1-SNAPSHOT
curl -X POST -H "Content-Type: application/json" -d @valid-testdata.json http://localhost:8080/message-endpoint
```

For developers: for valid JSON format, follow this schema

http://157.181.161.108/wiki/doku.php?id=jsonschema

## iNETSense Probe:

After the build is finished, run with the `-config PATH_TO_CONFIG` parameter, to provide a custom configuration.
The default `config.ini` file is this:

```ini
; probe config ini
[server]
host = localhost
port = 8080

[probe]
probe-id = Test id
test-interval = 10000
```