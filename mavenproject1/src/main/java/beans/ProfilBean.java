import business.UtilisateurEntrepriseBean;
import entities.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.mindrot.jbcrypt.BCrypt;

@Named(value = "profilBean")
@RequestScoped
public class ProfilBean {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String description;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    private Utilisateur utilisateur;

    public ProfilBean() {
    }

    @PostConstruct
    public void chargerUtilisateurConnecte() {
        // Simule l'email de l'utilisateur connecté (remplace par la session réelle)
        String emailUtilisateur = "user@example.com"; // À remplacer par la session utilisateur

        utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(emailUtilisateur);

        if (utilisateur != null) {
            this.username = utilisateur.getUsername();
            this.email = utilisateur.getEmail();
            this.description = utilisateur.getDescription();
        }
    }

    public void modifierUtilisateur() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (utilisateur != null) {
            utilisateur.setUsername(username);
            utilisateur.setDescription(description);

            // Mise à jour du mot de passe si renseigné
            if (password != null && !password.isEmpty()) {
                if (!password.equals(confirmPassword)) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Les mots de passe ne correspondent pas", null));
                    return;
                }
                utilisateur.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            }

            utilisateurEntrepriseBean.mettreAJourUtilisateur(utilisateur);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Profil mis à jour avec succès", null));
        }
    }
    public String getEmail() {
        // Récupérer l'email de la session ou de la base de données
        if (email == null) {
            // Exemple: récupérer l'email de la session de l'utilisateur connecté
            email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userEmail");
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // Getters et Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }


    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
