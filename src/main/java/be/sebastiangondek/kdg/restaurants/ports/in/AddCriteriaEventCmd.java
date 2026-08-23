package be.sebastiangondek.kdg.restaurants.ports.in;

import java.time.LocalDateTime;

// Command for appending a new price-range  to the criteria event history.
public record AddCriteriaEventCmd(LocalDateTime effectiveAt, double cheapMax, double regularMax, double expensiveMax) {}
