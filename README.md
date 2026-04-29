Security-Examples
=================

A repo for storing good and bad examples of security for web apps.

## Container Scanning
```
cd /vagrant
mvn clean package -s settings.xml -DskipTests
cd PersistentXSS/
bash build-docker.sh
sudo grype persistent-xss:latest -c ../.grype.yaml
```