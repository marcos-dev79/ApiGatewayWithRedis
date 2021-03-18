package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Domains.Hotel;
import org.example.Domains.ResponseHotel;
import org.example.services.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelService hotelService;

    String cityRequestObject = TestUtils.readResponseFromFile("querycity.json");
    String hotelRequestObject = TestUtils.readResponseFromFile("queryhotel.json");

    private ObjectMapper mapper = new ObjectMapper();


    private String cityID = "1032";
    private String hotelID = "2";
    private LocalDate checkInDate = LocalDate.parse("2021-10-02");
    private LocalDate checkOutDate = LocalDate.parse("2021-10-10");
    private Integer numberOfAdults = 2;
    private Integer numberOfChildren = 1;

    @Test
    @DisplayName("Check If City API Result is Working")
    void checkIfCityAPIResultIsWorking() throws JsonProcessingException {
        ResponseHotel[] hotels = mapper.readValue(cityRequestObject, ResponseHotel[].class);
        List<ResponseHotel> responseHotels = Arrays.asList(hotels);

        doAnswer(invocation -> {
            return responseHotels;
        }).when(hotelService).processHotelByCity(checkInDate, checkOutDate, cityID, numberOfAdults, numberOfChildren);

        List<ResponseHotel> receivedData = hotelService.processHotelByCity(checkInDate, checkOutDate, cityID, numberOfAdults, numberOfChildren);
        assertEquals(receivedData, responseHotels);
    }

    @Test
    @DisplayName("Check If Hotel API Result is Working")
    void checkIfHotelAPIResultIsWorking() throws JsonProcessingException {
        ResponseHotel[] hotels = mapper.readValue(hotelRequestObject, ResponseHotel[].class);
        List<ResponseHotel> responseHotels = Arrays.asList(hotels);

        doAnswer(invocation -> {
            return responseHotels;
        }).when(hotelService).processHotelsByID(checkInDate, checkOutDate, hotelID, numberOfAdults, numberOfChildren);

        List<ResponseHotel> receivedData = hotelService.processHotelsByID(checkInDate, checkOutDate, hotelID, numberOfAdults, numberOfChildren);
        assertEquals(receivedData, responseHotels);
    }

}
