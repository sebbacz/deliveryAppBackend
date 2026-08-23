package be.sebastiangondek.kdg.orders.ports.in;

import java.util.UUID;

// Command carrying the courier's latest GPS
public record UpdateCourierLocationCmd(UUID orderId, double latitude, double longitude) {}
