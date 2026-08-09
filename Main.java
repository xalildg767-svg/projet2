public class Main {
    public static void main(String[] args) {
        Elevage monElevage = new Elevage();

        // 1. Insertion de données de test dans la BDD
        System.out.println("--- Insertion des données ---");
        monElevage.ajouter(new Vache("V-101", 650.0, 36, 28.0));
        monElevage.ajouter(new Vache("V-102", 590.0, 24, 22.5));
        monElevage.ajouter(new Poule("P-101", 2.0, 12, 1.0));
        monElevage.ajouter(new Poule("P-102", 1.9, 8, 1.0));
        monElevage.ajouter(new Mouton("M-101", 80.0, 15, 200.0));

        // 2. Consultation des bilans
        monElevage.afficherProductionParEspece();

        // 3. Vérification des non-vaccinés avant campagne
        monElevage.listerNonVaccines();

        // 4. Vaccination de tout le monde dans MySQL
        monElevage.vaccinerTous();

        // 5. Vérification des non-vaccinés après campagne
        monElevage.listerNonVaccines();
    }
}