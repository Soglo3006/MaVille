package com.maville.model;

import org.bson.types.ObjectId;

/**
 * Représente un intervenant impliqué dans un projet.
 * Contient des informations telles que le nom complet, l'email, le type d'intervenant, et l'ID de la ville.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class Intervenant {
    private ObjectId id;
    private String nomComplet;
    private String email;
    private String mdp;
    private String type;            // 'Entreprise public', 'Entrepreneur privé' ou 'Particulier'
    private String villeId;
    private boolean aCandidature;   // Indique si l'intervenant a une candidature active

    /**
     * Constructeur de la classe Intervenant.
     *
     * @param nomComplet Le nom complet de l'intervenant.
     * @param email      L'email de l'intervenant.
     * @param mdp        Le mot de passe de l'intervenant.
     * @param type       Le type d'intervenant ('Entreprise public', 'Entrepreneur privé' ou 'Particulier').
     * @param villeId    L'identifiant de la ville associée à l'intervenant.
     */
    public Intervenant(String nomComplet, String email, String mdp, String type, String villeId) {
        this.nomComplet = nomComplet;
        this.email = email;
        this.mdp = mdp;
        this.type = type;
        this.villeId = villeId;
        this.aCandidature = false;
    }

    /**
     * Obtient l'identifiant de l'intervenant.
     *
     * @return L'identifiant de l'intervenant.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'intervenant.
     *
     * @param id L'identifiant de l'intervenant à définir.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtient le nom complet de l'intervenant.
     *
     * @return Le nom complet de l'intervenant.
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * Définit le nom complet de l'intervenant.
     *
     * @param nomComplet Le nom complet de l'intervenant à définir.
     */
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    /**
     * Obtient l'email de l'intervenant.
     *
     * @return L'email de l'intervenant.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de l'intervenant.
     *
     * @param email L'email de l'intervenant à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtient le mot de passe de l'intervenant.
     *
     * @return Le mot de passe de l'intervenant.
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Définit le mot de passe de l'intervenant.
     *
     * @param mdp Le mot de passe de l'intervenant à définir.
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Obtient le type d'intervenant.
     *
     * @return Le type d'intervenant.
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type d'intervenant.
     *
     * @param type Le type d'intervenant à définir.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtient l'identifiant de la ville associée à l'intervenant.
     *
     * @return L'identifiant de la ville.
     */
    public String getVilleId() {
        return villeId;
    }

    /**
     * Définit l'identifiant de la ville associée à l'intervenant.
     *
     * @param villeId L'identifiant de la ville à définir.
     */
    public void setVilleId(String villeId) {
        this.villeId = villeId;
    }

    /**
     * Vérifie si l'intervenant a une candidature active.
     *
     * @return {@code true} si l'intervenant a une candidature active, {@code false} sinon.
     */
    public boolean isACandidature() {
        return aCandidature;
    }

    /**
     * Définit si l'intervenant a une candidature active.
     *
     * @param aCandidature {@code true} si l'intervenant a une candidature active, {@code false} sinon.
     */
    public void setACandidature(boolean aCandidature) {
        this.aCandidature = aCandidature;
    }

    /**
     * Vérifie si l'identifiant de la ville est valide.
     *
     * @return {@code true} si l'identifiant de la ville est valide, {@code false} sinon.
     */
    public boolean villeIdEstOk() {
        return villeId != null && villeId.matches("\\d{8}");
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'intervenant.
     *
     * @return Une chaîne de caractères décrivant l'intervenant.
     */
    @Override
    public String toString() {
        return "Intervenant{" +
                "id=" + id +
                ", nomComplet='" + nomComplet + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", villeId='" + villeId + '\'' +
                ", aCandidature=" + aCandidature +
                '}';
    }
}
