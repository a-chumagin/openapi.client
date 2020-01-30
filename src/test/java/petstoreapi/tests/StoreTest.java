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
    public void storeIncreasingTests() throws ApiException {
        Map<String, Integer> result_before = apiInstance.getInventory();
        Integer quantity = 5;
        Order.StatusEnum order_status = Order.StatusEnum.APPROVED;
        Long id = getID();
        Order order = new Order().id(id).petId(198772L).
                quantity(quantity).status(order_status).complete(true).shipDate(OffsetDateTime.parse("2020-01-13T21:04:23.797Z"));
        apiInstance.placeOrder(order);
        Map<String, Integer> result_after = apiInstance.getInventory();
        assertEquals(getApprovedQuantity(result_after), (getApprovedQuantity(result_before) + quantity));
    }

    @Test
    public void notExistedStoreId() {
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            apiInstance.getOrderById(1234567890L);
        });
        assertEquals(exception.getResponseBody(), "Order not found");
    }

    @Test
    public void deleteOrder(){
        Integer quantity = 5;
        Order.StatusEnum order_status = Order.StatusEnum.APPROVED;
        Long id = getID();
        Order order = new Order().id(id).petId(198772L).
                quantity(quantity).status(order_status).complete(true).shipDate(OffsetDateTime.parse("2020-01-13T21:04:23.797Z"));
//        Order response_order = apiInstance.placeOrder(order);
    }

    private Integer getApprovedQuantity(Map<String, Integer> inventory) {
        return getQuantity(inventory, Order.StatusEnum.APPROVED.getValue());
    }

    private Integer getQuantity(Map<String, Integer> inventory, String prop) {
        return inventory.get(prop);
    }

    private Long getID() {
        long generatedLong = new Random().nextLong();
        return generatedLong;
    }
}
