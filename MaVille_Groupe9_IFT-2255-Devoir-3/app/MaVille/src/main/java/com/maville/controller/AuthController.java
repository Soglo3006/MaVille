package com.maville.controller;

import com.maville.model.Resident;
import com.maville.model.Intervenant;
import com.maville.repository.ResidentRepository;
import com.maville.repository.IntervenantRepository;

/**
 * Contrôleur d'authentification pour gérer les opérations liées aux résidents et aux intervenants.
 * Cette classe permet d'enregistrer, de connecter et de récupérer des utilisateurs (résidents ou intervenants).
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class AuthController {
    private ResidentRepository residentRepository;
    private IntervenantRepository intervenantRepository;
    private String emailDuUserConnecte;

    /**
     * Constructeur de AuthController.
     *
     * @param residentRepository       Le repository des résidents.
     * @param intervenantRepository    Le repository des intervenants.
     */
    public AuthController(ResidentRepository residentRepository, IntervenantRepository intervenantRepository) {
        this.residentRepository = residentRepository;
        this.intervenantRepository = intervenantRepository;
    }

    /**
     * Enregistre un nouveau résident.
     *
     * @param resident Le résident à enregistrer.
     */
    public void enregistrerResident(Resident resident) {
        residentRepository.sauvegarder(resident);
    }

    /**
     * Enregistre un nouvel intervenant.
     *
     * @param intervenant L'intervenant à enregistrer.
     */
    public void enregistrerIntervenant(Intervenant intervenant) {
        intervenantRepository.sauvegarder(intervenant);
    }

    /**
     * Connecte un résident en vérifiant son email et son mot de passe.
     *
     * @param email L'email du résident.
     * @param mdp   Le mot de passe du résident.
     * @return {@code true} si la connexion est réussie, {@code false} sinon.
     */
    public boolean connecterResident(String email, String mdp) {
        Resident resident = residentRepository.trouverAvecEmail(email);
        if (resident != null && resident.getMdp().equals(mdp)) {
            this.emailDuUserConnecte = email;
            return true;
        }
        return false;
    }

    /**
     * Connecte un intervenant en vérifiant son email et son mot de passe.
     *
     * @param email L'email de l'intervenant.
     * @param mdp   Le mot de passe de l'intervenant.
     * @return {@code true} si la connexion est réussie, {@code false} sinon.
     */
    public boolean connecterIntervenant(String email, String mdp) {
        Intervenant intervenant = intervenantRepository.trouverAvecEmail(email);
        if (intervenant != null && intervenant.getMdp().equals(mdp)) {
            this.emailDuUserConnecte = email;
            return true;
        }
        return false;
    }

    /**
     * Récupère un résident à partir de son email.
     *
     * @param email L'email du résident.
     * @return Le résident correspondant, ou {@code null} si aucun résident n'est trouvé.
     */
    public Resident getResidentByEmail(String email) {
        return residentRepository.trouverAvecEmail(email);
    }

    /**
     * Récupère un intervenant à partir de son email.
     *
     * @param email L'email de l'intervenant.
     * @return L'intervenant correspondant, ou {@code null} si aucun intervenant n'est trouvé.
     */
    public Intervenant getIntervenantByEmail(String email) {
        return intervenantRepository.trouverAvecEmail(email);
    }

    /**
     * Obtient l'email de l'utilisateur actuellement connecté.
     *
     * @return L'email de l'utilisateur connecté, ou {@code null} si aucun utilisateur n'est connecté.
     */
    public String getEmailDuUserConnecte() {
        return emailDuUserConnecte;
    }

    /**
     * Définit l'email de l'utilisateur actuellement connecté.
     *
     * @param email L'email à définir comme utilisateur connecté.
     */
    public void setEmailDuUserConnecte(String email) {
        this.emailDuUserConnecte = email;
    }

    /**
     * Obtient l'ID de l'utilisateur actuellement connecté.
     *
     * @return L'ID de l'utilisateur connecté sous forme de chaîne de caractères, ou {@code null} si aucun utilisateur n'est connecté.
     */
    public String getUserId() {
        if (emailDuUserConnecte == null) {
            return null;
        }
        Resident resident = residentRepository.trouverAvecEmail(emailDuUserConnecte);
        if (resident != null) {
            return resident.getId().toHexString(); // Convertit ObjectId en String
        }
        Intervenant intervenant = intervenantRepository.trouverAvecEmail(emailDuUserConnecte);
        if (intervenant != null) {
            return intervenant.getId().toHexString(); // Convertit ObjectId en String
        }
        return null;
    }

    /**
     * Déconnecte l'utilisateur actuellement connecté.
     */
    public void logout() {
        this.emailDuUserConnecte = null;
    }
}
