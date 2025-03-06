package com.maville.repository;

import com.maville.model.Notification;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repository pour gérer les opérations CRUD liées aux notifications dans la base de données MongoDB.
 * Fournit des méthodes pour sauvegarder, récupérer et mettre à jour les notifications des résidents.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class NotificationRepository {
    private MongoCollection<org.bson.Document> notificationCollection;

    /**
     * Constructeur de la classe NotificationRepository.
     *
     * @param database La base de données MongoDB à utiliser.
     */
    public NotificationRepository(MongoDatabase database) {
        this.notificationCollection = database.getCollection("Notifications");
    }

    /**
     * Sauvegarde une nouvelle notification dans la collection "Notifications".
     *
     * @param notification La notification à sauvegarder.
     */
    public void sauvegarder(Notification notification) {
        org.bson.Document doc = new org.bson.Document("residentEmail", notification.getResidentEmail())
                .append("description", notification.getDescription())
                .append("timestamp", notification.getTimestamp()) // Date compatible
                .append("isRead", notification.isRead());
        notificationCollection.insertOne(doc);
    }

    /**
     * Récupère toutes les notifications pour un résident spécifique.
     *
     * @param residentEmail L'email du résident dont on souhaite récupérer les notifications.
     * @return Une liste de notifications pour le résident.
     */
    public List<Notification> getNotificationsByResident(String residentEmail) {
        List<Notification> notifications = new ArrayList<>();
        for (org.bson.Document doc : notificationCollection.find(eq("residentEmail", residentEmail))) {
            Notification notification = new Notification();
            notification.setId(doc.getObjectId("_id"));
            notification.setResidentEmail(doc.getString("residentEmail"));
            notification.setDescription(doc.getString("description"));
            notification.setTimestamp(doc.getDate("timestamp")); // Récupérer en tant que Date
            notification.setRead(doc.getBoolean("isRead", false));
            notifications.add(notification);
        }
        return notifications;
    }

    /**
     * Compte le nombre de notifications non lues pour un résident spécifique.
     *
     * @param residentEmail L'email du résident.
     * @return Le nombre de notifications non lues.
     */
    public long countNotificationsNonLues(String residentEmail) {
        return notificationCollection.countDocuments(and(eq("residentEmail", residentEmail), eq("isRead", false)));
    }

    /**
     * Marque une notification comme lue dans la collection "Notifications".
     *
     * @param notificationId L'identifiant de la notification à marquer comme lue.
     */
    public void markerLue(ObjectId notificationId) {
        org.bson.Document update = new org.bson.Document("$set", new org.bson.Document("isRead", true));
        notificationCollection.updateOne(eq("_id", notificationId), update);
    }
}
