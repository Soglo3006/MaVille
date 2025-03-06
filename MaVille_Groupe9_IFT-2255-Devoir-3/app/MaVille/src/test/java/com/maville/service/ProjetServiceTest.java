package com.maville.service;

import com.maville.model.PreferenceHoraire;
import com.maville.model.Projet;
import com.maville.repository.PreferenceHoraireRepository;
import com.maville.repository.ProjetRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjetServiceTest {

    private ProjetRepository projetRepository;
    private PreferenceHoraireRepository preferenceHoraireRepository;
    private NotificationService notificationService;
    private ProjetService projetService;

    @BeforeEach
    void setUp() {
        projetRepository = mock(ProjetRepository.class);
        preferenceHoraireRepository = mock(PreferenceHoraireRepository.class);
        notificationService = mock(NotificationService.class);
        projetService = new ProjetService(projetRepository, preferenceHoraireRepository, notificationService);
    }

    @Test
    void testSoumettreNouveauProjet() {
        Projet projet = new Projet("Titre Test", "Description", null, "Quartier Test", "2025-01-01", "2025-01-10", "08:00-16:00");
        String intervenantId = "intervenant123";

        doNothing().when(projetRepository).sauvegarder(projet, intervenantId);

        projetService.soumettreNouveauProjet(projet, intervenantId);

        verify(projetRepository, times(1)).sauvegarder(projet, intervenantId);
        verify(notificationService, times(1)).sendNotificationToQuartier(eq("Quartier Test"), contains("Titre Test"));
    }

    @Test
    void testModifierStatutProjet() {
        ObjectId projetId = new ObjectId();
        Projet projet = new Projet("Titre Projet", "Description", null, "Quartier Test", "2025-01-01", "2025-01-10", "08:00-16:00");
        projet.setId(projetId);

        when(projetRepository.getProjetById(projetId)).thenReturn(projet);

        projetService.modifierStatutProjet(projetId, "En cours");

        verify(projetRepository, times(1)).majProjetStatut(projetId, "En cours");
        verify(notificationService, times(1)).sendNotificationToQuartier(eq("Quartier Test"), contains("En cours"));
    }

    @Test
    void testGetTravauxAvenir3Mois() {
        List<Projet> mockProjects = Arrays.asList(
                new Projet("Projet 1", "Description 1", null, "Quartier 1", "2025-02-01", "2025-02-28", "08:00-16:00"),
                new Projet("Projet 2", "Description 2", null, "Quartier 2", "2025-03-01", "2025-03-31", "09:00-17:00")
        );

        when(projetRepository.getProjetAvenir3Mois()).thenReturn(mockProjects);

        List<Projet> result = projetService.getTravauxAvenir3Mois();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projetRepository, times(1)).getProjetAvenir3Mois();
    }

    @Test
    void testGetProjetsParIntervenant() {
        String intervenantId = "intervenant123";
        List<Projet> mockProjects = Arrays.asList(
                new Projet("Projet 1", "Description 1", null, "Quartier 1", "2025-01-01", "2025-01-15", "08:00-16:00")
        );

        when(projetRepository.getProjetsParIntervenant(intervenantId)).thenReturn(mockProjects);

        List<Projet> result = projetService.getProjetsParIntervenant(intervenantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(projetRepository, times(1)).getProjetsParIntervenant(intervenantId);
    }

    @Test
    void testVerifierConflitsAvecChevauchement() {
        Projet projet = new Projet("Projet Test", "Description", null, "Quartier Test", "2025-01-01", "2025-01-10", "08:00-12:00");
        List<PreferenceHoraire> preferences = Arrays.asList(
                new PreferenceHoraire("Lundi", "10:00", "14:00"),
                new PreferenceHoraire("Mardi", "07:00", "09:00")
        );

        when(preferenceHoraireRepository.getAllPreferences()).thenReturn(preferences);

        boolean conflit = projetService.verifierConflits(projet);

        assertTrue(conflit);
        verify(preferenceHoraireRepository, times(1)).getAllPreferences();
    }

    @Test
    void testVerifierConflitsSansChevauchement() {
        Projet projet = new Projet("Projet Test", "Description", null, "Quartier Test", "2025-01-01", "2025-01-10", "08:00-12:00");
        List<PreferenceHoraire> preferences = Arrays.asList(
                new PreferenceHoraire("Lundi", "12:00", "14:00"),
                new PreferenceHoraire("Mardi", "14:00", "16:00")
        );

        when(preferenceHoraireRepository.getAllPreferences()).thenReturn(preferences);

        boolean conflit = projetService.verifierConflits(projet);

        assertFalse(conflit);
        verify(preferenceHoraireRepository, times(1)).getAllPreferences();
    }

    @Test
    void testVerifierConflitsFormatInvalide() {
        Projet projet = new Projet("Projet Test", "Description", null, "Quartier Test", "2025-01-01", "2025-01-10", "InvalidFormat");

        boolean conflit = projetService.verifierConflits(projet);

        assertFalse(conflit);
        verify(preferenceHoraireRepository, times(1)).getAllPreferences();
    }
}
