package com.tossapon.stadiumfinder.Model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */
@Parcel
public class PriceRate {
    int id;
    String name;
    double price;
    int field_id;

    @ParcelConstructor
    public PriceRate(int id, String name, double price, int field_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.field_id = field_id;
    }
}
