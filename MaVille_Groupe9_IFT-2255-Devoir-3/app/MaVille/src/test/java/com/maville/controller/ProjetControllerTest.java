package com.maville.controller;

import com.maville.model.Projet;
import com.maville.service.ProjetService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjetControllerTest {

    private ProjetController controller;
    private ProjetService mockService;

    @BeforeEach
    void setUp() {
        mockService = Mockito.mock(ProjetService.class);
        controller = new ProjetController(mockService);
    }

    @Test
    void testGetTravauxAvenir3Mois() {
        List<Projet> mockProjets = Arrays.asList(
                new Projet("Travaux 1", "Description 1", null, "Quartier 1", "2025-01-01", "2025-03-31", "08:00-17:00"),
                new Projet("Travaux 2", "Description 2", null, "Quartier 2", "2025-02-01", "2025-04-30", "09:00-18:00")
        );

        when(mockService.getTravauxAvenir3Mois()).thenReturn(mockProjets);

        List<Projet> result = controller.getTravauxAvenir3Mois();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Travaux 1", result.get(0).getTitre());
        verify(mockService, times(1)).getTravauxAvenir3Mois();
    }

    @Test
    void testVerifierConflits() {
        Projet projet = new Projet("Travaux 3", "Description 3", null, "Quartier 3", "2025-05-01", "2025-06-30", "10:00-19:00");

        when(mockService.verifierConflits(projet)).thenReturn(true);

        boolean conflit = controller.verifierConflits(projet);

        assertTrue(conflit);
        verify(mockService, times(1)).verifierConflits(projet);
    }

    @Test
    void testSoumettreNouveauProjet() {
        Projet projet = new Projet("Travaux 4", "Description 4", null, "Quartier 4", "2025-07-01", "2025-08-30", "11:00-20:00");
        String intervenantId = "intervenant123";

        doNothing().when(mockService).soumettreNouveauProjet(projet, intervenantId);

        controller.soumettreNouveauProjet(projet, intervenantId);

        verify(mockService, times(1)).soumettreNouveauProjet(projet, intervenantId);
    }

    @Test
    void testModifierStatutProjet() {
        ObjectId projetId = new ObjectId();
        String nouveauStatut = "En cours";

        doNothing().when(mockService).modifierStatutProjet(projetId, nouveauStatut);

        controller.modifierStatutProjet(projetId, nouveauStatut);

        verify(mockService, times(1)).modifierStatutProjet(projetId, nouveauStatut);
    }

    @Test
    void testGetProjetsParIntervenant() {
        String intervenantId = "intervenant123";
        List<Projet> mockProjets = Arrays.asList(
                new Projet("Travaux 5", "Description 5", null, "Quartier 5", "2025-09-01", "2025-10-31", "12:00-21:00"),
                new Projet("Travaux 6", "Description 6", null, "Quartier 6", "2025-11-01", "2025-12-31", "13:00-22:00")
        );

        when(mockService.getProjetsParIntervenant(intervenantId)).thenReturn(mockProjets);

        List<Projet> result = controller.getProjetsParIntervenant(intervenantId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Travaux 5", result.get(0).getTitre());
        verify(mockService, times(1)).getProjetsParIntervenant(intervenantId);
    }
}
