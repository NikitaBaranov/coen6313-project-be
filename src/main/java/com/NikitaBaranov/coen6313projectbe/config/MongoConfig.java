package com.NikitaBaranov.coen6313projectbe.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfig {
    private static final String MONGO_DB_NAME = "6313-project";

    @Value("${PROJECT_DATA_TIER_SERVICE_PORT}")
    private String mongoPort;
    @Value("${PROJECT_DATA_TIER_SERVICE_HOST}")
    private String mongoHost;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoClient mongoClient = new MongoClient(mongoHost, Integer.parseInt(mongoPort));
        return new SimpleMongoDbFactory(mongoClient, MONGO_DB_NAME);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }
}