# Drombler JStore Client Agent

A headless background service which runs on end-user devices (desktops, NAS, IoT devices,...).

The _Drombler JStore Client Agent_ can access multiple [Drombler JStore](https://github.com/Drombler/drombler-jstore) backends.

Note: by installing / using this software you're agreeing to the [Oracle Binary Code License](Oracle-BCL.txt).
Please see the [Oracle Java web site](http://java.oracle.com) for the latest version of this license.

You can get the binary from the [latest release](https://github.com/Drombler/drombler-jstore-client-agent/releases/latest) (early access release).

## Current Goals

   * download and update applications from Drombler JStore
   * download and update applications using a JNLP-like file
   * download and update native components:
      * JREs (supporting multiple parallel major version installations)
      * custom runtimes created by JLink (supporting multiple parallel major version installations)
      * JavaFX native components?
      * Other native components, e.g. SWT?
   * support Security Manager
   * start applications in a configured environment
   * OS integration: 
      * desktop shortcuts
      * URL protocol registration
      * run as native OS service/ deamon


https://puces-blog.blogspot.com/2018/06/the-next-generation-of-java-application.html


## Build the project from sources
```bash
mvn clean install
```
Please note that the develop branch (SNAPSHOT version) of the project might depend on SNAPSHOT versions of other projects.

If you don't want to build the dependent projects as well, please make sure to define a proxy in your [Maven Repository Manager](https://maven.apache.org/repository-management.html) to the following Maven Repository: https://oss.sonatype.org/content/repositories/snapshots/ and include it in your [single group](https://help.sonatype.com/repomanager3/formats/maven-repositories#MavenRepositories-ConfiguringApacheMaven).
