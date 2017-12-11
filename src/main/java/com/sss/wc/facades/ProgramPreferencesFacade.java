/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.facades;

import com.sss.wc.entity.ProgramPreferences;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nilukagun
 */
@Stateless
public class ProgramPreferencesFacade extends AbstractFacade<ProgramPreferences> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgramPreferencesFacade() {
        super(ProgramPreferences.class);
    }
    
}
