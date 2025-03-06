package com.maville.repository;

import com.maville.model.Intervenant;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;

/**
 * Repository pour gérer les opérations CRUD liées aux intervenants dans la base de données MongoDB.
 * Fournit des méthodes pour sauvegarder, trouver et mettre à jour les informations des intervenants.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class IntervenantRepository {
    private MongoCollection<Document> intervenantsCollection;

    /**
     * Constructeur de la classe IntervenantRepository.
     *
     * @param database La base de données MongoDB à utiliser.
     */
    public IntervenantRepository(MongoDatabase database) {
        this.intervenantsCollection = database.getCollection("Intervenants");
    }

    /**
     * Sauvegarde un nouvel intervenant dans la collection "Intervenants".
     *
     * @param intervenant L'intervenant à sauvegarder.
     */
    public void sauvegarder(Intervenant intervenant) {
        Document doc = new Document("nomComplet", intervenant.getNomComplet())
                .append("email", intervenant.getEmail())
                .append("mdp", intervenant.getMdp())
                .append("type", intervenant.getType())
                .append("villeId", intervenant.getVilleId())
                .append("aCandidature", intervenant.isACandidature());
        intervenantsCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        intervenant.setId(id);
    }

    /**
     * Trouve un intervenant dans la collection "Intervenants" en utilisant son email.
     *
     * @param email L'email de l'intervenant à trouver.
     * @return L'intervenant correspondant, ou {@code null} si aucun intervenant n'est trouvé.
     */
    public Intervenant trouverAvecEmail(String email) {
        Document doc = intervenantsCollection.find(eq("email", email)).first();
        if (doc != null) {
            Intervenant intervenant = new Intervenant(
                doc.getString("nomComplet"),
                doc.getString("email"),
                doc.getString("mdp"),
                doc.getString("type"),
                doc.getString("villeId")
            );
            intervenant.setId(doc.getObjectId("_id"));
            intervenant.setACandidature(doc.getBoolean("aCandidature", false));
            return intervenant;
        }
        return null;
    }    

    /**
     * Vérifie si un intervenant a une candidature active.
     *
     * @param intervenantId L'identifiant de l'intervenant.
     * @return {@code true} si l'intervenant a une candidature active, {@code false} sinon.
     */
    public boolean aCandidatureActive(String intervenantId) {
        Document doc = intervenantsCollection.find(eq("_id", new ObjectId(intervenantId)))
                                .projection(new Document("aCandidature", 1))
                                .first();
        if (doc != null) {
            return doc.getBoolean("aCandidature", false);
        }
        return false;
    }

    /**
     * Marque la candidature d'un intervenant comme active ou inactive.
     *
     * @param intervenantId L'identifiant de l'intervenant.
     * @param statut        Le statut à définir pour la candidature ({@code true} pour active, {@code false} pour inactive).
     */
    public void marquerCandidature(String intervenantId, boolean statut) {
        Document filter = new Document("_id", new ObjectId(intervenantId));
        Document update = new Document("$set", new Document("aCandidature", statut));
        intervenantsCollection.updateOne(filter, update);
    }
}
