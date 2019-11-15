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
    public Change vendItem(String itemid, BigDecimal deposit) throws VendingPersistenceException, NoItemInventoryException, InsufficientFundsException {
        Change myChange = null;
//        get items from database
        Item toReturn = dao.getItem(itemid);
       
        checkInventory(toReturn.getNumberOfItemsInInventory());
        checkMoney(deposit);

        if (toReturn == null) {
            throw new NoItemInventoryException("Invalid Item");
        } else {

            BigDecimal itemCost = toReturn.getItemCost();
            BigDecimal changeMoney = deposit.subtract(itemCost).multiply(new BigDecimal(100));
            myChange = new Change(changeMoney.intValue());

            int availibleItem = toReturn.getNumberOfItemsInInventory() - 1;
            toReturn.setNumberOfItemsInInventory(availibleItem);
            dao.updateItem(toReturn);
        }

        return myChange;

    }

    private void checkInventory(int itemid) throws NoItemInventoryException {
        if (itemid <= 0) {
            throw new NoItemInventoryException("This Item Is Not Available In The Store");
        }

    }

    private void checkMoney(BigDecimal deposit) throws InsufficientFundsException {
        //if price is greater than deposit, then return insufficent(price>deposit =2, deposit=1)
        if (deposit.compareTo(deposit)>0) {
            throw new InsufficientFundsException("insufficient funds ");
        }
        //  vendItem(itemcost.getItemid())
    }

}

