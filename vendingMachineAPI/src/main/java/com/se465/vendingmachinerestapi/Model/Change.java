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
public class Change {
    int dollar;
    int quarter;
    int dime;
    int nickel;
   //int pennies;
    int leftOverPennies;

    public Change(int pennies) {

        dollar = pennies / 100;
        pennies -= dollar * 100;

        quarter = pennies / 25;
        pennies -= quarter * 25;
        dime = pennies / 10;
        pennies -= dime * 10;
        nickel = pennies / 5;
        pennies -= nickel * 5;
        leftOverPennies = pennies;

    }

    public int getDollar() {
        return dollar;
    }

    public void setDollar(int dollar) {
        this.dollar = dollar;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getDime() {
        return dime;
    }

    public void setDime(int dime) {
        this.dime = dime;
    }

    public int getNickel() {
        return nickel;
    }

    public void setNickel(int nickel) {
        this.nickel = nickel;
    }

    public int getLeftOverPennies() {
        return leftOverPennies;
    }

    public void setLeftOverPennies(int leftOverPennies) {
        this.leftOverPennies = leftOverPennies;
    }

    }

    


