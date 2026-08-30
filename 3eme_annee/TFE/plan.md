# Plan du TFE — NoTrackRun

## 1. Introduction

- 1.1. Origine et contexte du projet
- 1.2. Problématique et objectifs
- 1.3. Périmètre du travail et répartition entre étudiants
- 1.4. Démarche suivie

## 2. Spécification

- 2.1. Fonctionnement général
- 2.2. Acteurs et rôles
- 2.3. Besoins fonctionnels
- 2.4. Besoins non fonctionnels
- 2.5. Règles métier clés
- 2.6. Cas d'utilisation principaux

Description du fonctionnement général du produit de bout en bout : gestion des organisations et des accès, construction des entraînements via les Training Resources, transmission au runner, exécution hors ligne, remontée des performances. Formalisation des acteurs et de leurs responsabilités, des besoins fonctionnels et non fonctionnels, des règles métier clés et des principaux cas d'utilisation.

## 3. Conception

- 3.1. Architecture générale
- 3.2. Choix technologiques et justification
- 3.3. Modélisation des données
- 3.4. Conception des Training Resources
- 3.5. Authentification et autorisations
- 3.6. Conception de l'API et communication avec le mobile
- 3.7. Fonctionnement hors ligne et synchronisation

Choix d'architecture  et modélisation des données, en particulier la distinction structurante entre plan-modèle réutilisable et plan assigné. Conception de l'abstraction commune aux Training Resources, du système d'authentification à tokens du runner, des principes de conception de l'API mobile, et du fonctionnement hors ligne.

## 4. Implementation

- 4.1. Mise en place de l'environnement
- 4.2. Implémentation du modèle métier
- 4.3. Implémentation des Training Resources
- 4.4. Implémentation de l'API
- 4.5. Interface utilisateur

Descente au niveau de l'implémentation des choix posés en conception : mise en place de l'environnement Laravel, migrations et relations Eloquent du modèle métier, traduction concrète des Training Resources en interfaces et Policies, implémentation de l'API mobile (middleware d'authentification, synchronisation incrémentale, restauration en cascade), et exploitation des permissions côté interface utilisateur.

## 5. Sécurité et protection des données

- 5.1. Principes de confidentialité et minimisation des données
- 5.2. Sécurité de l'authentification
- 5.3. Isolation des données
- 5.4. Sécurisation de l'API
- 5.5. RGPD et Privacy by Design

## 6. Tests, validation et mise en production

- 6.1. Stratégie de test
- 6.2. Scénarios de test représentatifs
- 6.3. Mise en production

## 7. Appréciation personnelle

- 7.1. Lien avec les cours suivis
- 7.2. Difficultés rencontrées
- 7.3. Compétences acquises et apport professionnel

## 8. Conclusion

- 8.1. Rappel de la problématique et bilan du travail réalisé
- 8.2. Atteinte des objectifs et réponse aux contraintes
- 8.3. Limites de la solution et perspectives d'évolution

## 9. Bibliographie

## 10. Annexes

