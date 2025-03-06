# Introduction 
   - Introduction assez légère, vous auriez pu ajouter un peu plus de contenu.

# Échéancier
   - Super, il est assez original.

# Glossaire 
- Application MaVille
- Info entraves et travaux
- Code de la ville 
- Type de problème, Signaler un problème
- Type de travaux, Travaux
- Statut du projet, Projet

Les points marqués ci-dessus sont les points importants que vous n'avez pas définis dans votre glossaire.

- **API (Application Programming Interface)** :
    - L'API est un concept technique généralement bien compris par les développeurs et les utilisateurs d'applications. Il n'est pas nécessaire de l'inclure dans un glossaire spécifique à l'application MaVille, car il ne constitue pas une fonctionnalité propre à cette application.

- **MongoDB** :
    - MongoDB est une technologie de base de données spécifique. Son inclusion n'est pas pertinente dans un glossaire destiné aux utilisateurs de l'application, car les utilisateurs finaux ne s'intéressent généralement pas aux détails techniques des bases de données utilisées par l'application.

- **Interopérabilité** :
    - Ce terme technique, bien que pertinent pour les développeurs et les architectes de systèmes, est trop complexe pour figurer dans un glossaire destiné aux utilisateurs finaux de l'application. Il ne représente pas une fonctionnalité que les utilisateurs de l'application auront besoin de comprendre.

- **Ville de Montréal (acteur secondaire)** :
    - Bien que la Ville de Montréal soit un acteur important dans l'application, il n'est pas nécessaire de la définir spécifiquement dans le glossaire. Elle fait partie intégrante des processus liés aux travaux publics, et son rôle est généralement bien compris par les utilisateurs.

### Cas d'utilisation 
- Il manque plusieurs CU.
- Les détails de la correction de cette section peuvent être trouvés dans l'autre fichier de feedback.

# Scénarios     

- **S'inscrire comme résident** :
    - **Étape du scénario** :
        - L'étape 3 doit être : "Le système affiche le formulaire d'inscription".
    - **Scénario alternatif** :
        - Dans le scénario alternatif, on doit permettre à l'utilisateur de réessayer si les informations n'étaient pas valides.

- **Rechercher des travaux** :
    - **Postcondition** :
        - Le système affiche bien les travaux.
    - **Scénario alternatif** :
        - Le scénario 1a n'est pas pertinent, car si l'utilisateur n'est pas inscrit, il ne voudra pas voir les travaux.

- **Permettre la planification des travaux** :
    - Comme mentionné dans l'autre fichier de feedback, ce n'est pas un CU, mais plutôt un regroupement de plusieurs CUs.

- **Signaler un problème à la ville** :
    - **Étape du scénario** :
        - L'étape 3 doit être : "Le système affiche le formulaire de signalement de problème".
    - **Scénario alternatif** :
        - Le scénario 1a n'est pas pertinent, car si l'utilisateur n'est pas inscrit, il ne voudra pas voir les travaux.

- **Recevoir des notifications personnalisées** :
    - **Étape du scénario** :
        - L'étape 3 doit être : "Le système affiche la page permettant la personnalisation des notifications".
        - L'étape 5 : "Le système confirme les modifications".
    - **Scénario alternatif** :
        - Le scénario 1a n'est pas pertinent, car si l'utilisateur n'est pas inscrit, il ne voudra pas recevoir de notifications personnalisées.

- **Soumettre une requête de travail** :
    - **Étape du scénario** :
       - **Étapes manquantes** :
            - Le système affiche le formulaire de requête.
            - Le système valide les informations.
            - Le système confirme la soumission du formulaire.
    - **Scénario alternatif** :
        - Le scénario 1a n'est pas pertinent, car si l'utilisateur n'est pas inscrit, il ne pourra pas soumettre une requête directement sans avoir créé un compte.
        - **3a** : Ce scénario n'est pas pertinent pour l'étape 3, car la validation du formulaire ne se fait pas lorsque l'utilisateur entre les informations, mais après par le système.

- **Consulter ses notifications** :
    - **Postcondition** :
        - L'utilisateur a consulté ses notifications.
    - **Scénario alternatif** :
        - Même problème que mentionné plus haut.

- **S'inscrire comme intervenant** :
    - **Étape du scénario** :
        - Même problème que mentionné plus haut. Le système doit afficher les champs permettant à l'utilisateur de remplir le formulaire.
    - **Scénario alternatif** :
        - Que se passe-t-il si les informations entrées par l'utilisateur sont incorrectes ?

- **Soumettre un nouveau projet de travaux** :
    - **Étape du scénario** :
        - Même problème que mentionné plus haut. Le système doit afficher les champs permettant à l'utilisateur de remplir le formulaire.
    - **Scénario alternatif** :
        - **2a** : Comment l'utilisateur a-t-il accès à cette fonctionnalité s'il n'est pas inscrit dans le système ?
        - Après l'étape **3a.1**, l'utilisateur doit pouvoir retourner à l'étape 3 pour compléter le formulaire à nouveau ou ajouter les informations manquantes.

- **Consulter la liste des requêtes de travail** :
    - **Scénario alternatif** :
        - Il manque dans votre scénario alternatif "Répondre aux requêtes" pour être cohérent avec votre diagramme de CU. 
    - **Postcondition** :
        - **"Les requêtes qui ont été répondues sont mises à jour"** : Cette postcondition n'est pas juste, car on peut consulter la liste des requêtes de travail sans forcément déposer sa candidature.

- **Mettre à jour les informations d'un chantier** :
    - **Étape du scénario** :
        - Pour être cohérent avec votre diagramme de CU, vos étapes principales doivent inclure le CU **Recevoir des notifications personnalisées**.
    - Même problème que mentionné plus haut.
    - Le système doit afficher les options permettant à l'utilisateur de mettre à jour les informations d'un chantier.

# Diagramme d'activités 

### Respect du formalisme 
- Formalisme UML respecté.

### Contenu du diagramme 
- Voir les détails de la correction dans l'autre fichier de feedback.

# Analyse 📈 

#### Risques 
- **Résidents inscrits comme intervenants** :
    - Dans la plupart des applications bien conçues, les rôles sont définis et gérés lors de l'inscription initiale, ce qui rend ce risque peu probable. De plus, un tel risque pourrait être facilement évité grâce à une gestion basique des rôles au niveau du système d'authentification. Il est peu probable qu'une mauvaise gestion des rôles soit une menace sérieuse si des contrôles de base sont déjà en place.

- **L'application n'est pas disponible** :
    - Bien que la disponibilité de l'application soit toujours un point important, ce risque est trop générique et concerne pratiquement toutes les applications. Ce risque pourrait être intégré dans un risque plus général lié à la fiabilité ou à la performance de l'application, sans avoir besoin d'être formulé comme un risque distinct.

#### Besoins non fonctionnels 
- Les besoins non fonctionnels sont bien choisis, mais l'explication des besoins et la justification sont trop légères. Voici un exemple d'un besoin plus complet :
    - **Sécurité** : L'application doit garantir la sécurité des données des utilisateurs, incluant le chiffrement des informations personnelles (adresse, courriel, mot de passe). Une authentification forte (2FA) est nécessaire pour les résidents et les intervenants.

#### Besoins matériels 
- Ok.

#### Solution de stockage 
- Ok.

#### Solutions d'intégration 
- Ok.

# Prototype 
- Le prototype plante sur une entrée invalide.

# Git 
- Git ok.
- Le readme manque de détails sur le projet.

# Rapports 
- Aucune image ne s'affiche.

# Bonus 
