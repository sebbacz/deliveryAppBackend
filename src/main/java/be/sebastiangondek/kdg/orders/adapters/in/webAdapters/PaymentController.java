package be.sebastiangondek.kdg.orders.adapters.in.webAdapters;

import be.sebastiangondek.kdg.orders.core.DefaultCreatePaymentIntentUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Public REST controller for initiating Stripe payment from the frontend
@RestController
@RequestMapping("/unsecured/payments")
public class PaymentController {

    private final DefaultCreatePaymentIntentUseCase createPaymentIntentUseCase;

    public PaymentController(DefaultCreatePaymentIntentUseCase createPaymentIntentUseCase) {
        this.createPaymentIntentUseCase = createPaymentIntentUseCase;
    }


    @PostMapping("/create-intent")
    public ResponseEntity<Map<String, String>> createIntent(@RequestBody Map<String, Long> body) {
        long amountInCents = body.get("amount");
        String clientSecret = createPaymentIntentUseCase.createIntent(amountInCents);
        return ResponseEntity.ok(Map.of("clientSecret", clientSecret));
    }
}
