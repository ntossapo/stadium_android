package com.tossapon.stadiumfinder.Model.Advance;

import com.tossapon.stadiumfinder.Model.Basic.User;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by benvo_000 on 9/3/2559.
 */
@Parcel
public class PlayerStat extends User {
    int reservesAndPlay;
    int reservesAndMiss;
    int allReserves;
    int allJoin;

    public PlayerStat(String facebook_id, String facebook_token, String name, String picurl) {
        super(facebook_id, facebook_token, name, picurl);
    }

    @ParcelConstructor
    public PlayerStat(String facebook_id, String facebook_token, String name, String picurl, int reservesAndPlay, int reservesAndMiss, int allReserves, int allJoin) {
        super(facebook_id, facebook_token, name, picurl);
        this.reservesAndPlay = reservesAndPlay;
        this.reservesAndMiss = reservesAndMiss;
        this.allReserves = allReserves;
        this.allJoin = allJoin;
    }

    public int getReservesAndPlay() {
        return reservesAndPlay;
    }

    public int getReservesAndMiss() {
        return reservesAndMiss;
    }

    public int getAllReserves() {
        return allReserves;
    }

    public int getAllJoin() {
        return allJoin;
    }
}
