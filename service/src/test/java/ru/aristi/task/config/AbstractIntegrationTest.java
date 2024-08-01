package ru.aristi.task.config;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.aristi.task.TestConfig;
import ru.aristi.task.config.container.PostgresContainer;

@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest implements PostgresContainer {
}
