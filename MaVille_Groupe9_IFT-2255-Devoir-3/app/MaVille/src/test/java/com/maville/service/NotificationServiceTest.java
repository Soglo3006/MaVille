package com.maville.service;

import com.maville.model.Notification;
import com.maville.repository.NotificationRepository;
import com.maville.repository.ResidentRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    private NotificationService service;
    private NotificationRepository mockNotificationRepository;
    private ResidentRepository mockResidentRepository;

    @BeforeEach
    void setUp() {
        mockNotificationRepository = Mockito.mock(NotificationRepository.class);
        mockResidentRepository = Mockito.mock(ResidentRepository.class);
        service = new NotificationService(mockNotificationRepository, mockResidentRepository);
    }

    @Test
    void testSendNotificationToQuartier() {
        String quartier = "Rosemont";
        String message = "Travaux prévus dans votre quartier.";
        List<String> mockEmails = Arrays.asList("user1@example.com", "user2@example.com");

        when(mockResidentRepository.getEmailsByQuartier(quartier)).thenReturn(mockEmails);

        service.sendNotificationToQuartier(quartier, message);

        verify(mockResidentRepository, times(1)).getEmailsByQuartier(quartier);
        verify(mockNotificationRepository, times(mockEmails.size())).sauvegarder(any(Notification.class));
    }

    @Test
    void testGetNotificationsForResident() {
        String residentEmail = "user1@example.com";
        List<Notification> mockNotifications = Arrays.asList(
                new Notification(residentEmail, "Message 1"),
                new Notification(residentEmail, "Message 2")
        );

        when(mockNotificationRepository.getNotificationsByResident(residentEmail)).thenReturn(mockNotifications);

        List<Notification> result = service.getNotificationsForResident(residentEmail);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Message 1", result.get(0).getDescription());
        verify(mockNotificationRepository, times(1)).getNotificationsByResident(residentEmail);
    }

    @Test
    void testGetUnreadNotificationCount() {
        String residentEmail = "user1@example.com";
        long unreadCount = 5;

        when(mockNotificationRepository.countNotificationsNonLues(residentEmail)).thenReturn(unreadCount);

        long result = service.getUnreadNotificationCount(residentEmail);

        assertEquals(unreadCount, result);
        verify(mockNotificationRepository, times(1)).countNotificationsNonLues(residentEmail);
    }

    @Test
    void testMarkNotificationAsRead() {
        ObjectId notificationId = new ObjectId();

        doNothing().when(mockNotificationRepository).markerLue(notificationId);

        service.markNotificationAsRead(notificationId);

        verify(mockNotificationRepository, times(1)).markerLue(notificationId);
    }
}
