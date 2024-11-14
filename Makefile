# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive lib/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture

testInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/test/TestInvader.java

testLecture:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/io/TestLecteurDonnees.java





Lecture: 
	javac -d bin -sourcepath src src/io/TestLecteurDonnees.java
	java -classpath bin io/TestLecteurDonnees cartes/carteSujet.map

Simu:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -classpath bin:lib/gui.jar simu/TestSimulateur cartes/carteSujet.map

SimuScenario0:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -classpath bin:lib/gui.jar simu/TestSimulateur cartes/carteSujet.map 0

SimuScenario1:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/carteSujet.map 1

SimuScenario2:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/carteSujet.map 2

SimuStrategieElementaire:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/carteSujet.map -strategie elementaire

SimuStrategieElementaireDesert:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/desertOfDeath-20x20.map -strategie elementaire

SimuStrategieEvoluee:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/carteSujet.map -strategie evoluee

SimuStrategieEvolueeDesert:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/desertOfDeath-20x20.map -strategie evoluee

SimuStrategieEvolueeMushroom:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/mushroomOfHell-20x20.map -strategie evoluee

SimuStrategieEvolueeSpiral:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/simu/TestSimulateur.java
	java -ea -classpath bin:lib/gui.jar simu/TestSimulateur cartes/spiralOfMadness-50x50.map -strategie evoluee

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:lib/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:lib/gui.jar test/TestInvader

exeLecture: 
	java -classpath bin io/TestLecteurDonnees cartes/carteSujet.map

clean:
	rm -rf bin/*
