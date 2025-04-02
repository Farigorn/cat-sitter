import com.otus.sy.catsitter.api.model.*
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import config.MkplAppSettings
import config.RabbitConfig
import config.RabbitExchangeConfiguration
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.AfterClass
import org.junit.BeforeClass
import org.testcontainers.containers.RabbitMQContainer
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RabbitMqTest {

    companion object {
        const val exchange = "test-exchange"
        const val exchangeType = "direct"
        const val RMQ_PORT = 5672

        private val container = run {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
            RabbitMQContainer("rabbitmq:latest").apply {
//                withExposedPorts(5672, 15672) // Для 3-management
                withExposedPorts(RMQ_PORT)
            }
        }


        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            container.start()
//            println("CONTAINER PORT (15672): ${container.getMappedPort(15672)}")
        }

        @AfterClass
        @JvmStatic
        fun afterAll() {
            container.stop()
        }
    }

    private val appSettings = MkplAppSettings(
        rabbit = RabbitConfig(
            port = container.getMappedPort(RMQ_PORT)
        ),
        controllerConfig = RabbitExchangeConfiguration(
            keyIn = "in-v1",
            keyOut = "out-v1",
            exchange = exchange,
            queue = "v1-queue",
            consumerTag = "v1-consumer-test",
            exchangeType = exchangeType
        ),
    )
    private val app = RabbitApp(appSettings = appSettings)

    @BeforeTest
    fun tearUp() {
        app.start()
    }

    @AfterTest
    fun tearDown() {
        println("Test is being stopped")
        app.close()
    }

    @Test
    fun BookingCreateTest() {
        val (keyOut, keyIn) = with(appSettings.controllerConfig) { Pair(keyOut, keyIn) }
        val (tstHost, tstPort) = with(appSettings.rabbit) { Pair(host, port) }
        ConnectionFactory().apply {
            host = tstHost
            port = tstPort
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, apiMapper.writeValueAsBytes(bookingCreate))

                runBlocking {
                    withTimeoutOrNull(3000L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = apiMapper.readValue(responseJson, BookingCreateResponse::class.java)
                val expected = MkplBookingStub.get()

                assertEquals(expected.ownerId.asUUID(), response.booking?.ownerId)
                assertEquals(expected.sitterId.asUUID(), response.booking?.sitterId)
            }
        }
    }


    private val bookingCreate = with(MkplBookingStub.get()) {
        BookingCreateRequest(
            ownerId = ownerId.asUUID(),
            sitterId = sitterId.asUUID(),
            notes = notes,
            requestType = "create",
            debug = BookingDebug(
                mode = BookingRequestDebugMode.STUB,
                stub = BookingRequestDebugStubs.SUCCESS
            )
        )
    }
}