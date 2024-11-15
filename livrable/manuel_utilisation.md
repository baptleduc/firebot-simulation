
# ⚒️ Manuel d'Utilisation : Simulation de Robots Pompiers

## Introduction

Cette application, développée en Java, simule le fonctionnement d'une équipe de robots pompiers autonomes dans un environnement naturel. Elle permet de déplacer les robots, de gérer leur interaction avec des incendies, et de superviser leurs interventions pour éteindre les feux le plus rapidement possible.

---

## Pré-requis

- **Java Development Kit (JDK)** : version 11 ou supérieure ☕️

---

## Installation

1. Téléchargez et extrayez l'archive du projet 📥.
2. Assurez-vous que le fichier `gui.jar` est dans le répertoire du projet 📂.

---

## Commandes Disponibles

### Compilation
Pour compiler les différentes classes nécessaires à la simulation et aux tests, utilisez :

```bash
make all
```

---

### Exécution des Tests

#### Test de l'interface graphique
```bash
make exeInvader
```
Lance `TestInvader` pour tester l'affichage graphique dans `gui.jar`. 💻

#### Test de lecture des données
```bash
make exeLecture
```
Exécute `TestLecteurDonnees` pour charger et afficher les données de cartes (`carteSujet.map`). 🗺️

---

### Exécution de la Simulation

#### Simulation simple
```bash
make Simu
```
Lance la simulation de base avec la carte `carteSujet.map`. 🏞️

#### Simulation avec scénario spécifique
- **Scénario 0** :
  ```bash
  make SimuScenario0
  ```

- **Scénario 1** :
  ```bash
  make SimuScenario1
  ```

- **Scénario 2** *(test plus court chemin)* : 
  ```bash
  make SimuScenario2
  ```

#### Simulation avec stratégies
- **Stratégie élémentaire sur la carte du sujet** :
  ```bash
  make SimuStrategieElementaire
  ```

- **Stratégie évoluée sur la carte du sujet** :
  ```bash
  make SimuStrategieEvoluee
  ```
  *Pour les commandes suivantes, substituez 'Evoluee' par 'Elementaire' pour changer de stratégie.*
- **Stratégie évoluée sur la carte desert** :
  ```bash
  make SimuStrategieEvolueeDesert
  ```

- **Stratégie évoluée sur la carte mushroom** :
  ```bash
  make SimuStrategieEvolueeMushroom
  ```

- **Stratégie évoluée sur la carte spiral** :
  ```bash
  make SimuStrategieEvolueeSpiral
  ```

---

## Structure du Projet

- **Source (`src/`)** : Dossier contenant tous les fichiers source Java organisés par package. 📂
- **Binaire (`bin/`)** : Dossier où les fichiers `.class` compilés sont générés. 💾
- **Interface graphique (`lib/gui.jar`)** : Archive contenant les classes de l'interface graphique. 🖥️
- **Cartes (`cartes/`)** : Dossier contenant les fichiers de cartes pour la simulation. 🗺️
- **Images (`images/`)** : Dossier contenant toutes les images pour l’affichage des simulations. 🖼️

---

## Nettoyage des fichiers compilés

Pour nettoyer le répertoire des fichiers compilés :

```bash
make clean
```

Supprime tous les fichiers `.class` présents dans le répertoire `bin/`. 🧹
