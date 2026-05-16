# TICKET-001

## Type

FEATURE

## Titre

Développement des applications Frontend : Backoffice Angular et Mobile Flutter

---

## Contexte

Dans le cadre du projet Core Banking AI-Native, il est nécessaire de développer les deux interfaces utilisateur principales du système :

* une application Backoffice en Angular pour les opérations internes de la banque
* une application Mobile en Flutter pour les clients finaux

Ces deux applications doivent consommer les API des microservices Spring Boot e-banking.

---

## Objectif

Fournir deux interfaces complètes, modernes et enterprise-grade permettant :

### Backoffice Angular

* gestion des clients
* gestion des comptes bancaires
* gestion des cartes bancaires
* gestion des transactions
* supervision de la fraude
* consultation des rapports
* contrôle du mode Idle
* consultation des audits

### Application Mobile Flutter

* authentification utilisateur
* consultation des comptes
* consultation du solde
* historique des transactions
* virements bancaires
* gestion des cartes (blocage / opposition)
* affichage carte bancaire virtuelle
* notifications utilisateur

---

## Contraintes techniques

### Angular Backoffice

* Angular latest stable
* Angular Material obligatoire
* architecture modulaire
* séparation components / services / models
* communication REST avec backend
* gestion d’état propre

### Flutter Mobile

* Flutter stable
* architecture clean (presentation / domain / data)
* UI mobile-first bancaire
* gestion des états (Bloc ou Riverpod)
* communication REST avec backend

---

## Contraintes métier

* respect du mode Idle (lecture seule si activé côté backend)
* gestion des erreurs métier renvoyées par les microservices
* sécurité via JWT
* gestion des rôles (ADMIN, BACKOFFICE, CUSTOMER)

---

## Dépendances

Ce ticket dépend de :

* vision.md
* architecture.md
* domain_model.md
* backend e-banking services
* API REST stable

---

## Livrables attendus

### Frontend Angular

* structure projet complète
* modules fonctionnels
* dashboard opérationnel
* services API
* gestion auth

### Mobile Flutter

* application fonctionnelle
* écrans principaux
* navigation complète
* intégration API
* gestion état

---

## Critères de validation

* interface fonctionnelle sans erreur critique
* communication API opérationnelle
* respect de l’architecture définie
* respect des règles UI/UX bancaires
* code maintenable et structuré

---

## Priorité

HIGH

---

## Statut

TODO

---

## Notes IA

Ce ticket doit être traité comme une tâche d’ingénierie logicielle enterprise.

L’IA doit assister la génération du frontend sans inventer de règles métier.

Elle doit respecter strictement :

* PRD.md
* architecture.md
* rules/
* domain_model.md

Aucune simplification non justifiée n’est autorisée.
