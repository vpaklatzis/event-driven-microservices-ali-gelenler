## Run kafka and app containers

`cd microservices-demo/docker-compose`

`docker-compose -f common.yml -f kafka_cluster.yml -f services.yml up`

or

`docker-compose up`

## Run kafkacat container

`docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092`

## Build the application and a docker image

`cd microservices-demo`

`mvn install -DskipTests`