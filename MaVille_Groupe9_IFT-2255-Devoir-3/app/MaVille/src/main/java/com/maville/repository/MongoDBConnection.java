package com.maville.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Classe utilitaire pour établir et gérer la connexion à la base de données MongoDB.
 * Utilise le pattern Singleton pour assurer une seule instance de la connexion.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class MongoDBConnection {
    private static MongoDatabase database;

    /**
     * Obtient la base de données MongoDB en utilisant la chaîne de connexion fournie.
     * Si la connexion n'est pas encore établie, elle est initialisée.
     *
     * @param connectionString La chaîne de connexion MongoDB.
     * @return L'objet {@link MongoDatabase} représentant la base de données.
     */
    public static MongoDatabase getDatabase(String connectionString) {
        if (database == null) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("MaVille");
        }
        return database;
    }
}
