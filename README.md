## Run kafka containers

```cd microservices-demo/docker-compose```

```docker-compose -f common.yml -f kafka_cluster.yml up```    

## Run kafkacat container

```docker run -it --network=host confluentinc/cp-kafkacat kafkacat -L -b localhost:19092```

## Run the application

```cd microservices-demo```

```mvn install -DskipTests```