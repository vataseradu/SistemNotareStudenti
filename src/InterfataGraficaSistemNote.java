import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
class PanelCuImagineDeFundal extends JPanel {
    private Image imagineDeFundal;

    public PanelCuImagineDeFundal(String caleImagine) {
        imagineDeFundal = new ImageIcon(caleImagine).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagineDeFundal, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

public class InterfataGraficaSistemNote {
    private JFrame frame;
    private SistemNoteStudenti sistemNote;

    public InterfataGraficaSistemNote() {

        frame = new JFrame("Sistem de notare studenti");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        sistemNote = new SistemNoteStudenti();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagineDeFundal = new ImageIcon("src/background.jpg").getImage();
                g.drawImage(imagineDeFundal, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new FlowLayout());

        JList<Student> listaStudenti = new JList<>();
        DefaultListModel<Student> modelLista = new DefaultListModel<>();
        listaStudenti.setModel(modelLista);

        JScrollPane panouDerulant = new JScrollPane(listaStudenti);
        mainPanel.add(panouDerulant, BorderLayout.CENTER);

        JButton adaugaStudentButton = new JButton("Adaugă Student");

        adaugaStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeStudent = JOptionPane.showInputDialog(frame, "Introduceți numele studentului:");
                if (numeStudent != null && !numeStudent.trim().isEmpty()) {
                    sistemNote.adaugaStudent(numeStudent);
                    updateListaStudenti(modelLista, sistemNote.getStudenti());
                }
            }
        });

        JButton noteazaStudentButton = new JButton("Notează Student");
        noteazaStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student studentSelectat = listaStudenti.getSelectedValue();
                if (studentSelectat != null) {
                    String notaInput = JOptionPane.showInputDialog(frame, "Introduceți nota pentru " + studentSelectat.getNume() + ":");
                    try {
                        if (Double.parseDouble(notaInput) < 0 || Double.parseDouble(notaInput) > 10) {
                            throw new NotaInvalidaException();
                            }
                            double nota = Double.parseDouble(notaInput);
                            sistemNote.noteazaStudent(studentSelectat.getNume(), nota);
                            updateListaStudenti(modelLista, sistemNote.getStudenti());

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Notă invalidă. Introduceți o valoare numerică.");
                    }
                     /*catch (Exception ex){
                         JOptionPane.showMessageDialog(frame, "Notă invalidă.");
                     }*/
                    catch (NotaInvalidaException ex) {
                        JOptionPane.showMessageDialog(frame,"Introduceti o nota cuprinsa intre [1,10].");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selectați un student pentru notare.");
                }
            }
        });

        JButton stergeStudentButton = new JButton("Șterge Student");
        stergeStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student studentSelectat = listaStudenti.getSelectedValue();
                if (studentSelectat != null) {
                    sistemNote.stergeStudent(studentSelectat.getNume());
                    updateListaStudenti(modelLista, sistemNote.getStudenti());
                } else {
                    JOptionPane.showMessageDialog(frame, "Vă rugăm să selectați un student pentru ștergere.");
                }
            }
        });

        JButton afiseazaMedieButton = new JButton("Afișează Medie");
        afiseazaMedieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double medie = sistemNote.calculeazaMedie();
                JOptionPane.showMessageDialog(frame, "Media claselor este: " + medie);
            }
        });

        mainPanel.add(adaugaStudentButton, BorderLayout.NORTH);
        mainPanel.add(noteazaStudentButton, BorderLayout.SOUTH);
        mainPanel.add(stergeStudentButton, BorderLayout.WEST);
        mainPanel.add(afiseazaMedieButton, BorderLayout.EAST);

        adaugaStudentButton.setBackground(Color.GREEN);
        stergeStudentButton.setBackground(Color.red);
        afiseazaMedieButton.setBackground(Color.yellow);
        noteazaStudentButton.setBackground(Color.orange);


        frame.add(mainPanel);
        frame.setVisible(true);

    }

    private void updateListaStudenti(DefaultListModel<Student> modelLista, List<Student> studenti) {

        modelLista.clear();
        for (Student student : studenti) {
            modelLista.addElement(student);
        }

    }

    public static void main(String[] args) {
            new InterfataGraficaSistemNote();
    }
}
class NotaInvalidaException extends Exception {
    public NotaInvalidaException() {
        super("Nota introdusa nu este cuprinsa intre 1 si 10.");
    }
}
