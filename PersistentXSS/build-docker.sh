TAG=$(date "+%Y%m%d-%H%M")
docker build -t persistent-xss:$TAG -t persistent-xss:latest .
docker tag persistent-xss:$TAG altairbob/persistent-xss:$TAG
docker tag persistent-xss:$TAG altairbob/persistent-xss:latest