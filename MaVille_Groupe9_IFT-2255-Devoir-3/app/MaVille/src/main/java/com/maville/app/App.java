package com.maville.app;

import com.maville.controller.AuthController;
import com.maville.controller.PreferenceHoraireController;
import com.maville.controller.ProjetController;
import com.maville.repository.IntervenantRepository;
import com.maville.repository.MongoDBConnection;
import com.maville.repository.NotificationRepository;
import com.maville.repository.PreferenceHoraireRepository;
import com.maville.repository.RequeteTravailRepository;
import com.maville.repository.ResidentRepository;
import com.maville.repository.ProjetRepository;
import com.maville.service.NotificationService;
import com.maville.service.PreferenceHoraireService;
import com.maville.service.ProjetService;
import com.maville.view.MainMenu;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;

/**
 * Classe principale de l'application Maville.
 * Cette classe initialise les connexions à la base de données, les repositories,
 * les services, les contrôleurs et lance le menu principal de l'application.
 * 
 * @version 1.0
 * @since 2024-04-27
 */
public class App {

    /**
     * Point d'entrée de l'application.
     * Initialise les composants nécessaires et affiche le menu principal.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Initialiser la connexion à la base de données
        String connectionString = "mongodb+srv://emmanuellepilon:F7qFLOVXzdJAO7U7@cluster0.s21yr.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        MongoDatabase database = MongoDBConnection.getDatabase(connectionString);

        // Initialiser les repositories
        ResidentRepository residentRepository = new ResidentRepository(database);
        IntervenantRepository intervenantRepository = new IntervenantRepository(database);
        RequeteTravailRepository requeteTravailRepository = new RequeteTravailRepository(database);
        PreferenceHoraireRepository preferenceHoraireRepository = new PreferenceHoraireRepository(database);
        ProjetRepository projetRepository = new ProjetRepository(database);
        NotificationRepository notificationRepository = new NotificationRepository(database);

        // Initialiser les services
        PreferenceHoraireService preferenceHoraireService = new PreferenceHoraireService(preferenceHoraireRepository);
        NotificationService notificationService = new NotificationService(notificationRepository, residentRepository);
        ProjetService projetService = new ProjetService(projetRepository, preferenceHoraireRepository, notificationService);

        // Initialiser les contrôleurs
        AuthController authController = new AuthController(residentRepository, intervenantRepository);
        PreferenceHoraireController preferenceHoraireController = new PreferenceHoraireController(preferenceHoraireService);
        ProjetController projetController = new ProjetController(projetService);

        // Initialiser le menu principal avec les contrôleurs et repositories
        MainMenu mainMenu = new MainMenu(
            authController,
            requeteTravailRepository,
            preferenceHoraireController,
            projetController,
            notificationRepository,
            residentRepository,
            intervenantRepository,
            projetRepository,
            preferenceHoraireRepository,
            notificationService
        );

        // Afficher le menu principal
        Scanner scanner = new Scanner(System.in);
        mainMenu.displayMainMenu(scanner);
    }
}
