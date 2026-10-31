# Plan de vérification — double caméra

## But

Vérifier progressivement si le Xiaomi 11T peut fournir deux flux vidéo indépendants, l’un depuis la caméra arrière et l’autre depuis la caméra avant, afin de les présenter comme deux sources distinctes dans OBS.

## Étape A — capacités déclarées

1. Installer l’application sur le Xiaomi 11T.
2. Accorder l’autorisation d’accès à la caméra.
3. Relever si Android annonce FEATURE_CAMERA_CONCURRENT.
4. Relever le nombre de combinaisons simultanées retournées par ProcessCameraProvider.availableConcurrentCameraInfos.

Un résultat positif à cette étape est un indice favorable, pas une preuve d’enregistrement vidéo simultané.

## Étape B — ouverture simultanée

- Ouvrir une caméra avant et une caméra arrière dans une même session CameraX concurrente.
- Afficher les deux aperçus indépendamment.
- Vérifier que chaque aperçu reste actif lorsque l’autre est sollicité.
- Vérifier le comportement après mise en veille, retour au premier plan et interruption par un appel ou une autre application.

## Étape C — enregistrement indépendant

- Ajouter un enregistrement vidéo distinct pour chaque caméra.
- Vérifier que les deux fichiers sont lisibles et qu’ils contiennent bien des images différentes.
- Mesurer la cadence réelle, les pertes d’images, la résolution, la température et la consommation.
- Tester plusieurs durées, d’abord courtes puis prolongées.

## Étape D — transmission

Seulement après validation de la capture locale :
- éprouver deux transports indépendants ;
- tester SRT en premier, sans exclure WebRTC ;
- raccorder les flux à deux sources OBS distinctes ;
- vérifier la stabilité sur réseau Wi-Fi puis sur le partage de connexion mobile.

## Critères de réussite

- Les deux caméras sont actives simultanément.
- Deux flux distincts sont produits, sans composition préalable dans le téléphone.
- La caméra arrière et la caméra avant restent identifiables et sélectionnables séparément dans OBS.
- La stabilité, la température et la consommation sont acceptables pour un usage prolongé.
- Un retour propre à une caméra unique est possible si la combinaison double échoue.

## Compte rendu à renseigner

| Mesure | Résultat |
|---|---|
| Modèle exact et variante du Xiaomi 11T | À relever |
| Version Android / MIUI ou HyperOS | À relever |
| FEATURE_CAMERA_CONCURRENT | À tester |
| Nombre de combinaisons CameraX | À tester |
| Résolution arrière / avant simultanée | À tester |
| Cadence arrière / avant | À tester |
| Durée du test | À tester |
| Température et consommation | À tester |
| Enregistrement simultané | À tester |
| Deux sources OBS indépendantes | À tester |

Ne pas conclure à la compatibilité du Xiaomi 11T avant d’avoir effectué les étapes B et C sur l’appareil réel.
