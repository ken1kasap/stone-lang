### Install Gluonj into local maven repository

Download gluonj.jar of GluonJ 2.3 from [here](https://www.csg.ci.i.u-tokyo.ac.jp/projects/gluonj/download.html)

And then command this.

```
mvn install:install-file -Dfile=gluonj.jar -DgroupId=gluonj -DartifactId=gluonj -Dversion=2.3 -Dpackaging=jar -DgeneratePom=true
```
