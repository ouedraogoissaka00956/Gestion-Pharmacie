package pharmacie;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pharmacie.PrincipalePage; 

public class GererMedicament extends JFrame {

    private JTextField nomField;
    private JTextField prixField;
    private JTextField quantiteField;
    private JTable table;
    private DefaultTableModel model;

    public GererMedicament() {
        super("Gérer Médicament");

        // Configuration de la fenêtre principale
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Couleur de fond pour l'ensemble de la fenêtre
        getContentPane().setBackground(new Color(0, 153, 0));

        // Label en haut de la page avec la mention "Gestion des Médicaments"
        JLabel headerLabel = new JLabel("Gestion des Médicaments", SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 153, 0));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Prendre toute la largeur de la fenêtre pour le label
        add(headerLabel, gbc);

        // Champs pour entrer les détails du médicament
        JLabel nomLabel = new JLabel("Nom du Médicament:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nomLabel, gbc);

        nomField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nomField, gbc);

        JLabel prixLabel = new JLabel("Prix (€):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(prixLabel, gbc);

        prixField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(prixField, gbc);

        JLabel quantiteLabel = new JLabel("Quantité:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(quantiteLabel, gbc);

        quantiteField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(quantiteField, gbc);

        JButton ajouterButton = new JButton("Ajouter Médicament");
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(ajouterButton, gbc);

        model = new DefaultTableModel();
        model.addColumn("Nom");
        model.addColumn("Prix (€)");
        model.addColumn("Quantité");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        JButton mettreAJourButton = new JButton("Mettre à jour");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        add(mettreAJourButton, gbc);

        JButton supprimerButton = new JButton("Supprimer");
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(supprimerButton, gbc);

        JButton retourButton = new JButton("Retour");
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(retourButton, gbc);

        // Actions des boutons
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMedicament();
            }
        });

        mettreAJourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreAJourMedicament();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerMedicament();
            }
        });

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrincipalePage().setVisible(true); // Ouvre la page principale
                dispose(); // Ferme la fenêtre actuelle
            }
        });

        // Configuration finale de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500); // Ajustement de la taille de la fenêtre
        setVisible(true);
    }

    // Méthodes pour gérer les médicaments
    private void ajouterMedicament() {
        String nom = nomField.getText();
        String prix = prixField.getText();
        String quantite = quantiteField.getText();

        if (nom.isEmpty() || prix.isEmpty() || quantite.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            model.addRow(new Object[]{nom, prix, quantite});
            clearFields();
        }
    }

    private void mettreAJourMedicament() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            model.setValueAt(nomField.getText(), selectedRow, 0);
            model.setValueAt(prixField.getText(), selectedRow, 1);
            model.setValueAt(quantiteField.getText(), selectedRow, 2);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament à mettre à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerMedicament() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nomField.setText("");
        prixField.setText("");
        quantiteField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GererMedicament().setVisible(true); // Ouvre la fenêtre de gestion des médicaments
            }
        });
    }
}
