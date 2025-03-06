package com.maville.controller;

import com.maville.model.Resident;
import com.maville.service.MongoDBService;

/**
 * Contrôleur pour gérer les opérations liées aux résidents.
 * Permet l'inscription et la connexion des résidents.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class ResidentController {
    private MongoDBService dbService; 

    /**
     * Constructeur de ResidentController.
     *
     * @param dbService Le service MongoDB utilisé pour les opérations de base de données.
     */
    public ResidentController(MongoDBService dbService) {
        this.dbService = dbService;
    }

    /**
     * Inscrit un nouveau résident après avoir vérifié certaines conditions.
     *
     * @param resident Le résident à inscrire.
     * @return Un message indiquant le résultat de l'inscription.
     */
    public String inscrireResident(Resident resident) {
        if (!resident.auDessusDe16()) {
            return "Erreur: Le résident doit avoir plus de 16 ans.";
        }
        if (dbService.trouverResidentAvecEmail(resident.getEmail()) != null) {
            return "Erreur: Adresse courriel déjà utilisée.";
        }
        dbService.sauvegarderResident(resident);
        return "Inscription réussie.";
    }

    /**
     * Connecte un résident en vérifiant son email et son mot de passe.
     *
     * @param email L'email du résident.
     * @param mdp   Le mot de passe du résident.
     * @return Un message indiquant le résultat de la connexion.
     */
    public String connecterResident(String email, String mdp) {
        Resident resident = dbService.trouverResidentAvecEmail(email);
        if (resident != null && resident.getMdp().equals(mdp)) {
            return "Connexion réussie.";
        }
        return "Erreur: Adresse courriel ou mot de passe incorrect.";
    }
}
