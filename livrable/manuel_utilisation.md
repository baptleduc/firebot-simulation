  
#  ⚒️ Manuel d'Utilisation : Simulation de Robots Pompiers

  

## Introduction

Cette application, développée en Java, simule le fonctionnement d'une équipe de robots pompiers autonomes dans un environnement naturel. Elle permet de déplacer les robots, de gérer leur interaction avec des incendies, et de superviser leurs interventions pour éteindre les feux le plus rapidement possible.

  

## Pré-requis

-  **Java Development Kit (JDK)** : version 11 ou supérieure ☕️

  

## Installation

1. Téléchargez et extrayez l'archive du projet 📥.

2. Assurez-vous que le fichier `gui.jar` est dans le répertoire du projet 📂.

  

## Exécution des Tests

  

### Exécution du test de l'interface graphique

```bash

make  exeInvader

```

Lance `TestInvader` pour tester l'affichage graphique dans `gui.jar`. 💻

  

### Exécution du test de lecture

```bash

make  exeLecture

```

Exécute `TestLecteurDonnees` pour charger et afficher les données de cartes (`carteSujet.map`). 🗺️

  

## Utilisation de l'interface graphique

  

-  **Bouton Début** : Réinitialise la simulation à son état initial. 🔄

-  **Bouton Lecture** : Lance la simulation, déclenchant automatiquement les événements suivant un intervalle de temps prédéfini. ▶️

-  **Bouton Suivant** : Exécute l'événement suivant manuellement. ⏩

-  **Bouton Quitter** : Ferme l'application. ❌

-  **Tps entre 2 affichages (ms)** : Champ permettant de spécifier l'intervalle de temps (en millisecondes) entre deux mises à jour de l'affichage. Une valeur plus petite accélérera la fréquence d'actualisation, tandis qu'une valeur plus grande la ralentira. ⏱️

-  **Nb de pas simulés entre 2 affichages** : Champ permettant de déterminer le nombre de pas de simulation à exécuter entre chaque mise à jour de l'affichage. Une valeur plus élevée accélère l'avancement de la simulation en réduisant la fréquence d'affichage des résultats intermédiaires. ⚡

  

## Structure du Projet

  

-  **Source (src)** : Dossier contenant tous les fichiers source Java organisés par package. 📂

-  **Binaire (bin)** : Dossier où les fichiers `.class` compilés sont générés. 💾

-  **Interface graphique (lib/gui.jar)** : Archive contenant les classes de l'interface graphique. 🖥️

-  **Cartes (cartes/)** : Dossier contenant le fichier de carte pour la simulation. 🗺️

-  **Images (images/)** : Dossier contenant toutes les images pour l’affichage des simulations. 🖼️