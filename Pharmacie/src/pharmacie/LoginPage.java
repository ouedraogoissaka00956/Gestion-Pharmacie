package pharmacie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import pharmacie.PrincipalePage;

public class LoginPage extends JFrame {

    public LoginPage() {
        super("Page de Connexion");

        // Configuration de la fenêtre principale
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Couleur de fond
        getContentPane().setBackground(new Color(0, 153, 0));

        // Label en haut
        JLabel headerLabel = new JLabel("Veuillez vous connecter", SwingConstants.CENTER);
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

        // Champ Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);
        
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Boutons
        JButton loginButton = new JButton("Se connecter");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(loginButton, gbc);

        JButton registerButton = new JButton("S'inscrire");
        gbc.gridx = 1;
        add(registerButton, gbc);

        // Actions des boutons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Vérification des identifiants dans la base de données
                if (checkCredentials(username, password)) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie !");
                    new PrincipalePage().setVisible(true); // Correction ici
                    dispose(); // Ferme la fenêtre actuelle
                } else {
                    JOptionPane.showMessageDialog(null, "Identifiant ou mot de passe incorrect !");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterPage().setVisible(true); // Ouvre la page d'inscription
                dispose(); // Ferme la fenêtre actuelle
            }
        });

        // Configuration finale de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setVisible(true);
    }

    private boolean checkCredentials(String username, String password) {
        boolean isValid = false;
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "SELECT * FROM utilisateurs WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            isValid = rs.next(); // Si un résultat est trouvé, les identifiants sont valides
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
        return isValid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}
