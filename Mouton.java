public class Mouton extends Animal {
    private double laineParJour;

    public Mouton(String id, double poids, int age, double laineParJour) {
        super(id, poids, age);
        this.laineParJour = laineParJour;
    }

    @Override
    public String crier() {
        return "Bêê";
    }

    @Override
    public double productionJournaliere() {
        return laineParJour; // grammes de laine/jour
    }
}