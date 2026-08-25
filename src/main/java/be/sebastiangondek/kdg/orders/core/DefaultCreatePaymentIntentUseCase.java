package be.sebastiangondek.kdg.orders.core;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import be.sebastiangondek.kdg.orders.ports.in.CreatePaymentIntentUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Creates a Stripe PaymentIntent so the frontend can render the Stripe Elements card form.
@Service
public class DefaultCreatePaymentIntentUseCase implements CreatePaymentIntentUseCase {

    public DefaultCreatePaymentIntentUseCase(@Value("${stripe.secret-key}") String secretKey) {
        Stripe.apiKey = secretKey;
    }


     //Creates a Stripe PaymentIntent and returns its client secret.

    public String createIntent(long amountInCents) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("eur")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getClientSecret();
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe PaymentIntent: " + e.getMessage(), e);
        }
    }
}
