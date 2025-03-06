package com.maville.view;

import com.maville.controller.AuthController;
import com.maville.controller.PreferenceHoraireController;
import com.maville.controller.ProjetController;
import com.maville.controller.RequeteTravailController;
import com.maville.model.Notification;
import com.maville.model.PreferenceHoraire;
import com.maville.model.RequeteTravail;
import com.maville.model.Resident;
import com.maville.model.Travail;
import com.maville.model.Projet;
import com.maville.repository.IntervenantRepository;
import com.maville.repository.RequeteTravailRepository;
import com.maville.repository.ResidentRepository;
import com.maville.repository.NotificationRepository;
import com.maville.service.TravauxEnCoursEtEntraveService;
import com.maville.service.NotificationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.bson.types.ObjectId;

/**
 * Classe représentant le menu principal pour les résidents.
 * Permet aux résidents de s'inscrire, de gérer leurs préférences horaires, de consulter les travaux en cours et les entraves,
 * de gérer les requêtes de travail, de soumettre de nouvelles requêtes, et de consulter leurs notifications.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class ResidentMenu {
    private AuthController authController;
    private RequeteTravailController requeteTravailController;
    private TravauxEnCoursEtEntraveService travauxEnCoursEtEntraveService;
    private PreferenceHoraireController preferenceHoraireController;
    private ProjetController projetController;
    private IntervenantRepository intervenantRepository;
    private NotificationService notificationService;

    /**
     * Constructeur de la classe ResidentMenu.
     *
     * @param authController              Le contrôleur d'authentification.
     * @param requeteTravailRepository    Le repository des requêtes de travail.
     * @param preferenceHoraireController Le contrôleur des préférences horaires.
     * @param projetController            Le contrôleur des projets.
     * @param notificationRepository      Le repository des notifications.
     * @param residentRepository          Le repository des résidents.
     * @param intervenantRepository       Le repository des intervenants.
     */
    public ResidentMenu(AuthController authController, 
                        RequeteTravailRepository requeteTravailRepository, 
                        PreferenceHoraireController preferenceHoraireController, 
                        ProjetController projetController, 
                        NotificationRepository notificationRepository, 
                        ResidentRepository residentRepository,
                        IntervenantRepository intervenantRepository) {
        this.authController = authController;
        this.travauxEnCoursEtEntraveService = new TravauxEnCoursEtEntraveService();
        this.requeteTravailController = new RequeteTravailController(requeteTravailRepository);
        this.preferenceHoraireController = preferenceHoraireController;
        this.projetController = projetController;
        this.notificationService = new NotificationService(notificationRepository, residentRepository);
        this.intervenantRepository = intervenantRepository; 
    }

    /**
     * Enregistre un nouveau résident en collectant les informations via le scanner.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    public void enregistrerResident(Scanner scanner) {
        System.out.println("Entrer votre nom complet:");
        String nomComplet = scanner.nextLine();
        System.out.println("Entrer votre date de naissance (AAAA-MM-JJ):");
        String dateNaissance = scanner.nextLine();
        System.out.println("Entrer votre email:");
        String email = scanner.nextLine();
        System.out.println("Entrer votre mot de passe:");
        String mdp = scanner.nextLine();
        System.out.println("Entrer votre téléphone (optionnel, entrer 'x' si vous ne voulez pas le soumettre):");
        String telephone = scanner.nextLine();
        System.out.println("Entrer votre adresse (numéro rue):");
        String adresse = scanner.nextLine();
        System.out.println("Entrer votre code postal (A1A 1A1):");
        String codePostal = scanner.nextLine();
        Resident resident = new Resident(nomComplet, dateNaissance, email, mdp, telephone, adresse, codePostal);

        // Vérifier si la date a été entrée dans un bon format
        if (!resident.estDateValide()) {
            System.out.println("Erreur: Format de la date est invalide. Svp utiliser YYYY-MM-DD.");
            return; // Retourne au menu principal
        }
        // Vérifier si au-dessus de 16 ans
        if (!resident.auDessusDe16()) {
            System.out.println("Erreur: Vous devez avoir plus de 16 ans pour vous inscrire.");
            return; // Retourne au menu principal
        }
        // Vérifier si le code postal a été fourni correctement
        if (!resident.estCodePostalValide()) {
            System.out.println("Erreur: Format du code postal est invalide. Svp utiliser A1A 1A1.");
            return; // Retourne au menu principal
        }

        authController.enregistrerResident(resident);
        System.out.println("Résident inscrit avec succès!");
        authController.setEmailDuUserConnecte(email);
        displayResidentMenu(scanner);
    }

    /**
     * Affiche le menu principal pour les résidents et gère les choix de l'utilisateur.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    public void displayResidentMenu(Scanner scanner) {
        while (true) {
            // Récupérer le courriel du résident connecté
            String residentEmail = authController.getEmailDuUserConnecte();
            // Récupérer le nombre de notifications non lues
            long unreadCount = notificationService.getUnreadNotificationCount(residentEmail);
            System.out.println("\nBienvenue dans le menu pour les résidents, faites votre choix.");
            System.out.println("1. Voir/ Configurer les plages horaires (Menu)");
            System.out.println("2. Consulter les travaux en cours (Menu)");
            System.out.println("3. Consulter les entraves (Menu)");
            System.out.println("4. Gérer ses requêtes de travail (Menu)");
            System.out.println("5. Soumettre une nouvelle requête de travail");
            System.out.println("6. Consulter ses notifications (" + unreadCount + " non lues)");
            System.out.println("7. Retourner au menu d'authentification");
            System.out.println("8. Quitter l'application");
            try {
                String input = scanner.nextLine(); 
                int choice = Integer.parseInt(input); 
                switch (choice) {
                    case 1:
                        gererSesPreferencesHoraires(scanner);
                        break;
                    case 2:
                        displayRechercherTravauxMenu(scanner);
                        break;
                    case 3:
                        displayRechercherEntravesMenu(scanner);
                        break;
                    case 4:
                        displayRequetesTravailMenu(scanner);
                        break;
                    case 5:
                        submitNewRequeteTravail(scanner); 
                        break;
                    case 6:
                        consulterNotifications(scanner);
                        break;
                    case 7:
                        return; // Retourner au menu principal
                    case 8:
                        System.out.println("Au revoir !");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Choix invalide");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            }
        }
    }

    /**
     * Affiche le sous-menu pour gérer les préférences horaires du résident.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void gererSesPreferencesHoraires(Scanner scanner) {
        while (true) {
            System.out.println("\nGestion des préférences d'horaires:");
            System.out.println("1. Voir mes préférences d'horaires");
            System.out.println("2. Ajouter une préférence d'horaire");
            System.out.println("3. Modifier une préférence d'horaire");
            System.out.println("4. Supprimer une préférence d'horaire");
            System.out.println("5. Retourner au menu principal");
            System.out.print("Choisissez une option: ");
            try {
                int choix = Integer.parseInt(scanner.nextLine()); 
                switch (choix) {
                    case 1:
                        voirPreferencesHoraires(scanner);
                        break;
                    case 2:
                        ajouterPreferenceHoraire(scanner);
                        break;
                    case 3:
                        modifierPreferenceHoraire(scanner);
                        break;
                    case 4:
                        supprimerPreferenceHoraire(scanner);
                        break;
                    case 5:
                        return; // Retourner au menu des résidents
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur: veuillez entrer un nombre pour choisir une option.");
            }
        }
    }

    /**
     * Affiche toutes les préférences horaires du résident connecté.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void voirPreferencesHoraires(Scanner scanner) {
        String email = authController.getEmailDuUserConnecte();
        List<PreferenceHoraire> horaires = preferenceHoraireController.getHoraires(email);
        if (horaires == null || horaires.isEmpty()) {
            System.out.println("\nAucune préférence d'horaire trouvée.");
        } else {
            System.out.println("\nPréférences d'horaires:");
            for (int i = 0; i < horaires.size(); i++) {
                PreferenceHoraire hor = horaires.get(i);
                System.out.printf("%d. %s: %s - %s (ID: %s)%n", 
                                  i + 1, 
                                  hor.getJour(), 
                                  hor.getHeureDebut(), 
                                  hor.getHeureFin(),
                                  hor.getId());
            }
        }
    }

    /**
     * Ajoute une nouvelle préférence horaire pour le résident connecté.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void ajouterPreferenceHoraire(Scanner scanner) {
        System.out.println("\nAjouter une nouvelle préférence d'horaire:");
        System.out.print("Jour de la semaine (ex: Lundi): ");
        String jour = scanner.nextLine().trim();
        System.out.print("Heure de début (HH:mm): ");
        String heureDebut = scanner.nextLine().trim();
        System.out.print("Heure de fin (HH:mm): ");
        String heureFin = scanner.nextLine().trim();

        PreferenceHoraire horaire = new PreferenceHoraire(jour, heureDebut, heureFin); // ID et email seront définis dans le service
        if (!horaire.estHeureValide()) {
            System.out.println("\nErreur: Les heures ne sont pas valides ou l'heure de début est après l'heure de fin.");
            return;
        }

        String email = authController.getEmailDuUserConnecte();
        preferenceHoraireController.ajouterHoraire(email, horaire);
        System.out.println("\nPréférence d'horaire ajoutée avec succès!");
    }

    /**
     * Modifie une préférence horaire existante pour le résident connecté.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void modifierPreferenceHoraire(Scanner scanner) {
        String email = authController.getEmailDuUserConnecte();
        List<PreferenceHoraire> horaires = preferenceHoraireController.getHoraires(email);
        if (horaires == null || horaires.isEmpty()) {
            System.out.println("Vous n'avez pas de préférences d'horaires à modifier.");
            return;
        }

        voirPreferencesHoraires(scanner);
        System.out.print("Entrez l'ID de la préférence horaire à modifier: ");
        try {
            String id = scanner.nextLine().trim();
            PreferenceHoraire oldHoraire = preferenceHoraireController.getHoraireById(id);
            if (oldHoraire == null) {
                System.out.println("Préférence horaire non trouvée.");
                return;
            }

            System.out.printf("Modification de la préférence horaire (ID: %s):%n", id);
            System.out.printf("Jour actuel (%s): ", oldHoraire.getJour());
            String nouveauJour = scanner.nextLine().trim();
            nouveauJour = nouveauJour.isEmpty() ? oldHoraire.getJour() : nouveauJour;

            System.out.printf("Heure de début actuelle (%s): ", oldHoraire.getHeureDebut());
            String nouvelleHeureDebut = scanner.nextLine().trim();
            nouvelleHeureDebut = nouvelleHeureDebut.isEmpty() ? oldHoraire.getHeureDebut() : nouvelleHeureDebut;

            System.out.printf("Heure de fin actuelle (%s): ", oldHoraire.getHeureFin());
            String nouvelleHeureFin = scanner.nextLine().trim();
            nouvelleHeureFin = nouvelleHeureFin.isEmpty() ? oldHoraire.getHeureFin() : nouvelleHeureFin;

            // Utiliser le constructeur avec ID et residentEmail
            PreferenceHoraire nouveauHoraire = new PreferenceHoraire(oldHoraire.getId(), nouveauJour, nouvelleHeureDebut, nouvelleHeureFin, email);
            if (!nouveauHoraire.estHeureValide()) {
                System.out.println("\nErreur: Les heures ne sont pas valides ou l'heure de début est après l'heure de fin.");
                return;
            }

            preferenceHoraireController.modifierHoraire(email, id, nouveauHoraire);
            System.out.println("\nPréférence d'horaire modifiée avec succès!");
        } catch (Exception e) {
            System.out.println("\nErreur: Entrée invalide ou une erreur est survenue.");
        }
    }

    /**
     * Supprime une préférence horaire existante pour le résident connecté.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void supprimerPreferenceHoraire(Scanner scanner) {
        String email = authController.getEmailDuUserConnecte();
        List<PreferenceHoraire> horaires = preferenceHoraireController.getHoraires(email);
        if (horaires == null || horaires.isEmpty()) {
            System.out.println("Vous n'avez pas de préférences d'horaires à supprimer.");
            return;
        }

        voirPreferencesHoraires(scanner);
        System.out.print("Entrez l'ID de la préférence horaire à supprimer: ");
        try {
            String id = scanner.nextLine().trim();
            PreferenceHoraire horaire = preferenceHoraireController.getHoraireById(id);
            if (horaire == null) {
                System.out.println("Préférence horaire non trouvée.");
                return;
            }

            preferenceHoraireController.supprimerHoraire(email, id);
            System.out.println("\nPréférence d'horaire supprimée avec succès!");
        } catch (Exception e) {
            System.out.println("\nErreur: Entrée invalide ou une erreur est survenue.");
        }
    }

    /**
     * Affiche le sous-menu pour rechercher et consulter les travaux.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void displayRechercherTravauxMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nRechercher des travaux:");
            System.out.println("1. Rechercher les travaux par quartier (code postal)");
            System.out.println("2. Filtrer les travaux par quartier");
            System.out.println("3. Consulter tous les travaux");
            System.out.println("4. Consulter les travaux à venir (3 prochains mois)");
            System.out.println("5. Retourner au menu principal");
            try {
                String input = scanner.nextLine(); 
                int choice = Integer.parseInt(input); 
                switch (choice) {
                    case 1:
                        afficherTravauxParQuartier(scanner);
                        break;
                    case 2:
                        afficherTravauxGroupesParQuartier(scanner);
                        break;
                    case 3:
                        consulterTravauxEnCours(scanner);
                        break;
                    case 4:
                        consulterTravauxAVenir(scanner);
                        break;
                    case 5:
                        return; // Retourner au menu principal
                    default:
                        System.out.println("Choix invalide");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            }
        }
    }

    /**
     * Affiche les travaux correspondant à un quartier basé sur le code postal.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void afficherTravauxParQuartier(Scanner scanner) {
        System.out.print("Entrez le code postal à trois caractères (ex: H2G): ");
        String codePostal = scanner.nextLine().toUpperCase().trim();

        // Valider le format du code postal
        if (!codePostal.matches("^[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z]$")) {
            System.out.println("Code postal invalide ou non reconnu.");
            return;
        }

        try {
            List<Travail> travauxParQuartier = travauxEnCoursEtEntraveService.getTravauxParArrondissement(codePostal);
            if (travauxParQuartier.isEmpty()) {
                System.out.println("Aucun travail trouvé pour cet arrondissement.");
            } else {
                System.out.println("Travaux trouvés pour l'arrondissement correspondant au code postal " + codePostal + " :");
                for (Travail travail : travauxParQuartier) {
                    System.out.println(travail);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la récupération des travaux pour cet arrondissement: " + e.getMessage());
        }
    }

    /**
     * Affiche les travaux regroupés par quartier.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void afficherTravauxGroupesParQuartier(Scanner scanner) {
        try {
            List<Travail> travauxEnCours = travauxEnCoursEtEntraveService.getTravauxEnCours();
            
            Map<String, List<String>> travauxGroupesParQuartier = new HashMap<>();
            
            for (Travail travail : travauxEnCours) {
                String quartier = travail.getBoroughId();
                if (quartier == null || quartier.trim().isEmpty()) {
                    quartier = "Quartier Inconnu";
                }
                String travailDescription = "Travail en cours: " + travail.getReasonCategory() + " - " + travail.getCurrentStatus();
                travauxGroupesParQuartier.computeIfAbsent(quartier, k -> new ArrayList<>()).add(travailDescription);
            }
            
            if (travauxGroupesParQuartier.isEmpty()) {
                System.out.println("Aucun travail trouvé.");
            } else {
                System.out.println("\n=== Travaux regroupés par Quartier ===");
                for (Map.Entry<String, List<String>> entry : travauxGroupesParQuartier.entrySet()) {
                    String quartier = entry.getKey();
                    List<String> travaux = entry.getValue();
                    System.out.println("\n--- " + quartier + " ---");
                    for (String travail : travaux) {
                        System.out.println(travail);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la récupération des travaux: " + e.getMessage());
        }
    }

    /**
     * Consulte et affiche tous les travaux en cours.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void consulterTravauxEnCours(Scanner scanner) {
        try {
            List<Travail> travaux = travauxEnCoursEtEntraveService.getTravauxEnCours();
            if (travaux.isEmpty()) {
                System.out.println("Aucun travail en cours.");
            } else {
                System.out.println("Travaux en cours:");
                for (Travail travail : travaux) {
                    System.out.println(travail);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la récupération des travaux en cours: " + e.getMessage());
        }
    }

    /**
     * Consulte et affiche les travaux à venir dans les 3 prochains mois.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void consulterTravauxAVenir(Scanner scanner) {
        try {
            List<Projet> travauxAvenir = projetController.getTravauxAvenir3Mois();
            if (travauxAvenir == null || travauxAvenir.isEmpty()) {
                System.out.println("Aucun travail à venir dans les 3 prochains mois.");
            } else {
                System.out.println("Travaux à venir dans les 3 prochains mois:");
                for (Projet travail : travauxAvenir) {
                    System.out.printf("Titre: %s%nDescription: %s%nType: %s%nDate Début: %s%nDate Fin: %s%nHoraire: %s%n%n",
                                      travail.getTitre(),
                                      travail.getDescription(),
                                      travail.getTypeTravaux(),
                                      travail.getDateDebut(),
                                      travail.getDateFin(),
                                      travail.getHoraire());
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des travaux à venir: " + e.getMessage());
        }
    }

    /**
     * Affiche le sous-menu pour gérer les entraves.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void displayRechercherEntravesMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nGestion des entraves:");
            System.out.println("1. Consulter toutes les entraves");
            System.out.println("2. Rechercher les entraves par nom de rue");
            System.out.println("3. Filtrer les entraves par noms de rues");
            System.out.println("4. Retourner au menu de recherche des travaux");
            System.out.print("Choisissez une option: ");
            try {
                String input = scanner.nextLine();
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        consulterEntraves(scanner);
                        break;
                    case 2:
                        rechercherEntravesParNomRue(scanner);
                        break;
                    case 3:
                        afficherEntravesRegroupeesParRues(scanner);
                        break;
                    case 4:
                        return; // Retourner au menu de recherche des travaux
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur: veuillez entrer un nombre pour choisir une option.");
            }
        }
    }

    /**
     * Consulte et affiche toutes les entraves.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void consulterEntraves(Scanner scanner) {
        try {
            List<com.maville.model.Entrave> entraves = travauxEnCoursEtEntraveService.getEntraves();
            if (entraves.isEmpty()) {
                System.out.println("Aucune entrave trouvée.");
            } else {
                System.out.println("\nEntraves:");
                for (com.maville.model.Entrave entrave : entraves) {
                    System.out.println(entrave);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la récupération des entraves: " + e.getMessage());
        }
    }

    /**
     * Recherche et affiche les entraves correspondant à un nom de rue spécifique.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void rechercherEntravesParNomRue(Scanner scanner) {
        System.out.print("Entrez le nom de la rue (pour la rue Sherbrooke, écrivez Sherbrooke): ");
        String nomRue = scanner.nextLine().trim();

        if (nomRue.isEmpty()) {
            System.out.println("Erreur: Le nom de la rue ne peut pas être vide.");
            return;
        }

        try {
            List<com.maville.model.Entrave> entravesParRue = travauxEnCoursEtEntraveService.getEntravesParNomRue(nomRue);
            if (entravesParRue.isEmpty()) {
                System.out.println("Aucune entrave trouvée pour la rue \"" + nomRue + "\".");
            } else {
                System.out.println("Entraves trouvées pour la rue \"" + nomRue + "\" :");
                for (com.maville.model.Entrave entrave : entravesParRue) {
                    System.out.println(entrave);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la récupération des entraves: " + e.getMessage());
        }
    }

    /**
     * Affiche les entraves regroupées par noms de rues.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void afficherEntravesRegroupeesParRues(Scanner scanner) {
        try {
            Map<String, List<com.maville.model.Entrave>> entravesParRue = travauxEnCoursEtEntraveService.getEntravesGroupedByNomRue();
            if (entravesParRue.isEmpty()) {
                System.out.println("Aucune entrave trouvée.");
            } else {
                System.out.println("\nEntraves regroupées par noms de rues:");
                for (Map.Entry<String, List<com.maville.model.Entrave>> entry : entravesParRue.entrySet()) {
                    String nomRue = entry.getKey();
                    List<com.maville.model.Entrave> entraves = entry.getValue();
                    
                    System.out.println("\n=== " + nomRue + " ===");
                    System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %-20s",
                        "ID Requête", "ID Rue", "Nom Rue", "Impact Rue", "Largeur Impact", "Trottoir Bloqué", "Piste Cyclable Bloquée"));
                    System.out.println("---------------------------------------------------------------------------------------------");
                    
                    for (com.maville.model.Entrave entrave : entraves) {
                        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %-20s",
                            entrave.getIdRequest(),
                            entrave.getStreetId(),
                            entrave.getShortName(),
                            entrave.getStreetImpactType(),
                            entrave.getStreetImpactWidth(),
                            entrave.getSidewalkBlockedType(),
                            entrave.getBikePathBlockedType()));
                    }
                }
                System.out.println(); // Ligne vide à la fin
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la récupération des entraves: " + e.getMessage());
        }
    }

    /**
     * Affiche le sous-menu pour gérer les requêtes de travail.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void displayRequetesTravailMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nFaire le suivi de ses requêtes de travail:");
            System.out.println("1. Accepter une candidature");
            System.out.println("2. Refuser une candidature");
            System.out.println("3. Retourner au menu principal");
            System.out.print("Choisissez une option: ");
            try {
                String input = scanner.nextLine(); 
                int choice = Integer.parseInt(input); 
                switch (choice) {
                    case 1:
                        accepterCandidature(scanner);
                        break;
                    case 2:
                        refuserCandidature(scanner);
                        break;
                    case 3:
                        return; // Retourner au menu des résidents
                    default:
                        System.out.println("Choix invalide");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            }
        }
    }

    /**
     * Accepte une candidature pour une requête de travail sélectionnée.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void accepterCandidature(Scanner scanner) {
        List<RequeteTravail> requetes = requeteTravailController.getAllRequetesTravail();
        List<RequeteTravail> candidatures = new ArrayList<>();

        // Filtrer les requêtes avec des candidatures en attente
        for (RequeteTravail requete : requetes) {
            if (requete.getIdCandidat() != null && requete.getStatutCandidature().equalsIgnoreCase("En attente")) {
                candidatures.add(requete);
            }
        }

        if (candidatures.isEmpty()) {
            System.out.println("Aucune candidature en attente.");
            return;
        }

        System.out.println("Candidatures en attente:");
        for (int i = 0; i < candidatures.size(); i++) {
            RequeteTravail requete = candidatures.get(i);
            System.out.printf("%d. %s (Candidat ID: %s)%n", i + 1, requete.getTitre(), requete.getIdCandidat());
        }

        System.out.print("Entrez le numéro de la candidature à accepter: ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix < 1 || choix > candidatures.size()) {
                System.out.println("Choix invalide.");
                return;
            }

            RequeteTravail requete = candidatures.get(choix - 1);
            System.out.print("Entrez un message pour l'intervenant: ");
            String message = scanner.nextLine().trim();

            requeteTravailController.confirmerCandidature(requete.getId().toHexString(), requete.getIdCandidat(), message);

            // Marquer l'intervenant comme ayant une candidature active
            intervenantRepository.marquerCandidature(requete.getIdCandidat(), true);

            System.out.println("Candidature acceptée avec succès!");
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez entrer un numéro.");
        }
    }

    /**
     * Refuse une candidature pour une requête de travail sélectionnée.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void refuserCandidature(Scanner scanner) {
        List<RequeteTravail> requetes = requeteTravailController.getAllRequetesTravail();
        List<RequeteTravail> candidatures = new ArrayList<>();
    
        // Filtrer les requêtes avec des candidatures en attente
        for (RequeteTravail requete : requetes) {
            if (requete.getIdCandidat() != null && requete.getStatutCandidature().equalsIgnoreCase("En attente")) {
                candidatures.add(requete);
            }
        }
    
        if (candidatures.isEmpty()) {
            System.out.println("Aucune candidature en attente.");
            return;
        }
    
        System.out.println("Candidatures en attente:");
        for (int i = 0; i < candidatures.size(); i++) {
            RequeteTravail requete = candidatures.get(i);
            System.out.printf("%d. %s (Candidat ID: %s)%n", i + 1, requete.getTitre(), requete.getIdCandidat());
        }
    
        System.out.print("Entrez le numéro de la candidature à refuser: ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix < 1 || choix > candidatures.size()) {
                System.out.println("Choix invalide.");
                return;
            }
    
            RequeteTravail requete = candidatures.get(choix - 1);
            System.out.print("Entrez un message pour l'intervenant: ");
            String message = scanner.nextLine().trim();
    
            requeteTravailController.refuserCandidature(requete.getId().toHexString(), requete.getIdCandidat(), message);
    
            // Marquer l'intervenant comme n'ayant plus de candidature active
            intervenantRepository.marquerCandidature(requete.getIdCandidat(), false);
    
            System.out.println("Candidature refusée avec succès!");
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez entrer un numéro.");
        }
    }    

    /**
     * Soumet une nouvelle requête de travail en collectant les informations via le scanner.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void submitNewRequeteTravail(Scanner scanner) {
        System.out.println("\nEntrer le titre:");
        String titre = scanner.nextLine();
        System.out.println("Entrer la description:");
        String description = scanner.nextLine();
        System.out.println("Entrer le type (ex: Réparation, Entretien, etc.):");
        String type = scanner.nextLine();
        System.out.println("Entrer la date de début espérée (YYYY-MM-DD):");
        String dateDebut = scanner.nextLine();
        System.out.println("Entrer la date de fin espérée (YYYY-MM-DD):");
        String dateFin = scanner.nextLine();
        RequeteTravail requeteTravail = new RequeteTravail(titre, description, type, dateDebut, dateFin, "Prévu", "", "");

        if (!requeteTravail.estDateValide(dateDebut) || !requeteTravail.estDateValide(dateFin)) {
            System.out.println("Erreur: Format de la date est invalide. Svp utiliser YYYY-MM-DD.");
            return;
        }

        requeteTravailController.soumettreNouvRequeteTravail(requeteTravail);
        System.out.println("\nRequête de travail soumise avec succès!");
    }    

    /**
     * Consulte et affiche les notifications du résident connecté.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void consulterNotifications(Scanner scanner) {
        String residentEmail = authController.getEmailDuUserConnecte();
        List<Notification> notifications = notificationService.getNotificationsForResident(residentEmail);

        if (notifications.isEmpty()) {
            System.out.println("Vous n'avez aucune notification.");
            return;
        }

        System.out.println("\n=== Vos Notifications ===");
        for (Notification notification : notifications) {
            String statut = notification.isRead() ? "" : "(Nouvelle)";
            System.out.println("ID: " + notification.getId());
            System.out.println("Description: " + notification.getDescription() + " " + statut);
            System.out.println("Date: " + notification.getTimestamp());
            System.out.println("-----------------------------------");
        }

        System.out.print("Entrez l'ID de la notification pour la marquer comme lue (ou appuyez sur Entrée pour revenir): ");
        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            try {
                ObjectId notificationId = new ObjectId(input);
                notificationService.markNotificationAsRead(notificationId);
                System.out.println("Notification marquée comme lue.");
            } catch (IllegalArgumentException e) {
                System.out.println("ID de notification invalide.");
            }
        }
    }
}
