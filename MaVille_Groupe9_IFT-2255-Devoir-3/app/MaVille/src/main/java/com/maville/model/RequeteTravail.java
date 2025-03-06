package com.maville.model;

import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Représente une requête de travail soumise par un résident.
 * Contient des informations telles que le titre, la description, le type, les dates, et les candidatures.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class RequeteTravail {
    private ObjectId id;
    private String titre;
    private String description;
    private String type;
    private String dateDebut;
    private String dateFin;
    private String status;              // Ex: "Prévu", "En cours", "Fermée"
    private String idIntervenantChoisi; // ID de l'intervenant accepté
    private String message;             // Message pour l'intervenant
    private String idCandidat;          // ID de l'intervenant qui a postulé
    private String statutCandidature;   // Ex: "En attente", "Acceptée", "Refusée"

    /**
     * Constructeur de la classe RequeteTravail.
     *
     * @param titre                  Le titre de la requête de travail.
     * @param description            La description de la requête de travail.
     * @param type                   Le type de la requête de travail.
     * @param dateDebut              La date de début au format "yyyy-MM-dd".
     * @param dateFin                La date de fin au format "yyyy-MM-dd".
     * @param status                 Le statut de la requête (ex: "Prévu").
     * @param idIntervenantChoisi    L'ID de l'intervenant accepté.
     * @param message                Le message destiné à l'intervenant.
     */
    public RequeteTravail(String titre, String description, String type, String dateDebut, String dateFin, String status, String idIntervenantChoisi, String message) {
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.idIntervenantChoisi = idIntervenantChoisi;
        this.message = message;
        this.idCandidat = null;
        this.statutCandidature = "En attente";
    }

    /**
     * Obtient l'identifiant de la requête de travail.
     *
     * @return L'identifiant de la requête de travail.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la requête de travail.
     *
     * @param id L'identifiant de la requête de travail à définir.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtient le titre de la requête de travail.
     *
     * @return Le titre de la requête de travail.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre de la requête de travail.
     *
     * @param titre Le titre de la requête de travail à définir.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Obtient la description de la requête de travail.
     *
     * @return La description de la requête de travail.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la requête de travail.
     *
     * @param description La description de la requête de travail à définir.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtient le type de la requête de travail.
     *
     * @return Le type de la requête de travail.
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type de la requête de travail.
     *
     * @param type Le type de la requête de travail à définir.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtient la date de début de la requête de travail.
     *
     * @return La date de début au format "yyyy-MM-dd".
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date de début de la requête de travail.
     *
     * @param dateDebut La date de début au format "yyyy-MM-dd" à définir.
     */
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Obtient la date de fin de la requête de travail.
     *
     * @return La date de fin au format "yyyy-MM-dd".
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date de fin de la requête de travail.
     *
     * @param dateFin La date de fin au format "yyyy-MM-dd" à définir.
     */
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Obtient le statut de la requête de travail.
     *
     * @return Le statut de la requête de travail.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit le statut de la requête de travail.
     *
     * @param status Le statut de la requête de travail à définir.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtient l'identifiant de l'intervenant choisi pour la requête de travail.
     *
     * @return L'identifiant de l'intervenant choisi.
     */
    public String getIdIntervenantChoisi() {
        return idIntervenantChoisi;
    }

    /**
     * Définit l'identifiant de l'intervenant choisi pour la requête de travail.
     *
     * @param idIntervenantChoisi L'identifiant de l'intervenant choisi à définir.
     */
    public void setIdIntervenantChoisi(String idIntervenantChoisi) {
        this.idIntervenantChoisi = idIntervenantChoisi;
    }

    /**
     * Obtient le message destiné à l'intervenant.
     *
     * @return Le message destiné à l'intervenant.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message destiné à l'intervenant.
     *
     * @param message Le message destiné à l'intervenant à définir.
     */
    public void setMessage(String message){
        this.message = message;
    }

    /**
     * Obtient l'identifiant du candidat (intervenant qui a postulé).
     *
     * @return L'identifiant du candidat.
     */
    public String getIdCandidat() {
        return idCandidat;
    }

    /**
     * Définit l'identifiant du candidat (intervenant qui a postulé).
     *
     * @param idCandidat L'identifiant du candidat à définir.
     */
    public void setIdCandidat(String idCandidat) {
        this.idCandidat = idCandidat;
    }

    /**
     * Obtient le statut de la candidature.
     *
     * @return Le statut de la candidature (ex: "En attente", "Acceptée", "Refusée").
     */
    public String getStatutCandidature() {
        return statutCandidature;
    }

    /**
     * Définit le statut de la candidature.
     *
     * @param statutCandidature Le statut de la candidature à définir.
     */
    public void setStatutCandidature(String statutCandidature) {
        this.statutCandidature = statutCandidature;
    }

    /**
     * Valide une date au format "yyyy-MM-dd".
     *
     * @param date La date à valider.
     * @return {@code true} si la date est valide, {@code false} sinon.
     */
    public boolean estDateValide(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la requête de travail.
     *
     * @return Une chaîne de caractères décrivant la requête de travail.
     */
    @Override
    public String toString() {
        return "-------------------------------------" + "\n" +
                "ID Requête: " + id + "\n" +
                "Titre: '" + titre + '\'' + "\n" +
                "Description: '" + description + '\'' + "\n" +
                "Type: '" + type + '\'' + "\n" +
                "Date de début: '" + dateDebut + '\'' + "\n" +
                "Date de fin: '" + dateFin + '\'' + "\n" +
                "Statut: '" + status + '\'' + "\n" +
                "Intervenant sélectionné: '" + idIntervenantChoisi + '\'' + "\n" +
                "Message: '" + message + '\'' + "\n" +
                "Candidat ID: '" + idCandidat + '\'' + "\n" +
                "Statut Candidature: '" + statutCandidature + '\'' + "\n" +
                "-------------------------------------";
    }
}
