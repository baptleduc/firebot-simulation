
# 📖 Manuel de Conception

## 1. Introduction
Ce projet, développé pour le module de POO à Ensimag, vise à modéliser une simulation utilisant des robots et des scénarios de déplacement, avec une interface graphique interactive. Les choix de conception se sont concentrés sur la modularité, la réutilisabilité des composants, et la flexibilité de la simulation. 🤖💡

## 2. Choix de conception

##### A. Les classes


Nous avons choisi d'organiser les classes de robot de la manière suivante :

Nous utilisons le **factory pattern**. Nous définissons une interface qui créer de manière commune tous les types de robot dans une classe mère mais qui délègue les choix du type de robot aux sous classes.

Cette organisation permet de facilement étendre la simulation en ajoutant de nouveaux types de robots ou en modifiant les comportements des robots existants, en respectant notamment aussi le patron stratégie.

##### B. -

##### C. Plus court chemin

Nous avons choisi d’utiliser l'algorithme A* pour trouver le plus court chemin entre deux cases. Pour cela, nous avons conçu une interface `PlusCourtChemin`, implémentée par la classe `PlusCourtCheminAstar` *(en respectant le patern strategy)*. Cette approche permet de maintenir une grande **flexibilité**, car il est possible d'ajouter de nouveaux algorithmes de recherche de chemin en implémentant simplement cette interface.

Le choix de l'algorithme utilisé se fait au moment de la configuration du scénario. Le robot dispose d'une classe qui utilise l'algorithme de recherche de chemin passé en argument via la méthode `robot.deplacementPlusCourtChemin`. Cette méthode applique l'algorithme de recherche sélectionné pour calculer et effectuer le déplacement du robot sur son chemin optimal.

Afin de tester notre algo de plus court chemin, nous avons mis en place une carte 

