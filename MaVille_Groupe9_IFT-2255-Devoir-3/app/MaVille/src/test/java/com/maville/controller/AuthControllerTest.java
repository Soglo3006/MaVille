package com.maville.controller;

import com.maville.model.Intervenant;
import com.maville.model.Resident;
import com.maville.repository.IntervenantRepository;
import com.maville.repository.ResidentRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour la classe AuthController.
 * Ce que fait ce test :
 *
 *   Il se sert de Mockito pour mocker les ResidentRepository et IntervenantRepository.
 *   Il vérifie les comportements attendus de chaque méthode du AuthController.
 *   Il s'assure que les méthodes enregistrerResident et enregistrerIntervenant invoquent bien les repository correspondants.
 *   Il teste les connexions (succès et échecs).
 *   Il teste la récupération par email (résident et intervenant).
 *   Il teste la déconnexion (logout).
 *   Il teste getUserId dans différents scénarios : résident loggé, intervenant loggé, aucun utilisateur loggé, et email inconnu.
 */
class AuthControllerTest {

    private AuthController authController;
    private ResidentRepository residentRepository;
    private IntervenantRepository intervenantRepository;

    @BeforeEach
    void setUp() {
        residentRepository = Mockito.mock(ResidentRepository.class);
        intervenantRepository = Mockito.mock(IntervenantRepository.class);
        authController = new AuthController(residentRepository, intervenantRepository);
    }

    @Test
    void testEnregistrerResident() {
        Resident resident = new Resident("John Doe", "2000-01-01", "john@example.com", "pass", "555-1234", "123 Rue", "H2G 1A1");
        doNothing().when(residentRepository).sauvegarder(resident);

        authController.enregistrerResident(resident);

        verify(residentRepository, times(1)).sauvegarder(resident);
    }

    @Test
    void testEnregistrerIntervenant() {
        Intervenant intervenant = new Intervenant("Jane Doe", "jane@example.com", "secret", "Entrepreneur privé", "12345678");
        doNothing().when(intervenantRepository).sauvegarder(intervenant);

        authController.enregistrerIntervenant(intervenant);

        verify(intervenantRepository, times(1)).sauvegarder(intervenant);
    }

    @Test
    void testConnecterResident_Success() {
        Resident resident = new Resident("John Doe", "2000-01-01", "john@example.com", "password", "555-1234", "123 Rue", "H2G 1A1");
        when(residentRepository.trouverAvecEmail("john@example.com")).thenReturn(resident);

        boolean result = authController.connecterResident("john@example.com", "password");

        assertTrue(result);
        assertEquals("john@example.com", authController.getEmailDuUserConnecte());
    }

    @Test
    void testConnecterResident_Fail() {
        when(residentRepository.trouverAvecEmail("john@example.com")).thenReturn(null);

        boolean result = authController.connecterResident("john@example.com", "password");

        assertFalse(result);
        assertNull(authController.getEmailDuUserConnecte());
    }

    @Test
    void testConnecterResident_WrongPassword() {
        Resident resident = new Resident("John Doe", "2000-01-01", "john@example.com", "password", "555-1234", "123 Rue", "H2G 1A1");
        when(residentRepository.trouverAvecEmail("john@example.com")).thenReturn(resident);

        boolean result = authController.connecterResident("john@example.com", "wrong");

        assertFalse(result);
        assertNull(authController.getEmailDuUserConnecte());
    }

    @Test
    void testConnecterIntervenant_Success() {
        Intervenant intervenant = new Intervenant("Jane Doe", "jane@example.com", "secret", "Entrepreneur privé", "12345678");
        when(intervenantRepository.trouverAvecEmail("jane@example.com")).thenReturn(intervenant);

        boolean result = authController.connecterIntervenant("jane@example.com", "secret");

        assertTrue(result);
        assertEquals("jane@example.com", authController.getEmailDuUserConnecte());
    }

    @Test
    void testConnecterIntervenant_Fail() {
        when(intervenantRepository.trouverAvecEmail("jane@example.com")).thenReturn(null);

        boolean result = authController.connecterIntervenant("jane@example.com", "secret");

        assertFalse(result);
        assertNull(authController.getEmailDuUserConnecte());
    }

    @Test
    void testGetResidentByEmail() {
        Resident resident = new Resident("John Doe", "2000-01-01", "john@example.com", "pass", "555-1234", "123 Rue", "H2G 1A1");
        when(residentRepository.trouverAvecEmail("john@example.com")).thenReturn(resident);

        Resident found = authController.getResidentByEmail("john@example.com");

        assertNotNull(found);
        assertEquals("john@example.com", found.getEmail());
    }

    @Test
    void testGetResidentByEmail_NotFound() {
        when(residentRepository.trouverAvecEmail("unknown@example.com")).thenReturn(null);

        Resident found = authController.getResidentByEmail("unknown@example.com");

        assertNull(found);
    }

    @Test
    void testGetIntervenantByEmail() {
        Intervenant intervenant = new Intervenant("Jane Doe", "jane@example.com", "secret", "Entrepreneur privé", "12345678");
        when(intervenantRepository.trouverAvecEmail("jane@example.com")).thenReturn(intervenant);

        Intervenant found = authController.getIntervenantByEmail("jane@example.com");

        assertNotNull(found);
        assertEquals("jane@example.com", found.getEmail());
    }

    @Test
    void testGetIntervenantByEmail_NotFound() {
        when(intervenantRepository.trouverAvecEmail("unknown@example.com")).thenReturn(null);

        Intervenant found = authController.getIntervenantByEmail("unknown@example.com");

        assertNull(found);
    }

    @Test
    void testLogout() {
        authController.setEmailDuUserConnecte("john@example.com");
        authController.logout();
        assertNull(authController.getEmailDuUserConnecte());
    }

    @Test
    void testGetUserId_Resident() {
        Resident resident = new Resident("John Doe", "2000-01-01", "john@example.com", "pass", "555-1234", "123 Rue", "H2G 1A1");
        ObjectId fakeId = new ObjectId();
        resident.setId(fakeId);

        when(residentRepository.trouverAvecEmail("john@example.com")).thenReturn(resident);
        authController.setEmailDuUserConnecte("john@example.com");

        String userId = authController.getUserId();
        assertEquals(fakeId.toHexString(), userId);
    }

    @Test
    void testGetUserId_Intervenant() {
        Intervenant intervenant = new Intervenant("Jane Doe", "jane@example.com", "secret", "Entrepreneur privé", "12345678");
        ObjectId fakeId = new ObjectId();
        intervenant.setId(fakeId);

        when(intervenantRepository.trouverAvecEmail("jane@example.com")).thenReturn(intervenant);
        authController.setEmailDuUserConnecte("jane@example.com");

        String userId = authController.getUserId();
        assertEquals(fakeId.toHexString(), userId);
    }

    @Test
    void testGetUserId_NoOneLoggedIn() {
        authController.logout();
        String userId = authController.getUserId();
        assertNull(userId);
    }

    @Test
    void testGetUserId_UserNotFound() {
        // L'email est défini, mais personne n'est trouvé dans les repositories
        authController.setEmailDuUserConnecte("unknown@example.com");

        when(residentRepository.trouverAvecEmail("unknown@example.com")).thenReturn(null);
        when(intervenantRepository.trouverAvecEmail("unknown@example.com")).thenReturn(null);

        String userId = authController.getUserId();
        assertNull(userId);
    }
}
