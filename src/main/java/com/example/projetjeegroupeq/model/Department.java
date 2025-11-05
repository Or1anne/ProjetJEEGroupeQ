package com.example.projetjeegroupeq.model;
import jakarta.persistence.*;


@Entity
@Table(name = "department")
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gère la génération et l'incrémentation automatiquement
