FROM jboss/wildfly

COPY data/mysql-connector-java-5.1.40-bin.jar /opt/jboss/wildfly/standalone/deployments/

COPY data/standalone.xml /opt/jboss/wildfly/standalone/configuration/

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

ADD /autodeploy/gamificationApp-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
