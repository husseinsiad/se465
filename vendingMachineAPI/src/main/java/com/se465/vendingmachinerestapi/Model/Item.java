/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se465.vendingmachinerestapi.Model;

import java.math.BigDecimal;

/**
 *
 * @author a1467740
 */
public class Item {
    private String itemid;
    private String itemName;
    private BigDecimal itemCost;
    private int numberOfItemsInInventory;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getNumberOfItemsInInventory() {
        return numberOfItemsInInventory;
    }

    public void setNumberOfItemsInInventory(int numberOfItemsInInventory) {
        this.numberOfItemsInInventory = numberOfItemsInInventory;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.itemid != null ? this.itemid.hashCode() : 0);
        hash = 73 * hash + (this.itemName != null ? this.itemName.hashCode() : 0);
        hash = 73 * hash + (this.itemCost != null ? this.itemCost.hashCode() : 0);
        hash = 73 * hash + this.numberOfItemsInInventory;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.numberOfItemsInInventory != other.numberOfItemsInInventory) {
            return false;
        }
        if ((this.itemid == null) ? (other.itemid != null) : !this.itemid.equals(other.itemid)) {
            return false;
        }
        if ((this.itemName == null) ? (other.itemName != null) : !this.itemName.equals(other.itemName)) {
            return false;
        }
        if (this.itemCost != other.itemCost && (this.itemCost == null || !this.itemCost.equals(other.itemCost))) {
            return false;
        }
        return true;
    }

}
