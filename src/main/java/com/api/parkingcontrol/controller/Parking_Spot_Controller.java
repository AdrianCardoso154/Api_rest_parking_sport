package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.Parking_Spot_Dto;
import com.api.parkingcontrol.modal.Parking_Spot_Modal;
import com.api.parkingcontrol.services.Parking_Spor_Service;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)//para ser acessado de qualquer fonte
@RequestMapping("/parking_spot")//uri a nivel de classe(pesquisar)(url do postman para testes
public class Parking_Spot_Controller {//injeção de dependencia vai contrutor
    final Parking_Spor_Service parking_spor_service;
        public Parking_Spot_Controller(Parking_Spor_Service parking_spor_service){
            this.parking_spor_service = parking_spor_service;
        }

        @PostMapping
        public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid Parking_Spot_Dto parking_spot_dto){
            //validações para saber se a vaga esta sendo usada
            if(parking_spor_service.existsByLicensePlateCar(parking_spot_dto.getLicensePlateCar())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Placa de carro já em uso");
            }if(parking_spor_service.existsByParkiingSpotNumber(parking_spot_dto.getParkiingSpotNumber())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: vaga de estacionamento esta em uso");
            }if(parking_spor_service.existsByApartamentAndBlock(parking_spot_dto.getApartament(), parking_spot_dto.getBlock())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: ja tem um apartemento/bloco registrado nessa vaga");
            }
            var parkingSpotModel = new Parking_Spot_Modal();//dentro de um scopo fechado pode-se usar o "var" que ele entende que e uma instancia
            BeanUtils.copyProperties(parking_spot_dto, parkingSpotModel);//metodo para converter(primeiro o que vai se convertido, no que ele vai ser convertido)
            parkingSpotModel.setRegestrationDate(LocalDateTime.now(ZoneId.of("UTC")));//setando a data em utc
            return ResponseEntity.status(HttpStatus.CREATED).body(parking_spor_service.save(parkingSpotModel));
        }
        @GetMapping
        public ResponseEntity<Page<Parking_Spot_Modal>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
            //metodo para fazer o select no banco
            return ResponseEntity.status(HttpStatus.OK).body(parking_spor_service.findAll(pageable));
        }

        @GetMapping("/{id}")
        public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id")UUID id){//retorna apenas um passando o id
            Optional<Parking_Spot_Modal> parking_spot_modalOptional = parking_spor_service.findByid(id);
            if(!parking_spot_modalOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada ");
            }
            return ResponseEntity.status(HttpStatus.OK).body(parking_spot_modalOptional.get());
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id")UUID id){
            Optional<Parking_Spot_Modal> parking_spot_modalOptional = parking_spor_service.findByid(id);
            if(!parking_spot_modalOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada ");
            }
            parking_spor_service.delete(parking_spot_modalOptional.get());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("vaga deletada com sucesso");

        }

         @PutMapping("/{id}")
         public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id")UUID id,
                                                         @RequestBody @Valid Parking_Spot_Dto parking_spot_dto){
            Optional<Parking_Spot_Modal> parking_spot_modalOptional = parking_spor_service.findByid(id);
            if(!parking_spot_modalOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada ");
            }
             /*
                //primeiro modo de fazer
                   var parkingSpotModel = parking_spot_modalOptional.get();
            //campos que vieram da alteração
            parkingSpotModel.setParkiingSpotNumber(parking_spot_dto.getParkiingSpotNumber());
            parkingSpotModel.setLicensePlateCar(parking_spot_dto.getLicensePlateCar());
            parkingSpotModel.setBrandCar(parking_spot_dto.getBrandCar());
            parkingSpotModel.setModelCar(parking_spot_dto.getModelCar());
            parkingSpotModel.setColorCar(parking_spot_dto.getColorCar());
            parkingSpotModel.setResponsibleName(parking_spot_dto.getResponsibleName());
            parkingSpotModel.setApartament(parking_spot_dto.getApartament());
            parkingSpotModel.setBlock(parking_spot_dto.getBlock());

             */
             //Outra formar de fazer
            var parkingSpotModel = new Parking_Spot_Modal();
            BeanUtils.copyProperties(parking_spot_dto, parkingSpotModel);
            parkingSpotModel.setId(parking_spot_modalOptional.get().getId());
            parkingSpotModel.setRegestrationDate(parking_spot_modalOptional.get().getRegestrationDate());

            return ResponseEntity.status(HttpStatus.OK).body(parking_spor_service.save(parkingSpotModel));
    }



}
