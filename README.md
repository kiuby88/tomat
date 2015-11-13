

![TOMAT](https://gitlab.scenic.uma.es/josec/tomat/raw/master/dist/src/main/dist/resources/icon/tomat.png?raw=true "TOMAT") TOMAT
==================

This project has been developed as a proof of concept to translate between the standards  [TOSCA][1] and [CAMP][2].

To deploy the generated CAMP plans we use [Brooklyn][3] as an implementation of the standard.

The Project
-------------------
TOSCA supports the description of an application (or system) and its deployment management by providing a [topology and plans][4] for it.   
In this sense, TOMAT is a tool to generate a Brooklyn plan **from a TOSCA topology** (understanding Brooklyn as a CAMP implementation).

Then, the generated plan may be used to deploy our application using Brooklyn (check the [usage instructions][6]).

Project structure
-------------------
TOMAT has the following subprojects:

- **core** contains the necessary logic to process and translate the TOSCA topology.
- **cli** implementats TOMAT's command line interface.
- **dist**: builds TOMAT jars executable.

Building
------------
To build the project, first clone the repository.

```
git clone http://gitlab.scenic.uma.es/josec/tomat.git
cd tomat
```

Then, you can compile the project using maven:
```
mvn clean install
```

Usage
---------
To launch the tool, you mayuse cli.

```
cd dist/taget/tomat-dist/bin
tomat %arguments
```

Of course, you can also use the generated jar with dependencies:
```
cd dist/target/tomat-dist/
java -jar tomat-jar-with-dependencies.jar %arguments
``` 

Above, ```%arguments``` describe the input of the command. You can check the expected arguments to the command with
```
tomat help 
```

Information on the command arguments may be obteained with ```tomat help %command```, for example:

```
tomat help translate
```

Translation
---------------

You can find a sample TOSCA topology [here][7]:

> tomat/dist/target/tomat-dist/resources/Chat-Application-JBoss.xml

This topology can be translated with the following command:
```
tomat translate Chat-Application-JBoss.xml -o Chat-Application-JBoss.yaml
```
You can have access to the intermediate **Agnostic Graph** if executed in verbose mode:
```
tomat --verbose translate Chat-Application-JBoss.xml -o Chat-Application-JBoss.yaml
```

After the previous command, the file with the [generated plan] is available in [8]:
> tomat/dist/target/tomat-dist/resources/Chat-Application-JBoss.yaml


Launch Brooklyn BluePrints
---------------------------
The generated CAMP plan can then be launched using Brooklyn.
Please, check [Brooklyn's documentation][9] to download, configure and start Brooklyn.

* CAMP plans are handled by Brooklyn 0.9.0 or later versions.

Test
---------
You can check coverage of the current project by using:
```
cd core
mvn cobertura:cobertura
```
The generated coverage report can be found at:
> core/target/site/cobertura/index.html

Limitations
-------------------
Currently, TOMAt is an alpha version and only supports:

- Java Applications: using JBoss, Jetty and TOMCAT.
- MySQL: a connected database.
- XML TOSCA topology.
- YAML Brooklyn Blue Prints generation.

TO-DO list
-------------------
Although the prototype is completely operative, further support is in our list:
- For additional technology translations, e.g., PHP or node.
- For additional database connections.
- For additional target translation technologies.

TOMAT Icon was developed by <div>Icon made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed under <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0">CC BY 3.0</a></div>

[1]: https://www.oasis-open.org/committees/tosca/
[2]: https://www.oasis-open.org/committees/camp/
[3]: https://brooklyn.incubator.apache.org/
[4]: http://docs.oasis-open.org/tosca/TOSCA/v1.0/os/TOSCA-v1.0-os.html#_Toc356403643
[6]: https://brooklyn.incubator.apache.org/v/latest/start/index.html
[7]: https://gitlab.scenic.uma.es/josec/tomat/blob/master/dist/src/main/dist/resources/Chat-Application-JBoss.xml
[8]: https://gitlab.scenic.uma.es/josec/tomat/blob/master/dist/src/main/dist/resources/Chat-Application-JBoss.yaml
[9]: https://brooklyn.io