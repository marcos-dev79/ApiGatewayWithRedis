package org.example.services;

import org.example.Domains.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HotelService implements Serializable {

    @Autowired
    RestTemplate restTemplate;



    @Value("${apiByCity}")
    private String apiByCityUrl;

    @Value("${hotelById}")
    private String apiHotelByID;

    @Cacheable(cacheNames = "HotelsByCity", key="#cityId", unless="#result == null")
    private List<Hotel> getAllHotels(String cityId) {
        Hotel[] hotels = restTemplate.getForObject(apiByCityUrl + cityId, Hotel[].class);
        return Arrays.asList(hotels);
    }

    @Cacheable(cacheNames = "HotelByID", key="#hotelID", unless="#result == null")
    private List<Hotel> getHotelByID(String hotelID) {
        Hotel[] hotels = restTemplate.getForObject(apiHotelByID + hotelID, Hotel[].class);
        return Arrays.asList(hotels);
    }

    /**
     * This Method Process the business logic for a specific Hotel
     * @param checkInDate
     * @param checkOutDate
     * @param hotelIDvalue
     * @return List<ResponseHotel>
     */
    public List<ResponseHotel> processHotelsByID(LocalDate checkInDate, LocalDate checkOutDate, String hotelIDvalue, Integer numberOfAdults, Integer numberOfChildren) {

        BigDecimal adultPrice = BigDecimal.ZERO;
        BigDecimal childPrice = BigDecimal.ZERO;

        BigDecimal adultDayPrice = BigDecimal.ZERO;
        BigDecimal childDayPrice = BigDecimal.ZERO;

        Integer days = checkInDate.until(checkOutDate).getDays();

        Hotel hotel = getHotelByID(hotelIDvalue).get(0);

        List<ResponseRoom> responseRooms = new ArrayList<>();
        List<ResponseHotel> responseHotelList = new ArrayList<>();
        ResponseHotel responseHotel = new ResponseHotel();

        List<Room> rooms = hotel.getRooms();

        for(Room r : rooms) {

            PriceDetail priceDetail = new PriceDetail();
            ResponseRoom responseRoom = new ResponseRoom();
            Category category = new Category();

            Price p = r.getPrice();

            adultPrice = p.getAdult().multiply(new BigDecimal(days)).multiply(new BigDecimal(numberOfAdults)).divide(new BigDecimal(0.7), RoundingMode.HALF_UP);
            childPrice = p.getChild().multiply(new BigDecimal(days)).multiply(new BigDecimal(numberOfChildren)).divide(new BigDecimal(0.7), RoundingMode.HALF_UP);

            adultDayPrice = p.getAdult().divide(new BigDecimal(0.7), RoundingMode.HALF_UP);
            childDayPrice = p.getChild().divide(new BigDecimal(0.7), RoundingMode.HALF_UP);

            category.setName(r.getCategoryName());
            priceDetail.setPricePerDayAdult(adultDayPrice);
            priceDetail.setPricePerDayChild(childDayPrice);

            responseRoom.setCategory(category);
            responseRoom.setRoomID(r.getRoomID());
            responseRoom.setTotalPrice(adultPrice.add(childPrice));
            responseRoom.setPriceDetail(priceDetail);

            responseRooms.add(responseRoom);
        }

        responseHotel.setCity(hotel.getCityName());
        responseHotel.setId(hotel.getId());
        responseHotel.setName(hotel.getName());
        responseHotel.setRooms(responseRooms);

        responseHotelList.add(responseHotel);

        return responseHotelList;
    }

    /**
     * This method process the business logic for the Hotels in a given CITY
     *
     * @param checkInDate
     * @param checkOutDate
     * @param cityID
     * @return List<ResponseHotel>
     */
    public List<ResponseHotel> processHotelByCity(LocalDate checkInDate, LocalDate checkOutDate, String cityID, Integer numberOfAdults, Integer numberOfChildren) {
        BigDecimal adultPrice = BigDecimal.ZERO;
        BigDecimal childPrice = BigDecimal.ZERO;

        BigDecimal adultDayPrice = BigDecimal.ZERO;
        BigDecimal childDayPrice = BigDecimal.ZERO;

        Integer days = checkInDate.until(checkOutDate).getDays();

        List<Hotel> hotelList = getAllHotels(cityID);
        List<ResponseHotel> responseHotelList1 = new ArrayList<>();

        for(Hotel h : hotelList) {

            List<ResponseRoom> responseRooms1 = new ArrayList<>();
            List<Room> rooms = h.getRooms();
            ResponseHotel responseHotel1 = new ResponseHotel();

            for(Room r : rooms) {
                Price p = r.getPrice();

                adultPrice = p.getAdult().multiply(new BigDecimal(days)).multiply(new BigDecimal(numberOfAdults)).divide(new BigDecimal(0.7), RoundingMode.HALF_UP);
                childPrice = p.getChild().multiply(new BigDecimal(days)).multiply(new BigDecimal(numberOfChildren)).divide(new BigDecimal(0.7), RoundingMode.HALF_UP);

                adultDayPrice = p.getAdult().divide(new BigDecimal(0.7), RoundingMode.HALF_UP);
                childDayPrice = p.getChild().divide(new BigDecimal(0.7), RoundingMode.HALF_UP);

                Category category1 = new Category();
                category1.setName(r.getCategoryName());

                PriceDetail priceDetail1 = new PriceDetail();
                priceDetail1.setPricePerDayAdult(adultDayPrice);
                priceDetail1.setPricePerDayChild(childDayPrice);

                ResponseRoom responseRoom1 = new ResponseRoom();
                responseRoom1.setCategory(category1);
                responseRoom1.setRoomID(r.getRoomID());
                responseRoom1.setTotalPrice(adultPrice.add(childPrice));
                responseRoom1.setPriceDetail(priceDetail1);

                responseRooms1.add(responseRoom1);
            }

            responseHotel1.setCity(h.getCityName());
            responseHotel1.setId(h.getId());
            responseHotel1.setName(h.getName());
            responseHotel1.setRooms(responseRooms1);

            responseHotelList1.add(responseHotel1);

        }

        return responseHotelList1;
    }




}
