package com.se465.vendingmachinerestapi.Data;

import com.se465.vendingmachinerestapi.Model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.se465.vendingmachinerestapi.Data.VendingPersistenceException;
import com.se465.vendingmachinerestapi.Model.Change;
import com.se465.vendingmachinerestapi.Service.InsufficientFundsException;
import com.se465.vendingmachinerestapi.Service.NoItemInventoryException;
import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Item getItem(String itemid) throws VendingPersistenceException, NoItemInventoryException {
        try {
            Item item = jdbc.queryForObject("SELECT * FROM item WHERE itemid= ?", new ItemMapper(), itemid);
//            System.out.println("Item List Completed");
            return item;
        } catch (DataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Item> getAllItems() throws VendingPersistenceException {
        String sqlQuery = "SELECT * FROM item";
        List<Item> items = jdbc.query(sqlQuery, new ItemMapper());
        return items;
    }

    @Override
    public void updateItem(Item item) throws VendingPersistenceException, NoItemInventoryException {
        String updateInventory = "UPDATE item set numberOfItemsInInventory= ? where itemid= ?;";
        jdbc.update(updateInventory,
                item.getNumberOfItemsInInventory(),
                item.getItemid());

    }

    @Override
    public Change vendItem(String itemid, BigDecimal deposit) throws VendingPersistenceException, NoItemInventoryException, InsufficientFundsException {
        Change myChange = null;
//        get items from database
        Item toReturn = getItem(itemid);
        if (toReturn == null) {
            throw new NoItemInventoryException("Item does not exist");
        } else {
            checkInventory(toReturn.getNumberOfItemsInInventory());
            checkMoney(toReturn.getItemCost(), deposit);

            BigDecimal itemCost = toReturn.getItemCost();
            BigDecimal changeMoney = deposit.subtract(itemCost).multiply(new BigDecimal(100));
            myChange = new Change(changeMoney.intValue());

            int availibleItem = toReturn.getNumberOfItemsInInventory() - 1;
            toReturn.setNumberOfItemsInInventory(availibleItem);
            //Update Inventory
            updateItem(toReturn);
        }

        return myChange;

    }

    private void checkInventory(int itemid) throws NoItemInventoryException {
        if (itemid <= 0) {
            throw new NoItemInventoryException("This Item Is Not Available In The Store");
        }

    }

    private void checkMoney(BigDecimal itemCost, BigDecimal deposit) throws InsufficientFundsException {
        //if price is greater than deposit, then return insufficent(price>deposit =2, deposit=1)
        if (itemCost.compareTo(deposit) == 1) {
            throw new InsufficientFundsException("insufficient funds ");
        }
        //  vendItem(itemcost.getItemid())
    }

    private static final class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int i) throws SQLException {
            Item item = new Item();
            item.setItemid(rs.getString("itemid"));
            item.setItemName(rs.getString("itemName"));
            item.setItemCost(rs.getBigDecimal("itemCost"));
            item.setNumberOfItemsInInventory(rs.getInt("numberOfItemsInInventory"));
            return item;
        }

    }

}
