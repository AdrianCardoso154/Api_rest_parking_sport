package com.api.parkingcontrol.modal;

//marcações da jpa para o banco
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PARKING_SPOT")
public class Parking_Spot_Modal implements Serializable {
    private static final long serialVersionUID = 1L;//converções que serão feitas para o banco

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 10)
    private String parkiingSpotNumber ;
    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar;//placa do carro
    @Column(nullable = false,length = 70)
    private String brandCar;//marca do carro
    @Column(nullable = false,length = 70)
    private String modelCar;
    @Column(nullable = false,length = 70)
    private String colorCar;
    @Column(nullable = false)
    private LocalDateTime regestrationDate;
    @Column(nullable = false, length = 130)
    private String responsibleName;
    @Column(nullable = false, length = 30)
    private String apartament;
    @Column(nullable = false, length = 30)
    private String block;//bloco ou torre

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParkiingSpotNumber() {
        return parkiingSpotNumber;
    }

    public void setParkiingSpotNumber(String parkiingSpotNumber) {
        this.parkiingSpotNumber = parkiingSpotNumber;
    }

    public String getLicensePlateCar() {
        return licensePlateCar;
    }

    public void setLicensePlateCar(String licensePlateCar) {
        this.licensePlateCar = licensePlateCar;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public LocalDateTime getRegestrationDate() {
        return regestrationDate;
    }

    public void setRegestrationDate(LocalDateTime regestrationDate) {
        this.regestrationDate = regestrationDate;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
