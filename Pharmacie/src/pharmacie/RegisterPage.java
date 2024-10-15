package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterPage extends JFrame {

    public RegisterPage() {
        super("Page d'Inscription");

        // Configuration de la fenêtre principale
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Couleur de fond
        getContentPane().setBackground(new Color(0, 153, 0));

        // Label en haut
        JLabel headerLabel = new JLabel("Inscription", SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Prendre toute la largeur
        add(headerLabel, gbc);

        // Champ Nom d'utilisateur
        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);
        
        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Champ Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);
        
        JTextField emailField = new JTextField(15);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Champ Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);
        
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Champ Confirmation Mot de passe
        JLabel confirmPasswordLabel = new JLabel("Confirmation Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(confirmPasswordLabel, gbc);
        
        JPasswordField confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        // Boutons
        JButton registerButton = new JButton("S'inscrire");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(registerButton, gbc);

        JButton loginButton = new JButton("Se connecter");
        gbc.gridx = 1;
        add(loginButton, gbc);

        // Actions des boutons
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Validation
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis !");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas !");
                    return;
                }

                // Logique d'inscription ici
                try {
                    // Connexion à la base de données
                    Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pharmacie", "postgres", "#posgrer@2024");
                    
                    // Préparation de la requête d'insertion
                    String sql = "INSERT INTO utilisateurs (username, email, password) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, username);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password); // Envisagez de hacher le mot de passe avant de l'enregistrer

                    // Exécution de la requête
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inscription réussie !");
                    
                    new LoginPage().setVisible(true); // Redirige vers la page de connexion
                    dispose(); // Ferme la fenêtre actuelle

                    // Ferme la connexion
                    pstmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'inscription : " + ex.getMessage());
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true); // Ouvre la page de connexion
                dispose(); // Ferme la fenêtre actuelle
            }
        });

        // Configuration finale de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterPage());
    }
}
