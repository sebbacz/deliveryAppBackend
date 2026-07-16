package be.kdg.dishsg.payment.adapters.in.webAdapters;

import be.kdg.dishsg.payment.core.DefaultCreatePaymentIntentUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Public REST controller for initiating Stripe payment intents from the frontend checkout flow.
@RestController
@RequestMapping("/unsecured/payments")
public class PaymentController {

    private final DefaultCreatePaymentIntentUseCase createPaymentIntentUseCase;

    public PaymentController(DefaultCreatePaymentIntentUseCase createPaymentIntentUseCase) {
        this.createPaymentIntentUseCase = createPaymentIntentUseCase;
    }

    /**
     * Creates a Stripe PaymentIntent for the given amount.
     * Called by the frontend before rendering the Stripe payment form.
     *
     * Request body: { "amount": 1500 }   (amount in euro cents)
     * Response:     { "clientSecret": "pi_xxx_secret_xxx" }
     */
    @PostMapping("/create-intent")
    public ResponseEntity<Map<String, String>> createIntent(@RequestBody Map<String, Long> body) {
        long amountInCents = body.get("amount");
        String clientSecret = createPaymentIntentUseCase.createIntent(amountInCents);
        return ResponseEntity.ok(Map.of("clientSecret", clientSecret));
    }
}
