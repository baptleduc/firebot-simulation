
# 📖 Manuel de Conception

## 1. Introduction
Ce projet, développé pour le module de POO à Ensimag, vise à modéliser une simulation utilisant des robots et des scénarios de déplacement, avec une interface graphique interactive. Les choix de conception se sont concentrés sur la modularité, la réutilisabilité des composants, et la flexibilité de la simulation. 🤖💡

## 2. Choix de conception

##### A. -
##### B. -

##### C. Plus court chemin

Nous avons choisi d’utiliser l'algorithme A* pour trouver le plus court chemin entre deux cases. Pour cela, nous avons conçu une interface `PlusCourtChemin`, implémentée par la classe `PlusCourtCheminAstar`. Cette approche permet de maintenir une grande flexibilité, car il est possible d'ajouter de nouveaux algorithmes de recherche de chemin en implémentant simplement cette interface.

Le choix de l'algorithme utilisé se fait au moment de la configuration du scénario. Le robot dispose d'une classe qui utilise l'algorithme de recherche de chemin passé en argument via la méthode `robot.deplacementPlusCourtChemin`. Cette méthode applique l'algorithme de recherche sélectionné pour calculer et effectuer le déplacement du robot sur son chemin optimal.



