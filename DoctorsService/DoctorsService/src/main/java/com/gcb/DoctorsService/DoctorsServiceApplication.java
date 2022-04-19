package com.gcb.DoctorsService;

import com.gcb.DoctorsService.entity.Specialty;
import com.gcb.DoctorsService.repository.SpecialtyRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DoctorsServiceApplication implements CommandLineRunner {

    @Autowired
    private SpecialtyRepository specialtyRepositoy;

    public static void main(String[] args) {
        SpringApplication.run(DoctorsServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
        
        List<Specialty> specialties = getSpecialties();
        
        for (var specialty : specialties) {
            Specialty specialtyPersist = specialty;
            specialtyRepositoy.save(specialtyPersist);
            
        }
    }

    private List<Specialty> getSpecialties() {
        Specialty alergologia = new Specialty("Alergologia");
        Specialty angiologia = new Specialty("Angiologia");
        Specialty bucoMaxilo = new Specialty("Buco maxilo");
        Specialty cardiologiaClinica = new Specialty("Cardiologia clínca");
        Specialty cardiologiaInfantil = new Specialty("Cardiologia infantil");
        Specialty cirurgiaCabecaEPescoco = new Specialty("Cirurgia cabeça e pescoço");
        Specialty cirurgiaCardiaca = new Specialty("Cirurgia cardíaca");
        Specialty cirurgiaTorax = new Specialty("Cirurgia de tórax");
        
        List<Specialty> specialties = new ArrayList<>();
        
        specialties.add(alergologia);
        specialties.add(angiologia);
        specialties.add(bucoMaxilo);
        specialties.add(cardiologiaClinica);
        specialties.add(cardiologiaInfantil);
        specialties.add(cirurgiaCabecaEPescoco);
        specialties.add(cirurgiaCardiaca);
        specialties.add(cirurgiaTorax);
        
        return specialties;
    }
}
