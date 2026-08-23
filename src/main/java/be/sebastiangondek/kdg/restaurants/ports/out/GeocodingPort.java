package be.sebastiangondek.kdg.restaurants.ports.out;

//to lat/lon coordinates.

public interface GeocodingPort {
    //retrun lat/lon or null if geo failed
    Double[] geocode(String address);
}
