# Contexte de rédaction — Partie 2 : Analyse et spécification

## 1. Objectif de ce document

Ce document sert de référence pour la rédaction de la **partie 2 du TFE : Analyse et spécification**.

La partie 2 doit présenter le fonctionnement du produit et les besoins auxquels la plateforme doit répondre, avant d'aborder les choix de conception et l'implémentation dans les parties suivantes.

La structure retenue est :

* **2.1. Fonctionnement général du produit**
* **2.2. Acteurs et rôles**
* **2.3. Besoins fonctionnels**
* **2.4. Besoins non fonctionnels**
* **2.5. Règles métier clés**
* **2.6. Cas d'utilisation principaux**

La partie 2 doit rester principalement orientée **analyse métier et spécification**. Les détails d'implémentation Laravel, de base de données ou de code doivent être gardés pour les parties 3 et 4 lorsque cela est pertinent.

Le TFE doit privilégier la logique :

**problème → besoin → contrainte → choix de conception → implémentation → résultat**

et éviter de transformer cette partie en simple liste de fonctionnalités.

---

# 2. Contexte général du produit

Le projet est un **véritable produit commercial**, actuellement en phase bêta, et non un projet scolaire fictif.

Il s'agit d'une solution de coaching sportif connecté composée principalement :

* d'une **plateforme web** utilisée par les organisations et les coachs ;
* d'une **API** assurant notamment la communication avec l'application mobile ;
* d'une **application mobile** utilisée par les runners.

La plateforme web et les API ont été développées intégralement par l'étudiant B, tandis que l'application mobile est développée par l'étudiant A.

L'objectif général du produit est de permettre la planification, la réalisation et le suivi d'entraînements sportifs tout en limitant la collecte et l'accès aux données personnelles des sportifs.

Le produit est déjà déployé en production dans une phase bêta.

---

# 3. Fonctionnement métier général

Le fonctionnement peut être résumé comme suit :

**Organisation → Coach → Runner → Entraînement → Résultats → Analyse**

Une organisation ou un club devient client du produit.

Lors de son onboarding, l'équipe de la plateforme crée les accès correspondant au quota prévu pour l'organisation.

Une organisation dispose notamment d'un nombre défini de :

* **coach accesses** ;
* **runner accesses**.

Le quota correspond donc au nombre d'accès disponibles pour l'organisation.

Les accès ne sont pas nécessairement créés individuellement par le club à partir de zéro : l'équipe responsable de la plateforme crée les accès selon le quota prévu.

---

# 4. Fonctionnement côté coach

Le coach constitue l'utilisateur principal de la plateforme web pour la gestion sportive.

Il peut notamment :

* créer et gérer des runners ;
* créer des ressources d'entraînement ;
* utiliser des ressources déjà disponibles dans la plateforme ;
* créer des templates à partir de ressources ;
* composer des séances ;
* construire des training plans ;
* attribuer des plans aux runners ;
* consulter les résultats réalisés par les runners.

Le workflow principal côté coach est donc :

**Créer/utiliser des ressources → construire les séances → construire le training plan → attribuer le plan au runner → suivre les résultats.**

---

# 5. Training Resources

Le système possède une abstraction appelée **Training Resource**.

Les ressources permettent au coach de construire progressivement des entraînements plus complexes.

Les principales catégories comprennent notamment :

* Block Types ;
* Session Types ;
* Templates ;
* Plans.

Les ressources peuvent être utilisées pour construire des séances et des plans.

Le coach peut également créer ses propres templates de ressources ou utiliser des ressources déjà présentes dans la plateforme.

Le système est conçu pour permettre une certaine flexibilité dans la composition des entraînements plutôt que d'imposer une structure rigide pour chaque type de séance.

Les détails techniques de cette architecture seront développés dans la partie consacrée à la conception des Training Resources.

---

# 6. Attribution d'un entraînement au runner

Une fois le training plan construit, le coach peut l'attribuer à un runner.

Le plan contient différentes séances organisées selon une logique de calendrier relatif.

Lorsqu'un plan est attribué, une date de début permet de positionner les séances dans le calendrier du runner.

Le runner peut ensuite récupérer son entraînement depuis l'application mobile.

---

# 7. Fonctionnement côté runner

Le runner utilise principalement l'application mobile.

L'application mobile communique avec la plateforme via l'API.

Le runner récupère son entraînement depuis le serveur.

Il peut ensuite réaliser la séance, y compris lorsque la connexion réseau n'est pas disponible.

Le fonctionnement doit donc prendre en compte un contexte **hors ligne**.

Après avoir terminé une séance, l'application tente d'envoyer les résultats vers la plateforme.

Deux situations sont possibles :

### Connexion disponible

Si le runner dispose d'une connexion réseau après avoir terminé sa séance, les résultats peuvent être envoyés immédiatement vers l'API.

### Pas de connexion disponible

Si aucune connexion réseau n'est disponible, les résultats restent temporairement sur le mobile.

Ils seront envoyés lorsque le runner retrouvera une connexion.

Ce fonctionnement implique que la plateforme doit être capable de gérer des résultats provenant d'un runner qui a pu rester hors ligne pendant une période prolongée.

Les conséquences techniques de ce fonctionnement seront développées dans la partie **3.7 — Fonctionnement hors ligne et synchronisation**.

---

# 8. Résultats d'entraînement

Après l'exécution d'une séance, les résultats sont transmis à la plateforme via l'API.

Les données reçues sont volontairement limitées aux informations nécessaires au suivi sportif.

Les résultats sont notamment associés aux blocs de la séance.

Un même `block_id` peut apparaître plusieurs fois, notamment lorsqu'un bloc correspond à plusieurs répétitions.

Le suivi peut donc être réalisé à un niveau détaillé, notamment **bloc par bloc**.

La plateforme ne repose pas nécessairement sur la réception de données GPS détaillées pour assurer ce suivi.

Cette limitation des données transmises participe à l'objectif général de minimisation des données personnelles.

---

# 9. Consultation des résultats par le coach

Le coach dispose de plusieurs niveaux de consultation des performances.

## 9.1. Vue détaillée

Les résultats peuvent être consultés **bloc par bloc**.

Cette vue permet notamment de comparer les résultats réalisés avec la structure de l'entraînement prévu.

Elle permet donc une analyse détaillée d'une séance.

## 9.2. Vue globale

La plateforme propose également une vue d'ensemble via un **dashboard**.

Cette vue peut présenter notamment :

* des statistiques ;
* des graphiques ;
* une vision globale de l'activité des runners.

L'objectif est donc de proposer à la fois :

**une analyse détaillée des séances**

et

**une vue synthétique de l'activité sportive.**

Les détails exacts des statistiques et graphiques disponibles devront être précisés ultérieurement si nécessaire avant la rédaction de la section correspondante.

---

# 10. Cycle fonctionnel global

Le fonctionnement du produit peut être représenté de manière simplifiée comme suit :

```text
Organisation / Club
        │
        │ Quota d'accès
        ▼
Création des accès
        │
        ▼
     Coach
        │
        ├──────────────► Création / utilisation des Training Resources
        │
        ▼
Création des séances
        │
        ▼
Création du Training Plan
        │
        ▼
Attribution au Runner
        │
        ▼
Application mobile
        │
        │ Pull du plan
        ▼
Exécution de la séance
        │
        │
        ├── Réseau disponible ──────► Push des résultats
        │
        └── Hors ligne ─────────────► Stockage temporaire
                                           │
                                           ▼
                                   Retour du réseau
                                           │
                                           ▼
                                    Push des résultats
                                           │
                                           ▼
                                         API
                                           │
                                           ▼
                                  Plateforme web
                                           │
                          ┌────────────────┴────────────────┐
                          ▼                                 ▼
                   Résultats détaillés                Dashboard
                    bloc par bloc                 statistiques/graphes
                          │                                 │
                          └────────────────┬────────────────┘
                                           ▼
                                         Coach
```

---

# 11. Acteurs à prendre en compte dans la partie 2

Les principaux acteurs du système sont :

* l'organisation / club ;
* l'administrateur d'organisation ;
* le coach ;
* le coach administrateur ;
* le runner.

Une distinction importante doit être conservée dans toute la rédaction :

### Administrateur d'organisation

Il appartient à une organisation cliente donnée.

Son périmètre est limité à cette organisation.

Il peut notamment gérer les coachs et les accès de son organisation et disposer d'une vue globale de l'activité de celle-ci.

### Coach administrateur

Il s'agit d'un rôle interne à la plateforme.

Il n'est pas simplement l'administrateur d'un club.

Il peut notamment créer des ressources globales utilisables par les coachs des différentes organisations.

Ces deux rôles ne doivent donc pas être confondus dans le TFE.

---

# 12. Contraintes importantes à faire apparaître dans l'analyse

Plusieurs contraintes structurent le fonctionnement du produit.

## 12.1. Gestion des quotas

Chaque organisation dispose d'un nombre défini d'accès coach et runner.

Le système doit donc permettre de gérer ces quotas.

## 12.2. Isolation entre organisations

Une organisation ne doit pas pouvoir accéder aux données d'une autre organisation.

Le périmètre des droits dépend donc notamment de l'organisation à laquelle l'utilisateur appartient.

## 12.3. Relation coach / runner

Un runner est associé à une organisation et à un coach.

Cette double relation permet notamment de conserver le runner dans l'organisation lorsqu'un coach est supprimé et de pouvoir ensuite le réaffecter.

## 12.4. Fonctionnement hors ligne

Le runner peut récupérer un plan puis rester hors ligne avant de transmettre ses résultats.

La plateforme doit donc gérer la possibilité que l'état du serveur ait changé entre la récupération du plan et l'envoi des résultats.

## 12.5. Protection des données

Le produit cherche à limiter la collecte de données personnelles.

Le coach travaille notamment avec le pseudo du runner plutôt qu'avec une identité personnelle complète.

Les données sportives transmises à la plateforme sont également limitées au nécessaire pour le suivi.

---

# 13. Ce qui doit être développé dans chaque sous-section

## 2.1. Fonctionnement général du produit

Présenter le cycle complet :

**organisation → accès → coach → ressources → plans → runner → application mobile → résultats → analyse.**

Ne pas entrer trop profondément dans l'implémentation technique.

## 2.2. Acteurs et rôles

Présenter précisément :

* organisation ;
* administrateur d'organisation ;
* coach ;
* coach administrateur ;
* runner.

Insister sur la différence entre admin d'organisation et coach administrateur.

## 2.3. Besoins fonctionnels

Transformer les fonctionnalités principales en besoins exprimés du point de vue du système.

Exemples :

* gérer les organisations ;
* gérer les accès ;
* gérer les coachs ;
* gérer les runners ;
* créer/utiliser des Training Resources ;
* créer des séances ;
* créer des plans ;
* attribuer des plans ;
* récupérer les entraînements ;
* transmettre les performances ;
* consulter les performances ;
* afficher les statistiques.

La section doit privilégier une **synthèse structurée**, plutôt qu'une liste exhaustive de toutes les fonctionnalités de l'application.

## 2.4. Besoins non fonctionnels

Prendre notamment en compte :

* sécurité ;
* confidentialité ;
* isolation des organisations ;
* disponibilité ;
* fonctionnement hors ligne ;
* fiabilité de la synchronisation ;
* maintenabilité ;
* évolutivité ;
* flexibilité du système de Training Resources.

## 2.5. Règles métier clés

Présenter les contraintes métier qui ne sont pas de simples fonctionnalités.

Notamment :

* gestion des quotas ;
* appartenance d'un coach à une organisation ;
* relation organisation/coach/runner ;
* possibilité de réaffecter un runner ;
* partage des Training Resources ;
* différence entre ressources d'organisation et ressources protégées créées par le coach administrateur ;
* contraintes liées aux plans et aux séances ;
* conservation nécessaire des références utilisées par les résultats.

## 2.6. Cas d'utilisation principaux

Prévoir un diagramme de cas d'utilisation et quelques scénarios représentatifs.

Les cas les plus pertinents pourront notamment être :

1. gérer les accès d'une organisation ;
2. gérer les runners ;
3. créer un training plan ;
4. attribuer un plan à un runner ;
5. récupérer un entraînement via l'application mobile ;
6. transmettre les résultats d'une séance ;
7. consulter les performances.

Il faudra sélectionner environ **4 à 5 cas représentatifs** dans le TFE afin d'éviter de transformer la section en documentation exhaustive.

---

# 14. Éléments à ne pas inventer lors des prochaines rédactions

Si une information n'est pas confirmée, ne pas la présenter comme un fonctionnement réel du produit.

En particulier, il reste à préciser si nécessaire :

* les limites exactes des quotas ;
* la procédure exacte d'onboarding d'une organisation ;
* les statistiques précises affichées dans le dashboard ;
* les graphiques disponibles ;
* les endpoints exacts de l'API ;
* les règles précises de validation des performances ;
* les détails de gestion des erreurs côté mobile/API ;
* certains détails de gestion des accès.

Ces éléments pourront être complétés lorsque les informations seront disponibles.

---

# 15. Niveau de détail attendu

La partie 2 doit permettre au lecteur de comprendre :

**qui utilise le système, pourquoi il l'utilise, ce qu'il doit pouvoir faire et quelles contraintes le système doit respecter.**

Elle ne doit pas encore expliquer en profondeur :

* les classes Laravel ;
* les contrôleurs ;
* les migrations ;
* les requêtes SQL ;
* la structure détaillée des modèles Eloquent ;
* le code des endpoints ;
* l'implémentation des permissions.

Ces éléments appartiennent principalement aux parties **3 — Conception** et **4 — Réalisation**.

La rédaction doit rester académique, claire et orientée vers la compréhension du système réel.

Le produit étant déjà utilisé en production dans une phase bêta, il est pertinent de présenter les besoins et contraintes comme issus d'un **contexte professionnel réel**, avec des exigences de sécurité, de maintenabilité, d'évolutivité et d'intégration avec une application mobile.

---

# 16. Référence de travail pour les prochaines conversations

Lorsque nous reprendrons la partie 2, considérer ce document comme le **contexte de référence**.

Méthode de travail souhaitée :

1. traiter une sous-section à la fois ;
2. identifier les informations nécessaires ;
3. poser les questions manquantes avant de rédiger si nécessaire ;
4. produire une rédaction académique adaptée au TFE ;
5. relire et corriger ensemble ;
6. seulement ensuite passer à la sous-section suivante.

Ordre de travail :

**2.1 → 2.2 → 2.3 → 2.4 → 2.5 → 2.6**

La rédaction doit conserver la terminologie utilisée dans le projet, notamment :

* Organisation ;
* Admin d'organisation ;
* Coach ;
* Coach administrateur ;
* Runner ;
* Training Resource ;
* Block Type ;
* Session Type ;
* Template ;
* Training Plan ;
* Training Session ;
* Block ;
* API ;
* Pull ;
* Push ;
* Dashboard ;
* performances.

