# Programming 6 — Keep Dishes Going

**Sebastian Gondek**

- Documentation: `/documentation`
- Wireframes: `/documentation/wireframes`

---

## Challenges

- **Event sourcing** — building the event store with snapshot support and keeping the CQRS projection in sync was the hardest technical part.
- **Dual authentication** — running Keycloak JWT and custom session tokens side by side in a single Spring Security filter chain without breaking either path.
- **Retroactive price range** — recalculating a restaurant's price history using a criteria event log required applying event sourcing outside the Order aggregate as well.

## Accomplishments

- Strict hexagonal architecture: zero Spring/JPA annotations anywhere in the domain layer.
- Fully event-sourced Order aggregate with snapshots and a separate CQRS read projection.
- Live courier tracking on the order page, driven by RabbitMQ location events.

---

## Finished Features

- [x] Owner sign up / sign in (Keycloak + custom session tokens)
- [x] Create restaurant with all required fields
- [x] Dish lifecycle: draft → publish → unpublish
- [x] Apply all pending dish changes at once
- [x] Schedule dish changes to go live at a chosen time
- [x] Mark dish out of stock / back in stock
- [x] Manually open or close restaurant; opening-hours scheduler
- [x] Accept / reject orders (rejection requires a reason)
- [x] Auto-decline orders after 5 minutes
- [x] Mark order ready for pickup
- [x] Max 10 live dishes enforced
- [x] Price range evolution graph (retroactive)
- [x] Stripe payment integration
- [x] Order tracking with live status polling and courier map
- [x] RabbitMQ: publish accepted + ready events to delivery service
- [x] RabbitMQ: consume pickedup, delivered, and location events
- [x] Restaurant list + map view with filters and sorting
- [x] Basket validation at checkout (out of stock / unpublished detection)
- [x] Customers can order without signing in

## Unfinished Features

- i hope everything is done
