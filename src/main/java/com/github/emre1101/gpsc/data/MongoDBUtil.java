package com.github.emre1101.gpsc.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author emreg
 */
public class MongoDBUtil {

    private static MongoDBUtil _instance = null;

    private static final Logger logger = LoggerFactory.getLogger(MongoDBUtil.class);

    private static final String CONFIG_FILE = "config/db/mongodb-config.properties";
    private static final String CONFIG_IS_NULL_OR_ZERO_LENGTH = " is null or is not defined! Please check config file: ";

    private static String username;
    private static String password;
    private static String database;

    private MongoDBUtil() {
        loadConfig();
    }

    public static MongoDBUtil getInstance() {

        if (_instance == null) {
            synchronized (MongoDBUtil.class) {
                if (_instance == null) {
                    _instance = new MongoDBUtil();
                }
            }
        }
        return _instance;
    }
    
    
    public static void insert(Document doc){
		MongoDatabase db = MongoDBUtil.getInstance().getMongoClient().getDatabase("gpsc");
		MongoCollection<Document> coll = db.getCollection("appPagesNew");
		coll.insertOne(doc);
	}

    public MongoClient getMongoClient(){
    	CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new PermissionCodec()),
                MongoClient.getDefaultCodecRegistry());  
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
//        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(),options);
        return  mongoClient;
    }

    private void loadConfig() {

        final String configFile = CONFIG_FILE;
        final Properties configEntries = new Properties();
        InputStream inputStream;

        try {
            inputStream = new FileInputStream(configFile);
            configEntries.load(inputStream);

            username = configEntries.getProperty("username");
            if (username == null || username.length()==0) {
                final String errorMsg = "username" + CONFIG_IS_NULL_OR_ZERO_LENGTH + configFile;
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }

            password = configEntries.getProperty("password");
            if (password == null || password.length()==0) {
                final String errorMsg = "password" + CONFIG_IS_NULL_OR_ZERO_LENGTH + configFile;
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
            database = configEntries.getProperty("database");
            if (database == null || database.length()==0) {
                final String errorMsg = "database" + CONFIG_IS_NULL_OR_ZERO_LENGTH + configFile;
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }


        } catch (IOException e) {
            final String errorMsg = "Failed to load mongodb config: " + configFile;
            throw new IllegalStateException(errorMsg, e);
        }
    }
    



}
