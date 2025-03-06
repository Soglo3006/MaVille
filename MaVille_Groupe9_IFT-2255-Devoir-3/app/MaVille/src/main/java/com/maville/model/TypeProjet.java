package com.maville.model;

/**
 * Enumération représentant les différents types de projets.
 * Chaque type de projet a une description associée.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public enum TypeProjet {
    TRAVAUX_ROUTIERS("Travaux routiers"),
    TRAVAUX_DE_GAZ_OU_ELECTRICITE("Travaux de gaz ou électricité"),
    CONSTRUCTION_OU_RENOVATION("Construction ou rénovation"),
    ENTRETIEN_PAYSAGER("Entretien paysager"),
    TRANSPORTS_EN_COMMUN("Transports en commun"),
    SIGNALISATION_ET_ECLAIRAGE("Signalisation et éclairage"),
    TRAVAUX_SOUTERRAINS("Travaux souterrains"),
    TRAVAUX_RESIDENTIELS("Travaux résidentiels"),
    ENTRETIEN_URBAIN("Entretien urbain"),
    ENTRETIEN_DES_RESEAU_DE_TELECOMMUNICATION("Entretien des réseaux de télécommunication");

    private final String description;

    /**
     * Constructeur de l'énumération TypeProjet.
     *
     * @param description La description du type de projet.
     */
    TypeProjet(String description) {
        this.description = description;
    }

    /**
     * Obtient la description du type de projet.
     *
     * @return La description du type de projet.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retourne la description du type de projet.
     *
     * @return La description du type de projet.
     */
    @Override
    public String toString() {
        return description;
    }
}
