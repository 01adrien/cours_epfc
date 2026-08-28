# Contexte général du TFE

Je réalise mon TFE en 3e année de bachelier en informatique de gestion.

Le projet est un **véritable projet commercial**, et non un projet scolaire fictif. Il s'agit d'une solution de sport connecté actuellement en **phase bêta**, avec la plateforme web et les applications mobiles déjà déployées en production.

L'objectif général est de proposer une solution de coaching sportif respectueuse de la vie privée et limitant la collecte de données personnelles.

Le projet est composé de deux parties :

* **Application mobile** : développée par l'étudiant A.
* **Plateforme web + API** : développées intégralement par moi, étudiant B.

Mon TFE porte donc principalement sur la **conception et la réalisation de la plateforme web et des API**.

on a un expert metier pour l'architecture des plans d'entrainement (coach)

---

# Fonctionnement métier

Le fonctionnement général est :

```text
Organisation / Club
        │
        ├── Coachs
        │     │
        │     └── Runners
        │
        └── Vue d'ensemble de l'activité
```

## Onboarding d'un club

Un club contacte l'entreprise et commande un certain nombre d'accès coachs et sportifs.

L'organisation est ensuite créée dans la plateforme.

Le club dispose d'une interface lui permettant de :

* gérer ses coachs ;
* gérer ses accès ;
* avoir une vue d'ensemble de l'activité des coachs et runners.

Un coach appartient à **une seule organisation**.

Une organisation peut avoir **plusieurs coachs**.

Un runner est lié à la fois :

* à son organisation ;
* à son coach.

Cette double relation permet notamment de conserver le runner dans l'organisation si son coach est supprimé et de pouvoir ensuite le réaffecter à un autre coach.

---

# Utilisateurs et rôles

Il existe notamment :

* organisation ;
* administrateur d'organisation ;
* coach ;
* coach administrateur ;
* runner.

## Deux niveaux d'administration bien distincts

Il y a **deux niveaux d'admin séparés**, à ne pas confondre :

1. **Administrateur d'organisation (admin orga)** : propre à **chaque club/organisation cliente**. Il gère les coachs et les accès *au sein de son organisation*, et dispose d'une vue d'ensemble de l'activité de ses coachs et runners. Il n'a aucune visibilité ni pouvoir sur les autres organisations.

2. **Coach administrateur (coach admin)** : rôle **interne à la plateforme**, tenu par l'équipe produit (nous + l'expert métier). Il crée des ressources d'entraînement globales, utilisables par tous les coachs de toutes les organisations (ex. `VMA Session`). Il n'est pas rattaché à une seule organisation cliente comme un coach normal.

Ces deux rôles n'ont donc ni le même périmètre (une organisation vs toute la plateforme), ni la même nature (gestion administrative d'un club vs création de contenu métier global). Ce point doit être présenté clairement dans le TFE pour éviter toute confusion entre "admin d'un club" et "admin de la plateforme".

Un coach possède notamment un pseudo/nom et un mot de passe.

Le coach voit uniquement le **pseudo du runner**, et non une identité personnelle complète.

L'organisation dispose d'une vue globale de l'activité mais ne fonctionne pas comme une interface permettant au coach d'accéder aux données des autres coachs.

---

# Training Resources

Le coach dispose d'un système de ressources permettant de construire les entraînements.

Les principales ressources comprennent notamment :

* Block Types ;
* Session Types ;
* Templates ;
* Plans.

Les Training Resources héritent d'une classe commune `TrainingResource`.

Chaque type de ressource peut définir ses **propres règles de permissions**.

Par exemple, un `BlockType` possède ses propres méthodes telles que :

* `canView()`
* `canEdit()`
* `canDelete()`
* `canCopy()`

## Mécanisme de partage (`share`)

Le partage n'est pas encore entièrement exploité fonctionnellement, mais son fonctionnement actuel est précis :

* **Au sein d'une organisation, les Training Resources sont partagées d'office entre tous les coachs** de cette organisation, indépendamment de qui les a créées.
* **Seul le coach admin (rôle plateforme, voir ci-dessus) peut créer des ressources protégées** : utilisables par tous les coachs, mais **non modifiables** par eux. Exemple : une ressource `VMA Session` créée par le coach admin peut être utilisée dans des séances par n'importe quel coach, mais ni elle ni les sous-ressources qui la composent (BlockTypes associés, etc.) ne peuvent être modifiées par un coach normal — notamment parce qu'elle peut être exploitée par une logique algorithmique côté plateforme.
* Le partage est donc aujourd'hui binaire : *partagé par défaut dans l'organisation* vs *protégé en modification si créé par un coach admin*. La structure du système permettra à terme d'étendre ce pouvoir de protection à n'importe quel coach, pas seulement au coach admin.

---

# Modèle des entraînements

Un plan est composé de séances.

Chaque séance possède notamment :

* un `week_number` ;
* un `day_number`.

Le plan fonctionne donc comme un calendrier relatif.

Lorsqu'un coach attribue un plan à un runner, il choisit une date de début et les séances sont placées dans le calendrier du runner en fonction de leur semaine et de leur jour.

Une séance est composée de blocs.

Structure simplifiée :

```text
Plan
│
├── Semaine 1
│   ├── Jour 1
│   │    └── Training Session
│   │          ├── Block
│   │          ├── Block
│   │          └── Block
│   │
│   └── Jour 3
│        └── Training Session
│
└── Semaine 2
     └── ...
```

Un `BlockType` définit les champs disponibles pour un bloc.

Les champs possibles comprennent notamment :

* DURATION ;
* DISTANCE ;
* REPETITION ;
* INTENSITY ;
* RECOVERY_DISTANCE ;
* RECOVERY_DURATION ;
* RECOVERY_INTENSITY ;
* DISTANCE_OR_DURATION.

La structure de données principale est notamment :

```text
block_type
    │
    └── block_type_fields
             │
             └── block_field_types

training_sessions
    │
    └── session_block
             │
             ├── block_type
             │
             └── session_block_field
```

Un `session_block` appartient à une séance et possède un ordre.

Un `session_block_field` contient la valeur concrète d'un champ pour ce bloc.

Exemple :

```text
Block
├── Type : Interval
├── Distance : 1000
├── Repetition : 6
└── Intensity : 4
```

Cette architecture permet de construire des séances de manière flexible sans devoir créer une structure de base de données différente pour chaque type de séance.

---

# Application mobile et API

L'application mobile reçoit les séances programmées par le coach.

Le runner réalise ensuite la séance sur son téléphone.

Les données de performance envoyées vers la plateforme sont volontairement limitées.

Le serveur reçoit notamment :

* distance ;
* durée ;
* succès ;
* index du résultat ;
* identifiant du bloc concerné.

Exemple de données reçues (un `block_id` peut apparaître plusieurs fois, par exemple pour des répétitions d'intervalles) :

```json
[
    {
        "index": 1,
        "distance": 1000,
        "duration": 400,
        "success": true,
        "block_id": 6365
    },
    {
        "index": 2,
        "distance": 469,
        "duration": 180,
        "success": true,
        "block_id": 6365
    }
]
```

Les performances sont donc liées au runner et aux blocs de la séance.

La plateforme web ne reçoit pas les données GPS détaillées du sportif dans ce flux. L'objectif est de limiter les données transmises au strict nécessaire pour le suivi sportif.

---

# Fonctionnement hors ligne et synchronisation

Le runner peut récupérer (pull) son plan puis rester **hors ligne pendant une période prolongée** (ex. deux semaines) avant de repousser (push) ses résultats.

## Problème de désynchronisation

Pendant que le runner est hors ligne, le coach peut modifier le plan côté serveur : par exemple **supprimer une séance ou un bloc** que le runner a déjà en local et est en train, ou a déjà fini, d'exécuter.

## Solution actuelle : suppression logique

Pour garantir que les résultats envoyés par le runner puissent toujours être associés à un bloc/séance valide en base, le système utilise une **suppression logique (soft delete)** plutôt qu'une suppression physique : une séance ou un bloc "supprimé" par le coach est en réalité **restauré/conservé** au moment où les résultats du runner arrivent, afin que l'intégrité référentielle (les `block_id` reçus) reste garantie.

## Limite actuelle et système de snapshot envisagé

Aujourd'hui, le coach ne dispose pas d'une traçabilité fine de ce que le runner a réellement reçu et exécuté : il sait seulement **quand** le runner a fait un push ou un pull du plan, pas **quelle version exacte** du plan le runner avait en main au moment de l'exécution.

Un système de **snapshot** est envisagé pour résoudre ce point : il permettrait de figer et conserver l'état exact du plan/de la séance tel que reçu par le runner à un instant T, pour que le coach puisse ensuite comparer ce que le runner a réellement suivi par rapport à l'état actuel du plan côté serveur. Ce mécanisme n'est pas encore implémenté ; il constitue une perspective d'évolution à documenter dans le TFE (limites actuelles / évolutions envisagées).

## Tentative antérieure : event sourcing (testée puis abandonnée)

Une première approche par **event sourcing** a été testée puis abandonnée, et son analyse est un bon contenu pour le TFE (démarche d'exploration → évaluation → pivot argumenté).

**Principe testé** : des événements (`UpdateUserSessionBlockEvent`, etc.) étaient déclenchés manuellement dans les contrôleurs à chaque modification d'un `SessionBlock`. Chaque événement portait un payload (`SessionBlockPayload`) contenant l'état complet et dénormalisé du bloc concerné à cet instant (label, couleur, champs, unités, icônes...), associé à un `userId`, un `entityId`, un `entityType` et un `eventType`.

**Pourquoi ce n'était pas un event sourcing "pur"** : le payload de chaque événement contenait déjà un état complet dénormalisé d'une seule entité, et non un delta/commande compact (ex. "le champ X est passé de A à B"). C'était donc un hybride : la granularité fine d'un event log, mais le contenu d'un mini-snapshot par entité.

**Deux problèmes concrets identifiés** :

1. **Décalage avec le format attendu côté mobile.** L'app mobile attend un **payload consolidé** (plan/séance entière) traité en un seul lot, pas une série de micro-événements à rejouer. Reconstruire "ce que le runner a reçu" aurait nécessité de retrouver tous les événements pertinents antérieurs à la date de pull, de ne garder que le dernier événement par bloc (puisque chaque événement était déjà un état complet), puis de réassembler manuellement la hiérarchie plan → séance → blocs. Un travail de reconstruction en lecture plus lourd que ce que le pattern est censé apporter, sans bénéficier de son intérêt réel (rejouabilité fine, deltas compacts).

2. **Déclenchement dispersé en contrôleurs.** Les événements étaient créés manuellement dans les contrôleurs, ce qui impose de penser à les déclencher à *chaque* point d'entrée touchant un `SessionBlock` (update direct, suppression en cascade, actions admin, import en masse...). Risque de capture incomplète si un point d'entrée est oublié — la fiabilité de l'event sourcing dépend normalement d'une capture garantie et centralisée, pas dispersée.

**Conclusion retenue** : l'event sourcing (même rendu plus "pur", avec des deltas fins et un vrai replay) aurait *aggravé* le problème plutôt que de le résoudre, car le besoin côté mobile est grossier (un état consolidé par pull), alors que le pattern event sourcing vise une granularité fine adaptée à l'audit détaillé, pas à la reconstruction rapide d'un gros payload cohérent.

**Décision retenue pour le TFE** : partir sur un **snapshot au pull**, avec la règle suivante : à chaque pull du runner, si le plan a changé depuis le dernier snapshot, on stocke directement le payload consolidé exactement tel qu'envoyé au mobile (même format que la transmission normale) — pas de reconstruction, pas de replay. Un vrai audit trail fin des actions du coach (indépendant du besoin de snapshot pour la sync) resterait un système séparé, à ne pas re-fusionner avec le besoin de synchronisation comme la première tentative l'avait fait à tort. Cette tentative event sourcing abandonnée est un bon contenu pour la partie 3.7.7-3.7.8 (illustration d'une démarche d'ingénieur avec exploration, évaluation des trade-offs, et pivot argumenté).

## Piste alternative retenue : reconstruction par date (SCD Type 2), et non event sourcing

Après réflexion, une piste plus économe que le snapshot matérialisé a été retenue comme axe de conception à approfondir : plutôt que de dupliquer un payload complet à chaque pull, **reconstruire l'état du plan à la demande**, en prenant la dernière date de pull du runner et en filtrant les données actuelles pour reconstituer "ce qu'il avait alors".

**Clarification terminologique importante pour le TFE** : cette approche est bien du **SCD (Slowly Changing Dimension) Type 2**, et non de l'event sourcing — la distinction mérite d'être explicite dans le mémoire :

- **SCD Type 2** : on stocke des **versions d'état** (chaque ligne = un état complet valide sur une période, avec des bornes `valid_from`/`valid_to`). Connaître l'état à une date T se fait par une **requête filtrée** (`WHERE valid_from <= T AND (valid_to > T OR valid_to IS NULL)`), sans calcul ni rejeu.
- **Event sourcing** : on stocke des **événements/deltas** (des faits horodatés). Connaître l'état à une date T nécessite de **rejouer/replier** la séquence d'événements pour reconstruire l'état, qui n'est jamais stocké directement.

La piste retenue (filtrer les versions valides à la date de pull) correspond à un filtre direct sur des états versionnés, donc au SCD Type 2 — pas à un rejeu d'événements.

**Positionnement par rapport à la tentative event sourcing abandonnée** : cette dernière n'était en réalité ni l'un ni l'autre proprement — elle empruntait la structure de stockage de l'event sourcing (un log d'événements horodatés avec `entityId`/`entityType`/`eventType`) sans son bénéfice réel (deltas compacts, replay incrémental), puisque chaque événement contenait déjà un état complet dénormalisé plutôt qu'un delta. Reconstruire l'état à une date donnée nécessitait quand même de filtrer puis dédupliquer (garder le dernier événement par bloc avant la date), une opération de reconstruction plus lourde qu'un simple filtre SCD, sans les bénéfices structurels de l'un ou l'autre pattern.

**Ce qui existe déjà, et ce qui manque** :
- Pour les **suppressions** : le soft delete déjà en place sur les séances/blocs constitue une brique directement réutilisable pour ce filtre par date (`deleted_at IS NULL OR deleted_at > pull_date`).
- Pour les **modifications (update)** : rien n'existe encore. Un `UPDATE` classique écrase la valeur précédente, qui devient donc irrécupérable pour une reconstruction a posteriori. Il faudrait faire évoluer ces updates vers un versionnement (nouvelle ligne à chaque modification, avec bornes de validité), a minima sur les entités concernées par la reconstruction (`session_block`, `session_block_field`).

**Point de vigilance identifié** : le risque de "capture dispersée" déjà repéré lors de la tentative event sourcing (déclenchement manuel en contrôleurs, avec un risque d'oubli à un point d'entrée) s'applique de la même façon au versionnement SCD des updates. Pour l'éviter, il est recommandé de centraliser la logique d'écriture (service/repository dédié, ou observer Eloquent sur le modèle) plutôt que de la disperser dans chaque contrôleur.

**Comparaison rapide avec le snapshot matérialisé** (option évoquée précédemment) :

| | Snapshot matérialisé (copie au pull) | Reconstruction par date (SCD Type 2) |
|---|---|---|
| Stockage | Duplication du payload à chaque pull (si changement) | Pas de duplication ; versions incrémentales par champ modifié |
| Lecture | Directe (état déjà figé) | Requête de reconstruction à travers plan → séance → blocs → champs |
| Écriture | Simple (un insert au pull) | Chaque update doit créer une nouvelle version plutôt qu'un update in-place |
| Fiabilité | Robuste, capturé explicitement au bon moment | Dépend de la rigueur du versioning à l'écriture (même risque que l'event sourcing abandonné si mal centralisé) |
| Cohérence avec l'existant | Nouveau mécanisme dédié | Prolonge la logique déjà en place (soft delete) |

Les deux options restent valables ; le choix final dépendra du temps disponible pour l'implémentation. Le snapshot matérialisé est plus simple et plus sûr à réaliser dans le temps imparti du TFE ; la reconstruction par date (SCD Type 2) est plus élégante et plus économe en stockage, mais demande plus de rigueur (versionnement des updates + centralisation du point d'écriture).

**Sources à exploiter pour le chapitre 3.7 (état de l'art / justification du choix)** :

- *Théorie SCD* :
  - Kimball Group, "Design Tip #152 – Slowly Changing Dimension Types 0, 4, 5, 6, 7" — référence académique historique sur les types SCD : `kimballgroup.com/2013/02/design-tip-152-slowly-changing-dimension-types-0-4-5-6-7`
  - Luzmo, "Slowly Changing Dimensions: Types and Examples" — bon résumé du piège principal du Type 2 (oubli de clôturer les anciennes lignes, entraînant des doublons en lecture) : `luzmo.com/blog/slowly-changing-dimensions`
  - GeeksforGeeks, "Slowly Changing Dimensions" — résumé des compromis (stockage, complexité de détection des changements) : `geeksforgeeks.org/software-testing/slowly-changing-dimensions`
  - Point à noter dans le TFE : ces sources sont orientées data warehouse/BI ; le projet applique le principe SCD Type 2 dans un contexte **transactionnel (OLTP)**, pas décisionnel — à mentionner explicitement comme adaptation du pattern à un nouveau contexte.

- *Implémentation Laravel (pistes déjà existantes, à citer en état de l'art même si une solution custom est développée)* :
  - `gazugafan/laravel-temporal` — package le plus proche d'un vrai SCD Type 2 sur Eloquent : conserve l'ancienne version intacte et insère une nouvelle révision à chaque update, testé spécifiquement sur MySQL/MariaDB. Point d'attention : la restriction temporelle n'est pas automatiquement appliquée en dehors du query builder Eloquent (`packagist.org/packages/gazugafan/laravel-temporal`).
  - `ProAI/eloquent-versioning` — permet de ne versionner que certains attributs choisis plutôt que la ligne entière, compatible timestamps et soft delete (`github.com/ProAI/eloquent-versioning`).
  - `jarektkaczyk/laravel-history` — package récent, approche "time travel" clé en main (`github.com/jarektkaczyk/laravel-history`).
  - `latomate07/Laraversion` — plus orienté restauration de versions façon Git que reconstruction à une date donnée ; utile comme exemple de modélisation de diff entre versions (`github.com/latomate07/Laraversion`).

---

# Authentification et sécurité

L'authentification repose sur :

* Laravel Sanctum ;
* un système de tokens personnalisé.

Les tokens peuvent :

* expirer ;
* être révoqués.

Les permissions dépendent notamment :

* de l'organisation ;
* du coach ;
* du rôle ;
* de la ressource concernée.

La séparation des accès entre organisations et coachs est une contrainte importante du système.

La protection de la vie privée est un principe central du produit :

* minimisation des données ;
* pas d'identité personnelle obligatoire pour le runner ;
* accès limité aux données nécessaires ;
* limitation des données sportives transmises ;
* absence de suivi GPS obligatoire côté plateforme ;
* réflexion autour du RGPD et du Privacy by Design (position à formaliser plus précisément — base légale, durée de conservation, processus de suppression — avant rédaction du chapitre RGPD).

Attention à distinguer dans le TFE **anonymisation** et **pseudonymisation** : les performances sont liées à un runner en base, donc il faut analyser précisément si le système peut réellement être qualifié d'anonyme ou s'il s'agit plutôt de pseudonymisation/minimisation.

---

# Technologies

Plateforme web :

* **Laravel 12**
* **PHP 8**
* **MariaDB**
* **Inertia.js**
* **Vue.js**
* **Laravel Sanctum**
* système de tokens personnalisé

La plateforme est déjà en production et en phase bêta.

J'ai développé **l'intégralité de la plateforme web et des API**.

---

# Mon rôle dans le projet

Je suis l'étudiant B.

J'ai développé toute la partie :

* plateforme web ;
* backend Laravel ;
* API ;
* base de données ;
* logique métier ;
* authentification ;
* autorisations ;
* gestion des organisations ;
* gestion des coachs ;
* gestion des runners ;
* Training Resources ;
* création des séances ;
* création des plans ;
* calendrier ;
* affectation des entraînements ;
* réception et traitement des performances ;
* gestion de la synchronisation et de la désynchronisation (suppression logique, snapshot envisagé) ;
* interface web avec Inertia/Vue.

L'étudiant A développe l'application mobile.

La communication entre nos deux parties se fait via l'API.

---

# Orientation du TFE

Le TFE ne doit pas être une simple documentation du code.

Il doit montrer une démarche :

```text
Problématique
      ↓
Analyse des besoins
      ↓
Contraintes
      ↓
Choix de conception
      ↓
Architecture
      ↓
Implémentation
      ↓
Sécurité / confidentialité
      ↓
Tests
      ↓
Mise en production
      ↓
Bilan
```

Une problématique possible est :

**« Comment concevoir une plateforme de coaching sportif permettant le suivi et la planification des entraînements tout en limitant la collecte et l'accès aux données personnelles des sportifs ? »**

Un autre axe important est la conception d'un système flexible de Training Resources permettant de créer et composer différents types de séances sans rigidifier la structure de la base de données.

Le fait que le projet soit un **produit commercial réel déjà en production** doit être exploité dans le TFE : contraintes réelles, maintenabilité, sécurité, évolutivité, compromis techniques, intégration avec une application mobile et retour d'expérience.

---

# Points encore à préciser (pour prochaines conversations)

Ces points restent ouverts et gagneraient à être clarifiés avant la rédaction des chapitres concernés :

1. **API concrète** : exemples de requêtes/réponses pour au moins 2-3 endpoints clés (authentification, récupération d'un plan par le mobile, envoi des résultats), pour nourrir les chapitres "Conception de l'API" et "Implémentation de l'API".
2. **Mise en production** : infrastructure de déploiement (serveur, CI/CD éventuel), et si possible quelques chiffres d'usage réels en bêta (nombre d'organisations, de coachs, de runners actifs) pour donner du poids au chapitre "Mise en production" et à l'appréciation personnelle.
3. **RGPD** : position plus formelle sur la base légale du traitement, la durée de conservation des données, et l'existence ou non d'un processus de suppression des données à la demande — même si la réponse est "en réflexion, pas encore formalisé".

---

# Structure de TFE retenue (2 niveaux, ~60 pages hors annexes)

1. **Introduction** *(~4 p.)*
   1.1. Contexte professionnel et commercial
   1.2. Problématique et objectifs
   1.3. Périmètre du travail et répartition entre étudiants
   1.4. Démarche suivie

2. **Analyse et spécification** *(~10 p.)*
   2.1. Fonctionnement général du produit
   2.2. Acteurs et rôles
   2.3. Besoins fonctionnels (synthèse)
   2.4. Besoins non fonctionnels
   2.5. Règles métier clés
   2.6. Cas d'utilisation principaux

3. **Conception de la solution** *(~16 p.)*
   3.1. Architecture générale
   3.2. Choix technologiques et justification
   3.3. Modélisation des données
   3.4. Conception des Training Resources
   3.5. Authentification et autorisations
   3.6. Conception de l'API et communication avec le mobile
   3.7. Fonctionnement hors ligne et synchronisation

4. **Réalisation de la plateforme web** *(~14 p.)*
   4.1. Mise en place de l'environnement
   4.2. Implémentation du modèle métier
   4.3. Implémentation des Training Resources
   4.4. Implémentation de l'API
   4.5. Interface utilisateur

5. **Sécurité et protection des données** *(~8 p.)*
   5.1. Principes de confidentialité et minimisation
   5.2. Sécurité de l'authentification
   5.3. Isolation des données
   5.4. Sécurisation de l'API
   5.5. RGPD et Privacy by Design

6. **Tests, validation et mise en production** *(~6 p.)*
   6.1. Stratégie de test
   6.2. Scénarios de test représentatifs
   6.3. Mise en production

7. **Appréciation personnelle** *(~5 p.)*
   7.1. Lien avec les cours suivis
   7.2. Difficultés rencontrées
   7.3. Compétences acquises et apport professionnel

8. **Conclusion** *(~3 p.)*
   8.1. Rappel de la problématique et bilan du travail réalisé
   8.2. Atteinte des objectifs et réponse aux contraintes
   8.3. Limites de la solution et perspectives d'évolution

9. **Bibliographie**

10. **Annexes** *(hors comptage des 60 p.)*
    10.1. Diagrammes UML
    10.2. MCD/MLD
    10.3. Documentation de l'API
    10.4. Scénarios de tests complets
    10.5. Captures d'écran
    10.6. Exemples de données échangées

---

# Point important pour la rédaction

Le TFE doit privilégier :

**problème → choix → justification → implémentation → résultat**

plutôt que :

**liste des fonctionnalités → liste des fichiers → extraits de code.**

Les parties les plus intéressantes techniquement à approfondir sont notamment :

* architecture Laravel/Inertia/Vue ;
* modèle organisation/coaches/runners, avec distinction claire entre admin d'organisation (par club) et coach admin (rôle plateforme) ;
* système d'authentification Sanctum + tokens personnalisés ;
* fonctionnement hors ligne côté mobile et ses conséquences côté API (suppression logique, tentative event sourcing testée puis abandonnée et raisons du pivot, piste retenue de reconstruction par date façon SCD Type 2 — à bien distinguer de l'event sourcing dans le TFE) ;
* système de permissions, y compris le mécanisme de partage des Training Resources ;
* abstraction `TrainingResource` ;
* modèle `BlockType / BlockTypeField / SessionBlock / SessionBlockField` ;
* composition dynamique des séances ;
* génération et affectation des plans ;
* communication API avec le mobile ;
* traitement des performances ;
* minimisation et protection des données.
