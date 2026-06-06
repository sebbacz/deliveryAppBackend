package be.kdg.dishsg.restaurant.infra.adapter;

import be.kdg.dishsg.restaurant.ports.out.GeocodingPort;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

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
            List<NominatimResult> results = restClient.get()
                    .uri("/search?format=json&limit=1&q={q}", address)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            if (results != null && !results.isEmpty()) {
                return new Double[]{
                        Double.parseDouble(results.get(0).lat()),
                        Double.parseDouble(results.get(0).lon())
                };
            }
        } catch (Exception ignored) {
            // Geocoding is best-effort; coordinates remain null if unavailable
        }
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record NominatimResult(@JsonProperty("lat") String lat, @JsonProperty("lon") String lon) {}
}
