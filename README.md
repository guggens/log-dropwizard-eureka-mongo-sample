# log-dropwizard-eureka-mongo-sample
Sample combining some dropwizard standalone apps: a eureka server, a resource writing to a mongo db and a log producer who discovers the writer and calls it resource.

this is the almight dropwizard eureka mongo example.

##How to set it up:

### Install MongoDB to some place where you feel apropriate.
### Start MongoDB by open a console in subfolder mongo
 * call: pathtomongo\bin\mongod -f mongolog.conf
 * check mongo\mongo.log if mongo is up properly.
 * leave console window open until you want to shutdown mongo.

### fire up log eureka server
 * go into log-eureka-server with a terminal 
 * run mvn clean package
 * fire up server by calling: java -jar target\log-eureka-server-0.0.1.jar server src/main/resources/log-eureka-server.yml
 * check if it is running: call http://localhost:20000/eureka/v2/apps (you can change port in the yml file if needed).

### fire up log writer to mongodb
 * go into log-writer with another terminal
 * run mvn clean package
 * fire up server by calling: java -jar target\log-writer-0.0.1.jar server src/main/resources/log-writer.yml
 * checks:
 * try out the dropwizard help check. should be fine for all parts: http://localhost:20011/healthcheck
	{"deadlocks":{"healthy":true},"eureka":{"healthy":true},"logwriter":{"healthy":true},"mongo":{"healthy":true}}
	
 * mark the service as up by making a POST on http://localhost:20011/tasks/up , f.e. with restclient firefox plugin or curl.
 * check http://localhost:20000/eureka/v2/apps , it should have the logwriter listed as installed app.
 * check http://localhost:20010/log/list should give you an empty list.
 * check http://localhost:20010/log?name=samplelogentry should give you a simple result and write a log entry.
 * check http://localhost:20010/log/list again, you now should have a log entry.

### fire up log producer
 * go into log-producer with a terminal
 * run mvn clean package
 * fire up server by calling: java -jar target\log-producer-0.0.1.jar server src/main/resources/log-producer.yml
 * call http://localhost:20020/produce - it should call log on the log-writer and produce another entry in mongo db.
 * check http://localhost:20010/log/list again, there should be one more entry.

### Stuff used to make this:
 * [dropwizard] (http://dropwizard.io/) obviously...
 * [mongodb](https://github.com/eeb/dropwizard-mongo) for mongodb access
 * [eureka client&server](https://github.com/jlewallen/dropwizard-discovery) eureka client and server implemetation