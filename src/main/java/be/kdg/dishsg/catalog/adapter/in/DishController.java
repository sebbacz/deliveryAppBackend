package be.kdg.dishsg.catalog.adapter.in;


import be.kdg.dishsg.catalog.adapter.in.request.PublishDishRequest;

import be.kdg.dishsg.catalog.adapter.in.request.UnpublishDishRequest;

import be.kdg.dishsg.catalog.port.in.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/catalog/dishes")
public class DishController {
    private final SaveDishDraftUseCase saveDraft;
    private final PublishDishUseCase publishDish;


    public DishController(SaveDishDraftUseCase saveDraft,
                          PublishDishUseCase publishDish
                           ) {
        this.saveDraft = saveDraft;
        this.publishDish = publishDish;

    }


    @PostMapping("/{dishId}/publish")
    public void publish(@PathVariable UUID dishId, @RequestBody PublishDishRequest request) {

    }

    @PostMapping("/{dishId}/unpublish")
    public void unpublish(@PathVariable UUID dishId, @RequestBody UnpublishDishRequest request) {

    }

    @PostMapping("/{dishId}/stock")
    public void changeStock(@PathVariable UUID dishId, @RequestBody UnpublishDishRequest request) {


    }
}