#powershell
cd server
mvn -q compile
echo 'compiled'
mvn -q package
echo 'packaged'
cd ..
mv server/target/*s.jar jars/server.jar