package com.maville.repository;

import com.maville.model.PreferenceHoraire;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository pour gérer les opérations CRUD liées aux préférences horaires des résidents dans la base de données MongoDB.
 * Fournit des méthodes pour sauvegarder, récupérer, mettre à jour et supprimer les préférences horaires.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class PreferenceHoraireRepository {
    private MongoCollection<Document> preferenceCollection;

    /**
     * Constructeur de la classe PreferenceHoraireRepository.
     *
     * @param database La base de données MongoDB à utiliser.
     */
    public PreferenceHoraireRepository(MongoDatabase database) {
        this.preferenceCollection = database.getCollection("PreferencesHoraires"); // Nom de la collection
    }

    /**
     * Récupère toutes les préférences horaires de la collection "PreferencesHoraires".
     *
     * @return Une liste de toutes les préférences horaires.
     */
    public List<PreferenceHoraire> getAllPreferences() {
        List<PreferenceHoraire> preferences = new ArrayList<>();
        for (Document doc : preferenceCollection.find()) {
            PreferenceHoraire pref = new PreferenceHoraire(
                doc.getString("jour"),
                doc.getString("heureDebut"),
                doc.getString("heureFin")
            );
            preferences.add(pref);
        }
        return preferences;
    }

    /**
     * Sauvegarde une nouvelle préférence horaire dans la collection "PreferencesHoraires".
     *
     * @param preferenceHoraire La préférence horaire à sauvegarder.
     */
    public void sauvegarder(PreferenceHoraire preferenceHoraire) {
        Document doc = new Document("jour", preferenceHoraire.getJour())
                .append("heureDebut", preferenceHoraire.getHeureDebut())
                .append("heureFin", preferenceHoraire.getHeureFin())
                .append("residentEmail", preferenceHoraire.getResidentEmail());

        preferenceCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        preferenceHoraire.setId(id);
    }

    /**
     * Récupère toutes les préférences horaires d'un résident spécifique en utilisant son email.
     *
     * @param email L'email du résident.
     * @return Une liste des préférences horaires du résident.
     */
    public List<PreferenceHoraire> getPreferencesHorairesByResidentEmail(String email) {
        List<PreferenceHoraire> preferences = new ArrayList<>();
        for (Document doc : preferenceCollection.find(eq("residentEmail", email))) {
            PreferenceHoraire preference = new PreferenceHoraire(
                doc.getObjectId("_id"),
                doc.getString("jour"),
                doc.getString("heureDebut"),
                doc.getString("heureFin"),
                doc.getString("residentEmail")
            );
            preferences.add(preference);
        }
        return preferences;
    }

    /**
     * Récupère une préférence horaire spécifique en utilisant son identifiant.
     *
     * @param id L'identifiant de la préférence horaire.
     * @return La préférence horaire correspondante, ou {@code null} si non trouvée.
     */
    public PreferenceHoraire getPreferenceHoraireById(String id) {
        Document doc = preferenceCollection.find(eq("_id", new ObjectId(id))).first();
        if (doc != null) {
            return new PreferenceHoraire(
                doc.getObjectId("_id"),
                doc.getString("jour"),
                doc.getString("heureDebut"),
                doc.getString("heureFin"),
                doc.getString("residentEmail")
            );
        }
        return null;
    }

    /**
     * Met à jour une préférence horaire existante dans la collection "PreferencesHoraires".
     *
     * @param preferenceHoraire La préférence horaire à mettre à jour.
     */
    public void majPreferenceHoraire(PreferenceHoraire preferenceHoraire) {
        Document updatedDoc = new Document("jour", preferenceHoraire.getJour())
                .append("heureDebut", preferenceHoraire.getHeureDebut())
                .append("heureFin", preferenceHoraire.getHeureFin())
                .append("residentEmail", preferenceHoraire.getResidentEmail());

        preferenceCollection.updateOne(eq("_id", preferenceHoraire.getId()), new Document("$set", updatedDoc));
    }

    /**
     * Supprime une préférence horaire de la collection "PreferencesHoraires" en utilisant son identifiant.
     *
     * @param id L'identifiant de la préférence horaire à supprimer.
     */
    public void supprimerPreferenceHoraire(String id) {
        preferenceCollection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
