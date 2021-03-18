package org.example.DTOs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Domains.Hotel;
import org.example.Domains.Room;

import java.util.List;


public class HotelDeserializer extends AbstractDeserializer<Hotel> {
    @Override
    public Hotel deserialize(final JsonNode node) throws JsonProcessingException {

        final Long id = node.get("id").asLong();
        final String name = node.get("name").asText();
        final Integer cityCode = node.get("cityCode").asInt();
        final String cityName = node.get("cityName").asText();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode response = node.path("rooms");
        List<Room> rooms = mapper.readValue(response.toString(), new TypeReference<>() {
        });

        return new Hotel(id, name, cityCode, cityName, rooms);

    }
}
