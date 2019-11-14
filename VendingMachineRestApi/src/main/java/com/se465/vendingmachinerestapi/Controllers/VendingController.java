package com.se465.vendingmachinerestapi.Controllers;

import com.se465.vendingmachinerestapi.Data.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.se465.vendingmachinerestapi.Model.Item;
import com.se465.vendingmachinerestapi.Service.VendingMachineServiceLayer;
import com.se465.vendingmachinerestapi.Data.VendingPersistenceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.util.List;

@RestController
//@RequestMapping("/api")
    public class VendingController {
        @Autowired
        ItemDao ItemDao;
        VendingMachineServiceLayer serviceLayer;
        @GetMapping("items")
        public List<Item> displayAllItems() throws VendingPersistenceException{
                List<Item> item=serviceLayer.getAllItems();
            return item;
    }
}
