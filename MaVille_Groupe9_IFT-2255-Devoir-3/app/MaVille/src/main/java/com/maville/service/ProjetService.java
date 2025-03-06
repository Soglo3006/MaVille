package com.maville.service;

import com.maville.model.Projet;
import com.maville.model.PreferenceHoraire;
import com.maville.repository.ProjetRepository;
import com.maville.repository.PreferenceHoraireRepository;

import java.util.List;

import org.bson.types.ObjectId;

/**
 * Service pour gérer les projets.
 * Fournit des méthodes pour soumettre de nouveaux projets, modifier le statut des projets,
 * récupérer les projets à venir, récupérer les projets par intervenant, et vérifier les conflits d'horaires.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class ProjetService {
    private ProjetRepository projetRepository;
    private PreferenceHoraireRepository preferenceHoraireRepository;
    private NotificationService notificationService; 
    
    /**
     * Constructeur de la classe ProjetService.
     *
     * @param projetRepository           Le repository des projets.
     * @param preferenceHoraireRepository Le repository des préférences horaires.
     * @param notificationService        Le service de notification.
     */
    public ProjetService(ProjetRepository projetRepository, PreferenceHoraireRepository preferenceHoraireRepository, NotificationService notificationService) { 
        this.projetRepository = projetRepository;
        this.preferenceHoraireRepository = preferenceHoraireRepository;
        this.notificationService = notificationService;
    }

    /**
     * Soumet un nouveau projet et envoie une notification aux résidents du quartier concerné.
     *
     * @param projet        Le projet à soumettre.
     * @param intervenantId L'identifiant de l'intervenant soumettant le projet.
     */
    public void soumettreNouveauProjet(Projet projet, String intervenantId) {
        projetRepository.sauvegarder(projet, intervenantId);
        
        // Envoyer une notification aux résidents du quartier
        String quartier = getQuartierFromProjet(projet);
        String message = "Un nouveau projet a été soumis dans votre quartier : " + projet.getTitre();
        notificationService.sendNotificationToQuartier(quartier, message);
    }

    /**
     * Extrait le quartier d'un projet.
     *
     * @param projet Le projet dont on veut extraire le quartier.
     * @return Le quartier du projet.
     */
    private String getQuartierFromProjet(Projet projet) {
        return projet.getQuartier();
    }

    /**
     * Modifie le statut d'un projet et envoie une notification aux résidents du quartier concerné.
     *
     * @param projetId      L'identifiant du projet à modifier.
     * @param nouveauStatut Le nouveau statut à définir pour le projet.
     */
    public void modifierStatutProjet(ObjectId projetId, String nouveauStatut) {
        projetRepository.majProjetStatut(projetId, nouveauStatut);
        
        // Récupérer le projet mis à jour pour obtenir le quartier
        Projet projet = projetRepository.getProjetById(projetId); // Méthode à implémenter dans ProjetRepository
        if (projet != null) {
            String quartier = getQuartierFromProjet(projet);
            String message = "Le statut du projet '" + projet.getTitre() + "' a été mis à jour en : " + nouveauStatut;
            notificationService.sendNotificationToQuartier(quartier, message);
        }
    }

    /**
     * Récupère tous les travaux à venir dans les 3 prochains mois.
     *
     * @return Une liste de projets à venir.
     */
    public List<Projet> getTravauxAvenir3Mois() {
        return projetRepository.getProjetAvenir3Mois();
    }

    /**
     * Récupère tous les projets associés à un intervenant spécifique.
     *
     * @param intervenantId L'identifiant de l'intervenant.
     * @return Une liste de projets associés à l'intervenant.
     */
    public List<Projet> getProjetsParIntervenant(String intervenantId) {
        return projetRepository.getProjetsParIntervenant(intervenantId);
    }

    /**
     * Vérifie s'il existe des conflits d'horaires entre un projet et les préférences horaires des résidents.
     *
     * @param projet Le projet à vérifier.
     * @return {@code true} s'il y a des conflits, {@code false} sinon.
     */
    public boolean verifierConflits(Projet projet) {
        // Récupérer toutes les préférences horaires des résidents
        List<PreferenceHoraire> preferences = preferenceHoraireRepository.getAllPreferences();

        // Supposons que les horaires sont au format "HH:mm-HH:mm"
        String[] horairesProjet = projet.getHoraire().split("-");
        if (horairesProjet.length != 2) {
            return false; // Format invalide, aucun conflit détecté
        }
        String projetHoraireDebut = horairesProjet[0];
        String projetHoraireFin = horairesProjet[1];

        for (PreferenceHoraire pref : preferences) {
            // Vérifier si les horaires du projet chevauchent avec les préférences
            if (chevauchement(projetHoraireDebut, projetHoraireFin, pref.getHeureDebut(), pref.getHeureFin())) {
                return true; // Conflit détecté
            }
        }
        return false; // Aucun conflit
    }

    /**
     * Vérifie si deux plages horaires se chevauchent.
     *
     * @param debut1 Début de la première plage horaire.
     * @param fin1   Fin de la première plage horaire.
     * @param debut2 Début de la seconde plage horaire.
     * @param fin2   Fin de la seconde plage horaire.
     * @return {@code true} si les plages horaires se chevauchent, {@code false} sinon.
     */
    private boolean chevauchement(String debut1, String fin1, String debut2, String fin2) {
        return debut1.compareTo(fin2) < 0 && debut2.compareTo(fin1) < 0;
    }
}
