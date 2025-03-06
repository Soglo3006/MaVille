# Projet-IFT-2255
## Description
L'application **MaVille** est un système intégré de gestion des travaux publics et privés à Montréal, visant à améliorer la communication et la coordination entre les résidents, les entreprises de construction, les autorités locales et les autres intervenants impliqués dans les projets de travaux. Le projet se concentre sur la réduction des désagréments liés aux travaux, tout en optimisant la gestion et le suivi des activités de chantier pour les citoyens comme pour les professionnels.
Contexte et Problématique

À Montréal, les chantiers et les travaux d'infrastructure engendrent fréquemment des désagréments pour les résidents : embouteillages, fermetures de routes, impacts sur la mobilité piétonne, etc. En dépit de la disponibilité de certaines informations sur les entraves à la circulation via le service "Info-entraves", les citoyens sont souvent mal informés sur les détails des travaux en cours ou à venir, ce qui accroît la frustration et l’imprévisibilité. De plus, la coordination entre les divers acteurs (publics et privés) n'est pas optimale, ce qui conduit à des perturbations inutiles.

MaVille vise à combler ces lacunes en offrant une plateforme centralisée permettant aux résidents et aux intervenants de mieux suivre les projets en cours, d’interagir efficacement et de planifier des travaux en toute transparence, réduisant ainsi les nuisances pour la population.

**Objectifs du Projet** :

- Améliorer la communication entre les résidents, les entreprises, les autorités publiques et les autres parties prenantes pour favoriser une gestion plus fluide des projets de travaux.
- Réduire les impacts négatifs des travaux sur la vie quotidienne des citoyens grâce à une meilleure planification, une gestion proactive des entraves et une plus grande transparence des projets.
- Faciliter l’engagement et la participation des citoyens dans la planification des travaux, en leur permettant de soumettre des requêtes et de donner des avis sur les projets.
- Centraliser la gestion des requêtes de travail résidentiel, permettant aux intervenants de consulter et répondre aux demandes spécifiques des résidents pour des travaux résidentiels.

L'objectif final de MaVille est de fournir une plateforme fluide et transparente, facilitant l’interaction entre citoyens et intervenants pour améliorer la gestion des travaux urbains à Montréal et réduire les frustrations liées à ces projets.

**Intégration avec les API de la Ville de Montréal** :

L’application s’intègre avec les services publics pour récupérer des données en temps réel sur les travaux en cours et les entraves à la circulation. Elle utilise les API disponibles de la Ville de Montréal pour afficher les informations actualisées concernant les projets d’infrastructure, les fermetures de routes et les travaux de maintenance.

**Architecture Technique et Structure du Projet**

- Backend : L'application utilise une architecture basée sur MongoDB pour stocker les données relatives aux utilisateurs (résidents et intervenants), aux requêtes de travaux, ainsi qu'aux projets de travaux. Les opérations sont gérées via des repositories pour chaque entité, en suivant le modèle CRUD (Create, Read, Update, Delete).
- Frontend : L’application est en ligne de commande pour le prototype, permettant aux utilisateurs de se connecter et de naviguer dans des menus interactifs, tout en conservant une interface simple et accessible.
- Authentification : Un système d’authentification permet de vérifier les informations de connexion des résidents et des intervenants via des identifiants uniques (email et mot de passe).
- Maven et Tests : Le projet utilise Maven pour la gestion des dépendances et des configurations. Des tests unitaires sont mis en place pour vérifier les fonctionnalités principales à travers des fichiers de test avec JUnit.

## Fonctionnalitées

### 1. Résidents

#### Inscription et Authentification

    Inscription en tant que résident.
    Connexion avec email et mot de passe.
    Déconnexion.

#### Gestion des Travaux

    Consulter les travaux publics en cours dans leur quartier.
    Consulter les entraves liées aux travaux (par rue ou quartier).
    Filtrer les travaux par type (ex. : Travaux routiers, Entretien urbain).
    Consulter les travaux à venir dans les 3 prochains mois.

#### Requêtes de Travaux

    Soumettre une nouvelle requête de travail.
    Fournir un titre, une description, des dates et un type de travail.
    Consulter l’état de leurs requêtes de travail.

#### Préférences d'Horaires

    Voir leurs plages horaires définies.
    Ajouter une nouvelle plage horaire.
    Modifier une plage horaire existante.
    Supprimer une plage horaire.

#### Notifications

    Recevoir des notifications liées aux travaux ou entraves dans leur quartier.
    Consulter l’historique des notifications.
    Marquer des notifications comme lues.

### 2. Intervenants

#### Inscription et Authentification

    Inscription en tant qu’intervenant (Entreprise publique, Entrepreneur privé, ou Particulier).
    Connexion avec email et mot de passe.
    Déconnexion.

#### Gestion des Projets

    Soumettre un nouveau projet de travaux.
        Fournir un titre, une description, un type de travaux, un quartier, des dates et un horaire.
    Modifier le statut d’un projet (Prévu, En cours, Terminé).
    Consulter la liste des projets soumis.

#### Candidature aux Requêtes de Travail

    Consulter les requêtes de travail disponibles.
    Soumettre une candidature pour une requête spécifique.
    Retirer une candidature active.
    Suivre l’état de leurs candidatures.

### 3. Fonctionnalités Partagées

#### Accessibilité Géographique

    Utilisation des quartiers et codes postaux pour organiser les informations.
    Permettre un accès ciblé aux travaux et entraves basés sur la localisation.

#### Sécurité

    Validation des données à l'inscription (ex. : format des emails, âges, codes d’identification).
    Authentification sécurisée des utilisateurs.

#### Communication

    Notifications automatiques pour alerter les résidents et intervenants sur des changements ou des conflits liés aux travaux.

## Structure du Répertoire

- **.gitignore** : Fichier de configuration Git, spécifiant les fichiers à ignorer lors des commits.
- **rapport.html** : Rapport du devoir1, devoir2 et maintenant devoir 3, contenant les détails et analyses relatifs à l'application.
- **README.md** : Ce fichier décrivant l'organisation du dépôt.
- **/diagrammes** : Dossier contenant toutes les images utilisées dans le rapport du devoir 1 & 2 & 3.

- **Prototype**
    - **app/MaVille** : Dossier principal contenant le code source du prototype de l'application.
        - **/src** : Dossier contenant le code et les classes de l'application.
            - **/main**
                - **/java/com/maville** : Dossier contenant les classes nécessaires pour notre prototype.
                    - **/app** : Dossier contenant le fichier 'App.java' qui contient la classe exécutable pour l'application.
                        - **App.java** : Classe principale qui initialise les contrôleurs et affiche le menu principal.
                    - **/controller** : Dossier contenant les classes de contrôleur pour gérer la logique de l'application.
                        - **AuthController.java** : Gère l'authentification des résidents et des intervenants.
                        - **IntervenantController.java** : Gère les opérations spécifiques aux intervenants, telles que l'inscription et l'authentification.
                        - **RequeteTravailController.java** : Gère les opérations liées aux requêtes de travail, telles que la soumission, la mise à jour et la récupération des requêtes.
                        - **ResidentController** : Gère les opérations spécifiques aux résidents, telles que l'inscription et l'authentification.
                        - **PreferenceHoraireController.java** :  Gère les opérations liées aux préférences horaires des résidents, telles que l'ajout, la modification et la suppression des plages horaires.
                        - **ProjetController.java** : Gère les opérations liées aux projets de travaux, comme la soumission de nouveaux projets, la modification de leur statut et la gestion des conflits.
                    - **/model** : Dossier contenant les classes de modèle représentant les entités de l'application.
                        - **Intervenant.java** : Classe représentant un intervenant avec ses attributs et méthodes.
                        - **RequeteTravail.java** : Classe représentant une requête de travail avec ses attributs et méthodes.
                        - **Resident.java** : Classe représentant un résident avec ses attributs et méthodes.
                        - **Tavail.java** : Classe représentant un travail avec ses attributs et méthodes.
                        - **TypeTravaux.java** : Classe énumération, qui listes tous les types possible pour un travail.
                        - **Entrave.java** : Classe représentant une entrave causée par des travaux, avec des informations telles que l'impact sur les rues ou les trottoirs.
                        - **Notification.java** : Classe représentant une notification envoyée aux résidents.
                        - **PreferenceHoraire.java** : Classe représentant une préférence horaire, incluant des attributs pour le jour et les heures de disponibilité.
                        - **Projet.java** : Classe représentant un projet de travaux, incluant des informations comme le titre, la description, le type, le statut et les horaires.
                    - **/repository** : Dossier contenant les classes de dépôt pour interagir avec la base de données.
                        - **IntervenantRepository.java** : Gère les opérations CRUD pour les intervenants, telles que la sauvegarde d'un nouveau résident et la méthode d'identification par email.
                        - **MongoDBConnection.java** : Établit une connexion à la base de données MongoDB (dans ce cas, à la base de données MaVille) Si la base de données n'est pas encore initialisée, la méthode 'getDatabase' crée une connexion et retourne l'instance de la base de données, afin de garantir que la connexion est réutilisée au lieu de la recréer à chaque appel.
                        - **RequeteTravailRepository.java** : Gère les opérations CRUD pour les requêtes de travail, telles que la sauvegarde d'un nouveau résident, la méthode d'identification par id et la mise à jour d'une requête.
                        - **ResidentRepository.java** : Gère les opérations CRUD pour les résidents, telles que la sauvegarde d'un nouveau résident et la méthode d'identification par email.
                        - **NotificationRepository.java** :  Gère les opérations CRUD pour les notifications, comme la sauvegarde et le marquage des notifications comme lues.
                        - **PreferenceHoraireRepository.java** :Gère les opérations CRUD pour les préférences horaires, telles que l'ajout, la récupération et la suppression de préférences horaires.
                        - **ProjetRepository.java** : Gère les opérations CRUD pour les projets, incluant la soumission, la modification du statut et la récupération des projets liés à un intervenant.
                    - **/service** : Dossier contenant les classes de service pour les opérations spécifiques.
                        - **TravauxEnCoursEtEntraveService.java** : Gère les appels API pour récupérer les travaux en cours et les entraves, puis réorganise leurs contenus afin qu'il soit facilement lisible.
                        - **MongoDBService.java** : Gère les opérations de base de données pour les collections Intervenants et Residents dans MongoDB. Elle fournit des méthodes pour trouver et enregistrer des intervenants et des résidents dans la base de données.
                        - **NotificationService.java** : Gère l'envoi de notifications aux résidents, ainsi que la récupération et la mise à jour des notifications.
                        - **PostalCodeMapper.java** :  Permet de mapper les codes postaux aux quartiers correspondants, facilitant la localisation des travaux ou des notifications.
                        - **PreferenceHoraireService.java** : Fournit des opérations spécifiques pour gérer les préférences horaires des résidents, comme l'ajout, la modification et la suppression.
                        - **ProjetService.java** : Gère les projets de travaux, notamment la soumission de nouveaux projets, la vérification des conflits d'horaires avec les résidents, et la modification du statut des projets.
                    - **/view** : Dossier contenant les classes de vue pour afficher les menus et interagir avec l'utilisateur.
                        - **IntervenantMenu.java** : Affiche le menu, puis sous-menus lié spécifiquement aux intervenants.
                        - **MainMenu.java** : Affiche le menu d'authentification des résidents et intervenants.
                        - **ResidentMenu.java** : Affiche le menu, puis sous-menus lié spécifiquement aux résidents.
                - **/resources/com/maville/resources/liste_CodesPostals_Rues.java** : Fichier contenant une liste de mappages entre les codes postaux et les rues de Montréal.
            - **/test/java/com/maville** : Dossier contenant les fichiers de test pour les modules de l'application.
                - **/app/AppTest.java** : Fichier de test pour le module 'App.java'. (1 test)
                - **/controller.java** : Dossier regroupant tous les fichiers de tests pour les classes controller.
                    - **AuthControllerTest.java** : Fichier regroupant tous les tests unitaires.
                    - **PreferenceHoraireControllerTest.java** : Fichier regroupant tous les tests unitaires. 
                    - **ProjetControllerTest.java** : Fichier regroupant tous les tests unitaires.
                - **/model** : Dossier regroupant tous les fichiers de tests pour les classes modèles.
                    - **IntervenantTest.java** : Fichier qui regroupe les tests unitaires des méthodes associé à un intervenant. 
                    - **ResidentTest.java** : Fichier qui regroupe les tests unitaires des méthodes associé à un résident.
                    - **RequeteTravailTest.java** : Fichier qui regroupe les tests unitaires des méthodes associé à une requête de travail.
                - **service** : Dossier regroupant tous les fichiers de tests pour les classes modèles.
                    - **NotificationServiceTest.java** : Fichier regroupant tous les tests unitaires.
                    - **ProjetServiceTest.java** : Fichier regroupant tous les tests unitaires. 
        - **/target** : Dossier contenant les classes compilées, ainsi que des fichiers de test pour l'application et la base de données. 
            - **MaVille-1.0-SNAPSHOT-shaded.jar** : Application compilée.
        - **pom.xml** : Fichier de configuration pour Maven, spécifiant les dépendances et les configurations pour l'architecture de l'application et la base de données MongoDB.

## Description des données incluses dans l'application

### 1. Utilisateurs

#### Résidents

    Nom complet : Identifie un résident par son nom.
    Email : Adresse électronique unique utilisée pour l'authentification.
    Mot de passe : Mot de passe sécurisé pour l'accès.
    Date de naissance : Permet de vérifier si l'utilisateur est éligible (plus de 16 ans).
    Téléphone (facultatif) : Moyen de contact alternatif.
    Adresse résidentielle : Permet d’associer le résident à un quartier spécifique de Montréal.
    Quartier (dérivé) : Déduit de l’adresse pour faciliter la gestion des préférences et notifications.

#### Intervenants

    Nom complet : Identifie un intervenant par son nom.
    Email : Adresse électronique unique utilisée pour l'authentification.
    Mot de passe : Mot de passe sécurisé pour l'accès.
    Type d’intervenant : Catégorie (Entreprise publique, Entrepreneur privé ou Particulier).
    Identifiant de la ville : Un code unique à 8 chiffres vérifié pour authentifier l'intervenant.

### 2. Travaux

#### Requêtes de Travail

    Titre : Brève description de la requête.
    Description : Détails complets de la requête.
    Statut : État actuel de la requête (Prévu, En cours, Terminé, etc.).
    Type de travaux : Catégorie des travaux nécessaires (par exemple, Travaux routiers, Entretien urbain).
    Date de début / fin : Période prévue pour les travaux.
    Soumise par : Identifiant de l’utilisateur ayant soumis la requête.
    Candidats associés : Intervenants ayant postulé pour cette requête.

#### Projets de Travaux

    Titre : Nom du projet.
    Description : Détails complets du projet.
    Type de travaux : Catégorie de travaux associés (Travaux routiers, Construction, etc.).
    Quartier : Zone géographique où les travaux auront lieu.
    Date de début / fin : Période de réalisation prévue.
    Horaire : Plages horaires pour la réalisation des travaux.
    Statut : État du projet (Prévu, En cours, Terminé).
    Soumis par : Identifiant de l’intervenant responsable du projet.

### 3. Notifications

    Destinataire : Email du résident recevant la notification.
    Message : Contenu de la notification.
    Statut : Indique si la notification est lue ou non.
    Horodatage : Date et heure de la notification.

### 4. Préférences d'Horaires

    Jour : Jour de la semaine (ex. : Lundi, Mardi).
    Heure de début / fin : Plages horaires indiquant la disponibilité du résident.
    Résident associé : Email du résident ayant défini cette préférence.

### 5. Entraves

    Rue impactée : Nom de la rue concernée.
    Type d’impact : Nature des perturbations (rue bloquée, trottoir fermé, etc.).
    Largeur de l’impact : Niveau d’obstruction (ex. : partiel, complet).
    Date de début / fin : Période pendant laquelle l’entrave est active.
    Piste cyclable / trottoir bloqué : Informations spécifiques sur les impacts pour les cyclistes ou piétons.

### 6. Système

#### Métadonnées des utilisateurs connectés

    Email utilisateur connecté : Suivi de la session active pour gérer les opérations.
    ID utilisateur connecté : Utilisé pour relier les opérations et requêtes à un utilisateur spécifique.

#### Quartiers et Codes postaux

    Quartiers : Liste des quartiers de Montréal utilisés pour catégoriser les utilisateurs et localiser les travaux.
    Codes postaux associés : Mappage des codes postaux aux quartiers pour simplifier l’identification géographique.

## Intructions et informations

    - Pour executer le prototype : 
        1. cd ~/MaVille_Groupe9_IFT-2255/app/MaVille
        2. mvn exec:java -Dexec.mainClass="com.maville.app.App"
        ou
        1. cd ~/MaVille_Groupe9_IFT-2255/app/MaVille/target
        2. java -jar MaVille-1.0-SNAPSHOT-shaded.jar

    - Pour executer les tests unitaires :
        1. cd ~/MaVille_Groupe9_IFT-2255/app/MaVille
        2. mvn clean test 

    - Compte résident:
        - Adresse couriel : ross@gmail.com
        - Mot de passe : 123

    - Compte intervenant:
        - Adresse couriel : rachel@gmail.com 
        - Mot de passe : 123

    - Lien vers la documentation JavaDoc :
        - localhost/app/MaVille/target/site/apidocs/index.html

    - Lien vers le rapport Jacoco : 
        - localhost/app/MaVille/target/site/jacoco/jacoco-sessions.html
