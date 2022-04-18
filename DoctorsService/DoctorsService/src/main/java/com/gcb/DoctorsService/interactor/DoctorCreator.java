package com.gcb.DoctorsService.interactor;

import com.gcb.DoctorsService.entity.Doctor;
import com.gcb.DoctorsService.entity.DoctorSpecialty;
import com.gcb.DoctorsService.entity.Specialty;
import com.gcb.DoctorsService.model.DoctorRequest;
import com.gcb.DoctorsService.model.Feed;
import com.gcb.DoctorsService.repository.DoctorRepository;
import com.gcb.DoctorsService.repository.DoctorSpecialtyRepository;
import com.gcb.DoctorsService.repository.SpecialtyRepository;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DoctorCreator {

    @Autowired
    private DoctorRepository repository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Object> createDoctorUseCase(DoctorRequest requestBody) throws Exception,GenericJDBCException {

        var specialtiesId = requestBody.getSpecialtiesId();

        requestBody.setCreatedAt(new Date().toString());

        var doctor = DoctorCreator.doctorConverter(requestBody);

        try {
            
            repository.save(doctor);
            
        } catch (GenericJDBCException e) {
            
            throw new Exception(e.getMessage());
            
        }catch(Exception e){
            
            throw new Exception(e.getMessage());
        }

        var specialtyIterable = specialtyRepository.findAll();

        List<Specialty> specialtyList = new ArrayList<>();
        specialtyIterable.forEach(specialtyList::add);

        List<Specialty> resultSpecialties = new ArrayList<>();
        for (Specialty item : specialtyList) {
            for (Long selectedItem : specialtiesId) {
                if (item.getId().equals(selectedItem)) {
                    Specialty specialty = new Specialty();
                    specialty.setId(item.getId());
                    specialty.setSpecialtyName(item.getSpecialtyName());
                    resultSpecialties.add(specialty);
                }
            }
        }

        List<DoctorSpecialty> idList = new ArrayList<>();

        var index = 0L;
        for (Specialty item : resultSpecialties) {
            DoctorSpecialty ds = new DoctorSpecialty();
            ds.setId(index);
            ds.setDoctor(doctor);
            ds.setSpecialty(item);
            idList.add(ds);
            index++;
        }

        for (var item : idList) {
            doctorSpecialtyRepository.save(item);
        }

        return null;

    }

    public static Doctor doctorConverter(DoctorRequest request) {

        String crm = request.getCrm();
        String name = request.getName();
        String landline = request.getLandline();
        String mobilePhone = request.getMobilePhone();
        String postalCode = request.getPostalCode();
        String complement = request.getAddressComplement();
        String street = request.getStreet();
        String neighborhood = request.getNeighborhood();
        String city = request.getCity();
        String uf = request.getUf();
        String status = request.getStatus();
        String createdAt = request.getCreatedAt();

        return new Doctor(
                crm,
                name,
                landline,
                mobilePhone,
                postalCode,
                complement,
                street,
                neighborhood,
                city,
                uf,
                status,""
        );
    }

    public ResponseEntity<Feed> generateFeed(String crm, String action) throws URISyntaxException, Exception {
        String baseUrl = "http://localhost:8084/feed/create";

        Feed feed = new Feed();
        feed.setRequestId(crm);
        feed.setRequestAction(action);
        feed.setCreatedAt(new Date().toString());
        feed.setUpdatedAt(new Date().toString());
        feed.setStatus("Queued");

        ResponseEntity<Feed> response = new RestTemplate().exchange(baseUrl,
                HttpMethod.POST,
                new HttpEntity<>(feed),
                Feed.class);

        var feedResponse = response.getBody();

        return new ResponseEntity<>(feed, HttpStatus.CREATED);

    }

}
