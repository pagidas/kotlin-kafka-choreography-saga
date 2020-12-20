package org.example.kafka

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.example.avro.order.events.OrderEvent
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.temporal.ChronoUnit

object KConsumer {
    private val log = LoggerFactory.getLogger(KConsumer::class.java)

    operator fun invoke() {
        val consumer = KafkaConsumer<String, OrderEvent>(properties).apply {
            subscribe(listOf("test-topic"))
            log.info("Subscribed to topic: test-topic")
        }

        consumer.use {
            while (true) {
                it.poll(Duration.of(100, ChronoUnit.MILLIS)).forEach { record ->
                    log.debug("Offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}")
                }
                consumer.commitAsync()
            }
        }
    }

    private val properties = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java,
            KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.GROUP_ID_CONFIG to "order-event-group",
            "schema.registry.url" to "http://localhost:8081"
    )
}