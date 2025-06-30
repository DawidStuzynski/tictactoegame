package game.tictactoegame

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
abstract class BaseIntegrationTest {

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer("postgres:15").apply {
            withDatabaseName("demo")
            withUsername("demo")
            withPassword("demo")
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }

        @BeforeAll
        @JvmStatic
        fun setup() {
            RestAssured.baseURI = "http://localhost:8080/api"
        }
    }
}