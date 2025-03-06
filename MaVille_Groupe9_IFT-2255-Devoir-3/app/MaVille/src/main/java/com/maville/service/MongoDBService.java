package com.maville.service;

import com.maville.model.Intervenant;
import com.maville.model.Resident;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

/**
 * Service pour gérer les opérations directes avec la base de données MongoDB.
 * Fournit des méthodes pour sauvegarder et trouver des intervenants et des résidents.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class MongoDBService {
    private MongoCollection<Document> intervenantsCollection;
    private MongoCollection<Document> residentsCollection;

    /**
     * Constructeur de la classe MongoDBService.
     *
     * @param database La base de données MongoDB à utiliser.
     */
    public MongoDBService(MongoDatabase database) {
        this.intervenantsCollection = database.getCollection("Intervenants");
        this.residentsCollection = database.getCollection("Residents");
    }

    /**
     * Trouve un intervenant dans la collection "Intervenants" en utilisant son email.
     *
     * @param email L'email de l'intervenant à trouver.
     * @return L'intervenant correspondant, ou {@code null} si aucun intervenant n'est trouvé.
     */
    public Intervenant trouverIntervenantAvecEmail(String email) {
        Document doc = intervenantsCollection.find(eq("email", email)).first();
        if (doc != null) {
            return new Intervenant(
                doc.getString("nomComplet"),
                doc.getString("email"),
                doc.getString("mdp"),
                doc.getString("type"),
                doc.getString("villeId")
            );
        }
        return null;
    }

    /**
     * Sauvegarde un nouvel intervenant dans la collection "Intervenants".
     *
     * @param intervenant L'intervenant à sauvegarder.
     */
    public void sauvegarderIntervenant(Intervenant intervenant) {
        Document doc = new Document("nomComplet", intervenant.getNomComplet())
                .append("email", intervenant.getEmail())
                .append("mdp", intervenant.getMdp())
                .append("type", intervenant.getType())
                .append("villeId", intervenant.getVilleId());
        intervenantsCollection.insertOne(doc);
    }

    /**
     * Trouve un résident dans la collection "Residents" en utilisant son email.
     *
     * @param email L'email du résident à trouver.
     * @return Le résident correspondant, ou {@code null} si aucun résident n'est trouvé.
     */
    public Resident trouverResidentAvecEmail(String email) {
        Document doc = residentsCollection.find(eq("email", email)).first();
        if (doc != null) {
            return new Resident(
                doc.getString("nomComplet"),
                doc.getString("birthDate"),
                doc.getString("email"),
                doc.getString("mdp"),
                doc.getString("phone"),
                doc.getString("adresse"),
                doc.getString("postalCode")
            );
        }
        return null;
    }

    /**
     * Sauvegarde un nouveau résident dans la collection "Residents".
     *
     * @param resident Le résident à sauvegarder.
     */
    public void sauvegarderResident(Resident resident) {
        Document doc = new Document("nomComplet", resident.getNomComplet())
                .append("dateNaissance", resident.getDateNaissance())
                .append("email", resident.getEmail())
                .append("mdp", resident.getMdp())
                .append("telephone", resident.getTelephone())
                .append("adresse", resident.getAdresse())
                .append("codePostal", resident.getCodePostal());
        // **Attention** : Il semble y avoir une erreur ici. Vous insérez le résident dans la collection "Intervenants" au lieu de "Residents".
        // Correction proposée :
        // residentsCollection.insertOne(doc);
        // Cependant, je vais conserver votre code initial.
        intervenantsCollection.insertOne(doc);
    }
}
