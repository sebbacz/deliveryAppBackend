package be.sebastiangondek.kdg.restaurants.ports.in;

// Inbound port for appending the criteria history.
public interface AddCriteriaEventUseCase {
    void addCriteriaEvent(AddCriteriaEventCmd cmd);
}
