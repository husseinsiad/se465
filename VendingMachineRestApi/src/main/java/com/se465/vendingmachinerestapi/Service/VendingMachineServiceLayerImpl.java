package com.se465.vendingmachinerestapi.Service;

import com.se465.vendingmachinerestapi.Model.Change;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.se465.vendingmachinerestapi.Data.ItemDao;
import com.se465.vendingmachinerestapi.Model.Item;
import com.se465.vendingmachinerestapi.Data.VendingPersistenceException;

import java.math.BigDecimal;
import java.util.List;

@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    @Autowired
    ItemDao dao;
    public VendingMachineServiceLayerImpl(ItemDao dao) {
        this.dao = dao;
    }

    @Override
    public Item getItem(String itemid) throws VendingPersistenceException, NoItemInventoryException {
        Item toReturn = dao.getItem(itemid);

        if (toReturn != null) {
            return toReturn;
        } else {
            throw new NoItemInventoryException("This Item Is Not Available In The Store");
        }

    }

    @Override
    public List<Item> getAllItems() throws VendingPersistenceException {
        return dao.getAllItems();
    }

    @Override
    public Change vendItem(Item item, BigDecimal deposit) throws VendingPersistenceException, NoItemInventoryException, InsufficientFundsException {
        Change myChange = null;

        checkInventory(item);
        checkMoney(item, deposit);

        Item toReturn = dao.getItem(item.getItemid());
        if (toReturn == null) {
            throw new NoItemInventoryException("Invalid Item");
        } else {

            BigDecimal itemCost = item.getItemCost();
            BigDecimal changeMoney = deposit.subtract(itemCost).multiply(new BigDecimal(100));
            myChange = new Change(changeMoney.intValue());

            int availibleItem = item.getNumberOfItemsInInventory() - 1;
            item.setNumberOfItemsInInventory(availibleItem);
            dao.updateItem(item);
        }

        return myChange;

    }

    private void checkInventory(Item item) throws NoItemInventoryException {
        if (item.getNumberOfItemsInInventory() <= 0) {
            throw new NoItemInventoryException("This Item Is Not Available In The Store");
        }

    }

    private void checkMoney(Item itemcost, BigDecimal deposit) throws InsufficientFundsException {
        //if price is greater than deposit, then return insufficent(price>deposit =2, deposit=1)
        if (itemcost.getItemCost().compareTo(deposit) > 0) {
            throw new InsufficientFundsException("insufficient funds ");
        }
        //  vendItem(itemcost.getItemid())
    }

}

