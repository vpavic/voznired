/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.vpavic.traintracker;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TrainTrackerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @TestConfiguration
    static class Config {

        @Bean
        public PostgreSQLContainer<?> postgreSqlContainer() {
            PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>(
                    DockerImageName.parse("postgres:11.5"));
            postgreSqlContainer.start();
            return postgreSqlContainer;
        }

        @Bean
        public HikariDataSource dataSource(PostgreSQLContainer<?> postgreSqlContainer) {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(postgreSqlContainer.getJdbcUrl());
            dataSource.setUsername(postgreSqlContainer.getUsername());
            dataSource.setPassword(postgreSqlContainer.getPassword());
            return dataSource;
        }

        @Bean
        public GenericContainer<?> redisContainer() {
            GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:5.0.7"))
                    .withExposedPorts(6379);
            redisContainer.start();
            return redisContainer;
        }

        @Bean
        public JedisConnectionFactory redisConnectionFactory(GenericContainer<?> redisContainer) {
            RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(
                    redisContainer.getContainerIpAddress(), redisContainer.getFirstMappedPort());
            return new JedisConnectionFactory(redisConfiguration);
        }

    }

}
