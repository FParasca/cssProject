package pt.ul.fc.css.urbanwheels.entities;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = true)
    private Integer timeInMaintenance;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private double custo;

    public Maintenance() {
    }

    public Maintenance(Admin admin,Bike bike, LocalDateTime date, String descricao, double custo) {
        this.admin = admin;
        this.bike = bike;
        this.date = date;
        this.descricao = descricao;
        this.custo = custo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Bike getBike() {
        return bike;
    }
    public void setBike(Bike bike) {
        this.bike = bike;
    }
}