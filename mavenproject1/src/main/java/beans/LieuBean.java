package beans;

import business.LieuEntrepriseBean;
import entities.Lieu;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

@Named("lieuBean")
@SessionScoped
public class LieuBean implements Serializable {
    private int idLieu = 0;
    private String nomLieu;
    private String description;
    private double latitude;
    private double longitude;
    private String weatherMessage;
    private int selectedLieu;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    // Récupérer la liste des lieux depuis la base de données
    public List<Lieu> getListeLieux() {
        return lieuEntrepriseBean.listerTousLesLieux();
    }

    // Ajouter ou Modifier un lieu
    public void enregistrerLieu() {
        if (idLieu == 0) {
            lieuEntrepriseBean.ajouterLieuEntreprise(nomLieu, description, latitude, longitude);
        } else {
            lieuEntrepriseBean.modifierLieuEntreprise(idLieu, nomLieu, description, latitude, longitude);
        }
        resetForm();
    }

    // Supprimer un lieu
    public void supprimerLieu(int id) {
        lieuEntrepriseBean.supprimerLieuEntreprise(id);
    }

    // Préparer la modification d'un lieu
    public void preparerModification(Lieu lieu) {
        this.idLieu = lieu.getId();
        this.nomLieu = lieu.getNomLieu();
        this.description = lieu.getDescription();
        this.latitude = lieu.getLatitude();
        this.longitude = lieu.getLongitude();
    }

    // Réinitialiser le formulaire
    private void resetForm() {
        this.idLieu = 0;
        this.nomLieu = "";
        this.description = "";
        this.latitude = 0;
        this.longitude = 0;
        this.weatherMessage = "";
    }
    
    public void fetchWeatherMessage(Lieu l) {
        if (l != null) {
            String serviceURL = "http://127.0.0.1:8080/j-weater/webapi/JarkartaWeather?latitude=" + l.getLatitude() + "&longitude=" + l.getLongitude();
            Client client = ClientBuilder.newClient();
            String response = client.target(serviceURL)
                    .request(MediaType.TEXT_PLAIN)
                    .get(String.class);
            // Enregistrement du message météo dans la variable weatherMessage
            this.weatherMessage = response;
        }
    }

    public void updateWeatherMessage(AjaxBehaviorEvent event) {
        Lieu lieu = lieuEntrepriseBean.trouverLieuParId(selectedLieu);
        this.fetchWeatherMessage(lieu);
    }

    // Getters et Setters
    public int getIdLieu() { return idLieu; }
    public void setIdLieu(int idLieu) { this.idLieu = idLieu; }

    public String getNomLieu() { return nomLieu; }
    public void setNomLieu(String nomLieu) { this.nomLieu = nomLieu; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getWeatherMessage() { return weatherMessage; }
    public void setWeatherMessage(String weatherMessage) { this.weatherMessage = weatherMessage; }

    public int getSelectedLieu() { return selectedLieu; }
    public void setSelectedLieu(int selectedLieu) { this.selectedLieu = selectedLieu; }
}
