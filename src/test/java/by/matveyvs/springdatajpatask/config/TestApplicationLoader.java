package by.matveyvs.springdatajpatask.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.annotation.Rollback;

@TestConfiguration
@Rollback(false)
public class TestApplicationLoader {
}
