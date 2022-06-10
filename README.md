# Intelligent home using zeroc ice

### Req.
1. bash
2. gradle
3. slice2java
4. slice2py
5. python with zeroc-ice module
6. docker, docker-compose

##### build and running server
```commandLine
./run.sh
```
it executes full build and run dockers (also compile java client and dependencies for python client)
##### running client
python one:
```commandLine
./scripts/pyclient.sh
```
java one:
```commandLine
./scripts/client.sh
```