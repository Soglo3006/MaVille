package com.maville.repository;

import com.maville.model.Projet;
import com.maville.model.TypeProjet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Repository pour gérer les opérations CRUD liées aux projets dans la base de données MongoDB.
 * Fournit des méthodes pour sauvegarder, récupérer, mettre à jour et filtrer les projets.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class ProjetRepository {
    private MongoCollection<Document> projetCollection;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructeur de la classe ProjetRepository.
     *
     * @param database La base de données MongoDB à utiliser.
     */
    public ProjetRepository(MongoDatabase database) {
        this.projetCollection = database.getCollection("Projet");
    }

    /**
     * Récupère les projets à venir dans les 3 prochains mois.
     *
     * @return Une liste de projets à venir.
     */
    public List<Projet> getProjetAvenir3Mois() {
        List<Projet> projetList = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate date3Mois = today.plusMonths(3);

        String todayStr = today.format(formatter);
        String date3MoisStr = date3Mois.format(formatter);

        // Requête: dateDebut >= todayStr AND dateDebut <= date3MoisStr
        for (Document doc : projetCollection.find(and(
                gte("dateDebut", todayStr),
                lte("dateDebut", date3MoisStr)
        ))) {
            Projet projet = new Projet(
                    doc.getString("titre"),
                    doc.getString("description"),
                    TypeProjet.valueOf(doc.getString("typeTravaux")),
                    doc.getString("quartier"),
                    doc.getString("dateDebut"),
                    doc.getString("dateFin"),
                    doc.getString("horaire")
            );
            projetList.add(projet);
        }

        return projetList;
    }

    /**
     * Sauvegarde un nouveau projet dans la collection "Projet" avec l'ID de l'intervenant associé.
     *
     * @param projet        Le projet à sauvegarder.
     * @param intervenantId L'identifiant de l'intervenant associé au projet.
     */
    public void sauvegarder(Projet projet, String intervenantId) {
        Document doc = new Document("titre", projet.getTitre())
                .append("description", projet.getDescription())
                .append("typeTravaux", projet.getTypeTravaux().name())
                .append("dateDebut", projet.getDateDebut())
                .append("dateFin", projet.getDateFin())
                .append("horaire", projet.getHoraire())
                .append("status", "Prévu")
                .append("quartier", projet.getQuartier())
                .append("intervenantId", intervenantId);
        projetCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        projet.setId(id);
    }

    /**
     * Récupère un projet spécifique en utilisant son identifiant.
     *
     * @param projetId L'identifiant du projet à récupérer.
     * @return Le projet correspondant, ou {@code null} si aucun projet n'est trouvé.
     */
    public Projet getProjetById(ObjectId projetId) {
        Document doc = projetCollection.find(eq("_id", projetId)).first();
        if (doc != null) {
            Projet projet = new Projet(
                    doc.getString("titre"),
                    doc.getString("description"),
                    TypeProjet.valueOf(doc.getString("typeTravaux")),
                    doc.getString("quartier"),
                    doc.getString("dateDebut"),
                    doc.getString("dateFin"),
                    doc.getString("horaire")
            );
            projet.setId(doc.getObjectId("_id"));
            projet.setStatus(doc.getString("status"));
            return projet;
        }
        return null;
    }

    /**
     * Met à jour le statut d'un projet existant dans la collection "Projet".
     *
     * @param projetId      L'identifiant du projet à mettre à jour.
     * @param nouveauStatut Le nouveau statut à définir pour le projet.
     */
    public void majProjetStatut(ObjectId projetId, String nouveauStatut) {
        Document update = new Document("$set", new Document("status", nouveauStatut));
        projetCollection.updateOne(eq("_id", projetId), update);
    }

    /**
     * Récupère tous les projets associés à un intervenant spécifique.
     *
     * @param intervenantId L'identifiant de l'intervenant.
     * @return Une liste de projets associés à l'intervenant.
     */
    public List<Projet> getProjetsParIntervenant(String intervenantId) {
        List<Projet> projets = new ArrayList<>();
        for (Document doc : projetCollection.find(eq("intervenantId", intervenantId))) {
            Projet projet = new Projet(
                    doc.getString("titre"),
                    doc.getString("description"),
                    TypeProjet.valueOf(doc.getString("typeTravaux")),
                    doc.getString("quartier"),
                    doc.getString("dateDebut"),
                    doc.getString("dateFin"),
                    doc.getString("horaire")
            );
            projet.setId(doc.getObjectId("_id"));
            projet.setStatus(doc.getString("status"));
            projets.add(projet);
        }
        return projets;
    }
}
