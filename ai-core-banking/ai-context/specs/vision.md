# Vision Produit — AI Core Banking

## Résumé

AI Core Banking est une plateforme bancaire digitale complète, conçue comme support pédagogique pour un workshop intensif de 3 jours sur l'ingénierie logicielle assistée par intelligence artificielle.

## Objectifs Stratégiques

1. **Démontrer l'AI Engineering** : Montrer comment construire un système enterprise réel en utilisant des LLM locaux et des outils d'IA comme assistants de développement.
2. **Système Bancaire Réaliste** : Simuler une banque digitale complète avec gestion des clients, comptes, cartes, transactions, fraude, reporting et notifications.
3. **AI-Native Development** : L'IA agit uniquement comme un copilote — le développeur humain reste le décideur final.

## Utilisateurs Cibles

| Persona | Rôle | Besoins |
|---------|------|---------|
| **Opérateur Bancaire** | Gestion quotidienne via le backoffice Angular | CRUD clients, comptes, cartes, monitoring fraude |
| **Client Final** | Interaction via l'app mobile Flutter | Consultation solde, virements, gestion cartes |
| **Développeur Workshop** | Apprenant du workshop | Comprendre l'architecture, les patterns, l'usage de l'IA |

## Principes Directeurs

- **Spec-Driven** : Tout code est guidé par des spécifications documentées
- **AI-Assisted** : L'IA accélère le développement mais ne décide pas seule
- **Enterprise-Grade** : Architecture microservices, sécurité JWT, base de données relationnelle
- **Pédagogique** : Chaque choix technique doit être explicable et reproductible

## Non-Objectifs

- Ce n'est PAS un système de production bancaire réel
- Pas de conformité réglementaire réelle (PCI-DSS, etc.)
- Pas de trading ou de marché financier
- Pas de multi-devises complexe (simplification pédagogique)
