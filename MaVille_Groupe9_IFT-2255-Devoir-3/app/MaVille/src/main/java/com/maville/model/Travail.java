package com.maville.model;

/**
 * Représente un travail reçu par l'API.
 * Contient des informations telles que l'identifiant, l'arrondissement, le statut actuel, et les catégories.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class Travail {
    private String id;
    private String boroughId;
    private String currentStatus;
    private String reasonCategory;
    private String submitterCategory;
    private String organizationName;

    /**
     * Obtient l'identifiant du travail.
     *
     * @return L'identifiant du travail.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant du travail.
     *
     * @param id L'identifiant du travail à définir.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtient l'identifiant de l'arrondissement associé au travail.
     *
     * @return L'identifiant de l'arrondissement.
     */
    public String getBoroughId() {
        return boroughId;
    }

    /**
     * Définit l'identifiant de l'arrondissement associé au travail.
     *
     * @param boroughId L'identifiant de l'arrondissement à définir.
     */
    public void setBoroughId(String boroughId) {
        this.boroughId = boroughId;
    }

    /**
     * Obtient le statut actuel du travail.
     *
     * @return Le statut actuel du travail.
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Définit le statut actuel du travail.
     *
     * @param currentStatus Le statut actuel du travail à définir.
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * Obtient la catégorie de motif du travail.
     *
     * @return La catégorie de motif du travail.
     */
    public String getReasonCategory() {
        return reasonCategory;
    }

    /**
     * Définit la catégorie de motif du travail.
     *
     * @param reasonCategory La catégorie de motif du travail à définir.
     */
    public void setReasonCategory(String reasonCategory) {
        this.reasonCategory = reasonCategory;
    }

    /**
     * Obtient la catégorie de soumissionnaire du travail.
     *
     * @return La catégorie de soumissionnaire du travail.
     */
    public String getSubmitterCategory() {
        return submitterCategory;
    }

    /**
     * Définit la catégorie de soumissionnaire du travail.
     *
     * @param submitterCategory La catégorie de soumissionnaire du travail à définir.
     */
    public void setSubmitterCategory(String submitterCategory) {
        this.submitterCategory = submitterCategory;
    }

    /**
     * Obtient le nom de l'organisation associée au travail.
     *
     * @return Le nom de l'organisation.
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Définit le nom de l'organisation associée au travail.
     *
     * @param organizationName Le nom de l'organisation à définir.
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du travail.
     *
     * @return Une chaîne de caractères décrivant le travail.
     */
    @Override
    public String toString() {
        return "Identifiant du travail : " + id + "\n" +
               "Arrondissement : " + boroughId + "\n" +
               "Statut actuel : " + currentStatus + "\n" +
               "Motif du travail : " + reasonCategory + "\n" +
               "Catégorie d'intervenant : " + submitterCategory + "\n" +
               "Nom de l'intervenant : " + organizationName + "\n" +
               "--------------------------------------------";
    }
}
