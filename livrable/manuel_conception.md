
# 📖 Manuel de Conception

## 1. Introduction
Ce projet, développé pour le module de POO à Ensimag, vise à modéliser une simulation utilisant des robots et des scénarios de déplacement, avec une interface graphique interactive. Les choix de conception se sont concentrés sur la modularité, la réutilisabilité des composants, et la flexibilité de la simulation. 🤖💡

## 2. Choix de conception

##### A. Les classes


Nous avons choisi d'organiser les classes de robot de la manière suivante :

- **RobotFactory.java** : Un factory pattern utilisé pour la création des différents types de robots, assurant une création centralisée et flexible des objets Robot.

- **Robot.java** : Classe de base abstraite qui définit les caractéristiques et comportements communs à tous les robots, comme le déplacement et la gestion de l'état.

- **RobotTerrestre.java** : Classe abstraite pour les robots terrestres, qui inclut des fonctionnalités communes aux robots se déplaçant sur le sol.

- **RobotAerien.java** : Classe abstraite pour les robots terrestres, qui inclut des fonctionnalités communes aux robots se déplaçant dans les aires.

- **Drone.java** : Classe qui modélise le robot de type drone capable de se deplacer sur toutes les cases.

- **RobotChenilles.java** : Classe qui modélise le robot de type chenille capable notamment de se déplacer sur les roches.

- **RobotPattes.java** : Classe qui modélise un robot à pattes, assez lent mais capable de se déplacer sur tous les terrains sauf l'eau.


- **RobotRoues.java** : Classe représentant un robot à roues, se déplacant uniquement sur du terrain libre ou habitat.

Cette organisation permet de facilement étendre la simulation en ajoutant de nouveaux types de robots ou en modifiant les comportements des robots existants, tout en respectant le patron stratégie.

##### B. -

##### C. Plus court chemin

Nous avons choisi d’utiliser l'algorithme A* pour trouver le plus court chemin entre deux cases. Pour cela, nous avons conçu une interface `PlusCourtChemin`, implémentée par la classe `PlusCourtCheminAstar` *(en respectant le patern strategy)*. Cette approche permet de maintenir une grande **flexibilité**, car il est possible d'ajouter de nouveaux algorithmes de recherche de chemin en implémentant simplement cette interface.

Le choix de l'algorithme utilisé se fait au moment de la configuration du scénario. Le robot dispose d'une classe qui utilise l'algorithme de recherche de chemin passé en argument via la méthode `robot.deplacementPlusCourtChemin`. Cette méthode applique l'algorithme de recherche sélectionné pour calculer et effectuer le déplacement du robot sur son chemin optimal.



