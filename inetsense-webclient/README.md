# inetsense-webclient


## Production

1. Go to the project folder `inetsense/inetsense-webclient/` and execute the `mvn clean install` command for create the needed dependency for the webserver.


## Develop


1. Download and install the [Sencha cmd](https://www.sencha.com/products/sencha-cmd/)

2. Download and extract the [ExtJS SDK 6.0](http://www.sencha.com/products/extjs/evaluate/)

3. Set the `SENCHA_SDK` and the `SENCHA_CMD` environmental variables

ie. on unix:
```
export SENCHA_SDK="/home/hello/Applications/ext-6.0.0/build"
export SENCHA_CMD="/home/hello/Applications/sencha-cmd/bin/Sencha/Cmd/sencha"

export MAVEN_OPTS="-Xms256m -Xmx512m"
````

4. Go to the project folder `inetsense/inetsense-webclient/` and execute the `mvn clean install -P rebuild` command for adding the needed sdk files to the project

5. Start developing via  `mvn clean install -P watch` command

6. ???

7. Profit


## Screenshots

![Home](/inetsense-webclient/docs/pictures/home.png?raw=true "Landing screen")

![Probes](/inetsense-webclient/docs/pictures/probes.png?raw=true "Probes table")

![Users](/inetsense-webclient/docs/pictures/users.png?raw=true "User table")

![Charts](/inetsense-webclient/docs/pictures/chart-poc.png?raw=true "Charts")


## TODO
- wire the server controller and the frontend stores (if it possible use fallback)
- upload extjs SDK to the repositoy or store it somewhere (dropbox?)
- optonal: add frontend gui test
