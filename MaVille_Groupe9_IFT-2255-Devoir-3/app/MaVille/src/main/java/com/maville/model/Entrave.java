package com.maville.model;

/**
 * Représente une entrave liée à une rue dans une requête de travail.
 * Contient des informations sur l'impact sur la rue, le trottoir et la piste cyclable.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class Entrave {
    private String idRequest;
    private String streetId;
    private String shortName;
    private String streetImpactType;
    private String streetImpactWidth;
    private String sidewalkBlockedType;
    private String bikePathBlockedType;

    /**
     * Obtient l'identifiant de la requête.
     *
     * @return L'identifiant de la requête.
     */
    public String getIdRequest() {
        return idRequest;
    }

    /**
     * Définit l'identifiant de la requête.
     *
     * @param idRequest L'identifiant de la requête à définir.
     */
    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    /**
     * Obtient l'identifiant de la rue.
     *
     * @return L'identifiant de la rue.
     */
    public String getStreetId() {
        return streetId;
    }

    /**
     * Définit l'identifiant de la rue.
     *
     * @param streetId L'identifiant de la rue à définir.
     */
    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    /**
     * Obtient le nom abrégé de la rue.
     *
     * @return Le nom abrégé de la rue.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Définit le nom abrégé de la rue.
     *
     * @param shortName Le nom abrégé de la rue à définir.
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Obtient le type d'impact sur la rue.
     *
     * @return Le type d'impact sur la rue.
     */
    public String getStreetImpactType() {
        return streetImpactType;
    }

    /**
     * Définit le type d'impact sur la rue.
     *
     * @param streetImpactType Le type d'impact sur la rue à définir.
     */
    public void setStreetImpactType(String streetImpactType) {
        this.streetImpactType = streetImpactType;
    }

    /**
     * Obtient la largeur de l'impact sur la rue.
     *
     * @return La largeur de l'impact sur la rue.
     */
    public String getStreetImpactWidth() {
        return streetImpactWidth;
    }

    /**
     * Définit la largeur de l'impact sur la rue.
     *
     * @param streetImpactWidth La largeur de l'impact sur la rue à définir.
     */
    public void setStreetImpactWidth(String streetImpactWidth) {
        this.streetImpactWidth = streetImpactWidth;
    }

    /**
     * Obtient le type de trottoir bloqué.
     *
     * @return Le type de trottoir bloqué.
     */
    public String getSidewalkBlockedType() {
        return sidewalkBlockedType;
    }

    /**
     * Définit le type de trottoir bloqué.
     *
     * @param sidewalkBlockedType Le type de trottoir bloqué à définir.
     */
    public void setSidewalkBlockedType(String sidewalkBlockedType) {
        this.sidewalkBlockedType = sidewalkBlockedType;
    }

    /**
     * Obtient le type de piste cyclable impactée.
     *
     * @return Le type de piste cyclable impactée.
     */
    public String getBikePathBlockedType() {
        return bikePathBlockedType;
    }

    /**
     * Définit le type de piste cyclable impactée.
     *
     * @param bikePathBlockedType Le type de piste cyclable impactée à définir.
     */
    public void setBikePathBlockedType(String bikePathBlockedType) {
        this.bikePathBlockedType = bikePathBlockedType;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'entrave.
     *
     * @return Une chaîne de caractères décrivant l'entrave.
     */
    @Override
    public String toString() {
        return "Identifiant de la requête : " + idRequest + "\n" +
               "Identifiant de la rue : " + streetId + "\n" +
               "Nom de la rue : " + shortName + "\n" +
               "Impact sur la rue : " + streetImpactType + "\n" +
               "Largeur d'impact : " + streetImpactWidth + "\n" +
               "Trottoir bloqué : " + sidewalkBlockedType + "\n" +
               "Piste cyclable impactée : " + bikePathBlockedType + "\n" +
               "--------------------------------------------";
    }
}
