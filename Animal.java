public abstract class Animal implements Vaccinable {
    protected String id;
    protected double poids; // en kg
    protected int age;       // en mois
    protected boolean vaccine = false;

    public Animal(String id, double poids, int age) {
        this.id = id;
        this.poids = poids;
        this.age = age;
    }

    public abstract String crier();
    public abstract double productionJournaliere();

    @Override
    public boolean estVaccine() {
        return vaccine;
    }

    @Override
    public void vacciner() {
        this.vaccine = true;
    }

    public void afficher() {
        System.out.printf("[%s] %s | Cri: %-8s | Production: %6.1f | Vacciné: %b%n",
                this.getClass().getSimpleName(), id, crier(), productionJournaliere(), vaccine);
    }

    public String getId() {
        return id;
    }
}