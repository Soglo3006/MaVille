package com.maville.service;

import com.maville.model.PreferenceHoraire;
import com.maville.repository.PreferenceHoraireRepository;

import java.util.List;

/**
 * Service pour gérer les préférences horaires des résidents.
 * Fournit des méthodes pour récupérer, ajouter, modifier et supprimer les préférences horaires.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class PreferenceHoraireService {
    private PreferenceHoraireRepository preferenceHoraireRepository;

    /**
     * Constructeur de la classe PreferenceHoraireService.
     *
     * @param preferenceHoraireRepository Le repository des préférences horaires.
     */
    public PreferenceHoraireService(PreferenceHoraireRepository preferenceHoraireRepository) {
        this.preferenceHoraireRepository = preferenceHoraireRepository;
    }

    /**
     * Récupère toutes les préférences horaires d'un résident en utilisant son email.
     *
     * @param email L'email du résident.
     * @return Une liste des préférences horaires du résident.
     */
    public List<PreferenceHoraire> getHoraires(String email) {
        return preferenceHoraireRepository.getPreferencesHorairesByResidentEmail(email);
    }

    /**
     * Ajoute une nouvelle préférence horaire pour un résident.
     *
     * @param email   L'email du résident.
     * @param horaire La préférence horaire à ajouter.
     */
    public void ajouterHoraire(String email, PreferenceHoraire horaire) {
        horaire.setResidentEmail(email);
        preferenceHoraireRepository.sauvegarder(horaire);
    }

    /**
     * Modifie une préférence horaire existante pour un résident.
     *
     * @param email               L'email du résident.
     * @param preferenceHoraireId L'identifiant de la préférence horaire à modifier.
     * @param nouveauHoraire      La nouvelle préférence horaire.
     */
    public void modifierHoraire(String email, String preferenceHoraireId, PreferenceHoraire nouveauHoraire) {
        PreferenceHoraire existingPreference = preferenceHoraireRepository.getPreferenceHoraireById(preferenceHoraireId);
        if (existingPreference != null && existingPreference.getResidentEmail().equals(email)) {
            nouveauHoraire.setId(existingPreference.getId());
            nouveauHoraire.setResidentEmail(email);
            preferenceHoraireRepository.majPreferenceHoraire(nouveauHoraire);
        }
    }

    /**
     * Supprime une préférence horaire existante pour un résident.
     *
     * @param email               L'email du résident.
     * @param preferenceHoraireId L'identifiant de la préférence horaire à supprimer.
     */
    public void supprimerHoraire(String email, String preferenceHoraireId) {
        PreferenceHoraire existingPreference = preferenceHoraireRepository.getPreferenceHoraireById(preferenceHoraireId);
        if (existingPreference != null && existingPreference.getResidentEmail().equals(email)) {
            preferenceHoraireRepository.supprimerPreferenceHoraire(preferenceHoraireId);
        }
    }

    /**
     * Récupère une préférence horaire spécifique en utilisant son identifiant.
     *
     * @param id L'identifiant de la préférence horaire.
     * @return La préférence horaire correspondante, ou {@code null} si non trouvée.
     */
    public PreferenceHoraire getHoraireById(String id) {
        return preferenceHoraireRepository.getPreferenceHoraireById(id);
    }
}
