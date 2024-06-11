Gatling test for battleship
===========================================

Test all:

```console
sbt Gatling/test
```


Run specific test:

```console
sbt 'Gatling/testOnly gatling.PersistenceBaseItSimulation'
sbt 'Gatling/testOnly gatling.PersistenceEnduranceItSimulation'
sbt 'Gatling/testOnly gatling.PersistenceLoadItSimulation'
sbt 'Gatling/testOnly gatling.PersistenceStressItSimulation'
sbt 'Gatling/testOnly gatling.PersistenceSparkItSimulation'
```

