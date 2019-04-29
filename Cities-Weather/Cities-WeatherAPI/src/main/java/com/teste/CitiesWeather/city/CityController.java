/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teste.CitiesWeather.city;

/**
 *
 * @author Piercarlo
 */
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    CityService cityService; //Service which will do all data retrieval/manipulation work

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public ResponseEntity<?> listCities() {
        List<City> cities = (List<City>) cityService.findAll();
        if (!cities.isEmpty()) {
            return new ResponseEntity<>(cities, HttpStatus.OK);
        }
        return new ResponseEntity<>("No cities found", HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/cities", method = RequestMethod.POST)
    public ResponseEntity<?> newCity(@RequestBody City city) {
        RestTemplate restTemplate = new RestTemplate();
        City cityCheck;
        try {
            cityCheck = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=" + city.getName()
                    + "," + city.getCountry() + "&APPID=eb8b1a9405e659b2ffc78f0a520b1a46&units=metric&lang=pt", City.class);
            if (cityCheck != null) {
                cityCheck.setCountry(city.getCountry());
                List<City> cities = (List<City>) cityService.findAll();
                if (cities != null) {
                    for (City cityIt : cities) {
                        System.out.println(cityIt.getName());
                        if ((cityIt.getName() == null ? cityCheck.getName() == null : cityIt.getName().equals(cityCheck.getName()))
                                && (cityIt.getCountry() == null ? cityCheck.getCountry() == null : cityIt.getCountry().equals(cityCheck.getCountry()))) {
                            return new ResponseEntity<>("Unable to create. A product with id " + city.getId() + " already exist.\")", HttpStatus.CONFLICT);
                        }
                    }
                } else {
                    return new ResponseEntity<>("Unable to create. A product with id " + city.getId() + " already exist.\")", HttpStatus.CONFLICT);
                }
            }
        } catch (RestClientException e) {
            return new ResponseEntity<>("Unable to create. City with name " + city.getName() + " is invalid.\")", HttpStatus.CONFLICT);
        }
        cityService.save(cityCheck);
        return new ResponseEntity<>("Product created", HttpStatus.CREATED);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/forecast/{country}/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> forecast(@PathVariable("country") String country, @PathVariable("name") String name) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + name + "," + country + "&cnt=5&APPID=eb8b1a9405e659b2ffc78f0a520b1a46&units=metric&lang=pt", String.class);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (RestClientException e) {
            return new ResponseEntity<>("No cities found", HttpStatus.NOT_FOUND);
        } catch (ParseException ex) {
            Logger.getLogger(CityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>("No cities found", HttpStatus.NOT_FOUND);
    }
}
