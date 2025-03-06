package com.maville.model;

import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Représente un projet géré par les intervenants.
 * Contient des informations telles que le titre, la description, le type de travaux, les dates, et le statut.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class Projet {
    private ObjectId id;
    private String titre;
    private String description;
    private TypeProjet typeTravaux;
    private String quartier;
    private String dateDebut;
    private String dateFin;
    private String horaire;
    private String status;

    /**
     * Constructeur de la classe Projet.
     *
     * @param titre        Le titre du projet.
     * @param description  La description du projet.
     * @param typeTravaux  Le type de travaux du projet.
     * @param quartier     Le quartier où se situe le projet.
     * @param dateDebut    La date de début du projet au format "yyyy-MM-dd".
     * @param dateFin      La date de fin du projet au format "yyyy-MM-dd".
     * @param horaire      Les horaires du projet.
     */
    public Projet(String titre, String description, TypeProjet typeTravaux, String quartier, String dateDebut, String dateFin, String horaire) {
        this.titre = titre;
        this.description = description;
        this.typeTravaux = typeTravaux;
        this.quartier = quartier;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.horaire = horaire;
        this.status = "Prévu"; // Initialiser le statut
    }

    /**
     * Obtient l'identifiant du projet.
     *
     * @return L'identifiant du projet.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Définit l'identifiant du projet.
     *
     * @param id L'identifiant du projet à définir.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtient le titre du projet.
     *
     * @return Le titre du projet.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre du projet.
     *
     * @param titre Le titre du projet à définir.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Obtient la description du projet.
     *
     * @return La description du projet.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description du projet.
     *
     * @param description La description du projet à définir.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtient le type de travaux du projet.
     *
     * @return Le type de travaux du projet.
     */
    public TypeProjet getTypeTravaux() {
        return typeTravaux;
    }

    /**
     * Définit le type de travaux du projet.
     *
     * @param typeTravaux Le type de travaux du projet à définir.
     */
    public void setTypeTravaux(TypeProjet typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    /**
     * Obtient le quartier du projet.
     *
     * @return Le quartier du projet.
     */
    public String getQuartier() {
        return quartier;
    }

    /**
     * Définit le quartier du projet.
     *
     * @param quartier Le quartier du projet à définir.
     */
    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    /**
     * Obtient la date de début du projet.
     *
     * @return La date de début au format "yyyy-MM-dd".
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * Définit la date de début du projet.
     *
     * @param dateDebut La date de début au format "yyyy-MM-dd" à définir.
     */
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Obtient la date de fin du projet.
     *
     * @return La date de fin au format "yyyy-MM-dd".
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Définit la date de fin du projet.
     *
     * @param dateFin La date de fin au format "yyyy-MM-dd" à définir.
     */
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Obtient les horaires du projet.
     *
     * @return Les horaires du projet.
     */
    public String getHoraire() {
        return horaire;
    }

    /**
     * Définit les horaires du projet.
     *
     * @param horaire Les horaires du projet à définir.
     */
    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    /**
     * Obtient le statut du projet.
     *
     * @return Le statut du projet.
     */
    public String getStatus() {  
        return status;
    }

    /**
     * Définit le statut du projet.
     *
     * @param status Le statut du projet à définir.
     */
    public void setStatus(String status) { 
        this.status = status;
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
     * Retourne une représentation sous forme de chaîne de caractères du projet.
     *
     * @return Une chaîne de caractères décrivant le projet.
     */
    @Override
    public String toString() {
        return "-------------------------------------" + "\n" +
                "ID Projet: " + id + "\n" +
                "Titre: '" + titre + '\'' + "\n" +
                "Description: '" + description + '\'' + "\n" +
                "Type: '" + typeTravaux + '\'' + "\n" +
                "Quartier: '" + quartier + '\'' + "\n" +
                "Date de début: '" + dateDebut + '\'' + "\n" +
                "Date de fin: '" + dateFin + '\'' + "\n" +
                "Horaire: '" + horaire + '\'' + "\n" +
                "Statut: '" + status + '\'' + "\n" +
                "-------------------------------------";
    }
}
