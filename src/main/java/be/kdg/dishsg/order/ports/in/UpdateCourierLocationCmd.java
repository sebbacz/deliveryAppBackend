package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public record UpdateCourierLocationCmd(UUID orderId, double latitude, double longitude) {}
