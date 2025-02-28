/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import business.SessionManager;
import business.UtilisateurEntrepriseBean;
import entities.Utilisateur;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import static jakarta.faces.application.FacesMessage.SEVERITY_ERROR;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("welcomeBean")
@RequestScoped
public class welcomeBean {
    private String nom;
    private String message;
    private String email;
    private String password;
    
    
    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private SessionManager sessionManager;

    // Getter pour 'nom'
    public String getNom() {
        return nom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    // Setter pour 'nom' (obligatoire pour que JSF puisse modifier la valeur)
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour 'message'
    public String getMessage() {
        return message;
    }
    public void setPassword(String password){
        this.password=password;
    }
     public String getPassword() {
        return password;
    }
    // Méthode pour afficher le message
    public void afficherMessage() {
        if (nom != null && !nom.trim().isEmpty()) {
            message = "Selamat datang, " + nom + "!";
        } else {
            message = "Veuillez entrer votre nom.";
        }
    }
    public String sAuthentifier(){
    Utilisateur utilisateur = utilisateurEntrepriseBean.authentifier(email,password);
    FacesContext context = FacesContext.getCurrentInstance();
    if(utilisateur != null){
        sessionManager.createSession("user",email);
        return "home?faces-redirect-true";
    }else {
        this.message="Email ou mot de passe incorrest.";
        context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null));
    }
        return null;
    } 
}