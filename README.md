Security-Examples
=================

A repo for storing good and bad examples of security for web apps.

## Container Scanning
```
cd /vagrant/PersistentXSS/
bash build-docker.sh
sudo grype persistent-xss:latest -c ../.grype.yaml
```