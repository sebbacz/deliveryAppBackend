package be.sebastiangondek.kdg.orders.ports.out;

//  interface combining LoadOrderPort and SaveOrderPort  implemented by the event-store adapter.
public interface OrderRepositoryPort extends LoadOrderPort, SaveOrderPort {}
