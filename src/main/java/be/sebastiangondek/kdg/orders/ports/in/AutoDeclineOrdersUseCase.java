package be.sebastiangondek.kdg.orders.ports.in;

// Inbound port invoked every 30 s by AutoDeclineOrdersScheduler to reject orders older than 5 minutes.
public interface AutoDeclineOrdersUseCase {
    void declineExpiredOrders();
}
