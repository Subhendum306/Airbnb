package com.airbnb.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BookingDto {
    private long bookingId;
    private String guestName;
    private int price;
    private int totalPrice;
}
