package com.example.projetjeegroupeq.model;

public enum Grade {
    EXECUTIVE_MANAGEMENT("EXECUTIVE_MANAGEMENT", "Direction générale"),
    MIDDLE_MANAGEMENT("MIDDLE_MANAGEMENT", "Manager intermédiaire"),
    SKILLED_EMPLOYEES("SKILLED_EMPLOYEES", "Employé qualifié"),
    EMPLOYEES("EMPLOYEES", "Employé"),
    INTERNS_APPRENTICES("INTERNS_APPRENTICES", "Stagiaire ou Apprenti");

    private final String dbValue; // La valeur exacte pour la BDD
    private final String label;   // La valeur jolie pour l'affichage (optionnel)

    Grade(String dbValue, String label) {
        this.dbValue = dbValue;
        this.label = label;
    }

    public String getDbValue() {
        return dbValue;
    }

    public String getLabel() {
        return label;
    }

    public static String getLabelFromDbValue(String dbValue) {
        if (dbValue == null) return "";

        // On parcourt tous les grades pour trouver celui qui correspond
        for (Grade g : values()) {
            if (g.getDbValue().equalsIgnoreCase(dbValue)) {
                return g.getLabel(); // On retourne le texte en français
            }
        }
        return dbValue; // Si pas trouvé (ex: ancienne donnée), on retourne l'anglais par défaut
    }
}