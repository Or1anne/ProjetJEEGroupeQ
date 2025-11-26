package com.example.projetjeegroupeq.model;

public enum ProjectStatus {
    WORKED_ON,
    FINISHED,
    CANCELLED;

    public String getTranslation() {
        return switch (this.name()) {
            case "WORKED_ON" -> "En cours";
            case "FINISHED" -> "Terminé";
            case "CANCELLED" -> "Annulé";
            default -> "Inconnu";
        };
    }
}
