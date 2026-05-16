# TICKET-003

## Type
FEATURE

## Titre
Finalisation des applications Frontend (Angular & Flutter)

---

## Contexte
Les socles des applications frontend (Backoffice Angular et Mobile Flutter) ont été générés. Il faut désormais implémenter les fonctionnalités métiers et les connecter aux API backend existantes.

---

## Objectif
Finaliser le MVP (Minimum Viable Product) pour la démo AI Core Banking.

### Backoffice Angular
- Implémenter les services HTTP (Customer, Account, Transaction, Fraud)
- Écran : Liste des clients et création
- Écran : Liste des comptes
- Écran : Liste des transactions (Transferts)
- Écran : Supervision des fraudes (Résolution)
- Toggle pour le mode Idle

### Application Mobile Flutter
- Implémenter les Repositories et UseCases
- Écran : Authentification (Mock JWT / bypass)
- Écran : Tableau de bord (Solde total, dernières transactions)
- Écran : Virement (Transfer)
- Écran : Liste des cartes bancaires

---

## Contraintes
- Angular : Standalone components, HttpClient, RxJS Signals
- Flutter : Clean Architecture, Riverpod, Dio
- Gestion des erreurs : Afficher les messages d'erreur renvoyés par l'API (notamment `IdleModeException`)

---

## Dépendances
- TICKET-002 (Scaffolding)
- API Backend (Services Customer, Account, Transaction, Fraud)

---

## Statut
ACTIVE
