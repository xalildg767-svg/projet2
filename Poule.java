public class Poule extends Animal {
    private double oeufsParJour; // type double pour la cohérence avec Animal

    public Poule(String id, double poids, int age, double oeufsParJour) {
        super(id, poids, age);
        this.oeufsParJour = oeufsParJour;
    }

    @Override
    public String crier() {
        return "Cot cot";
    }

    @Override
    public double productionJournaliere() {
        return oeufsParJour; // œufs/jour
    }
}