package org.example.restAPI;

import org.example.Domains.*;
import org.example.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiGatewayController {

    @Autowired
    HotelService hotelService;

    /**
     * Broker Search
     * @param cityID
     * @param checkInDate
     * @param checkOutDate
     * @param numberOfAdults
     * @param numberOfChildren
     * @param hotelID
     * @return
     */
    @GetMapping(value = "/query")
    public List<ResponseHotel> query(@RequestParam("cityID") String cityID,
                            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                            @RequestParam("numberOfAdults") Integer numberOfAdults,
                            @RequestParam("numberOfChildren") Integer numberOfChildren,
                            @RequestParam("hotelID") Optional<String> hotelID){


        if(!hotelID.isEmpty())
        {
            String hotelIDvalue = hotelID.get();
            return hotelService.processHotelsByID(checkInDate, checkOutDate, hotelIDvalue, numberOfAdults, numberOfChildren);

        }else{
            return hotelService.processHotelByCity(checkInDate, checkOutDate, cityID, numberOfAdults, numberOfChildren);
        }

    }

    /**
     * Optional methods
     */
    @CacheEvict(cacheNames = "HotelByID", allEntries = true)
    @GetMapping("/cacheclean")
    public void clean(){ }

    @CacheEvict(cacheNames = "HotelsByCity", allEntries = true)
    @GetMapping("/cachehotelclean")
    public void cleanHotels(){ }

}
