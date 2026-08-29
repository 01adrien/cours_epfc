# 1. Introduction

## 1.1. Contexte

Ce travail s'inscrit au cœur de l'innovation sportive, plus particulièrement dans le domaine du coaching connecté et des applications mobiles dédiées au sport.
Les outils numériques occupent aujourd'hui une place importante dans la pratique sportive, notamment pour accompagner les sportifs dans leur préparation, leur permettre de suivre leurs entraînements et donner aux coachs des moyens d'analyser les performances réalisées.

Dans ce contexte, le projet — nommé NoTrackRun — consiste en une plateforme de coaching sportif destinée à des organisations sportives (clubs, structures sportives) et aux coachs et sportifs qui en dépendent, permettant de faire le lien entre la planification des entraînements, leur réalisation par les sportifs et le suivi de leurs performances. La solution repose sur plusieurs composants complémentaires, notamment une application web destinée à la gestion et au suivi des entraînements ainsi qu'une application mobile utilisée par les sportifs.

Le projet a été développé dans une perspective réelle et commerciale, et non comme une simple démonstration technique réalisée dans le cadre académique. Cette dimension implique de prendre en compte des contraintes propres à un produit destiné à être utilisé par différents utilisateurs, notamment en matière de sécurité, de fiabilité, de confidentialité des données et d'évolution de la solution.

C'est dans ce contexte que Alexandre Henin et moi-même avons commencé à travailler ensemble sur le développement du projet. Nous nous sommes rencontrés au cours de notre bachelier en informatique de gestion et avons décidé de poursuivre cette collaboration autour de cette solution. Le développement avait déjà commencé avant que nous décidions d'en faire le sujet de nos travaux de fin d'études. Le TFE s'inscrit donc dans la continuité d'un projet existant, avec l'objectif d'en approfondir la conception, le développement et les problématiques techniques associées.

L'équipe projet ne se limite toutefois pas aux deux étudiants : la construction du contenu sportif de la plateforme — la structure des plans d'entraînement, la logique d'intensité fondée sur la VMA, la composition des séances — s'appuie sur la collaboration avec un coach expérimenté, qui joue le rôle d'expert métier pour ces aspects. Cette collaboration est développée en 1.4.

Le principe de minimisation des données qui structure l'ensemble de ce travail n'est pas né d'un choix de conception a posteriori : il trouve son origine dans un premier concept d'entraînement "sans traçage" développé initialement par Alexandre Henin dans un contexte militaire, où l'absence de suivi de localisation des personnels répond à un besoin opérationnel concret plutôt qu'à une préférence de confidentialité générale. Ce concept n'ayant pas été retenu dans ce cadre initial, il a ensuite été adapté et étendu au monde civil du coaching sportif — une trajectoire qui n'est pas inhabituelle pour des technologies initialement pensées dans un contexte de défense. Le nom du produit, NoTrackRun, porte directement la trace de cette origine.

Cette situation nous permet également d'aborder le projet avec une approche différente de celle d'un développement réalisé uniquement pour répondre à une demande académique : les choix effectués doivent répondre à des besoins concrets et tenir compte des contraintes d'un produit amené à évoluer.

## 1.2. Problématique et objectifs

Les applications sportives occupent aujourd'hui une place importante dans le suivi de l'activité physique. Elles permettent notamment d'enregistrer des performances, de suivre la progression d'un sportif et de partager certaines informations avec un coach. Cette utilisation croissante du numérique implique cependant la collecte et le traitement d'un nombre important de données liées aux utilisateurs.

Ces données peuvent avoir un caractère particulièrement sensible. Selon les fonctionnalités proposées, une application sportive peut notamment avoir accès à des informations relatives aux performances, aux habitudes d'entraînement, aux déplacements ou encore à la localisation d'un utilisateur. La multiplication des services sportifs numériques soulève ainsi des questions concernant la manière dont ces données sont collectées, stockées, utilisées et éventuellement partagées avec des services tiers.

Cette problématique s'inscrit également dans une réflexion plus large autour de la maîtrise des données par les acteurs qui les traitent. Dans un contexte européen marqué par le renforcement des exigences en matière de protection des données, il devient nécessaire de concevoir des solutions permettant de conserver une maîtrise aussi importante que possible des informations traitées — une préoccupation qui se traduit, à l'échelle de ce projet, par des choix concrets de conception plutôt que par une orientation générale.

Le projet cherche donc à se distinguer d'une approche dans laquelle la collecte de données serait systématique simplement parce qu'elle est techniquement possible. L'objectif est au contraire de déterminer quelles informations sont réellement nécessaires au fonctionnement du service, puis de limiter leur collecte et leur accès au strict besoin fonctionnel.

À cela s'ajoute une problématique propre au fonctionnement du produit : le sportif utilise principalement une application mobile pour consulter et réaliser ses entraînements, tandis que le coach utilise la plateforme web pour préparer les séances et suivre les résultats. Les informations doivent donc circuler entre ces deux environnements de manière fiable, y compris lorsque le sportif ne dispose pas d'une connexion réseau au moment où il réalise sa séance.

À partir de ces différents enjeux, la problématique principale de ce travail peut être formulée comme suit :

> Comment concevoir et développer une plateforme de coaching sportif permettant de planifier et de suivre les entraînements, tout en assurant une communication fiable avec l'application mobile et une gestion maîtrisée, sécurisée et respectueuse de la vie privée des données des utilisateurs ?

Les principaux objectifs du projet sont dès lors de développer une plateforme permettant de gérer les entraînements et leurs résultats, d'assurer leur communication avec l'application mobile et de fournir aux coachs les outils nécessaires au suivi des performances.

Un objectif important est également de mettre en œuvre une approche de minimisation des données, afin que les informations collectées et accessibles correspondent réellement aux besoins du service. La sécurité, l'isolation des données entre les utilisateurs et les organisations ainsi que la fiabilité de la synchronisation constituent également des objectifs essentiels du développement.

Enfin, le projet doit rester suffisamment évolutif pour pouvoir accompagner les besoins futurs du produit, tout en conservant une architecture permettant de maîtriser les données et les différents flux entre la plateforme et l'application mobile.

## 1.3. Périmètre du travail et répartition entre étudiants

Le projet étant constitué de plusieurs composants complémentaires, son développement a été réparti entre les deux étudiants afin de permettre une séparation claire des responsabilités tout en conservant une cohérence globale du produit.

Mon travail porte principalement sur la plateforme web et les services backend associés. Cette partie constitue le point central de gestion du système. Elle comprend notamment la gestion des données, la logique métier, la gestion des utilisateurs et des droits d'accès, la gestion des entraînements ainsi que la mise à disposition des fonctionnalités nécessaires à l'application mobile.

Le second volet du projet concerne l'application mobile destinée aux sportifs. Celle-ci permet notamment de récupérer les entraînements, de les consulter et de transmettre les résultats des séances réalisées. Son développement constitue le travail réalisé en parallèle par Alexandre Henin.

Les deux parties ne sont toutefois pas développées indépendamment. Elles doivent communiquer au travers d'une API, qui constitue l'interface entre la plateforme et l'application mobile. La conception de cette communication fait donc partie des éléments communs nécessaires au bon fonctionnement du produit.

Cette répartition permet ainsi de travailler sur deux systèmes distincts tout en conservant un objectif commun : développer une solution de coaching sportif complète. Les choix réalisés d'un côté peuvent avoir des conséquences sur l'autre, ce qui nécessite une coordination régulière, notamment concernant les données échangées, le fonctionnement des entraînements et la transmission des résultats.

## 1.4. Démarche suivie

Le développement du projet a été réalisé de manière progressive, en partant des besoins identifiés pour construire et faire évoluer la solution. La démarche suivie s'est articulée autour de plusieurs étapes : l'analyse du fonctionnement attendu, la conception de la solution, son développement, puis la validation des fonctionnalités réalisées. Les aspects liés à la sécurité, à la protection des données et à la communication entre la plateforme et l'application mobile ont été pris en compte tout au long du projet.

Cette démarche ne s'est pas construite dans l'isolement des deux étudiants. La structure d'un entraînement, la façon dont un plan s'organise en séances puis en blocs, le rôle central de la VMA dans le calcul des objectifs, ou encore la distinction entre course à pied et renforcement/conditionnement (cf. 2.1, 2.3) relèvent d'un savoir-faire sportif que ni l'un ni l'autre des étudiants ne possédait initialement. Ces aspects ont été définis et validés en collaboration avec un coach expérimenté, qui a agi comme expert métier tout au long du projet : c'est son expérience du terrain qui a guidé la manière dont les Training Resources devaient être structurées pour rester à la fois flexibles et réellement utilisables par d'autres coachs, plutôt qu'une modélisation construite a priori par les développeurs eux-mêmes. Cette collaboration s'est poursuivie au-delà de la phase de conception initiale : le produit étant déjà utilisé en bêta par de vrais clients, certains besoins — comme celui d'un second type d'entraînement orienté conditionnement — ont émergé directement de l'usage réel de la plateforme plutôt que d'une planification initiale exhaustive.

La suite de ce travail reprend cette démarche en présentant successivement le contexte et les besoins du projet, les choix de conception, la réalisation de la plateforme, les aspects liés à la sécurité et à la protection des données, puis les tests et la validation de la solution.
