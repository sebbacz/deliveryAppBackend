package be.kdg.dishsg.order.adapters.in.messaging;

import be.kdg.dishsg.order.ports.in.MarkOrderDeliveredCmd;
import be.kdg.dishsg.order.ports.in.MarkOrderDeliveredUseCase;
import be.kdg.dishsg.order.ports.in.MarkOrderPickedUpCmd;
import be.kdg.dishsg.order.ports.in.MarkOrderPickedUpUseCase;
import be.kdg.dishsg.order.ports.in.UpdateCourierLocationCmd;
import be.kdg.dishsg.order.ports.in.UpdateCourierLocationUseCase;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

// RabbitMQ listener that handles delivery service events: picked-up, delivered, and courier location updates.
@Component
public class DeliveryEventListener {

    private final MarkOrderPickedUpUseCase markOrderPickedUpUseCase;
    private final MarkOrderDeliveredUseCase markOrderDeliveredUseCase;
    private final UpdateCourierLocationUseCase updateCourierLocationUseCase;

    public DeliveryEventListener(MarkOrderPickedUpUseCase markOrderPickedUpUseCase,
                                 MarkOrderDeliveredUseCase markOrderDeliveredUseCase,
                                 UpdateCourierLocationUseCase updateCourierLocationUseCase) {
        this.markOrderPickedUpUseCase = markOrderPickedUpUseCase;
        this.markOrderDeliveredUseCase = markOrderDeliveredUseCase;
        this.updateCourierLocationUseCase = updateCourierLocationUseCase;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "kdg.delivery.pickedup.queue", durable = "true"),
            exchange = @Exchange(value = "kdg.events", type = "topic"),
            key = "delivery.*.order.pickedup.v1"
    ))
    public void handleOrderPickedUp(Map<String, Object> payload) {
        UUID orderId = UUID.fromString(payload.get("orderId").toString());
        markOrderPickedUpUseCase.markOrderPickedUp(new MarkOrderPickedUpCmd(orderId));
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "kdg.delivery.delivered.queue", durable = "true"),
            exchange = @Exchange(value = "kdg.events", type = "topic"),
            key = "delivery.*.order.delivered.v1"
    ))
    public void handleOrderDelivered(Map<String, Object> payload) {
        UUID orderId = UUID.fromString(payload.get("orderId").toString());
        markOrderDeliveredUseCase.markOrderDelivered(new MarkOrderDeliveredCmd(orderId));
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "kdg.delivery.location.queue", durable = "true"),
            exchange = @Exchange(value = "kdg.events", type = "topic"),
            key = "delivery.*.order.location.v1"
    ))
    public void handleCourierLocation(Map<String, Object> payload) {
        UUID orderId = UUID.fromString(payload.get("orderId").toString());
        double latitude = Double.parseDouble(payload.get("latitude").toString());
        double longitude = Double.parseDouble(payload.get("longitude").toString());
        updateCourierLocationUseCase.updateCourierLocation(new UpdateCourierLocationCmd(orderId, latitude, longitude));
    }
}
