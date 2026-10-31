# Nomade.app

Application Android compagnon de l’écosystème **Nomade**.

Nomade.app prend en charge les caméras intégrées au téléphone, les capteurs et leur transmission vers la régie Nomade sur Raspberry Pi. Elle est conçue pour pouvoir évoluer ultérieurement vers un fonctionnement autonome, sans chercher à remplacer dès maintenant la régie OBS.

## Objectif immédiat

Valider sur le Xiaomi 11T la possibilité d’utiliser simultanément les caméras arrière et avant, puis de produire deux flux indépendants destinés à deux sources OBS distinctes.

- Caméra arrière : source principale, destination SRT envisagée sur le port UDP 9001.
- Caméra avant : source secondaire, destination SRT envisagée sur le port UDP 9002.
- Aucune composition vidéo dans le téléphone : la mise en page reste du ressort d’OBS.
- Si l’appareil ne permet pas l’utilisation simultanée, l’application doit conserver un fonctionnement à caméra unique.

Les ports sont ceux déjà prévus dans la configuration actuelle du projet Raspberry Pi. Le transport vidéo et les paramètres définitifs restent à valider par des essais.

## État du projet

**Étape 0 — fondations et vérification matérielle.**

Le premier écran de l’application vérifie la présence de la fonctionnalité Android de caméra simultanée et interroge CameraX pour connaître les combinaisons simultanées annoncées par l’appareil. Cette vérification ne constitue pas encore un essai d’enregistrement vidéo simultané.

## Environnement technique initial

- Kotlin et Android Studio.
- AndroidX CameraX 1.6.2.
- Application Android native, avec une architecture organisée par responsabilités.
- Niveau Android minimal initial : API 26 (à réévaluer selon les besoins réels).
- Compilation avec JDK 17.

Les versions sont centralisées dans le catalogue gradle/libs.versions.toml.

## Ouvrir le projet

1. Cloner ce dépôt.
2. Ouvrir le dossier racine dans Android Studio.
3. Laisser Gradle synchroniser les dépendances.
4. Installer l’application sur le Xiaomi 11T.
5. Accorder l’autorisation d’accès à la caméra et relever le résultat affiché.

Voir docs/PLAN_TEST_DOUBLE_CAMERA.md pour le protocole d’essai.

## Organisation

- app/ : application Android.
- docs/ : architecture, décisions et protocoles d’essai.
- gradle/ : catalogue centralisé des versions.

## Relation avec le projet Nomade

Ce dépôt est indépendant de Thalyn-/nomade, qui reste responsable de la régie Raspberry Pi, d’OBS et des caméras externes. Les deux applications partageront un protocole documenté, notamment pour la télémétrie MQTT.

## État des validations

Aucune compilation ni aucun essai sur le Xiaomi 11T n’a encore été exécuté depuis ce dépôt. Les résultats matériels devront être consignés avant de choisir définitivement l’architecture de transmission vidéo.
