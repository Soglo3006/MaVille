package com.maville.controller;

import com.maville.model.PreferenceHoraire;
import com.maville.service.PreferenceHoraireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreferenceHoraireControllerTest {

    private PreferenceHoraireController controller;
    private PreferenceHoraireService mockService;

    @BeforeEach
    void setUp() {
        mockService = Mockito.mock(PreferenceHoraireService.class);
        controller = new PreferenceHoraireController(mockService);
    }

    @Test
    void testGetHoraires() {
        String email = "test@example.com";
        List<PreferenceHoraire> mockHoraires = Arrays.asList(
                new PreferenceHoraire("Lundi", "08:00", "12:00"),
                new PreferenceHoraire("Mardi", "14:00", "18:00")
        );

        when(mockService.getHoraires(email)).thenReturn(mockHoraires);

        List<PreferenceHoraire> horaires = controller.getHoraires(email);

        assertNotNull(horaires);
        assertEquals(2, horaires.size());
        assertEquals("Lundi", horaires.get(0).getJour());
        verify(mockService, times(1)).getHoraires(email);
    }

    @Test
    void testAjouterHoraire() {
        String email = "test@example.com";
        PreferenceHoraire horaire = new PreferenceHoraire("Mercredi", "10:00", "14:00");

        doNothing().when(mockService).ajouterHoraire(email, horaire);

        controller.ajouterHoraire(email, horaire);

        verify(mockService, times(1)).ajouterHoraire(email, horaire);
    }

    @Test
    void testModifierHoraire() {
        String email = "test@example.com";
        String horaireId = "horaire123";
        PreferenceHoraire nouveauHoraire = new PreferenceHoraire("Jeudi", "09:00", "12:00");

        doNothing().when(mockService).modifierHoraire(email, horaireId, nouveauHoraire);

        controller.modifierHoraire(email, horaireId, nouveauHoraire);

        verify(mockService, times(1)).modifierHoraire(email, horaireId, nouveauHoraire);
    }

    @Test
    void testSupprimerHoraire() {
        String email = "test@example.com";
        String horaireId = "horaire123";

        doNothing().when(mockService).supprimerHoraire(email, horaireId);

        controller.supprimerHoraire(email, horaireId);

        verify(mockService, times(1)).supprimerHoraire(email, horaireId);
    }

    @Test
    void testGetHoraireById() {
        String horaireId = "horaire123";
        PreferenceHoraire mockHoraire = new PreferenceHoraire("Vendredi", "13:00", "17:00");

        when(mockService.getHoraireById(horaireId)).thenReturn(mockHoraire);

        PreferenceHoraire horaire = controller.getHoraireById(horaireId);

        assertNotNull(horaire);
        assertEquals("Vendredi", horaire.getJour());
        assertEquals("13:00", horaire.getHeureDebut());
        verify(mockService, times(1)).getHoraireById(horaireId);
    }
}
