
./gradlew clean build fatJar



docker login --username=devikaawasthi --password=dvkawa12345

docker build -t devikaawasthi/footballbuzz .
 
sudo docker push devikaawasthi/footballbuzz:latest