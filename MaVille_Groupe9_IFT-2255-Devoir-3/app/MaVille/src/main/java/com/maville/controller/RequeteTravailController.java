package com.maville.controller;

import com.maville.model.RequeteTravail;
import com.maville.repository.RequeteTravailRepository;

import java.util.List;

/**
 * Contrôleur pour gérer les requêtes de travail.
 * Permet de soumettre, mettre à jour, confirmer ou refuser des candidatures, et de gérer les requêtes de travail.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class RequeteTravailController {
    private RequeteTravailRepository requeteTravailRepository;

    /**
     * Constructeur de RequeteTravailController.
     *
     * @param requeteTravailRepository Le repository des requêtes de travail.
     */
    public RequeteTravailController(RequeteTravailRepository requeteTravailRepository) {
        this.requeteTravailRepository = requeteTravailRepository;
    }

    /**
     * Soumet une nouvelle requête de travail.
     *
     * @param requeteTravail La requête de travail à soumettre.
     */
    public void soumettreNouvRequeteTravail(RequeteTravail requeteTravail) {
        requeteTravailRepository.sauvegarder(requeteTravail);
    }

    /**
     * Récupère une requête de travail par son ID.
     *
     * @param id L'ID de la requête de travail.
     * @return La requête de travail correspondante, ou {@code null} si non trouvée.
     */
    public RequeteTravail getRequeteDeTravailAvecId(String id) {
        return requeteTravailRepository.getRequeteTravailById(id);
    }

    /**
     * Met à jour une requête de travail existante.
     *
     * @param requeteTravail La requête de travail à mettre à jour.
     */
    public void majRequeteTravail(RequeteTravail requeteTravail) {
        requeteTravailRepository.majRequeteTravail(requeteTravail);
    }

    /**
     * Confirme une candidature pour une requête de travail.
     *
     * @param requeteId      L'ID de la requête de travail.
     * @param intervenantId L'ID de l'intervenant.
     * @param message        Le message de confirmation.
     */
    public void confirmerCandidature(String requeteId, String intervenantId, String message) {
        requeteTravailRepository.accepterCandidature(requeteId, intervenantId, message);
    }

    /**
     * Refuse une candidature pour une requête de travail.
     *
     * @param requeteId      L'ID de la requête de travail.
     * @param intervenantId L'ID de l'intervenant.
     * @param message        Le message de refus.
     */
    public void refuserCandidature(String requeteId, String intervenantId, String message) {
        requeteTravailRepository.refuserCandidature(requeteId, intervenantId, message);
    }

    /**
     * Ajoute une candidature à une requête de travail.
     *
     * @param requeteId      L'ID de la requête de travail.
     * @param intervenantId L'ID de l'intervenant.
     */
    public void ajouterCandidature(String requeteId, String intervenantId) {
        requeteTravailRepository.ajouterCandidature(requeteId, intervenantId);
    }

    /**
     * Retire une candidature d'une requête de travail.
     *
     * @param requeteId      L'ID de la requête de travail.
     * @param intervenantId L'ID de l'intervenant.
     */
    public void retirerCandidature(String requeteId, String intervenantId) {
        requeteTravailRepository.retirerCandidature(requeteId, intervenantId);
    }

    /**
     * Récupère une requête de travail associée à un candidat spécifique.
     *
     * @param intervenantId L'ID de l'intervenant.
     * @return La première requête de travail trouvée associée au candidat, ou {@code null} si aucune requête n'est trouvée.
     */
    public RequeteTravail getRequeteDeTravailAvecCandidat(String intervenantId) {
        List<RequeteTravail> requetes = requeteTravailRepository.getRequetesParCandidat(intervenantId);
        if (!requetes.isEmpty()) {
            return requetes.get(0);
        }
        return null;
    }

    /**
     * Récupère toutes les requêtes de travail.
     *
     * @return Une liste de toutes les requêtes de travail.
     */
    public List<RequeteTravail> getAllRequetesTravail() {
        return requeteTravailRepository.getAllRequetesTravail();
    }
}
