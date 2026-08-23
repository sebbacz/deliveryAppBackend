package be.sebastiangondek.kdg.restaurants.ports.in;

// Inbound port for scheduling pending dish drafts to go live at a future timestamp.
public interface ScheduleDishChangesUseCase {
    void scheduleChanges(ScheduleDishChangesCmd cmd);
}
