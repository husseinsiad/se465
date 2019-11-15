package com.se465.vendingmachinerestapi.Data;
import com.se465.vendingmachinerestapi.Service.NoItemInventoryException;
import com.se465.vendingmachinerestapi.Model.Item;
import java.util.List;
public interface ItemDao {
    public Item getItem(String itemid)throws VendingPersistenceException,NoItemInventoryException;
    public List<Item> getAllItems()throws VendingPersistenceException;
    public void updateItem(Item item)throws VendingPersistenceException,NoItemInventoryException;
}
