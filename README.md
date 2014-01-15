
#Osgi-Extensible

A demo of dynamically loading rules plugins into an app via an embedded OSGi container. 

The code to embed bundles was taken from [How To Embed OSGi by Neil Bartlett](http://njbartlett.name/2011/03/07/embedding-osgi.html)

The key point is that the two plug-ins loaded by the app have an identically named class MagicNumber which is binary incompatible. This represents evolutions to the business logic over time. OSGi runs these classes in separate class loaders such that they don't interfere with each other.  


##Building And Running 

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

##Anatomy

- The main application ```ExtendableApplication``` loads the OSGi framework and exports the core package ```com.github.simbo1905.osgi``` via the system bundle
- The package ```com.github.simbo1905.osgi``` contains the service interface ```RulesService``` along with the ```RulesInput``` and ```RulesOutput```
- The two plug-in modules build jars with a ```MANIFEST.FM``` which contains:
  - ```Import-Package``` which names the core ```com.github.simbo1905.osgi``` package
  - ```Bundle-Activator``` which names an specific activator class which knows how to instantiate and register the two specific ```RulesService``` implementations
  - Two different ```MagicNumber``` classes with the same name and package but which are binary incompatible with each other used to generate the 'hello world' style output  
- The main application then simply dynamically loads the modules and starts then then looks up the registered services and invokes them
- The two modules have identically named but binary incompatible classes yet are run in separated classloaders with only the core package types shared between the host application and each module

End.
