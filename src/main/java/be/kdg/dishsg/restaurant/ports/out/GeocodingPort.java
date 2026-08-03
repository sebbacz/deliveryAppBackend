package be.kdg.dishsg.restaurant.ports.out;

/**
 * Out-port for geocoding an address to lat/lon coordinates.
 */
public interface GeocodingPort {
    /**
     * @return [latitude, longitude], or null if geocoding failed.
     */
    Double[] geocode(String address);
}
