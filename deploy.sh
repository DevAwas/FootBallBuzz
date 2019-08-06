docker login 
docker pull devikaawasthi/footballbuzz:latest

docker run -d -p 80:80 ubuntu:16.04.4 footballbuzz:latest