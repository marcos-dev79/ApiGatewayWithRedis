package org.example.Domains;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseHotel {
    private Long id;
    private String name;
    private String city;
    private List<ResponseRoom> rooms;
}
