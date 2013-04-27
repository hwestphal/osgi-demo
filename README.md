OSGi Demonstration
==================

This sample application demonstrates the use of the [Felix](http://felix.apache.org/)
[OSGi](http://www.osgi.org/Specifications/HomePage) implementation together with [Maven](http://maven.apache.org/)
as build system.

Build and run
-------------

    > mvn clean install
    > mvn -P integrationtest verify
    > mvn -f bootstrap/pom.xml exec:exec

* Admin console: [http://localhost:8888/system/console/](http://localhost:8888/system/console/) (`admin`/`admin`)
* Demo webapp: [http://localhost:8888/demo/index.html](http://localhost:8888/demo/index.html)

Slides
------

An accompanying presentation can be watched [here](http://hwestphal.github.io/osgi-demo).
