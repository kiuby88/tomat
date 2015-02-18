

TOMAT 
==================

This project has been developed as a proof of concept to translate between the standards  [TOSCA][1] and [CAMP][2].

To deploy the generated CAMP plans we use [Brooklyn][3] as a standard implementation.


The Project
-------------------
TOSCA allows to describe an application (or any system) and its deployment management through the [topology and plans][4].   In this sense, TOMAT is a tool to generate a Brooklyn plan **from only a TOSCA topology** (understanding Brooklyn as a CAMP implementation).

Then, the generated plan is able to deployed using Brooklyn, check the [usage instructions][6].

Project structure
-------------------
Brooklyn is split into the following subprojects:

- **core**: contains the necessary logic to process and translate the TOSCA topology.
- **cli**: backing implementatio for TOMAT's command line interface.
- **dist**: builds TOMAT jars executable.





Building and Usage
-------------------
To build the project first clone the repository.

```
git clone http://gitlab.scenic.uma.es/josec/tomat.git
cd tomat
```

Then, you could compile the project using maven:
```
mvn clean install
```

Next, you can launch using the cli with

```
cd dist/taget/tomat-dist/bin
tomat %arguments
```

Of course you can also use the generated jar with dependencies:
```
cd dist/target/tomat-dist/
java -jar tomat-jar-with-dependencies.jar %arguments
``` 

Arguments, ```%arguments``` describe the input of the command. You can check the expected commands:
```
tomat help 
```

You can check the command arguments using ```tomat help %command```, for example:

```
tomat help translate
```

###Translation

You can find a TOSCA topology [example in][7]:

> tomat/dist/target/tomat-dist/resources/Chat-Application-JBoss.xml

This topology could be translated with:
```
tomat Chat-Application-JBoss.xml -o Chat-Application-JBoss.yaml
```

You can find the returned generation [plan in][8]:
> tomat/dist/target/tomat-dist/resources/Chat-Application-JBoss.yaml


Limitations
-------------------
Currently, TOMAt is an alpha version and supports only:

- Java Applications: using JBoss, Jetty and TOMCAT.
- MySQL: a connected database.
- XML TOSCA topology.
- YAML Brooklyn Blue Prints generation.

TODO
-------------------
- Adding more supported technology translations. e.g. PHP or node.
- Increasing the supported database connection.
- Adding more target translation technologies.


[1]: https://www.oasis-open.org/committees/tosca/
[2]: https://www.oasis-open.org/committees/camp/
[3]: https://brooklyn.incubator.apache.org/
[4]: http://docs.oasis-open.org/tosca/TOSCA/v1.0/os/TOSCA-v1.0-os.html#_Toc356403643
[6]: https://brooklyn.incubator.apache.org/v/latest/start/index.html
[7]: https://gitlab.scenic.uma.es/josec/tomat/blob/master/dist/src/main/dist/resources/Chat-Application-JBoss.xml
[8]: https://gitlab.scenic.uma.es/josec/tomat/blob/master/dist/src/main/dist/resources/Chat-Application-JBoss.yaml