package org.example.Domains;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private Long roomID;
    private String categoryName;
    private Price price;
}
