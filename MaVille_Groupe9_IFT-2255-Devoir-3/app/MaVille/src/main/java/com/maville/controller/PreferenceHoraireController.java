package com.maville.controller;

import com.maville.model.PreferenceHoraire;
import com.maville.service.PreferenceHoraireService;

import java.util.List;

/**
 * Contrôleur pour gérer les préférences horaires des utilisateurs.
 * Permet de récupérer, d'ajouter, de modifier et de supprimer des préférences horaires.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class PreferenceHoraireController {
    private PreferenceHoraireService preferenceHoraireService;

    /**
     * Constructeur de PreferenceHoraireController.
     *
     * @param preferenceHoraireService Le service des préférences horaires.
     */
    public PreferenceHoraireController(PreferenceHoraireService preferenceHoraireService) {
        this.preferenceHoraireService = preferenceHoraireService;
    }

    /**
     * Récupère les horaires préférés d'un utilisateur à partir de son email.
     *
     * @param email L'email de l'utilisateur.
     * @return Une liste des préférences horaires de l'utilisateur.
     */
    public List<PreferenceHoraire> getHoraires(String email) {
        return preferenceHoraireService.getHoraires(email);
    }

    /**
     * Ajoute une nouvelle préférence horaire pour un utilisateur.
     *
     * @param email   L'email de l'utilisateur.
     * @param horaire La préférence horaire à ajouter.
     */
    public void ajouterHoraire(String email, PreferenceHoraire horaire) {
        preferenceHoraireService.ajouterHoraire(email, horaire);
    }

    /**
     * Modifie une préférence horaire existante pour un utilisateur.
     *
     * @param email               L'email de l'utilisateur.
     * @param preferenceHoraireId L'ID de la préférence horaire à modifier.
     * @param nouveauHoraire      La nouvelle préférence horaire.
     */
    public void modifierHoraire(String email, String preferenceHoraireId, PreferenceHoraire nouveauHoraire) {
        preferenceHoraireService.modifierHoraire(email, preferenceHoraireId, nouveauHoraire);
    }

    /**
     * Supprime une préférence horaire existante pour un utilisateur.
     *
     * @param email               L'email de l'utilisateur.
     * @param preferenceHoraireId L'ID de la préférence horaire à supprimer.
     */
    public void supprimerHoraire(String email, String preferenceHoraireId) {
        preferenceHoraireService.supprimerHoraire(email, preferenceHoraireId);
    }

    /**
     * Récupère une préférence horaire spécifique par son ID.
     *
     * @param id L'ID de la préférence horaire.
     * @return La préférence horaire correspondante, ou {@code null} si non trouvée.
     */
    public PreferenceHoraire getHoraireById(String id) {
        return preferenceHoraireService.getHoraireById(id);
    }
}
