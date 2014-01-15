
#Osgi-Extensible

A demo of dynamically loading rules plugins into an app via an embedded OSGi container. 

The code to embed bundles was taken from [How To Embed OSGi by Neil Bartlett](http://njbartlett.name/2011/03/07/embedding-osgi.html)

Build and run it with: 

```sh
# Build the core app classes used by the plugins
cd osgi-main-app/
mvn clean compile install assembly:single
# Now build the plugins
cd ..
mvn package
# Run the core app which will load the plugins
java -jar ./osgi-main-app/target/osgi-main-app-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

The expected output is: 

```sh
using boot delegation and parent=framework
RuleOne Service Registered
RuleTwo Service Registered
services.size(): 2
RuleOne gives a magic number output of RuleOne:NUMBER_99
RuleTwo gives a magic number output of RuleTwo:NUMBER_42
awaiting stop
```

Use Ctrl+c to kill the app. 
