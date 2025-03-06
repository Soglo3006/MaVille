package com.maville.view;

import com.maville.controller.AuthController;
import com.maville.controller.ProjetController;
import com.maville.controller.RequeteTravailController;
import com.maville.model.Intervenant;
import com.maville.model.Projet;
import com.maville.model.RequeteTravail;
import com.maville.model.TypeProjet;
import com.maville.repository.IntervenantRepository;
import com.maville.repository.PreferenceHoraireRepository;
import com.maville.repository.ProjetRepository;
import com.maville.repository.RequeteTravailRepository;
import com.maville.service.NotificationService;
import com.maville.service.ProjetService;

import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant le menu principal pour les intervenants.
 * Permet aux intervenants de s'inscrire, de gérer leurs projets, et de gérer les requêtes de travail et candidatures.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class IntervenantMenu {
    private AuthController authController;
    private RequeteTravailController requeteTravailController;
    private ProjetController projetController;
    private IntervenantRepository intervenantRepository; 
    private PreferenceHoraireRepository preferenceHoraireRepository;
    private NotificationService notificationService;

    /**
     * Constructeur de la classe IntervenantMenu.
     *
     * @param authController              Le contrôleur d'authentification.
     * @param requeteTravailRepository    Le repository des requêtes de travail.
     * @param projetRepository            Le repository des projets.
     * @param intervenantRepository       Le repository des intervenants.
     * @param preferenceHoraireRepository Le repository des préférences horaires.
     * @param notificationService         Le service de notification.
     */
    public IntervenantMenu(AuthController authController, 
                        RequeteTravailRepository requeteTravailRepository, 
                        ProjetRepository projetRepository, 
                        IntervenantRepository intervenantRepository,
                        PreferenceHoraireRepository preferenceHoraireRepository,
                        NotificationService notificationService) {
        this.authController = authController;
        this.requeteTravailController = new RequeteTravailController(requeteTravailRepository);
        this.preferenceHoraireRepository = preferenceHoraireRepository;
        this.notificationService = notificationService;
        this.projetController = new ProjetController(new ProjetService(projetRepository, this.preferenceHoraireRepository, this.notificationService));
        this.intervenantRepository = intervenantRepository;
    }

    /**
     * Enregistre un nouvel intervenant en collectant les informations via le scanner.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    public void enregistrerIntervenant(Scanner scanner) {
        System.out.println("\n" + //
                        "Entrer votre nom complet:");
        String nomComplet = scanner.nextLine();
        System.out.println("Entrer votre email:");
        String email = scanner.nextLine();
        System.out.println("Entrer votre mot de passe:");
        String motDePasse = scanner.nextLine();
        System.out.println("Entrer le type ('Entreprise public', 'Entrepreneur privé' ou 'Particulier') :");
        String type = scanner.nextLine();
        System.out.println("Entrer l'identifiant de la ville:");
        String villeId = scanner.nextLine();

        Intervenant intervenant = new Intervenant(nomComplet, email, motDePasse, type, villeId);

        // Vérifier si l'id de la ville possède bien 8 chiffres
        if (!intervenant.villeIdEstOk()) {
            System.out.println("Erreur: Votre identifiant doit contenir 8 chiffres seulement");
            return;
        }

        authController.enregistrerIntervenant(intervenant);
        System.out.println("Intervenant inscrit avec succès!");
        authController.setEmailDuUserConnecte(email);
        displayIntervenantMenu(scanner);
    }
    
    /**
     * Affiche le menu principal pour les intervenants et gère les choix de l'utilisateur.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    public void displayIntervenantMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n" +
                                "Bienvenue dans le menu pour les intervenants, faite votre choix.");
            System.out.println("1. Consulter / Gérer ses projets (Menu)");
            System.out.println("2. Consulter / Gérer les requêtes de travail et candidature (Menu)");
            System.out.println("3. Retourner au menu principal d'authentification");
            System.out.println("4. Quitter l'application");
            System.out.println("");
            try {
                String input = scanner.nextLine(); 
                int choice = Integer.parseInt(input); 
                switch (choice) {
                    case 1:
                        displayConsulterProjetsMenu(scanner);
                        break;
                    case 2:
                        displayConsulterRequetesMenu(scanner);
                        break;
                    case 3:
                        break;  // retourner au menu principal d'authentification
                    case 4:
                        System.out.println("Au revoir !");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Choix invalide");
                        displayIntervenantMenu(scanner);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            }
        }
    }
    
    /**
     * Affiche le sous-menu pour consulter et gérer les projets de l'intervenant.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void displayConsulterProjetsMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n" + 
                                "Consulter / Gérer ses projets:");
            System.out.println("1. Modifier le statut d'un projet");
            System.out.println("2. Soumettre un nouveau projet de travaux");
            System.out.println("3. Retourner au menu principal");
            System.out.print("Choisissez une option: ");
            try {
                String input = scanner.nextLine(); 
                int choice = Integer.parseInt(input); 
                switch (choice) {
                    case 1:
                        modifierStatutProjet(scanner);
                        break;
                    case 2:
                        soumettreNouveauProjet(scanner);
                        break;
                    case 3:
                        displayIntervenantMenu(scanner);
                        break;
                    default:
                        System.out.println("Choix invalide");
                        displayConsulterProjetsMenu(scanner);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            }
        }
    }

    /**
     * Modifie le statut d'un projet sélectionné par l'intervenant.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void modifierStatutProjet(Scanner scanner) {
        String intervenantId = authController.getUserId(); // Assume que cette méthode retourne l'ID de l'intervenant connecté
        List<Projet> projets = projetController.getProjetsParIntervenant(intervenantId);
        
        if (projets.isEmpty()) {
            System.out.println("Vous n'avez aucun projet.");
            return;
        }

        System.out.println("Vos projets:");
        for (int i = 0; i < projets.size(); i++) {
            Projet projet = projets.get(i);
            System.out.printf("%d. %s (Statut: %s, ID: %s)%n", i + 1, projet.getTitre(), projet.getStatus(), projet.getId());
        }

        System.out.print("Entrez le numéro du projet à modifier: ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix < 1 || choix > projets.size()) {
                System.out.println("Choix invalide.");
                return;
            }

            Projet projet = projets.get(choix - 1);
            System.out.print("Entrez le nouveau statut du projet (Prévu, En cours, Terminé, etc.): ");
            String nouveauStatut = scanner.nextLine().trim();

            projetController.modifierStatutProjet(projet.getId(), nouveauStatut);
            System.out.println("Statut du projet mis à jour avec succès!");
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez entrer un numéro.");
        }
    }

    /**
     * Soumet un nouveau projet de travaux en collectant les informations via le scanner.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void soumettreNouveauProjet(Scanner scanner) {
        System.out.println("\nSoumettre un nouveau projet de travaux:");
        System.out.print("Titre: ");
        String titre = scanner.nextLine().trim();
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        System.out.println("Choisissez le type de travaux:");
        System.out.println("1. Travaux routiers");
        System.out.println("2. Travaux de gaz ou électricité");
        System.out.println("3. Construction ou rénovation");
        System.out.println("4. Entretien paysager");
        System.out.println("5. Transports en commun");
        System.out.println("6. Signalisation et éclairage");
        System.out.println("7. Travaux souterrains");
        System.out.println("8. Travaux résidentiels");
        System.out.println("9. Entretien urbain");
        System.out.println("10. Entretien des réseaux de télécommunication");
        System.out.print("Entrez un numéro (1-10): ");
        int choixType;
        try {
            choixType = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez entrer un chiffre.");
            return;
        }
        TypeProjet typeTravaux;
        switch (choixType) {
            case 1:
                typeTravaux = TypeProjet.TRAVAUX_ROUTIERS;
                break;
            case 2:
                typeTravaux = TypeProjet.TRAVAUX_DE_GAZ_OU_ELECTRICITE;
                break;
            case 3:
                typeTravaux = TypeProjet.CONSTRUCTION_OU_RENOVATION;
                break;
            case 4:
                typeTravaux = TypeProjet.ENTRETIEN_PAYSAGER;
                break;
            case 5:
                typeTravaux = TypeProjet.TRANSPORTS_EN_COMMUN;
                break;
            case 6:
                typeTravaux = TypeProjet.SIGNALISATION_ET_ECLAIRAGE;
                break;
            case 7:
                typeTravaux = TypeProjet.TRAVAUX_SOUTERRAINS;
                break;
            case 8:
                typeTravaux = TypeProjet.TRAVAUX_RESIDENTIELS;
                break;
            case 9:
                typeTravaux = TypeProjet.ENTRETIEN_URBAIN;
                break;
            case 10:
                typeTravaux = TypeProjet.ENTRETIEN_DES_RESEAU_DE_TELECOMMUNICATION;
                break;
            default:
                System.out.println("Choix invalide. Veuillez choisir un nombre entre 1 et 10.");
                return;
        }
        System.out.print("Quartier: ");
        String quartier = scanner.nextLine().trim();
        System.out.print("Date de début (YYYY-MM-DD): ");
        String dateDebut = scanner.nextLine().trim();
        System.out.print("Date de fin (YYYY-MM-DD): ");
        String dateFin = scanner.nextLine().trim();
        System.out.print("Horaire (ex: 08:00-17:00): ");
        String horaire = scanner.nextLine().trim();

        Projet projet = new Projet(titre, description, typeTravaux, quartier, dateDebut, dateFin, horaire);

        // Validation des dates
        if (!projet.estDateValide(dateDebut) || !projet.estDateValide(dateFin)) {
            System.out.println("Erreur: Format de la date est invalide. Svp utiliser YYYY-MM-DD.");
            return;
        }

        String intervenantId = authController.getUserId();
        if (intervenantId == null) {
            System.out.println("Erreur: Impossible de déterminer l'ID de l'intervenant connecté.");
            return;
        }

        projetController.soumettreNouveauProjet(projet, intervenantId);
        System.out.println("Projet soumis avec succès!");

        boolean conflit = projetController.verifierConflits(projet);
        if (conflit) {
            System.out.println("Attention: Des conflits ont été détectés avec les préférences horaires des résidents.");
        }
    }

    /**
     * Affiche le sous-menu pour consulter et gérer les requêtes de travail et candidatures.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void displayConsulterRequetesMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nConsulter / Gérer les requêtes de travail et candidature:");
            System.out.println("1. Soumettre sa candidature pour une requête de travail");
            System.out.println("2. Retirer sa candidature");
            System.out.println("3. Faire le suivi de sa candidature");
            System.out.println("4. Consulter les requêtes de travail");
            System.out.println("5. Retourner au menu principal");
            System.out.print("Choisissez une option: ");
            try {
                String input = scanner.nextLine(); 
                int choice = Integer.parseInt(input); 
                switch (choice) {
                    case 1:
                        soumettreCandidature(scanner);
                        break;
                    case 2:
                        retirerCandidature(scanner);
                        break;
                    case 3:
                        suivreCandidature(scanner);
                        break;
                    case 4:
                        consulterRequetesTravail(scanner);
                        break;
                    case 5:
                        return; // Retourner au menu principal d'intervenant
                    default:
                        System.out.println("Choix invalide");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            }
        }
    }    

    /**
     * Soumet une candidature pour une requête de travail sélectionnée.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void soumettreCandidature(Scanner scanner) {
        String intervenantId = authController.getUserId();
        
        // Vérifier si l'intervenant a déjà une candidature active
        if (intervenantRepository.aCandidatureActive(intervenantId)) {
            System.out.println("\nVous avez déjà une candidature active. Vous ne pouvez pas en soumettre une autre.");
            return;
        }
    
        List<RequeteTravail> requetes = requeteTravailController.getAllRequetesTravail();
        if (requetes.isEmpty()) {
            System.out.println("\nAucune requête de travail disponible.");
            return;
        }
    
        System.out.println("\nRequêtes de travail disponibles:");
        for (int i = 0; i < requetes.size(); i++) {
            RequeteTravail requete = requetes.get(i);
            System.out.printf("%d. %s (Statut: %s)%n", i + 1, requete.getTitre(), requete.getStatus());
        }
    
        System.out.print("\nEntrez le numéro de la requête à laquelle postuler: ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix < 1 || choix > requetes.size()) {
                System.out.println("\nChoix invalide.");
                return;
            }
    
            RequeteTravail requete = requetes.get(choix - 1);
            if (!requete.getStatus().equalsIgnoreCase("Prévu")) {
                System.out.println("\nVous ne pouvez postuler qu'à des requêtes avec le statut 'Prévu'.");
                return;
            }
    
            requeteTravailController.ajouterCandidature(requete.getId().toHexString(), intervenantId);
            intervenantRepository.marquerCandidature(intervenantId, true);
            System.out.println("\nCandidature soumise avec succès!");
        } catch (NumberFormatException e) {
            System.out.println("\nEntrée invalide. Veuillez entrer un numéro.");
        }
    }
    
    /**
     * Retire une candidature active pour une requête de travail.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void retirerCandidature(Scanner scanner) {
        String intervenantId = authController.getUserId();
    
        // Vérifier si l'intervenant a une candidature active
        if (!intervenantRepository.aCandidatureActive(intervenantId)) {
            System.out.println("\nVous n'avez aucune candidature active.");
            return;
        }
    
        // Récupérer la requête de travail à laquelle l'intervenant a postulé
        RequeteTravail requete = requeteTravailController.getRequeteDeTravailAvecCandidat(intervenantId);
        if (requete == null) {
            System.out.println("\nAucune requête de travail trouvée pour votre candidature.");
            return;
        }
    
        System.out.println("\nCandidature actuelle:");
        System.out.println(requete);
    
        System.out.print("\nVoulez-vous retirer votre candidature? (oui/non): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (confirmation.equals("oui") || confirmation.equals("o")) {
            requeteTravailController.retirerCandidature(requete.getId().toHexString(), intervenantId);
            intervenantRepository.marquerCandidature(intervenantId, false);
            System.out.println("\nCandidature retirée avec succès!");
        } else {
            System.out.println("\nOpération annulée.");
        }
    }
    
    /**
     * Fait le suivi d'une candidature active.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void suivreCandidature(Scanner scanner) {
        String intervenantId = authController.getUserId();
    
        // Récupérer la requête de travail à laquelle l'intervenant a postulé
        RequeteTravail requete = requeteTravailController.getRequeteDeTravailAvecCandidat(intervenantId);
        if (requete == null) {
            System.out.println("\nVous n'avez aucune candidature active.");
            return;
        }
    
        System.out.println("\nSuivi de votre candidature:");
        System.out.println(requete);
    }

    /**
     * Consulte et affiche toutes les requêtes de travail disponibles.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void consulterRequetesTravail(Scanner scanner) {
        List<RequeteTravail> requetesTravail = requeteTravailController.getAllRequetesTravail();
        if (requetesTravail.isEmpty()) {
            System.out.println("\nAucune requête de travail disponible.");
        } else {
            System.out.println("\nRequêtes de travail disponibles:");
            for (int i = 0; i < requetesTravail.size(); i++) {
                RequeteTravail requete = requetesTravail.get(i);
                System.out.printf("%d. %s (Statut: %s)%n", i + 1, requete.getTitre(), requete.getStatus());
            }
        }
    }
}