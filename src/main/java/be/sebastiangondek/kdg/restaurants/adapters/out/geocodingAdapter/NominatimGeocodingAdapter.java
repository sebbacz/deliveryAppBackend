package be.sebastiangondek.kdg.restaurants.adapters.out.geocodingAdapter;

import be.sebastiangondek.kdg.restaurants.ports.out.GeocodingPort;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

// Calls map api to resolve an address string to [lat, lon].
@Component
public class NominatimGeocodingAdapter implements GeocodingPort {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://nominatim.openstreetmap.org")
            .defaultHeader("Accept-Language", "en")
            .defaultHeader("User-Agent", "KeepDishesGoing/1.0 contact@kdg.be")
            .build();

    @Override
    public Double[] geocode(String address) {
        try {
            NominatimResult[] results = restClient.get()
                    .uri("/search?format=json&limit=1&q={q}", address)
                    .retrieve()
                    .body(NominatimResult[].class);
            if (results != null && results.length > 0) {
                return new Double[]{
                        Double.parseDouble(results[0].lat()),
                        Double.parseDouble(results[0].lon())
                };
            }
        } catch (Exception ignored) {}
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record NominatimResult(String lat, String lon) {}
}
