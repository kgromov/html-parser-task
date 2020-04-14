FROM tomcat
MAINTAINER Gromov Konstantin

ADD build/libs/parser-demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/parser.war
EXPOSE 8080
CMD ["catalina.sh", "run"]

#docker build -t parser .
#docker run -it --rm -p 8888:8080 parser