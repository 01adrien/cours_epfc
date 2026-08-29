# 5. Sécurité et protection des données

Cette partie rassemble et synthétise les mécanismes de sécurité et de confidentialité déjà présentés en détail dans les parties précédentes, plutôt que de les redémontrer. Certains points, en revanche, n'ont encore été traités nulle part ailleurs — ils sont développés ici, y compris lorsqu'il s'agit d'assumer une limite actuelle du produit plutôt que de la maquiller.

## 5.1. Principes de confidentialité et minimisation des données

Le principe de minimisation qui structure ce travail trouve son origine dans le concept fondateur du produit (cf. 1.1) et se retrouve formalisé comme besoin non fonctionnel explicite (cf. 2.4). Sa traduction concrète — pseudonyme du runner, absence de transmission GPS, payload d'écriture volontairement minimal côté API — est déjà entièrement démontrée en 3.3 et 3.6 ; ce principe ne fait donc l'objet d'aucun développement technique supplémentaire ici.

## 5.2. Sécurité de l'authentification

La sécurité du système à trois tokens du runner (empreinte SHA-256 en base, jamais de token en clair, rotation glissante) est déjà entièrement couverte en 4.4 et n'est pas redéveloppée. Un point n'a en revanche pas encore été montré : la politique de mot de passe appliquée aux comptes coach et organisation, définie globalement dans `AppServiceProvider::boot()` :

```php
Password::defaults(function () {
    return Password::min(12)
        ->letters()
        ->mixedCase()
        ->numbers()
        ->symbols()
        ->uncompromised();
});
```

`Password::defaults()` s'applique par défaut à toute règle de validation `password` du projet, sans que chaque `FormRequest` n'ait à la redéfinir. Au-delà des exigences classiques (longueur minimale, casse mixte, chiffres, symboles), `->uncompromised()` mérite une remarque : Laravel interroge l'API *Have I Been Pwned* pour vérifier que le mot de passe choisi ne figure pas parmi les mots de passe déjà exposés lors de fuites de données connues — une protection contre la réutilisation de mots de passe compromis, plutôt qu'une simple contrainte de format. C'est aujourd'hui la seule politique de mot de passe en place ; aucune authentification à deux facteurs ni expiration périodique de mot de passe ne sont mises en œuvre.

## 5.3. Isolation des données

L'isolation entre organisations découle structurellement du modèle de propriété décrit en 3.4 et implémenté en 4.3 : `basePermissionsEdit()`/`basePermissionRead()` comparent systématiquement l'organisation de l'utilisateur à celle de la ressource, rendant impossible l'accès d'un coach aux données d'une organisation à laquelle il n'appartient pas. Ce mécanisme n'est pas une brique de sécurité séparée, mais une conséquence directe du calcul de permissions déjà démontré ; il n'est donc pas redéveloppé ici.

## 5.4. Sécurisation de l'API

La validation des données entrantes via les `FormRequest` et l'usage de `$fillable` pour se prémunir de l'assignation de masse sont déjà présentés en 4.2 et 4.4. Le CORS repose sur une configuration standard, sans règle spécifique au projet méritant d'être détaillée.

**Limite actuelle.** Aucun rate limiting n'est en place à ce stade sur les routes sensibles de l'API (activation de token, tentative de connexion) : rien n'empêche aujourd'hui un enchaînement rapide de requêtes sur ces endpoints. C'est une limite assumée du produit en l'état actuel plutôt qu'un choix de conception, reprise en perspective d'évolution (cf. 8.3).

## 5.5. RGPD et Privacy by Design

Le seul principe RGPD effectivement mis en œuvre à ce jour est la minimisation des données, déjà démontrée à plusieurs reprises (cf. 3.3, 5.1). Aucun mécanisme de droit à l'effacement ni d'export des données personnelles n'est aujourd'hui implémenté, et aucune politique de durée de conservation n'est formellement définie. Plutôt que de présenter une conformité RGPD que le produit n'atteint pas encore, ces éléments sont assumés comme limites actuelles de la solution, à traiter en priorité dans les perspectives d'évolution (cf. 8.3).