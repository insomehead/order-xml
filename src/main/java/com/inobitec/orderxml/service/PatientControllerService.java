package com.inobitec.orderxml.service;

import com.inobitec.orderxml.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PatientControllerService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL = "http://localhost:8081/patient";
    private static final String BIRTHDAY = "birthday";
    private static final String FIRST_NAME = "firstName";
    private static final String MID_NAME = "midName";
    private static final String LAST_NAME = "lastName";

    public Patient getPatientById(Integer id) throws URISyntaxException {
        String baseUrl = BASE_URL + "/" + id.toString();
        URI uri = new URI(baseUrl);
        return restTemplate.getForObject(uri, Patient.class);
    }

    public Patient getPatientByBirthdayAndFullName(LocalDate birthday,
                                                   String firstName,
                                                   String midName,
                                                   String lastName) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam(BIRTHDAY, birthday)
                .queryParam(FIRST_NAME, firstName)
                .queryParam(MID_NAME, midName)
                .queryParam(LAST_NAME, lastName);
        return restTemplate.getForObject(uriBuilder.build().toUri(), Patient.class);
    }
}
