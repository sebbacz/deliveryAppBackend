package be.sebastiangondek.kdg.orders.ports.in;

public interface CreatePaymentIntentUseCase {
    String createIntent(long amountInCents);
}
