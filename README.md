STIXviz-webapp
==============

temp place for web app

Need at add java-taxii.jar to your local maven repository

If using Netbeans:
1) In Maven project open "Add dependency" dialog 
2) Make up some groupId, artifactId and version and fill them, OK. 
3) Dependency will be added to the pom.xml and will appear under "Libraries" node of maven project 
4) Right-click Lib node and "manually install artifact", fill the path to the jar 
Jar should be installed to local Maven repo with coordinates entered in step 2) 
ref: http://forums.netbeans.org/topic22907.html:

if using local Maven:
$ mvn install:install-file -Dfile=<path-to-file> -DgroupId=org.mitre.stix -DartifactId=java-taxii -Dversion=1.0.0 -Dpackaging=jar
ref:http://www.mkyong.com/maven/how-to-include-library-manully-into-maven-local-repository/


Pom dependency:
        <dependency>
            <groupId>org.mitre.stix</groupId>
            <artifactId>java-taxii</artifactId>
            <version>1.0.0</version>
        </dependency>
