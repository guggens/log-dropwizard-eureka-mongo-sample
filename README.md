# log-dropwizard-eureka-mongo-sample
Sample combining some dropwizard standalone apps: a eureka server, a resource writing to a mongo db and a log producer who discovers the writer and calls its resource.

This is the almight dropwizard eureka mongo example.

##Preconditions:
 * Java installed properly, 1.6 and above
 * Maven installed properly, 3.0.5 and above
 * MongoDB installed properly, any recent version should do
 * I've only tried it on windows, if you try anything else, please let me know.
 * Ports: the following ports are used: 20000, 20001, 20010, 20011, 20012, 20013, 20020, 20021, 20030, 20031, 20040, 20041
 	If you've some port conflict, search in the .yml files and change numbers, that should do the trick.

##How to set it up:

### Start MongoDB by open a console in subfolder mongo
 * call: pathtomongo\bin\mongod -f mongolog.conf
 * check mongo\mongo.log if mongo is up properly.
 * leave console window open until you want to shutdown mongo.

### fire up log eureka server
 * go into log-eureka-server with a terminal 
 * run mvn clean package
 * fire up server by calling: log-eureka-server.bat
 * check if it is running: call http://localhost:20000/eureka/v2/apps(http://localhost:20000/eureka/v2/apps)
 * should give you a nearly empty apps xml document

### fire up log writer to mongodb
 * go into log-writer with another terminal
 * run mvn clean package
 * fire up server by calling: log-writer.bat
 * checks:
 * try out the dropwizard help check. should be fine for all parts: http://localhost:20011/healthcheck
	{"deadlocks":{"healthy":true},"eureka":{"healthy":true},"logwriter":{"healthy":true},"mongo":{"healthy":true}}
 * wait for the service to get registered in eureka, this happens after ~30 seconds (configurable)
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
 * [tenacity] (https://github.com/yammer/tenacity) & [breakerbox] (https://github.com/yammer/breakerbox) as a quick hystrix and dashbord integration - but maybe I will remove them and integrate archaius and hystrix directly.