# Pop Cubes
_Alias "Pet Rescue Saga" dans son nom orignal_



## Présentation du jeu

Tous les animaux ont été capturés par la méchante Reine ! Il ne reste plus beaucoup de temps : une boule de cristal a prédit qu'elle utiliserait toute la fourrure récoltée pour faire un tapis rouge pour accueillir tous les invités de sa fête annuelle du Nouvel An.

Vous êtes le seul héros de ce monde : vous devez **ABSOLUMENT** sauver ces adorables créatures et les mettre en sureté ! Vous décicez donc de partir à la recherche des quatre armes les plus puissantes du "Monde 1" et vous battre contre la méchante Reine. Pour cela, vous devrez éliminer les blocs de couleurs qui feront tout pour vous arrêter et pour vous mettre en état de nuire.

Mais gare à vous, le Roi Tanuki a lancé un sort sur certains blocs _(en tombant de son lit en apprenant ce qu'avait prédit la boule de cristal)_ : ils se sont transformés en Mur et sont devenus indestructibles. Et pour couronner le tout, il vous a donné un nombre limité de mouvements ! Seuls les pièces et les diamants pourront vous aider à surmonter cette épreuve !

Je vous promets, vous allez vivre une si belle aventure ~~



-------------------------------------------------------------------
## Utilisation

Ouvrez un terminal, et allez dans le dossier principal **POP-CUBES/**.

```
cd POP-CUBES/
```

### _Compilation de jeu_
Pour compiler le jeu, il suffit d'entrer la commande suivante :

```
javac launcher/Launcher.java
```

### _Exécution de jeu_
Pour exécuter le projet, il y a une commande principale :

```
java launcher.Launcher
```

1. **Pour aider les utilisateurs à manipuler les bonnes commandes**, une commande supplémentaire a été ajoutée :

```
java launcher.Launcher --help
```

2. Mais actuellement, l'exécution peut aussi être gérée par deux arguments différents.
L'utilisateur peut tout de suite exécuter le jeu sur une interface graphique ou sur le terminal selon son choix.

Les arguments devront être écrits après ce tag : `--typeOfView=` :

- Si l'utilisateur veut utiliser l'interface graphique, il peut alors lancer la commande suivante :

```
java launcher.Launcher --typeOfView=GUI
```

- Si l'utilisateur veut utiliser le terminal, il peut alors lancer la commande suivante :

```
java launcher.Launcher --typeOfView=terminal
```



-------------------------------------------------------------------
## Génération de la Javadoc

Pour faciliter la compréhension du code Java, les utilisateurs ont aussi la possibilité de générer la Javadoc de tous les **packages** présents dans un dossier qu'ils choisissent. Pour se faire, il faut lancer la commande suivante :

```
javadoc -d [*le_chemin_du_dossier_où_vous_voulez_votre_javadoc*] controller launcher objects dataBase
```



-------------------------------------------------------------------
## Informations utiles

### Listes des contributeurs
- Tara AGGOUN (taraggoun@gmail.com)
- Elody TANG (elodytang@hotmail.fr)
