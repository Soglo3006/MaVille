package com.maville.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maville.model.Entrave;
import com.maville.model.Travail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

/**
 * Service pour gérer les travaux en cours et les entraves en cours.
 * Fournit des méthodes pour récupérer et filtrer les données depuis des API externes.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class TravauxEnCoursEtEntraveService {

    private static final String TRAVAUX_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b&limit=1000";
    private static final String ENTRAVES_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd&limit=1000";

    private final ObjectMapper objectMapper;
    private final PostalCodeMapper postalCodeMapper;

    /**
     * Constructeur de la classe TravauxEnCoursEtEntraveService.
     */
    public TravauxEnCoursEtEntraveService() {
        this.objectMapper = new ObjectMapper();
        this.postalCodeMapper = PostalCodeMapper.getInstance();
    }

    /**
     * Récupère la liste des travaux en cours depuis l'API.
     *
     * @return Liste de travaux.
     * @throws IOException Si une erreur survient lors de la récupération des données.
     */
    public List<Travail> getTravauxEnCours() throws IOException {
        String jsonData = fetchDataFromUrl(TRAVAUX_URL);
        return parseTravaux(jsonData);
    }

    /**
     * Récupère la liste des entraves depuis l'API.
     *
     * @return Liste d'entraves.
     * @throws IOException Si une erreur survient lors de la récupération des données.
     */
    public List<Entrave> getEntraves() throws IOException {
        String jsonData = fetchDataFromUrl(ENTRAVES_URL);
        return parseEntraves(jsonData);
    }

    /**
     * Filtre les travaux par arrondissement basé sur le code postal.
     *
     * @param codePostal Code postal à trois caractères (ex: H2G).
     * @return Liste de travaux filtrés.
     * @throws IOException Si une erreur survient lors de la récupération des données.
     */
    public List<Travail> getTravauxParArrondissement(String codePostal) throws IOException {
        String borough = postalCodeMapper.getBoroughByPostalCode(codePostal);
        if (borough == null) {
            throw new IllegalArgumentException("Code postal invalide ou non reconnu.");
        }

        List<Travail> tousTravaux = getTravauxEnCours();
        List<Travail> travauxFiltres = new ArrayList<>();
        for (Travail travail : tousTravaux) {
            if (borough.equalsIgnoreCase(travail.getBoroughId())) {
                travauxFiltres.add(travail);
            }
        }
        return travauxFiltres;
    }

    /**
     * Filtre les entraves par nom de rue.
     *
     * @param nomRue Nom de la rue à rechercher.
     * @return Liste d'entraves filtrées.
     * @throws IOException Si une erreur survient lors de la récupération des données.
     */
    public List<Entrave> getEntravesParNomRue(String nomRue) throws IOException {
        List<Entrave> toutesEntraves = getEntraves();
        List<Entrave> entravesFiltres = new ArrayList<>();

        for (Entrave entrave : toutesEntraves) {
            if (nomRue.equalsIgnoreCase(entrave.getShortName())) {
                entravesFiltres.add(entrave);
            }
        }

        return entravesFiltres;
    }

    /**
     * Récupère les entraves groupées par nom de rue.
     *
     * @return Une map où la clé est le nom de la rue et la valeur est une liste d'entraves associées.
     * @throws IOException Si une erreur survient lors de la récupération des données.
     */
    public Map<String, List<Entrave>> getEntravesGroupedByNomRue() throws IOException {
        List<Entrave> toutesEntraves = getEntraves();
        Map<String, List<Entrave>> entravesParRue = new HashMap<>();
    
        for (Entrave entrave : toutesEntraves) {
            String nomRue = entrave.getShortName();
            if (nomRue == null || nomRue.trim().isEmpty()) {
                nomRue = "Rue Inconnue"; // Gérer les entraves sans nom de rue
            }
            entravesParRue.computeIfAbsent(nomRue, k -> new ArrayList<>()).add(entrave);
        }
    
        return entravesParRue;
    }

    /**
     * Parse les données JSON des travaux en cours.
     *
     * @param jsonData Données JSON.
     * @return Liste de travaux.
     * @throws IOException Si une erreur survient lors de la parsing.
     */
    private List<Travail> parseTravaux(String jsonData) throws IOException {
        List<Travail> travauxList = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode recordsNode = rootNode.path("result").path("records");

        if (recordsNode.isArray()) {
            for (JsonNode record : recordsNode) {
                Travail travail = new Travail();
                travail.setId(record.path("id").asText());
                travail.setBoroughId(record.path("boroughid").asText());
                travail.setCurrentStatus(record.path("currentstatus").asText());
                travail.setReasonCategory(record.path("reason_category").asText());
                travail.setSubmitterCategory(record.path("submittercategory").asText());
                travail.setOrganizationName(record.path("organizationname").asText());
                travauxList.add(travail);
            }
        } else {
            throw new IOException("Les données JSON ne contiennent pas le noeud attendu.");
        }

        return travauxList;
    }

    /**
     * Parse les données JSON des entraves.
     *
     * @param jsonData Données JSON.
     * @return Liste d'entraves.
     * @throws IOException Si une erreur survient lors de la parsing.
     */
    private List<Entrave> parseEntraves(String jsonData) throws IOException {
        List<Entrave> entravesList = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode recordsNode = rootNode.path("result").path("records");

        if (recordsNode.isArray()) {
            for (JsonNode record : recordsNode) {
                Entrave entrave = new Entrave();
                entrave.setIdRequest(record.path("id_request").asText());
                entrave.setStreetId(record.path("streetid").asText());
                entrave.setShortName(record.path("shortname").asText());
                entrave.setStreetImpactType(record.path("streetimpacttype").asText());
                entrave.setStreetImpactWidth(record.path("streetimpactwidth").asText());
                entrave.setSidewalkBlockedType(record.path("sidewalk_blockedtype").asText());
                entrave.setBikePathBlockedType(record.path("bikepath_blockedtype").asText());
                entravesList.add(entrave);
            }
        } else {
            throw new IOException("Les données JSON ne contiennent pas le noeud 'records'.");
        }

        return entravesList;
    }

    /**
     * Récupère les données depuis une URL.
     *
     * @param urlString URL à récupérer.
     * @return Contenu de la réponse.
     * @throws IOException Si une erreur survient lors de la récupération des données.
     */
    private String fetchDataFromUrl(String urlString) throws IOException {
        URL url;
        try {
            url = new URI(urlString).toURL();
        } catch (URISyntaxException e) {
            throw new IOException("Syntaxe URL invalide: " + urlString, e);
        }

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }

        StringBuilder inline = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        return inline.toString();
    }
}
