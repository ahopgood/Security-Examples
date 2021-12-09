## Persistent XSS
An example application to practise and demonstrate persistent cross site scripting (XSS) attacks.  

This application uses an in-memory database and has no external dependencies.  
There is placeholder data added via flyway migrations on start up to provide a consistent start environment.  
Restarting the application **will** erase any data that the application was not pre-seeded with.  

### Building
#### Maven
```
mvn clean install
```
#### Docker
**Note** Currently the Dockerfile requires the maven build to have been run **first!**.  
 
There is a `Dockerfile` located in the root of this module.  
It can be used to build a docker image of the project with the following command `docker build -t persistent-xss:1 .` from the directory the Dockerfile is in (root module directory).  
You can change the name and version of the tagged image (`-t persistent-xss:1`) to anything you want.  
##### Tagging
Using the `build-docker.sh` script will create two tags of your docker build:
1. `latest` tag which is used in the docker compose file
2. `yyyymmdd-hhMM` resulting in a timestamp tag e.g. `20210120-1050`
### Running
#### Docker Compose
There is a docker-compose file called `docker-compose.yml` in the repository root which will start a service named `persistent-xss` if you use the following command:
```
docker-compose up --quiet-pull -d persistent-xss
```
**Note** this docker compose file expects the **tagged** image name to be `persistent-xss:latest`, if you've changed this then you need to update the docker compose file.  

### Developing
Run the `Application.java` class file to run the application.    

If you don't have it installed then you can install [browsersync](https://browsersync.io/) to enable automatic refresh of web assets on file saves.  
You can start up browser sync like so 
```
browser-sync start --server --files css/*.css,js/*.js,*.html
``` 
when in the `/src/main/resources/static/` directory.  