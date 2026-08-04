package be.kdg.dishsg.order.ports.in;

public interface AutoDeclineOrdersUseCase {
    void declineExpiredOrders();
}
