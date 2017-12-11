/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.facades;

import com.sss.wc.entity.ItemStock;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nilukagun
 */
@Stateless
public class ItemStockFacade extends AbstractFacade<ItemStock> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemStockFacade() {
        super(ItemStock.class);
    }
    
}
