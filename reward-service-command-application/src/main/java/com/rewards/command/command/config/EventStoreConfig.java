package com.rewards.command.command.config;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventStoreConfig {

    @Bean
    public EventStoreDBClient eventStoreDBClient() {
        // Define the connection string. The parameter "tls=false" is used since our local docker is running in insecure mode.
        String connectionString = "esdb://localhost:2114?tls=false";
        EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow(connectionString);
        return EventStoreDBClient.create(settings);
    }

}
