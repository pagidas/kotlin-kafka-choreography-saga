package org.example.pantry.pubsub

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer

val config: Config = ConfigFactory.load()
private val kafkaConfig = config.getObject("kafka.broker").toConfig()
private val schemaRegistryConfig = config.getObject("kafka.schema-registry").toConfig()

val producerProperties by lazy {
    mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConfig.getString("url"),
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java,
        "schema.registry.url" to schemaRegistryConfig.getString("url")
    )
}

val consumerProperties by lazy {
    mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConfig.getString("url"),
        ConsumerConfig.GROUP_ID_CONFIG to "pantry-group",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java,
        KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true,
        "schema.registry.url" to schemaRegistryConfig.getString("url")
    )
}