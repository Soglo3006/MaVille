package com.maville.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Service singleton pour mapper les codes postaux aux arrondissements correspondants.
 * Charge les données depuis un fichier JSON et fournit une méthode pour obtenir le quartier
 * basé sur le code postal.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class PostalCodeMapper {
    private static final String POSTAL_CODES_FILE = "com/maville/resources/liste_CodesPostals_Rues.json";
    private Map<String, String> postalCodeToBoroughMap;
    private static PostalCodeMapper instance;

    /**
     * Constructeur privé de la classe PostalCodeMapper.
     * Charge les données de mappage des codes postaux lors de l'initialisation.
     */
    private PostalCodeMapper() {
        postalCodeToBoroughMap = new HashMap<>();
        loadPostalCodes();
    }

    /**
     * Obtient l'instance unique de PostalCodeMapper (Singleton).
     *
     * @return L'instance unique de PostalCodeMapper.
     */
    public static PostalCodeMapper getInstance() {
        if (instance == null) {
            instance = new PostalCodeMapper();
        }
        return instance;
    }

    /**
     * Charge le fichier liste_CodesPostals_Rues.json et remplit la map de mappage.
     */
    private void loadPostalCodes() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Utilisation du ClassLoader pour charger la ressource sans slash initial
            InputStream is = getClass().getClassLoader().getResourceAsStream(POSTAL_CODES_FILE);
            if (is == null) {
                throw new RuntimeException("Fichier de codes postaux non trouvé: " + POSTAL_CODES_FILE);
            }
            JsonNode rootNode = objectMapper.readTree(is);
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                postalCodeToBoroughMap.put(entry.getKey(), entry.getValue().asText());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement des codes postaux: " + e.getMessage(), e);
        }
    }

    /**
     * Retourne le quartier correspondant au code postal donné.
     *
     * @param codePostal Code postal à trois caractères (ex: H2G).
     * @return Quartier ou {@code null} si non trouvé.
     */
    public String getBoroughByPostalCode(String codePostal) {
        return postalCodeToBoroughMap.get(codePostal.toUpperCase());
    }
}
