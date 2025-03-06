package com.maville.model;

import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Représente un résident de la ville.
 * Contient des informations personnelles telles que le nom, la date de naissance, l'email, et l'adresse.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class Resident {
    private ObjectId id;
    private String nomComplet;
    private String dateNaissance;
    private String email;
    private String mdp;
    private String telephone;
    private String adresse;
    private String codePostal;
    private String quartier; // Pour le traitement des notifications

    /**
     * Constructeur de la classe Resident.
     *
     * @param nomComplet     Le nom complet du résident.
     * @param dateNaissance  La date de naissance du résident au format "yyyy-MM-dd".
     * @param email          L'email du résident.
     * @param mdp            Le mot de passe du résident.
     * @param telephone      Le numéro de téléphone du résident.
     * @param adresse        L'adresse du résident.
     * @param codePostal     Le code postal de l'adresse du résident.
     */
    public Resident(String nomComplet, String dateNaissance, String email, String mdp, String telephone, String adresse, String codePostal) {
        this.nomComplet = nomComplet;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.mdp = mdp;
        this.telephone = telephone;
        this.adresse = adresse;
        this.codePostal = codePostal;
    }

    /**
     * Obtient l'identifiant du résident.
     *
     * @return L'identifiant du résident.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Définit l'identifiant du résident.
     *
     * @param id L'identifiant du résident à définir.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtient le nom complet du résident.
     *
     * @return Le nom complet du résident.
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * Définit le nom complet du résident.
     *
     * @param nomComplet Le nom complet du résident à définir.
     */
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    /**
     * Obtient la date de naissance du résident.
     *
     * @return La date de naissance au format "yyyy-MM-dd".
     */
    public String getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Définit la date de naissance du résident.
     *
     * @param dateNaissance La date de naissance au format "yyyy-MM-dd" à définir.
     */
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Obtient l'email du résident.
     *
     * @return L'email du résident.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email du résident.
     *
     * @param email L'email du résident à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtient le mot de passe du résident.
     *
     * @return Le mot de passe du résident.
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Définit le mot de passe du résident.
     *
     * @param mdp Le mot de passe du résident à définir.
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Obtient le numéro de téléphone du résident.
     *
     * @return Le numéro de téléphone du résident.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le numéro de téléphone du résident.
     *
     * @param telephone Le numéro de téléphone du résident à définir.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Obtient l'adresse du résident.
     *
     * @return L'adresse du résident.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse du résident.
     *
     * @param adresse L'adresse du résident à définir.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Obtient le code postal de l'adresse du résident.
     *
     * @return Le code postal de l'adresse du résident.
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Définit le code postal de l'adresse du résident.
     *
     * @param codePostal Le code postal de l'adresse du résident à définir.
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Obtient le quartier du résident.
     *
     * @return Le quartier du résident.
     */
    public String getQuartier() {
        return quartier;
    }

    /**
     * Définit le quartier du résident.
     *
     * @param quartier Le quartier du résident à définir.
     */
    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    /**
     * Vérifie si le résident a au moins 16 ans.
     *
     * @return {@code true} si le résident a au moins 16 ans, {@code false} sinon.
     */
    public boolean auDessusDe16() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateNaissance = LocalDate.parse(this.dateNaissance, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateNaissance, currentDate).getYears() >= 16;
    }

    /**
     * Vérifie si la date de naissance du résident est valide.
     *
     * @return {@code true} si la date de naissance est valide, {@code false} sinon.
     */
    public boolean estDateValide() {
        String bd = this.dateNaissance;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(bd, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Vérifie si le code postal du résident est valide.
     * Le format attendu est "A1A 1A1" ou "A1A1A1".
     *
     * @return {@code true} si le code postal est valide, {@code false} sinon.
     */
    public boolean estCodePostalValide() {
        String codePostal = this.codePostal;
        String postalCodePattern = "^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$";
        return Pattern.matches(postalCodePattern, codePostal);
    }
}
