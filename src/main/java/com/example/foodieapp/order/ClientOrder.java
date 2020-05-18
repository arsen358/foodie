//Created by Michał Basiński on 04.05.2020, 13:49;
package com.example.foodieapp.order;

import com.example.foodieapp.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class ClientOrder {
    private Order order;

    Order getOrder() {
        order.setStatus(OrderStatus.IN_PROGRESS);
        return order;
    }

    Order setFields(Order order) {
        Order o = getOrder();
        order.setItems(o.getItems());
        order.setStatus(OrderStatus.COMPLETE);
        return order;
    }

    void addItem(Item item){
        if(order == null){
            order = clear();
        }
        order.getItems().add(item);
    }

    String calculateSum(){
        double suma = 0;
        List<Item> items = order.getItems();
        for (Item i: items) {
            suma += i.getPrice();
        }
        return String.format("%.2f", suma).replaceAll(",", ".");
    }

    private Order clear(){
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        return order;
    }
}
