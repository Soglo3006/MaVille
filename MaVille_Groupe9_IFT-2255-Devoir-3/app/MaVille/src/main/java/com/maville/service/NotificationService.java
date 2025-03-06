package com.maville.service;

import com.maville.model.Notification;
import com.maville.repository.NotificationRepository;
import com.maville.repository.ResidentRepository;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Service pour gérer les notifications destinées aux résidents.
 * Fournit des méthodes pour envoyer des notifications, récupérer les notifications d'un résident,
 * compter les notifications non lues et marquer les notifications comme lues.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class NotificationService {
    private NotificationRepository notificationRepository;
    private ResidentRepository residentRepository;

    /**
     * Constructeur de la classe NotificationService.
     *
     * @param notificationRepository Le repository des notifications.
     * @param residentRepository     Le repository des résidents.
     */
    public NotificationService(NotificationRepository notificationRepository, ResidentRepository residentRepository) {
        this.notificationRepository = notificationRepository;
        this.residentRepository = residentRepository;
    }

    /**
     * Crée et envoie une notification à tous les résidents d'un quartier spécifique.
     *
     * @param quartier Le quartier ciblé.
     * @param message  Le message de la notification.
     */
    public void sendNotificationToQuartier(String quartier, String message) {
        // Récupérer les résidents du quartier
        List<String> residentEmails = residentRepository.getEmailsByQuartier(quartier);
        for (String email : residentEmails) {
            Notification notification = new Notification(email, message);
            notificationRepository.sauvegarder(notification);
        }
    }

    /**
     * Récupère toutes les notifications pour un résident spécifique.
     *
     * @param residentEmail L'email du résident.
     * @return Une liste de notifications pour le résident.
     */
    public List<Notification> getNotificationsForResident(String residentEmail) {
        return notificationRepository.getNotificationsByResident(residentEmail);
    }

    /**
     * Récupère le nombre de notifications non lues pour un résident spécifique.
     *
     * @param residentEmail L'email du résident.
     * @return Le nombre de notifications non lues.
     */
    public long getUnreadNotificationCount(String residentEmail) {
        return notificationRepository.countNotificationsNonLues(residentEmail);
    }

    /**
     * Marque une notification comme lue.
     *
     * @param notificationId L'identifiant de la notification à marquer comme lue.
     */
    public void markNotificationAsRead(ObjectId notificationId) {
        notificationRepository.markerLue(notificationId);
    }
}
