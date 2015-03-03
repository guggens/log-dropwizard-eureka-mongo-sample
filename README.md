# log-dropwizard-eureka-mongo-sample
Sample combining some dropwizard standalone apps: a eureka server, a resource writing to a mongo db and a log producer who discovers the writer and calls it resource.

this is the almight dropwizard eureka mongo example.

How to set it up:

1. Install MongoDB to some place where you feel apropriate.
2. Start MongoDB by open a console in subfolder mongo
2a call: pathtomongo\bin\mongod -f mongolog.conf
2b check mongo\mongo.log if mongo is up properly.
2c leave console window open until you want to shutdown mongo.

3. fire up log eureka server
3a go into log-eureka-server with a terminal 
3b run mvn clean package
3c fire up server by calling: java -jar target\log-eureka-server-0.0.1.jar server src/main/resources/log-eureka-server.yml
3d check if it is running: call http://localhost:20000/eureka/v2/apps (you can change port in the yml file if needed).

4. fire up log writer to mongodb
4a go into log-writer with another terminal
4b run mvn clean package
4c fire up server by calling: java -jar target\log-writer-0.0.1.jar server src/main/resources/log-writer.yml
4d checks:
4e try out the dropwizard help check. should be fine for all parts:
	http://localhost:20011/healthcheck
	{"deadlocks":{"healthy":true},"eureka":{"healthy":true},"logwriter":{"healthy":true},"mongo":{"healthy":true}}
	
4f mark the service as up by making a POST on http://localhost:20011/tasks/up , f.e. with restclient firefox plugin or curl.
4g check http://localhost:20000/eureka/v2/apps , it should have the logwriter listed as installed app.
4h check http://localhost:20010/log/list should give you an empty list.
4i check http://localhost:20010/log?name=samplelogentry should give you a simple result and write a log entry.
4j check http://localhost:20010/log/list again, you now should have a log entry.

5. fire up log producer
5a go into log-producer with a terminal
	 run mvn clean package
	 fire up server by calling: java -jar target\log-producer-0.0.1.jar server src/main/resources/log-producer.yml
	 call http://localhost:20020/produce - it should call log on the log-writer and produce another entry in mongo db.
	 check http://localhost:20010/log/list again, there should be one more entry.

