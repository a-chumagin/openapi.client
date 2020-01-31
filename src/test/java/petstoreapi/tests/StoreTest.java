package petstoreapi.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.StoreApi;
import org.openapitools.client.model.Order;
import org.threeten.bp.OffsetDateTime;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreTest {

    private StoreApi apiInstance;

    @BeforeEach
    public void setUp() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api/v3");
        apiInstance = new StoreApi(defaultClient);
    }

    @Test
    public void storeIncreasingTest() throws ApiException {
        Map<String, Integer> result_before = apiInstance.getInventory();
        Integer quantity = 5;
        Order.StatusEnum order_status = Order.StatusEnum.APPROVED;
        Long id = getID();
        Order order = new Order().id(id).petId(198772L).
                quantity(quantity).
                status(order_status).complete(true).
                shipDate(OffsetDateTime.parse("2020-01-13T21:04:23.797Z"));
        apiInstance.placeOrder(order);
        Map<String, Integer> result_after = apiInstance.getInventory();
        assertEquals(getApprovedQuantity(result_after), (getApprovedQuantity(result_before) + quantity));
    }

    @Test
    public void notExistedStoreIdTest() {
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            apiInstance.getOrderById(1234567890L);
        });
        assertEquals(exception.getResponseBody(), "Order not found");
    }

    @Test
    public void getOrderTest() throws ApiException {
        Order order = buildOrder();
        Order response_order = apiInstance.placeOrder(order);
        Order actualOrder = apiInstance.getOrderById(response_order.getId());
        assertEquals(actualOrder.getId(), response_order.getId());
    }

    @Test
    public void deleteOrderTest() throws ApiException {
        Order order = buildOrder();
        Order response_order = apiInstance.placeOrder(order);
        apiInstance.deleteOrder(response_order.getId());
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            apiInstance.getOrderById(response_order.getId());
        });
        assertEquals(exception.getResponseBody(), "Order not found");
    }

    private Order buildOrder() {
        Integer quantity = 5;
        Order.StatusEnum order_status = Order.StatusEnum.APPROVED;
        Long id = getID();
        return new Order().id(id).petId(198772L).
                quantity(quantity).
                status(order_status).
                complete(true).
                shipDate(OffsetDateTime.parse("2020-01-13T21:04:23.797Z"));
    }

    private Integer getApprovedQuantity(Map<String, Integer> inventory) {
        return getQuantity(inventory, Order.StatusEnum.APPROVED.getValue());
    }

    private Integer getQuantity(Map<String, Integer> inventory, String prop) {
        return inventory.get(prop);
    }

    private Long getID() {
        return new Random().nextLong();
    }
}
