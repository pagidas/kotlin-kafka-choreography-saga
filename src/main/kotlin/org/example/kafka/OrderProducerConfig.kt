package org.example.kafka

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer

val properties = mapOf(
    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java,
    "schema.registry.url" to "http://localhost:8081"
)