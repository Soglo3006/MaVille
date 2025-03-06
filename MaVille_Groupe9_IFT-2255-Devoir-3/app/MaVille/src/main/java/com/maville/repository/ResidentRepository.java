package com.maville.repository;

import com.maville.model.Resident;
import com.maville.service.PostalCodeMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Repository pour gérer les opérations CRUD liées aux résidents dans la base de données MongoDB.
 * Fournit des méthodes pour sauvegarder, trouver, mettre à jour les informations des résidents et valider les codes postaux.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class ResidentRepository {
    private MongoCollection<Document> residentCollection;

    /**
     * Constructeur de la classe ResidentRepository.
     *
     * @param database La base de données MongoDB à utiliser.
     */
    public ResidentRepository(MongoDatabase database) {
        this.residentCollection = database.getCollection("Residents");
    }

    /**
     * Sauvegarde un nouveau résident dans la collection "Residents".
     *
     * @param resident Le résident à sauvegarder.
     */
    public void sauvegarder(Resident resident) {
        // Pour les notifications
        PostalCodeMapper mapper = PostalCodeMapper.getInstance();
        String quartier = mapper.getBoroughByPostalCode(resident.getCodePostal().substring(0,3));
        resident.setQuartier(quartier);
        
        Document doc = new Document("nomComplet", resident.getNomComplet())
                .append("dateNaissance", resident.getDateNaissance())
                .append("email", resident.getEmail())
                .append("mdp", resident.getMdp())
                .append("telephone", resident.getTelephone())
                .append("adresse", resident.getAdresse())
                .append("codePostal", resident.getCodePostal())
                .append("quartier", quartier);
        residentCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        resident.setId(id); 
    }

    /**
     * Trouve un résident dans la collection "Residents" en utilisant son email.
     *
     * @param email L'email du résident à trouver.
     * @return Le résident correspondant, ou {@code null} si aucun résident n'est trouvé.
     */
    public Resident trouverAvecEmail(String email) {
        Document doc = residentCollection.find(eq("email", email)).first();
        if (doc != null) {
            Resident resident = new Resident(
                doc.getString("nomComplet"),
                doc.getString("dateNaissance"),
                doc.getString("email"),
                doc.getString("mdp"),
                doc.getString("telephone"),
                doc.getString("adresse"),
                doc.getString("codePostal")
            );
            resident.setId(doc.getObjectId("_id"));
            resident.setQuartier(doc.getString("quartier"));
            return resident;
        }
        return null;
    }

    /**
     * Met à jour les informations d'un résident existant dans la collection "Residents".
     *
     * @param resident Le résident avec les informations mises à jour.
     */
    public void majResident(Resident resident) {
        Document updatedDoc = new Document("nomComplet", resident.getNomComplet())
                .append("dateNaissance", resident.getDateNaissance())
                .append("email", resident.getEmail())
                .append("mdp", resident.getMdp())
                .append("telephone", resident.getTelephone())
                .append("adresse", resident.getAdresse())
                .append("codePostal", resident.getCodePostal());

        residentCollection.updateOne(eq("_id", resident.getId()), new Document("$set", updatedDoc));
    }

    /**
     * Valide le format d'un code postal.
     *
     * @param codePostal Le code postal à valider.
     * @return {@code true} si le code postal est valide, {@code false} sinon.
     */
    public boolean estCodePostalValide(String codePostal) {
        String postalCodePattern = "^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$";
        return Pattern.matches(postalCodePattern, codePostal);
    }

    /**
     * Récupère les emails des résidents d'un quartier spécifique.
     *
     * @param quartier Le quartier dont on souhaite récupérer les emails des résidents.
     * @return Une liste d'emails des résidents du quartier.
     */
    public List<String> getEmailsByQuartier(String quartier) {
        List<String> emails = new ArrayList<>();
        for (org.bson.Document doc : residentCollection.find(eq("quartier", quartier))) {
            emails.add(doc.getString("email"));
        }
        return emails;
    }
}
