mvn clean install -f ../RMessageSystem/pom.xml
mvn clean install -f ../RMessageClient/pom.xml
mvn clean package -f ../RFrontendService/pom.xml
mvn clean package -f ../RDBService/pom.xml
