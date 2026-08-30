# 2. Analyse et spécification

Cette partie présente le fonctionnement de NoTrackRun, elle décrit successivement le fonctionnement général du systéme, les acteurs qui y interviennent et leurs responsabilités respectives, puis formalise ce fonctionnement en besoins fonctionnels et non fonctionnels, en règles métier et en cas d'utilisation.L'objectif est de poser une compréhension commune du fonctionnement attendu de la plateforme avant d'en présenter la solution technique.

## 2.1. Fonctionnement général

Pour rappel le projet consiste en une solution de coaching sportif connecté destinée à des organisations sportives, telles que des clubs, et à leurs coachs et runners. Contrairement à une application de suivi sportif destinée directement au grand public, la solution est organisée autour d'une relation entre une organisation, ses coachs et les sportifs qu'ils accompagnent. La plateforme permet ainsi aux coachs de préparer les entraînements, de les attribuer aux runners et de suivre les performances réalisées.

Le fonctionnement général repose sur plusieurs systèmes complémentaires. La plateforme web constitue l'interface principale utilisée par les organisations et les coachs pour gérer les utilisateurs, préparer les entraînements et consulter les résultats. Une API assure la communication avec l'application mobile, utilisée par les runners pour récupérer leurs entraînements et transmettre leurs performances. L'application mobile permet quant à elle au runner de consulter son programme d'entraînement et de réaliser les séances qui lui ont été attribuées.

**Gestion des organisations et des accès.** Tout commence lorsqu'une organisation est intégrée à la plateforme. Lors de cet onboarding, un quota d'accès est défini pour l'organisation, comprenant notamment un nombre d'accès destinés aux coachs et un nombre d'accès destinés aux runners.

Une organisation peut ainsi disposer de plusieurs coachs. Chaque coach peut ensuite créer des runners. Un runner est lié à la fois à son organisation et à son coach.

La plateforme prend également en compte différents niveaux de responsabilité. L'organisation dispose d'un périmètre limité à son « club », tandis que certaines fonctionnalités de gestion de ressources relèvent d'un rôle d'administration à l'échelle de la plateforme.

**Création et planification des entraînements.** Une fois son accès créé, le coach utilise la plateforme pour préparer les entraînements de ses runners. Il peut créer de nouveaux runners, mais également construire les entraînements à partir des ressources disponibles.

Le système repose sur le concept de Training Resources. Celles-ci permettent de construire progressivement les différents éléments d'un entraînement. Un coach peut créer ses propres ressources et templates, mais également réutiliser des ressources déjà disponibles créées par ses collègues ou par le coach administrateur de la plateforme. Cette possibilité de réutilisation permet d'éviter de reconstruire systématiquement les mêmes éléments et facilite la préparation des entraînements.

La VMA (Vitesse Maximale Aérobie) est le marqueur central autour duquel s'organise la construction d'un entraînement. Elle est renseignée par le coach au moment où il attribue un plan à un runner, et constitue la donnée de référence à partir de laquelle les objectifs de chaque séance sont déterminés. La grande majorité des blocs qui composent une séance n'expriment pas leur intensité en valeur absolue (une vitesse ou une allure précise), mais en un niveau d'intensité relatif à la VMA du runner. C'est ce couple — intensité planifiée par le coach, VMA propre au runner — qui permet à la plateforme de calculer automatiquement le résultat attendu d'un bloc (l'allure ou l'objectif que le runner doit viser), sans que ni le coach ni le runner n'aient à effectuer ce calcul eux-mêmes. Un même plan, construit une seule fois par le coach, produit ainsi des objectifs concrets différents pour chaque runner auquel il est attribué, proportionnels à leurs capacités respectives.


**Transmission des entraînements au runner.** Une fois qu'un plan a été attribué, le runner récupère son entraînement depuis l'application mobile. La communication entre l'application et la plateforme s'effectue au travers de l'API.

Après avoir récupéré son programme, le runner peut réaliser une séance même sans connexion. Les résultats sont alors conservés temporairement par l'application et pourront être transmis à la plateforme lorsque la connexion sera à nouveau disponible.

**Réalisation et transmission des performances.** Après avoir terminé une séance, le runner transmet ses résultats à la plateforme par l'intermédiaire de l'API lorsque cela est possible. Si une connexion réseau est disponible immédiatement, les résultats peuvent être envoyés sans attendre. Dans le cas contraire, l'application attend le rétablissement de la connexion pour effectuer cette transmission.

Les données transmises correspondent aux informations nécessaires au suivi de la séance. Les performances sont notamment associées aux blocs qui composent l'entraînement, ce qui permet de conserver un niveau de détail suffisamment précis pour analyser le déroulement de la séance.

**Consultation et analyse des performances.** Une fois les résultats reçus par la plateforme, le coach peut consulter les performances de ses runners depuis l'interface web.

Deux niveaux de consultation sont proposés. Le premier correspond à une analyse détaillée de la séance, avec une consultation des résultats bloc par bloc — cette vue permet au coach d'examiner précisément la réalisation de l'entraînement et de comparer les performances obtenues aux éléments prévus dans la séance, la VMA du runner servant de référence commune pour interpréter ces deux valeurs. Le second niveau correspond à une vue plus globale de l'activité : la plateforme propose un dashboard permettant de présenter des statistiques et des graphiques afin de donner au coach une vision synthétique des performances et de l'activité de ses runners.

## 2.2. Acteurs et rôles

Le système distingue plusieurs acteurs selon leur niveau d'intervention. Cette séparation permet de répartir les responsabilités entre la gestion globale de la plateforme, la gestion d'une organisation, le suivi des runners et l'exécution des entraînements.



| Acteur | Périmètre | Responsabilités principales |
| --- | --- | --- |
| Administrateur plateforme | Toute la plateforme | Gestion des organisations, quotas et demandes de quotas |
| Coach administrateur | Toute la plateforme | Création et gestion des Training Resources de base |
| Administrateur organisation | Une organisation | Gestion des coachs, utilisateurs et besoins de l'organisation |
| Coach | Son organisation / ses runners | Création des runners, entraînements, plans et suivi des résultats |
| Runner | Ses propres entraînements | Réception des plans, exécution des séances et envoi des résultats |


## 2.3. Besoins fonctionnels

Les besoins fonctionnels décrivent les principales capacités que le système doit fournir aux différents acteurs. Ils permettent de définir ce que chaque utilisateur doit pouvoir réaliser au sein de la plateforme, sans encore préciser les conditions ou contraintes qui encadrent ces actions.

| Acteur | Besoin fonctionnel | Description |
| --- | --- | --- |
| Administrateur de la plateforme | Gérer les organisations | Créer et gérer les organisations présentes sur la plateforme. |
| Administrateur de la plateforme | Gérer les quotas | Définir et modifier les quotas d'accès aux coachs et aux runners des organisations. |
| Coach administrateur | Gérer les ressources de base | Créer et maintenir les ressources d'entraînement mises à disposition des coachs. |
| Administrateur d'organisation | Gérer les coachs | Créer, supprimer et gérer les coachs de son organisation. |
| Administrateur d'organisation | Gérer les utilisateurs | Gérer les utilisateurs de son organisation et leurs affectations. |
| Administrateur d'organisation | Gérer les besoins en accès | Demander une modification du quota de son organisation. |
| Coach | Gérer les runners | Créer et gérer les runners dont il assure le suivi. |
| Coach | Gérer les Training Resources | Créer et utiliser des ressources d'entraînement. |
| Coach | Créer des séances | Composer les séances d'entraînement à partir des ressources disponibles, pour un ou plusieurs types d'entraînement (course à pied, renforcement/conditionnement). |
| Coach | Gérer les Training Plans | Créer et organiser les plans d'entraînement. |
| Coach | Attribuer des plans | Attribuer un Training Plan à un runner, en renseignant sa VMA de référence. |
| Coach | Consulter les résultats | Consulter les performances réalisées par ses runners, au regard des objectifs calculés à partir de leur VMA. |
| Coach | Consulter les statistiques | Consulter une vue globale des performances au moyen de statistiques et de graphiques. |
| Runner | Récupérer ses entraînements | Récupérer les plans et séances qui lui sont attribués. |
| Runner | Réaliser ses séances | Consulter et réaliser les séances depuis l'application mobile. |
| Runner | Transmettre ses résultats | Envoyer les résultats des séances réalisées vers la plateforme. |

Ces besoins représentent les principales capacités attendues du produit. Ils permettent de couvrir l'ensemble du cycle fonctionnel, depuis la gestion des organisations jusqu'au suivi des performances des runners.

## 2.4. Besoins non fonctionnels

En complément des fonctionnalités précédemment identifiées, la plateforme doit respecter plusieurs exigences non fonctionnelles. Celles-ci concernent principalement la sécurité, la confidentialité des données, la disponibilité du service, la gestion des situations hors ligne ainsi que la maintenabilité et l'évolutivité du système.

| Catégorie | Besoin non fonctionnel | Description |
| --- | --- | --- |
| Sécurité | Contrôle des accès | L'accès aux fonctionnalités et aux données doit être limité en fonction du rôle de l'utilisateur et de son organisation. |
| Sécurité | Authentification sécurisée | Les utilisateurs et les échanges avec l'API doivent être authentifiés de manière sécurisée. |
| Sécurité | Isolation des organisations | Les données d'une organisation ne doivent pas être accessibles aux utilisateurs d'une autre organisation. |
| Confidentialité | Minimisation des données | La plateforme doit limiter la collecte et la transmission des données personnelles aux informations nécessaires au fonctionnement du service. |
| Confidentialité | Protection de l'identité du runner | Les informations personnelles du runner doivent être limitées. Le coach utilise notamment un pseudo plutôt qu'une identité personnelle complète. |
| Disponibilité | Accessibilité du service | La plateforme et son API doivent rester disponibles afin de permettre aux applications mobiles de récupérer les entraînements et de transmettre les résultats. |
| Hors ligne | Tolérance à l'absence de réseau | Le fonctionnement du runner ne doit pas dépendre d'une connexion réseau permanente. |
| Synchronisation | Transmission différée | Les résultats produits hors connexion doivent pouvoir être transmis ultérieurement lorsque le réseau est disponible. |
| Fiabilité | Intégrité des résultats | Les résultats reçus après une période hors ligne doivent pouvoir être correctement associés aux éléments de l'entraînement auxquels ils correspondent. |
| Maintenabilité | Évolution du système | L'architecture doit permettre de faire évoluer les fonctionnalités sans remettre en cause l'ensemble de la plateforme. |
| Évolutivité | Gestion de plusieurs organisations | Le système doit pouvoir accueillir plusieurs organisations avec leurs propres utilisateurs, ressources et données sans mélanger leurs périmètres. |
| Flexibilité | Construction des entraînements | Le système doit permettre aux coachs de créer et de réutiliser différentes Training Resources afin de construire des entraînements adaptés à leurs besoins. |

Ces exigences sont particulièrement importantes dans le contexte du projet, car la plateforme doit assurer la communication entre plusieurs types d'utilisateurs et une application mobile pouvant fonctionner temporairement sans connexion — les contraintes de fonctionnement hors ligne et de minimisation des données, déjà présentées en 2.1, se retrouvent ici formalisées en exigences non fonctionnelles à part entière.

## 2.5. Règles métier clés

Les règles métier définissent les contraintes qui encadrent l'utilisation des fonctionnalités du système. Elles précisent notamment les relations entre les utilisateurs, les conditions d'accès aux ressources et le comportement attendu lors de situations particulières.

**Organisation / quotas**

- Une organisation possède un quota d'accès coachs et un quota d'accès runners.
- L'administrateur de la plateforme crée l'organisation et gère ses quotas.
- L'administrateur d'organisation peut demander modification des quotas.
- Un coach ne peut être rattaché qu'à une seule organisation.
- Une organisation peut avoir plusieurs coachs.

**Utilisateurs**

- Un runner appartient à une organisation.
- Un runner est également rattaché à un coach.
- Si un coach est supprimé, le runner peut être réaffecté à un autre coach.

**Training Resources**

- Il existe des Training Resources de base, créées par le coach administrateur et disponibles pour les coachs de toutes les organisations.
- Un coach peut utiliser ces ressources de base.
- Un coach peut également créer ses propres Training Resources.
- Les ressources créées par un coach sont automatiquement partagées avec les autres coachs de la même organisation.
- Une organisation peut reaffecter une resource si elle n'a plus de coach propriétaire.
- Une Training Resource existante ne peut pas être modifiée directement par un coach s'il n'en est pas le créateur. Pour l'adapter, celui-ci doit en créer une copie, puis modifier cette copie. Cette règle s'applique également aux ressources de base.

**Plans / entraînements**

- Un coach crée des séances à partir des Training Resources.
- Il peut regrouper ces séances dans un Training Plan.
- Il peut attribuer un Training Plan à un runner, en renseignant sa VMA.
- Une fois attribué, le runner peut récupérer le plan via l'application mobile.

**Résultats / hors ligne**

- Le runner peut exécuter un plan sans connexion après l'avoir récupéré.
- Les résultats sont envoyés dès que la connexion est disponible.
- Les résultats sont associés aux blocs de la séance.
- Un bloc peut apparaître plusieurs fois dans les résultats, notamment lorsqu'il comporte des répétitions.
- Si le coach modifie ou supprime un bloc ou une séance pendant que le runner est hors ligne, les résultats du runner doivent quand même pouvoir être associés correctement à ce qu'il avait exécuté.

**Permissions / isolation**

- Une organisation ne peut accéder qu'à ses propres données.
- Un coach ne voit que les runners relevant de son périmètre.
- L'administrateur d'organisation peut avoir une vue globale sur l'activité de son organisation.
- Le coach administrateur peut créer des ressources globales utilisables par les différentes organisations.

::: annexe
Diagramme de uses cases reprennant les acteurs et actions ci-dessus — cf. annexe 10.2.
:::

