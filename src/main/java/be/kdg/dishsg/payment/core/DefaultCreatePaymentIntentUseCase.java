package be.kdg.dishsg.payment.core;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Stripe integration service that creates a PaymentIntent for a given euro-cent amount.
@Service
public class DefaultCreatePaymentIntentUseCase {

    public DefaultCreatePaymentIntentUseCase(@Value("${stripe.secret-key}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    /**
     * Creates a Stripe PaymentIntent and returns its client secret.
     *
     * @param amountInCents the amount to charge, in euro cents (e.g. 1500 = €15.00)
     * @return the client secret to pass to the frontend
     */
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
