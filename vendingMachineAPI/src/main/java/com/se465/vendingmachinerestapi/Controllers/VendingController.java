package com.se465.vendingmachinerestapi.Controllers;

import com.se465.vendingmachinerestapi.Data.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.se465.vendingmachinerestapi.Model.Item;
import com.se465.vendingmachinerestapi.Service.VendingMachineServiceLayer;
import com.se465.vendingmachinerestapi.Data.VendingPersistenceException;
import com.se465.vendingmachinerestapi.Model.Change;
import com.se465.vendingmachinerestapi.Service.InsufficientFundsException;
import com.se465.vendingmachinerestapi.Service.NoItemInventoryException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class VendingController {

    @Autowired
    ItemDao ItemDao;
    VendingMachineServiceLayer serviceLayer;

    @GetMapping("items")
    public List<Item> displayAllItems() throws VendingPersistenceException {
        List<Item> item = ItemDao.getAllItems();
        return item;
    }

    @GetMapping("items/{itemid}")
    public Item getItem(@PathVariable String itemid) throws VendingPersistenceException, NoItemInventoryException {
        Item item = ItemDao.getItem(itemid);
        return item;
    }

    @GetMapping("items/{itemid}/{deposit}")
    public Change vendingItem(@PathVariable String itemid,@PathVariable BigDecimal deposit) throws VendingPersistenceException, NoItemInventoryException,InsufficientFundsException {
        Change change = ItemDao.vendItem(itemid,deposit);
       return change;
    }
}
