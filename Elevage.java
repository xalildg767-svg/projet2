import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Elevage {

    // Ajouter un animal dans la base de données
    public void ajouter(Animal a) {
        String sql = "INSERT INTO animaux (id, type, poids, age, production, vaccine) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, a.getId());
            pstmt.setString(2, a.getClass().getSimpleName()); // Vache, Poule ou Mouton
            pstmt.setDouble(3, a.poids);
            pstmt.setInt(4, a.age);
            pstmt.setDouble(5, a.productionJournaliere());
            pstmt.setBoolean(6, a.estVaccine());

            pstmt.executeUpdate();
            System.out.println("-> Animal " + a.getId() + " ajouté avec succès à la base de données.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'animal : " + e.getMessage());
        }
    }

    // Récupérer la liste de tous les animaux depuis la BDD (Reconstitution des objets Java)
    public List<Animal> getTousLesAnimaux() {
        List<Animal> animaux = new ArrayList<>();
        String sql = "SELECT * FROM animaux";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String type = rs.getString("type");
                double poids = rs.getDouble("poids");
                int age = rs.getInt("age");
                double prod = rs.getDouble("production");
                boolean vaccine = rs.getBoolean("vaccine");

                Animal a = null;
                switch (type) {
                    case "Vache":
                        a = new Vache(id, poids, age, prod);
                        break;
                    case "Poule":
                        a = new Poule(id, poids, age, prod);
                        break;
                    case "Mouton":
                        a = new Mouton(id, poids, age, prod);
                        break;
                }

                if (a != null) {
                    if (vaccine) a.vacciner();
                    animaux.add(a);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des animaux : " + e.getMessage());
        }

        return animaux;
    }

    // Calcul du bilan par espèce à partir de la BDD
    public void afficherProductionParEspece() {
        List<Animal> animaux = getTousLesAnimaux();
        double totalLait = 0, totalOeufs = 0, totalLaine = 0;

        for (Animal a : animaux) {
            if (a instanceof Vache) totalLait += a.productionJournaliere();
            else if (a instanceof Poule) totalOeufs += a.productionJournaliere();
            else if (a instanceof Mouton) totalLaine += a.productionJournaliere();
        }

        System.out.println("\n--- Bilan de Production Journalière (depuis BDD) ---");
        System.out.printf("- Lait (Vaches)   : %.2f L%n", totalLait);
        System.out.printf("- Œufs (Poules)   : %.0f unités%n", totalOeufs);
        System.out.printf("- Laine (Moutons) : %.2f g%n", totalLaine);
    }

    // Lister les animaux non vaccinés depuis la BDD
    public void listerNonVaccines() {
        System.out.println("\n--- Liste des Animaux Non Vaccinés ---");
        String sql = "SELECT * FROM animaux WHERE vaccine = FALSE";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean aucun = true;
            while (rs.next()) {
                aucun = false;
                System.out.printf("[%s] %s | Production: %.1f | Vacciné: false%n",
                        rs.getString("type"),
                        rs.getString("id"),
                        rs.getDouble("production"));
            }
            if (aucun) {
                System.out.println("Tous les animaux sont vaccinés !");
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des non-vaccinés : " + e.getMessage());
        }
    }

    // Mettre à jour l'état de tous les animaux dans MySQL
    public void vaccinerTous() {
        String sql = "UPDATE animaux SET vaccine = TRUE";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            int rows = stmt.executeUpdate(sql);
            System.out.println("\n>> Campagne de vaccination effectuée : " + rows + " animaux mis à jour dans la BDD.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la vaccination : " + e.getMessage());
        }
    }
}