package org.example.Domains;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.DTOs.HotelDeserializer;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = HotelDeserializer.class)
public class Hotel {
    private Long id;
    private String name;
    private Integer cityCode;
    private String cityName;
    private List<Room> rooms;
}
