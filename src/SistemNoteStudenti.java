import java.util.List;
import java.util.stream.Collectors;

class SistemNoteStudenti {
    private List<Student> studenti;

    public SistemNoteStudenti() {
        this.studenti = new java.util.ArrayList<>();
    }

    public void adaugaStudent(String nume) {
        studenti.add(new Student(nume));
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void noteazaStudent(String nume, double nota) {
        for (Student student : studenti) {
            if (student.getNume().equals(nume)) {
                student.setNota(nota);
                return;
            }
        }
    }

    public void stergeStudent(String nume) {
        studenti = studenti.stream().filter(student -> !student.getNume().equals(nume)).collect(Collectors.toList());
    }

    public double calculeazaMedie() {
        double sumaNote = studenti.stream().mapToDouble(Student::getNota).sum();
        return studenti.isEmpty() ? 0.0 : (sumaNote / studenti.size());
    }
}