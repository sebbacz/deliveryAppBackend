package be.sebastiangondek.kdg.orders.adapters.in.messaging;

import be.sebastiangondek.kdg.orders.ports.in.MarkOrderDeliveredCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderDeliveredUseCase;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderPickedUpCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderPickedUpUseCase;
import be.sebastiangondek.kdg.orders.ports.in.UpdateCourierLocationCmd;
import be.sebastiangondek.kdg.orders.ports.in.UpdateCourierLocationUseCase;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

// Inbound messaging adapter: translates raw RabbitMQ delivery-service  into domain
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

    // accepts messages from any delivery service  regardless of id
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "kdg.delivery.pickedup.queue", durable = "true"),
            exchange = @Exchange(value = "kdg.events", type = "topic"),
            key = "delivery.*.order.pickedup.v1"
    ))
    public void handleOrderPickedUp(Map<String, Object> payload) {
        UUID orderId = UUID.fromString(payload.get("orderId").toString());
        markOrderPickedUpUseCase.markOrderPickedUp(new MarkOrderPickedUpCmd(orderId));
    }

    // DELIVERED  state
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "kdg.delivery.delivered.queue", durable = "true"),
            exchange = @Exchange(value = "kdg.events", type = "topic"),
            key = "delivery.*.order.delivered.v1"
    ))
    public void handleOrderDelivered(Map<String, Object> payload) {
        UUID orderId = UUID.fromString(payload.get("orderId").toString());
        markOrderDeliveredUseCase.markOrderDelivered(new MarkOrderDeliveredCmd(orderId));
    }

    // Location updates arrive frequently,  overwrites the previous coordinates
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
