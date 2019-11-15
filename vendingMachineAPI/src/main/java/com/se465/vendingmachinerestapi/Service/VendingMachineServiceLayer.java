package com.se465.vendingmachinerestapi.Service;
import java.util.List;
import com.se465.vendingmachinerestapi.Model.Item;
import com.se465.vendingmachinerestapi.Model.Change;
import com.se465.vendingmachinerestapi.Data.VendingPersistenceException;
import java.math.BigDecimal;
public interface VendingMachineServiceLayer {
    public Item getItem(String itemid)
            throws VendingPersistenceException, NoItemInventoryException;

    public List<Item> getAllItems()
            throws VendingPersistenceException;

    public Change vendItem(String itemid, BigDecimal deposit)
            throws VendingPersistenceException, NoItemInventoryException, InsufficientFundsException ;

}
