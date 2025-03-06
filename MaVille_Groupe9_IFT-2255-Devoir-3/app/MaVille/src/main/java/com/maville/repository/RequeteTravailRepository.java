package com.maville.repository;

import com.maville.model.RequeteTravail;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Repository pour gérer les opérations CRUD liées aux requêtes de travail dans la base de données MongoDB.
 * Fournit des méthodes pour sauvegarder, récupérer, mettre à jour et gérer les candidatures aux requêtes de travail.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class RequeteTravailRepository {
    private MongoCollection<Document> requetesTravailCollection;

    /**
     * Constructeur de la classe RequeteTravailRepository.
     *
     * @param database La base de données MongoDB à utiliser.
     */
    public RequeteTravailRepository(MongoDatabase database) {
        this.requetesTravailCollection = database.getCollection("RequetesTravail");
    }

    /**
     * Sauvegarde une nouvelle requête de travail dans la collection "RequetesTravail".
     *
     * @param requeteTravail La requête de travail à sauvegarder.
     */
    public void sauvegarder(RequeteTravail requeteTravail) {
        Document doc = new Document("title", requeteTravail.getTitre())
                .append("description", requeteTravail.getDescription())
                .append("type", requeteTravail.getType())
                .append("dateDebut", requeteTravail.getDateDebut())
                .append("dateFin", requeteTravail.getDateFin())
                .append("status", requeteTravail.getStatus());
        requetesTravailCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        requeteTravail.setId(id); 
    }

    /**
     * Récupère toutes les requêtes de travail présentes dans la collection "RequetesTravail".
     *
     * @return Une liste de toutes les requêtes de travail.
     */
    public List<RequeteTravail> getAllRequetesTravail() {
        List<RequeteTravail> requetes = new ArrayList<>();
        for (Document doc : requetesTravailCollection.find()) {
            RequeteTravail requete = new RequeteTravail(
                doc.getString("title"),
                doc.getString("description"),
                doc.getString("type"),
                doc.getString("dateDebut"),
                doc.getString("dateFin"),
                doc.getString("status"),
                doc.getString("idIntervenantChoisi"),
                doc.getString("message")
            );
            requete.setId(doc.getObjectId("_id"));
    
            // Récupérer idCandidat et statutCandidature si présents
            if (doc.containsKey("idCandidat")) {
                requete.setIdCandidat(doc.getString("idCandidat"));
            }
            if (doc.containsKey("statutCandidature")) {
                requete.setStatutCandidature(doc.getString("statutCandidature"));
            }
    
            requetes.add(requete);
        }
        return requetes;
    }    

    /**
     * Récupère une requête de travail spécifique en utilisant son identifiant.
     *
     * @param id L'identifiant de la requête de travail à récupérer.
     * @return La requête de travail correspondante, ou {@code null} si non trouvée.
     */
    public RequeteTravail getRequeteTravailById(String id) {
        Document doc = requetesTravailCollection.find(eq("_id", new ObjectId(id))).first();
        if (doc != null) {
            RequeteTravail requete = new RequeteTravail(
                doc.getString("title"),
                doc.getString("description"),
                doc.getString("type"),
                doc.getString("dateDebut"),
                doc.getString("dateFin"),
                doc.getString("status"),
                doc.getString("idIntervenantChoisi"),
                doc.getString("message")
            );
            // Assigner l'ID
            requete.setId(doc.getObjectId("_id"));
            return requete;
        }
        return null;
    }

    /**
     * Met à jour une requête de travail existante dans la collection "RequetesTravail".
     *
     * @param requeteTravail La requête de travail à mettre à jour.
     */
    public void majRequeteTravail(RequeteTravail requeteTravail) {
        Document updatedDoc = new Document("title", requeteTravail.getTitre())
                .append("description", requeteTravail.getDescription())
                .append("type", requeteTravail.getType())
                .append("dateDebut", requeteTravail.getDateDebut())
                .append("dateFin", requeteTravail.getDateFin())
                .append("status", requeteTravail.getStatus())
                .append("idIntervenantChoisi", requeteTravail.getIdIntervenantChoisi())
                .append("message", requeteTravail.getMessage());
        requetesTravailCollection.updateOne(eq("_id", requeteTravail.getId()), new Document("$set", updatedDoc));
    }

    /**
     * Ajoute une candidature à une requête de travail.
     *
     * @param requeteId      L'identifiant de la requête de travail.
     * @param intervenantId L'identifiant de l'intervenant candidat.
     */
    public void ajouterCandidature(String requeteId, String intervenantId) {
        ObjectId id = new ObjectId(requeteId);
        Document filter = new Document("_id", id);
        Document update = new Document("$set", new Document("idCandidat", intervenantId)
                                            .append("statutCandidature", "En attente"));
        requetesTravailCollection.updateOne(filter, update);
    }
    
    /**
     * Retire une candidature d'une requête de travail.
     *
     * @param requeteId      L'identifiant de la requête de travail.
     * @param intervenantId L'identifiant de l'intervenant candidat.
     */
    public void retirerCandidature(String requeteId, String intervenantId) {
        ObjectId id = new ObjectId(requeteId);
        Document filter = new Document("_id", id)
                                .append("idCandidat", intervenantId);
        Document update = new Document("$set", new Document("idCandidat", null)
                                            .append("statutCandidature", "En attente"));
        requetesTravailCollection.updateOne(filter, update);
    }

    /**
     * Accepte une candidature à une requête de travail.
     *
     * @param requeteId      L'identifiant de la requête de travail.
     * @param intervenantId L'identifiant de l'intervenant candidat.
     * @param message        Le message de confirmation à envoyer à l'intervenant.
     */
    public void accepterCandidature(String requeteId, String intervenantId, String message) {
        ObjectId id = new ObjectId(requeteId);
        Document filter = new Document("_id", id)
                                .append("idCandidat", intervenantId)
                                .append("statutCandidature", "En attente");
        Document update = new Document("$set", new Document("idIntervenantChoisi", intervenantId)
                                            .append("statutCandidature", "Acceptée")
                                            .append("message", message)
                                            .append("status", "En cours"));
        requetesTravailCollection.updateOne(filter, update);
    }

    /**
     * Refuse une candidature à une requête de travail.
     *
     * @param requeteId      L'identifiant de la requête de travail.
     * @param intervenantId L'identifiant de l'intervenant candidat.
     * @param message        Le message de refus à envoyer à l'intervenant.
     */
    public void refuserCandidature(String requeteId, String intervenantId, String message) {
        ObjectId id = new ObjectId(requeteId);
        Document filter = new Document("_id", id)
                                .append("idCandidat", intervenantId)
                                .append("statutCandidature", "En attente");
        Document update = new Document("$set", new Document("statutCandidature", "Refusée")
                                            .append("message", message));
        requetesTravailCollection.updateOne(filter, update);
    }

    /**
     * Récupère toutes les requêtes de travail auxquelles un intervenant spécifique a postulé.
     *
     * @param intervenantId L'identifiant de l'intervenant candidat.
     * @return Une liste de requêtes de travail associées au candidat.
     */
    public List<RequeteTravail> getRequetesParCandidat(String intervenantId) {
        List<RequeteTravail> requetes = new ArrayList<>();
        for (Document doc : requetesTravailCollection.find(eq("idCandidat", intervenantId))) {
            RequeteTravail requete = new RequeteTravail(
                doc.getString("title"),
                doc.getString("description"),
                doc.getString("type"),
                doc.getString("dateDebut"),
                doc.getString("dateFin"),
                doc.getString("status"),
                doc.getString("idIntervenantChoisi"),
                doc.getString("message")
            );
            requete.setId(doc.getObjectId("_id"));
            // Récupérer le statut de candidature et idCandidat
            requete.setIdCandidat(doc.getString("idCandidat"));
            requete.setStatutCandidature(doc.getString("statutCandidature"));
            requetes.add(requete);
        }
        return requetes;
    }    
}
