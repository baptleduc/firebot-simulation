
# 📖 Manuel de Conception

## 1. Introduction
Ce projet, développé pour le module de POO à Ensimag, vise à modéliser une simulation utilisant des robots et des scénarios de déplacement, avec une interface graphique interactive. Les choix de conception se sont concentrés sur la modularité, la réutilisabilité des composants, et la flexibilité de la simulation. 🤖💡

## 2. Choix de conception

### A. Utilisation du Stratégie Pattern

Le Stratégie Pattern permet de définir une famille d’algorithmes, de les encapsuler et de les rendre interchangeables. Nous avons utilisé ce patron pour les algorithmes de plus courts chemin et pour l'implémentation des différentes stratégies: 

#### Plus court chemin: 
![Diagramme UML Plus Court Chemin](../doc/assets/plus_courts_chemins.png)

Le choix de l'algorithme utilisé se fait au moment de la configuration du scénario. Le robot dispose d'une classe qui utilise l'algorithme de recherche de chemin passé en argument via la méthode `robot.deplacementPlusCourtChemin`. Cette méthode applique l'algorithme de recherche sélectionné pour calculer et effectuer le déplacement du robot sur son chemin optimal.

Afin de tester notre algo de plus court chemin, nous avons mis en place une carte 



![Diagramme UML Plus Court Chemin]()


#### Strategie :

TODO mathéo


##### Avantages

- Extensibilité : Ajouter un nouveau type d'algorithme/stratégie ne nécessite pas de modifier les classes existantes.

- Séparation des responsabilités : Chaque sous-classe est responsable de ses propres comportements spécifiques.


### B. Polymorphisme

Afin d’éviter de créer une fonction `main()` distincte pour chaque scénario, ce qui multiplierait les points d’entrée dans le programme, nous avons utilisé le polymorphisme en définissant une interface Scenario implémentée par tous les scénarios.
Grâce à cette approche, le programme dispose d’un point d’entrée unique, centralisé dans la classe TestSimulateur, simplifiant la gestion et l’exécution des différents scénarios.

Une stratégie peut être considérée par le simulateur comme un scénario, ce qui simplifie son exécution. Pour cela, une stratégie implémente l’interface Scenario, permettant de l’intégrer de manière uniforme avec les autres scénarios.

### C. Utilisation du Factory Pattern


Nous définissons une interface qui créer de manière commune tous les types de robot dans une classe mère mais qui délègue les choix du type de robot aux sous classes.


`RobotFactory :`

- Contient la méthode statique `getRobot(String typeRobot, Case caseCourante, Carte carte, double vitesseLue)`.
En fonction de la chaîne typeRobot, elle instancie et retourne l’un des sous-types de Robot (par exemple, RobotChenilles, Drone, etc.).

##### Avantages
- Réduction du couplage : La logique de création des robots est séparée de leur fonctionnement, rendant chaque composant plus indépendant.
- Extensibilité : Ajouter de nouveaux types de robots devient simple sans modifier le code existant, respectant le principe Open/Closed.
- Respecte le "Single Responsability Principle" en centralisant la logique de création des robot dans une place unique du code, le rendant plus lisible et plus maintenable.


### C. Utilisation du Command Pattern

Chaque événement de notre simulateur peut être encapsulé comme une commande sur le Robot avec une logique précise à executer

##### Avantages
- Les événements deviennent des objets autonomes, faciles à manipuler, enregistrer ou réorganiser.
- Simplifie l’ajout de nouvelles logiques d’événements.







