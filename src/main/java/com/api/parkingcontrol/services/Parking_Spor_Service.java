package com.api.parkingcontrol.services;

import com.api.parkingcontrol.Repository.Parking_Spot_Repository;
import com.api.parkingcontrol.modal.Parking_Spot_Modal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Parking_Spor_Service {

    //determinados momentos o SPRING vai ter que injetar a dependencia do repository(ponto de injeção)//@AutoWired tbm faz a msm coisa que a injeção por contrutor
    final Parking_Spot_Repository parking_Spot_Repository;

    public Parking_Spor_Service(Parking_Spot_Repository parking_Spot_Repository) {
        this.parking_Spot_Repository = parking_Spot_Repository;
    }
   @Transactional//garante que se algo der errado na hora de se fazer operações com o banco tudo volte ao normal para nao ter dados quebrados
    public Parking_Spot_Modal save(Parking_Spot_Modal parkingSpotModel) {
        return parking_Spot_Repository.save(parkingSpotModel);//metodo save e do jpa(ja pronto pra uso)
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parking_Spot_Repository.existsByLicensePlateCar(licensePlateCar);//verificar a placa do carro se está no estacionamento
    }

    public boolean existsByParkiingSpotNumber(String parkiingSpotNumber) {
        return parking_Spot_Repository.existsByParkiingSpotNumber(parkiingSpotNumber);
    }

    public boolean existsByApartamentAndBlock(String apartament, String block) {
        return parking_Spot_Repository.existsByApartamentAndBlock(apartament, block);
    }

    public Page<Parking_Spot_Modal> findAll(Pageable pageable) {
        return parking_Spot_Repository.findAll(pageable);//listagem do jpa(metoddo ja pronto)
    }

    public Optional<Parking_Spot_Modal> findByid(UUID id) {
        return parking_Spot_Repository.findById(id);//jpa metodo de listagem via o id;
    }
    @Transactional //trasações de dados massivos
    public void delete(Parking_Spot_Modal parking_spot_modal) {
        parking_Spot_Repository.delete(parking_spot_modal);
    }
}
