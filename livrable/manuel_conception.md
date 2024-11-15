
# 📖 Manuel de Conception

## 1. Introduction
Ce projet, développé pour le module de POO à Ensimag, vise à modéliser une simulation utilisant des robots et des scénarios de déplacement, avec une interface graphique interactive. Les choix de conception se sont concentrés sur la modularité, la réutilisabilité des composants, et la flexibilité de la simulation. 🤖💡

## 2. Choix de conception

#### A. Utilisation du Stratégie Pattern

Le Stratégie Pattern permet de définir une famille d’algorithmes, de les encapsuler et de les rendre interchangeables. Nous avons utilisé ce patron notamment avec les classes robots, les stratégies, la recherche de plus court chemin ou les evenements.

##### Exemple du strategy pattern avec les classes liées aux robots :



**`Robot` :**
- Contient les méthodes communes à tous les robots, comme la gestion de leur position, la vitesse ou le niveau d’eau.

- Définit des méthodes abstraites comme `getVitesse(NatureTerrain terrain)` ou `remplirReservoir()`, permettant à chaque type de robot de fournir une implémentation spécifique.

**`RobotTerrestre`:**
- Contient les méthodes communes aux robots terrestres comme `remplirReservoir()`


**`RobotChenilles` :** 

- Sous classe concrète
- Implémente par exemple la méthode `getVitesse()` pour retourner une vitesse réduite sur certains terrains spécifique au type de robot chenille.

##### Avantages

- Extensibilité : Ajouter un nouveau type de robot ne nécessite pas de modifier les classes existantes.

- Séparation des responsabilités : Chaque sous-classe est responsable de ses propres comportements spécifiques.

- Réutilisation du code : Les comportements communs sont centralisés dans la classe abstraite.


#### B. Utilisation du Factory Pattern


Nous définissons une interface qui créer de manière commune tous les types de robot dans une classe mère mais qui délègue les choix du type de robot aux sous classes.


`RobotFactory :`

- Contient la méthode statique `getRobot(String typeRobot, Case caseCourante, Carte carte, double vitesseLue)`.
En fonction de la chaîne typeRobot, elle instancie et retourne l’un des sous-types de Robot (par exemple, RobotChenilles, Drone, etc.).


Cette organisation permet de facilement étendre la simulation en ajoutant de nouveaux types de robots ou en modifiant les comportements des robots existants, en respectant notamment aussi le patron stratégie.



##### C. Plus court chemin

Nous avons choisi d’utiliser l'algorithme A* pour trouver le plus court chemin entre deux cases. Pour cela, nous avons conçu une interface `PlusCourtChemin`, implémentée par la classe `PlusCourtCheminAstar` *(en respectant le patern strategy)*. Cette approche permet de maintenir une grande **flexibilité**, car il est possible d'ajouter de nouveaux algorithmes de recherche de chemin en implémentant simplement cette interface.

Le choix de l'algorithme utilisé se fait au moment de la configuration du scénario. Le robot dispose d'une classe qui utilise l'algorithme de recherche de chemin passé en argument via la méthode `robot.deplacementPlusCourtChemin`. Cette méthode applique l'algorithme de recherche sélectionné pour calculer et effectuer le déplacement du robot sur son chemin optimal.

Afin de tester notre algo de plus court chemin, nous avons mis en place une carte 

