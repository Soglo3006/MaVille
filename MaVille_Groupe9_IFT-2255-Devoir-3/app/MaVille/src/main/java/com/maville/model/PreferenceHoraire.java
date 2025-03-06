package com.maville.model;

import org.bson.types.ObjectId;

/**
 * Représente une préférence horaire d'un résident.
 * Contient des informations sur le jour, les heures de début et de fin, et l'email du résident associé.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class PreferenceHoraire {
    private ObjectId id;            // ID MongoDB
    private String jour;
    private String heureDebut;      // Format: "HH:mm"
    private String heureFin;        // Format: "HH:mm"
    private String residentEmail;   // Email du résident associé

    /**
     * Constructeur pour une nouvelle préférence horaire sans ID.
     *
     * @param jour       Le jour de la préférence horaire.
     * @param heureDebut L'heure de début au format "HH:mm".
     * @param heureFin   L'heure de fin au format "HH:mm".
     */
    public PreferenceHoraire(String jour, String heureDebut, String heureFin) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    /**
     * Constructeur pour une préférence horaire avec ID et email du résident.
     *
     * @param id             L'identifiant MongoDB de la préférence horaire.
     * @param jour           Le jour de la préférence horaire.
     * @param heureDebut     L'heure de début au format "HH:mm".
     * @param heureFin       L'heure de fin au format "HH:mm".
     * @param residentEmail  L'email du résident associé.
     */
    public PreferenceHoraire(ObjectId id, String jour, String heureDebut, String heureFin, String residentEmail) {
        this.id = id;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.residentEmail = residentEmail;
    }

    /**
     * Obtient l'identifiant de la préférence horaire.
     *
     * @return L'identifiant de la préférence horaire.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la préférence horaire.
     *
     * @param id L'identifiant de la préférence horaire à définir.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtient le jour de la préférence horaire.
     *
     * @return Le jour de la préférence horaire.
     */
    public String getJour() {
        return jour;
    }

    /**
     * Définit le jour de la préférence horaire.
     *
     * @param jour Le jour de la préférence horaire à définir.
     */
    public void setJour(String jour) {
        this.jour = jour;
    }

    /**
     * Obtient l'heure de début de la préférence horaire.
     *
     * @return L'heure de début au format "HH:mm".
     */
    public String getHeureDebut() {
        return heureDebut;
    }

    /**
     * Définit l'heure de début de la préférence horaire.
     *
     * @param heureDebut L'heure de début au format "HH:mm" à définir.
     */
    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    /**
     * Obtient l'heure de fin de la préférence horaire.
     *
     * @return L'heure de fin au format "HH:mm".
     */
    public String getHeureFin() {
        return heureFin;
    }

    /**
     * Définit l'heure de fin de la préférence horaire.
     *
     * @param heureFin L'heure de fin au format "HH:mm" à définir.
     */
    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * Obtient l'email du résident associé à la préférence horaire.
     *
     * @return L'email du résident.
     */
    public String getResidentEmail() {
        return residentEmail;
    }

    /**
     * Définit l'email du résident associé à la préférence horaire.
     *
     * @param residentEmail L'email du résident à définir.
     */
    public void setResidentEmail(String residentEmail) {
        this.residentEmail = residentEmail;
    }

    /**
     * Valide les horaires de la préférence.
     * Vérifie que l'heure de début est avant l'heure de fin et que les formats sont corrects.
     *
     * @return {@code true} si les horaires sont valides, {@code false} sinon.
     */
    public boolean estHeureValide() {
        try {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
            java.time.LocalTime debut = java.time.LocalTime.parse(this.heureDebut, formatter);
            java.time.LocalTime fin = java.time.LocalTime.parse(this.heureFin, formatter);
            return debut.isBefore(fin);
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }
}
