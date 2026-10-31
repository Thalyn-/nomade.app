# Architecture initiale de Nomade.app

## Rôle

Nomade.app est le compagnon Android de la régie Nomade. Dans sa première version, elle fournit les caméras intégrées au téléphone et les données des capteurs. Le Raspberry Pi conserve la responsabilité de la régie OBS et des caméras externes.

## Principes non négociables

1. Les caméras avant et arrière doivent, si le matériel l’autorise, fonctionner comme deux sources indépendantes.
2. Le téléphone ne compose pas les deux images : OBS réalise l’incrustation et la mise en page.
3. Vidéo et télémétrie constituent deux circuits indépendants.
4. Une caméra unique doit rester utilisable si le fonctionnement simultané est indisponible.
5. La prise en charge des caméras externes par le téléphone n’est pas prioritaire.
6. L’architecture doit permettre une autonomie future, sans la développer dans cette première étape.

## Modules fonctionnels visés

- interface : commandes, état des sources et diagnostics.
- camera : découverte, ouverture et gestion des caméras intégrées.
- audio : sélection et acquisition des sources sonores.
- sensors : GPS, orientation, vitesse et données disponibles.
- network : état des liaisons et qualité réseau.
- mqtt : télémétrie et commandes échangées avec Nomade sur le réseau local.
- video : encodage et transport de chaque source.
- profiles : profils d’usage et réglages.
- core : orchestration et cycle de vie des services.

Cette séparation décrit la cible fonctionnelle ; les modules ne seront extraits en bibliothèques distinctes que lorsque le code le justifiera.

## Double caméra

La caméra arrière est la source principale ; la caméra avant est la source secondaire. Chaque caméra doit disposer de sa propre chaîne de capture, de son encodage et de son transport.

Les ports UDP 9001 (arrière) et 9002 (avant) sont les destinations envisagées côté Raspberry Pi, car ils figurent déjà dans la configuration du dépôt Nomade. Ils ne constituent pas encore un engagement définitif sur le protocole.

## Vidéo et télémétrie

- Vidéo : SRT est la première piste à éprouver ; WebRTC reste une solution à comparer.
- Télémétrie : MQTT, en conservant autant que possible la compatibilité avec le sujet existant nomade/capteurs et son format de données.
- L’adresse du courtier MQTT devra être celle du Raspberry Pi joignable sur le réseau local. 127.0.0.1 depuis Android désignerait le téléphone lui-même, et non le Pi.

## Continuité de diffusion

Le maintien d’une diffusion sans interruption lors de l’extinction du Raspberry Pi nécessitera ultérieurement un service intermédiaire de réception et de redistribution. Cette fonction est hors périmètre de la première version.

## Décisions encore ouvertes

- Transport vidéo définitif et bibliothèque SRT ou WebRTC.
- Résolutions, cadences et débits par source.
- Stratégie audio et synchronisation.
- Comportement en arrière-plan et service de premier plan.
- Modèle exact des messages MQTT et mécanisme de découverte du Pi.
- Prise en charge des appareils Android autres que le Xiaomi 11T.
