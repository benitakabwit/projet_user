package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nom_lieu")
    private String nomLieu;
    private String description;
    private double longitude;
    private double latitude;

    // Constructeurs
    public Lieu() {}

    public Lieu(String nomLieu, String description, double longitude, double latitude) {
        this.nomLieu = nomLieu;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomLieu() { return nomLieu; }
    public void setNomLieu(String nomLieu) {this.nomLieu = nomLieu; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

   
}