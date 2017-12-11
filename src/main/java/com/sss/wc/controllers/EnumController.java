/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.controllers;

import com.sss.wc.enums.Agency;
import com.sss.wc.enums.BillCategory;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import com.sss.wc.enums.Gender;
import com.sss.wc.enums.ItemType;
import com.sss.wc.enums.Privilege;

/**
 *
 * @author User
 */
@Named
@ApplicationScoped
public class EnumController {

    /**
     * Creates a new instance of EnumController
     */
    public EnumController() {
    }

    public Privilege[] getPrivileges() {
        return Privilege.values();
    }

    public Gender[] getGenders() {
        return Gender.values();
    }

    public ItemType[] getItemTypes() {
        return ItemType.values();
    }

    public BillCategory[] getBillCategorys() {
        return BillCategory.values();
    }

     public Agency[] getAgencies() {
        return Agency.values();
    }

    
    
}
