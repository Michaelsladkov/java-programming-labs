#bash
cd comm*
mvn compile 1>/dev/null
mvn package 1>/dev/null
mvn install 1>/dev/null
echo refreshed
cd ..
