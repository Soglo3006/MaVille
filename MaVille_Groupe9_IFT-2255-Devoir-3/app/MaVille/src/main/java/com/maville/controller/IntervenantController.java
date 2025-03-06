package com.maville.controller;

import com.maville.model.Intervenant;
import com.maville.service.MongoDBService;

/**
 * Contrôleur pour gérer les opérations liées aux intervenants.
 * Permet l'inscription et la connexion des intervenants.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class IntervenantController {
    private MongoDBService dbService;

    /**
     * Constructeur de IntervenantController.
     *
     * @param dbService Le service MongoDB utilisé pour les opérations de base de données.
     */
    public IntervenantController(MongoDBService dbService) {
        this.dbService = dbService;
    }

    /**
     * Inscrit un nouvel intervenant.
     *
     * @param intervenant L'intervenant à inscrire.
     * @return Un message indiquant le résultat de l'inscription.
     */
    public String inscrireIntervenant(Intervenant intervenant) {
        if (dbService.trouverIntervenantAvecEmail(intervenant.getEmail()) != null) {
            return "Erreur: Adresse courriel déjà utilisée.";
        }
        dbService.sauvegarderIntervenant(intervenant);
        return "Inscription réussie.";
    }

    /**
     * Connecte un intervenant en vérifiant son email et son mot de passe.
     *
     * @param email L'email de l'intervenant.
     * @param mdp   Le mot de passe de l'intervenant.
     * @return Un message indiquant le résultat de la connexion.
     */
    public String connecterIntervenant(String email, String mdp) {
        Intervenant intervenant = dbService.trouverIntervenantAvecEmail(email);
        if (intervenant != null && intervenant.getMdp().equals(mdp)) {
            return "Connexion réussie.";
        }
        return "Erreur: Adresse courriel ou mot de passe incorrect.";
    }
}
