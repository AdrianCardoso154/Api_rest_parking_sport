package com.api.parkingcontrol.Repository;

import com.api.parkingcontrol.modal.Parking_Spot_Modal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
//passar o modal e o identificador(uuid)
@Repository
public interface Parking_Spot_Repository extends JpaRepository<Parking_Spot_Modal, UUID> {
    boolean existsByLicensePlateCar(String LicensePlateCar);
    boolean existsByParkiingSpotNumber(String spotNumber);
    boolean existsByApartamentAndBlock(String apartament, String block);

}
