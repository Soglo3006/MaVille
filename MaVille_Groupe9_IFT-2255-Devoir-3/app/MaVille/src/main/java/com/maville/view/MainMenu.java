package com.maville.view;

import com.maville.controller.AuthController;
import com.maville.controller.PreferenceHoraireController;
import com.maville.controller.ProjetController;
import com.maville.repository.IntervenantRepository;
import com.maville.repository.NotificationRepository;
import com.maville.repository.PreferenceHoraireRepository;
import com.maville.repository.ProjetRepository;
import com.maville.repository.RequeteTravailRepository;
import com.maville.repository.ResidentRepository;
import com.maville.service.NotificationService;

import java.util.Scanner;

/**
 * Classe représentant le menu principal de l'application.
 * Permet aux utilisateurs de s'inscrire en tant que résident ou intervenant, de se connecter, et de naviguer dans les différents menus.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class MainMenu {
    private AuthController authController;
    private RequeteTravailRepository requeteTravailRepository;
    private PreferenceHoraireController preferenceHoraireController;
    private ProjetController projetController;
    private ProjetRepository projetRepository;
    private IntervenantRepository intervenantRepository;
    private NotificationRepository notificationRepository;
    private ResidentRepository residentRepository;
    private PreferenceHoraireRepository preferenceHoraireRepository;
    private NotificationService notificationService;

    /**
     * Constructeur de la classe MainMenu.
     *
     * @param authController              Le contrôleur d'authentification.
     * @param requeteTravailRepository    Le repository des requêtes de travail.
     * @param preferenceHoraireController Le contrôleur des préférences horaires.
     * @param projetController            Le contrôleur des projets.
     * @param notificationRepository      Le repository des notifications.
     * @param residentRepository          Le repository des résidents.
     * @param intervenantRepository       Le repository des intervenants.
     * @param projetRepository            Le repository des projets.
     * @param preferenceHoraireRepository Le repository des préférences horaires.
     * @param notificationService         Le service de notification.
     */
    public MainMenu(AuthController authController, 
                    RequeteTravailRepository requeteTravailRepository, 
                    PreferenceHoraireController preferenceHoraireController, 
                    ProjetController projetController, 
                    NotificationRepository notificationRepository, 
                    ResidentRepository residentRepository, 
                    IntervenantRepository intervenantRepository,
                    ProjetRepository projetRepository,
                    PreferenceHoraireRepository preferenceHoraireRepository,
                    NotificationService notificationService) {
        this.authController = authController;
        this.requeteTravailRepository = requeteTravailRepository;
        this.preferenceHoraireController = preferenceHoraireController;
        this.projetController = projetController;
        this.notificationRepository = notificationRepository;
        this.residentRepository = residentRepository;
        this.intervenantRepository = intervenantRepository;
        this.projetRepository = projetRepository;
        this.preferenceHoraireRepository = preferenceHoraireRepository;
        this.notificationService = notificationService;
    }

    /**
     * Affiche le menu principal et gère les choix de l'utilisateur.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    public void displayMainMenu(Scanner scanner) {
        while (true) { 
            System.out.println("__/\\\\\\\\____________/\\\\\\\\_________________/\\\\\\________/\\\\\\________/\\\\\\\\\\\\_____/\\\\\\\\\\\\___________________        ");
            System.out.println(" _\\/\\\\\\\\\\\\________/\\\\\\\\\\\\________________\\/\\\\\\_______\\/\\\\\\_______\\////\\\\\\____\\////\\\\\\___________________       ");
            System.out.println("  _\\/\\\\\\//\\\\\\____/\\\\\\//\\\\\\________________\\//\\\\\\______/\\\\\\___/\\\\\\____\\/\\\\\\_______\\/\\\\\\___________________      ");
            System.out.println("   _\\/\\\\\\\\///\\\\\\/\\\\\\/_\\/\\\\\\__/\\\\\\\\\\\\\\\\\\_____\\//\\\\\\____/\\\\\\___\\///_____\\/\\\\\\_______\\/\\\\\\________/\\\\\\\\\\\\\\\\__     ");
            System.out.println("    _\\/\\\\\\__\\///\\\\\\/___\\/\\\\\\_\\////////\\\\\\_____\\//\\\\\\__/\\\\\\_____/\\\\\\____\\/\\\\\\_______\\/\\\\\\______/\\\\\\/////\\\\\\_    ");
            System.out.println("     _\\/\\\\\\____\\///_____\\/\\\\\\___/\\\\\\\\\\\\\\\\\\\\_____\\//\\\\\\/\\\\\\_____\\/\\\\\\____\\/\\\\\\_______\\/\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\__   ");
            System.out.println("      _\\/\\\\\\_____________\\/\\\\\\__/\\\\\\/////\\\\\\______\\//\\\\\\\\\\______\\/\\\\\\____\\/\\\\\\_______\\/\\\\\\____\\//\\\\///////___  ");
            System.out.println("       _\\/\\\\\\_____________\\/\\\\\\_\\//\\\\\\\\\\\\\\\\/\\\\______\\//\\\\\\_______\\/\\\\\\__/\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\__\\//\\\\\\\\\\\\\\\\\\\\_ ");
            System.out.println("        _\\///______________\\///___\\////////\\//________\\///________\\///__\\/////////__\\/////////____\\//////////__");
            System.out.println("1. Inscription comme Résident");
            System.out.println("2. Inscription comme Intervenant");
            System.out.println("3. Connexion");

            try {
                String input = scanner.nextLine(); 
                int choice = Integer.parseInt(input); 

                switch (choice) {
                    case 1 -> new ResidentMenu(authController, requeteTravailRepository, preferenceHoraireController, projetController, notificationRepository, residentRepository, intervenantRepository).enregistrerResident(scanner);
                    case 2 -> new IntervenantMenu(authController, requeteTravailRepository, projetRepository, intervenantRepository, preferenceHoraireRepository, notificationService).enregistrerIntervenant(scanner);
                    case 3 -> connecter(scanner);
                    default -> System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
            }
        }
    }

    /**
     * Gère le processus de connexion de l'utilisateur.
     *
     * @param scanner Scanner pour lire les entrées de l'utilisateur.
     */
    private void connecter(Scanner scanner) {
        System.out.println("\n" +
                        "Entrer l'email:");
        String email = scanner.nextLine();
        System.out.println("Entrer le mot de passe:");
        String mdp = scanner.nextLine();

        if (authController.connecterResident(email, mdp)) {
            System.out.println("Connexion réussie!");
            new ResidentMenu(authController, requeteTravailRepository, preferenceHoraireController, projetController, notificationRepository, residentRepository, intervenantRepository).displayResidentMenu(scanner);
        } else if (authController.connecterIntervenant(email, mdp)) {
            System.out.println("Connexion réussie!");
            new IntervenantMenu(authController, requeteTravailRepository, projetRepository, intervenantRepository, preferenceHoraireRepository, notificationService).displayIntervenantMenu(scanner);
        } else {
            System.out.println("Email ou mot de passe invalide.");
            // Retourner au menu principal sans réinitialiser la boucle
        }
    }
}
