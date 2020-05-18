//Created by Michał Basiński on 29.04.2020, 14:14;
package com.example.foodieapp.order;

import com.example.foodieapp.item.Item;
import com.example.foodieapp.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@Controller
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
public class OrderController {
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private ClientOrder clientOrder;

    @Autowired
    public OrderController(OrderRepository orderRepository, ItemRepository itemRepository, ClientOrder clientOrder) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.clientOrder = clientOrder;
    }

    @PostMapping("/zamowienie/dodaj")
    public String addItemToOrder(@RequestParam Long itemId, Model model){
        Optional<Item> item = itemRepository.findById(itemId);
        item.ifPresent(clientOrder::addItem);
        if(item.isPresent()){
            model.addAttribute("message", "Do zamówienia dodano: " + item.get().getName());
        }
        else{
            model.addAttribute("message", "Błąd. Pozycja nie została do dana do koszyka");
        }
        return "message";
    }

    @GetMapping("/zamowienie")
    public String getOrder(Model model){
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("suma", clientOrder.calculateSum());
        model.addAttribute("items", clientOrder.getOrder().getItems());
        return "zamowienie";
    }

    @PostMapping("/zamowienie/realizuj")
    public String orderComplete(@ModelAttribute Order order, Model model){
        order = clientOrder.setFields(order);
        orderRepository.save(order);
        model.addAttribute("message", "Udało Ci się zrealizować zamówienie!");
        return "message";
    }

    @GetMapping("panel/zamowienia")
    public String getOrderList(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "zamowienia";
    }

    @PostMapping("panel/zamowienia/status")
    public String sortByStatus(@RequestParam String status, Model model){
        List<Order> orders = orderRepository.findAllByStatus(OrderStatus.valueOf(status));
        model.addAttribute("orders", orders);
        return "zamowienia";
    }

    @GetMapping("panel/zamowienia/edytuj")
    public String editOrder(@RequestParam Long id, Model model){
        Order order = orderRepository.getOne(id);
        model.addAttribute("order", order);
        return "edit";
    }
}
