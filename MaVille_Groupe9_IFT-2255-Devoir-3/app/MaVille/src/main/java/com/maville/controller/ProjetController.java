package com.maville.controller;

import com.maville.model.Projet;
import com.maville.service.ProjetService;

import java.util.List;

import org.bson.types.ObjectId;

/**
 * Contrôleur pour gérer les projets.
 * Permet de récupérer, soumettre, modifier le statut et vérifier les conflits des projets.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class ProjetController {
    private ProjetService projetService;

    /**
     * Constructeur de ProjetController.
     *
     * @param projetService Le service des projets.
     */
    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    /**
     * Récupère les travaux à venir dans les 3 prochains mois.
     *
     * @return Une liste de travaux à venir.
     */
    public List<Projet> getTravauxAvenir3Mois() {
        return projetService.getTravauxAvenir3Mois();
    }

    /**
     * Vérifie s'il y a des conflits pour un projet donné.
     *
     * @param projet Le projet à vérifier.
     * @return {@code true} s'il y a des conflits, {@code false} sinon.
     */
    public boolean verifierConflits(Projet projet) {
        return projetService.verifierConflits(projet);
    }

    /**
     * Soumet un nouveau projet avec l'ID de l'intervenant associé.
     *
     * @param projet        Le projet à soumettre.
     * @param intervenantId L'ID de l'intervenant.
     */
    public void soumettreNouveauProjet(Projet projet, String intervenantId) {
        projetService.soumettreNouveauProjet(projet, intervenantId);
    }

    /**
     * Modifie le statut d'un projet existant.
     *
     * @param projetId     L'ID du projet à modifier.
     * @param nouveauStatut Le nouveau statut du projet.
     */
    public void modifierStatutProjet(ObjectId projetId, String nouveauStatut) {
        projetService.modifierStatutProjet(projetId, nouveauStatut);
    }

    /**
     * Récupère les projets associés à un intervenant spécifique.
     *
     * @param intervenantId L'ID de l'intervenant.
     * @return Une liste des projets associés à l'intervenant.
     */
    public List<Projet> getProjetsParIntervenant(String intervenantId) {
        return projetService.getProjetsParIntervenant(intervenantId);
    }
}
