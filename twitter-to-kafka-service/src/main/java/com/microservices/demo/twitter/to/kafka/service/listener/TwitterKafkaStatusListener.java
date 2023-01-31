package com.microservices.demo.twitter.to.kafka.service.listener;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafka.producer.config.service.KafkaProducerService;
import com.microservices.demo.twitter.to.kafka.service.transformer.TwitterStatusToAvroTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Slf4j
@Component
public class TwitterKafkaStatusListener extends StatusAdapter {

    private final KafkaConfigData kafkaConfigData;

    private final KafkaProducerService<Long, TwitterAvroModel> kafkaProducer;

    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

    public TwitterKafkaStatusListener(KafkaConfigData kafkaConfigData,
                                      KafkaProducerService<Long, TwitterAvroModel> kafkaProducer,
                                      TwitterStatusToAvroTransformer twitterStatusToAvroTransformer) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.twitterStatusToAvroTransformer = twitterStatusToAvroTransformer;
    }

    @Override
    public void onStatus(Status status) {
        log.info("Received twitter status with text: {}. Sending to kafka topic: {}",
                status.getText(), kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
