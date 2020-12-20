package org.example.kafka

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.example.avro.order.events.OrderEvent
import org.slf4j.LoggerFactory
import java.util.*

object KProducer {
    private val log = LoggerFactory.getLogger(KProducer::class.java)

    operator fun invoke() {
        val producer = KafkaProducer<String, OrderEvent>(properties)

        for (i in 0..4) {
            val orderEvent = OrderEvent("OrderCreated", UUID.randomUUID().toString(), "PENDING")
            try {
                log.info("Pushing to topic: test-topic message: $orderEvent")
                val recordMeta = producer.send(ProducerRecord("test-topic", i.toString(), orderEvent)).get()
                log.debug("Record sent with key $i to partition ${recordMeta.partition()} with offset ${recordMeta.offset()}")
            } catch (e: Exception) {
                log.error("Error in sending record: $orderEvent")
            }
        }
    }

    private val properties = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java,
        "schema.registry.url" to "http://localhost:8081"
    )
}