package com.maville.model;

import org.bson.types.ObjectId;
import java.util.Date;

/**
 * Représente une notification envoyée à un résident.
 * Contient des informations telles que la description, l'horodatage et l'état de lecture.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class Notification {
    private ObjectId id;
    private String residentEmail; // Référence au résident
    private String description;
    private Date timestamp;
    private boolean isRead;

    /**
     * Constructeur par défaut de Notification.
     */
    public Notification() {}

    /**
     * Constructeur de Notification avec les paramètres nécessaires.
     *
     * @param residentEmail L'email du résident auquel la notification est destinée.
     * @param description   La description de la notification.
     */
    public Notification(String residentEmail, String description) {
        this.residentEmail = residentEmail;
        this.description = description;
        this.timestamp = new Date(); // Date et heure actuelles
        this.isRead = false;
    }

    /**
     * Obtient l'identifiant de la notification.
     *
     * @return L'identifiant de la notification.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la notification.
     *
     * @param id L'identifiant de la notification à définir.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtient l'email du résident associé à la notification.
     *
     * @return L'email du résident.
     */
    public String getResidentEmail() {
        return residentEmail;
    }

    /**
     * Définit l'email du résident associé à la notification.
     *
     * @param residentEmail L'email du résident à définir.
     */
    public void setResidentEmail(String residentEmail) {
        this.residentEmail = residentEmail;
    }

    /**
     * Obtient la description de la notification.
     *
     * @return La description de la notification.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la notification.
     *
     * @param description La description de la notification à définir.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtient l'horodatage de la notification.
     *
     * @return L'horodatage de la notification.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Définit l'horodatage de la notification.
     *
     * @param timestamp L'horodatage de la notification à définir.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Vérifie si la notification a été lue.
     *
     * @return {@code true} si la notification a été lue, {@code false} sinon.
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Définit l'état de lecture de la notification.
     *
     * @param read {@code true} si la notification a été lue, {@code false} sinon.
     */
    public void setRead(boolean read) {
        isRead = read;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la notification.
     *
     * @return Une chaîne de caractères décrivant la notification.
     */
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", residentEmail='" + residentEmail + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                '}';
    }
}
