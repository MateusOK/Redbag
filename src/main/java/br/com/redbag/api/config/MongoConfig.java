package br.com.redbag.api.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient, MongoDatabaseFactory mongoDatabaseFactory){
        return GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase());
    }

}
