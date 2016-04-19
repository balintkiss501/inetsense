# inetsense-webclient


Pls check the INS-15-task comment section!


## Develop

1. Download and install the [Sencha cmd](https://www.sencha.com/products/sencha-cmd/)

2. Download and extract the [ExtJS SDK 6.0](http://www.sencha.com/products/extjs/evaluate/)

3. Go to the project folder `inetsense/inetsense-webclient/iNETSense-frontend`
4. Add the ext framework sdk to the project via the following command:
`sencha app upgrade /path/to/ext/sdk/ext-6.0.0`

5. Build and start the project via  `sencha app watch`

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
- write howto about starting a released version of the frontend-client (no need to download sdk or sencha cmd)
- add a pom.xml / maven build option
- optonal: add frontend gui test
