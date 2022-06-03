#powershell
cd client
mvn -q compile
mvn -q package
cd ..
mv client/target/*s.jar jars/client.jar
cd server
mvn -q compile
mvn -q package
cd ..
mv server/target/*s.jar jars/server.jar