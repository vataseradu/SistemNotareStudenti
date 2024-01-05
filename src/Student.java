public class Student {
    private String nume;
    private double nota;

    public Student(String nume) {
        this.nume = nume;
        this.nota = 0.0;
    }

    public String getNume() {
        return nume;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return nume +
                ", nota=" + nota
                ;
    }
}