/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.controllers;

import com.sss.wc.entity.Institute;
import com.sss.wc.entity.Item;
import com.sss.wc.entity.ItemStock;
import com.sss.wc.facades.ItemStockFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author nilukagun
 */
@Named(value = "applicationController")
@ApplicationScoped
public class ApplicationController {

    @EJB
    ItemStockFacade itemStockFacade;

    /**
     * Creates a new instance of ApplicationController
     */
    public ApplicationController() {
    }

    public ItemStock findItemStock(Item i, double retailRate, Institute ins) {
        String j = "select i "
                + " from ItemStock i "
                + " where i.item=:i "
                + " and i.retailRate=:rr "
                + " and i.institute=:ins "
                + " order by i.id desc";
        Map m = new HashMap();
        m.put("i", i);
        m.put("rr", retailRate);
        m.put("ins", ins);

        ItemStock is = getItemStockFacade().findFirstBySQL(j, m);
        System.out.println("is = " + is);
        if (is == null) {
            is = new ItemStock();
            is.setItem(i);
            is.setRetailRate(retailRate);
            is.setInstitute(ins);
            getItemStockFacade().create(is);
        }
        System.out.println("is = " + is);
        return is;
    }
    
    public ItemStock findItemStock(Item i, double retailRate, double purchaseRate, Institute ins) {
        String j = "select i "
                + " from ItemStock i "
                + " where i.item=:i "
                + " and i.retailRate=:rr "
                + " and i.purchaseRate=:pr "
                + " and i.institute=:ins "
                + " order by i.id desc";
        Map m = new HashMap();
        m.put("i", i);
        m.put("rr", retailRate);
        m.put("pr", purchaseRate);
        m.put("ins", ins);

        ItemStock is = getItemStockFacade().findFirstBySQL(j, m);
        System.out.println("is = " + is);
        if (is == null) {
            is = new ItemStock();
            is.setItem(i);
            is.setRetailRate(retailRate);
            is.setPurchaseRate(purchaseRate);
            is.setInstitute(ins);
            getItemStockFacade().create(is);
        }
        System.out.println("is = " + is);
        return is;
    }

    public List<ItemStock> fillAllItemStocks(Institute ins) {
        String j = "select i "
                + " from ItemStock i "
                + " where i.institute=:ins "
                + " order by i.item.name";
        Map m = new HashMap();
        m.put("ins", ins);
        return getItemStockFacade().findBySQL(j, m);
    }

    
    public boolean adjustStock(ItemStock is, double currentStock) {
        boolean f = false;
        if (is != null) {
            is.setStock(currentStock);
            getItemStockFacade().edit(is);
            f = true;
        }
        return f;
    }

    public boolean addToStock(ItemStock is, double addingQty) {
        boolean f = false;
        if (is != null) {
            is.setStock(is.getStock() + addingQty);
            getItemStockFacade().edit(is);
            f = true;
        }
        return f;
    }

    public boolean deductFromStock(ItemStock is, double deductingStock) {
        boolean f = false;
        if (is != null) {
            is.setStock(is.getStock() - deductingStock);
            getItemStockFacade().edit(is);
            f = true;
        }
        return f;
    }

    public ItemStockFacade getItemStockFacade() {
        return itemStockFacade;
    }

}
