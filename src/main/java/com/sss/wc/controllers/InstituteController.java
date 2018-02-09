package com.sss.wc.controllers;

import com.sss.wc.entity.Institute;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.enums.Agency;
import com.sss.wc.enums.InstituteType;
import com.sss.wc.facades.InstituteFacade;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("instituteController")
@SessionScoped
public class InstituteController implements Serializable {

    @EJB
    private com.sss.wc.facades.InstituteFacade ejbFacade;
    private List<Institute> items = null;
    private List<Institute> vehicles = null;
    private List<Institute> stores = null;
    private List<Institute> banks = null;
    private List<Institute> dealers = null;
    private Institute selected;

    public List<Institute> getVehicles() {
        if (vehicles == null) {
            vehicles = getList(InstituteType.Vehicle);
        }
        return vehicles;
    }

    public void setVehicles(List<Institute> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Institute> getStores() {
        if (stores == null) {
            stores = getList(InstituteType.Stores);
        }
        return stores;
    }

    public void setStores(List<Institute> stores) {
        this.stores = stores;
    }

    public List<Institute> getBanks() {
        if (banks == null) {
            banks = getList(InstituteType.Bank);
        }
        return banks;
    }

    public void setBanks(List<Institute> banks) {
        this.banks = banks;
    }
    
     public List<Institute> getDealers() {
        if (dealers == null) {
            dealers = getList(InstituteType.Dealer);
        }
        return dealers;
    }
     
     public Institute getAgencyInstitute(Agency agency) {
        if (dealers == null) {
            dealers = getList(InstituteType.Dealer);
        }
        for(Institute d:dealers){
            if(d.getAgency()==agency) return d;
        }
        return null;
    }

    public void setDealers(List<Institute> dealers) {
        this.dealers = dealers;
    }

    public InstituteController() {
    }

    public String toCustomers() {
        selected = null;
        return "/maintenance/customers";
    }

    public String toDealers() {
        dealers=null;
        selected = null;
        return "/maintenance/dealers";
    }

    public String toWholesalers() {
        selected = null;
        return "/maintenance/wholesalers";
    }

    public String toRoutes() {
        selected = null;
        return "/maintenance/routes";
    }

    public String toVehicles() {
        selected = null;
        vehicles = null;
        return "/maintenance/vehicles";
    }

    public String toStores() {
        selected = null;
        stores = null;
        return "/maintenance/stores";
    }

    public String toBanks() {
        selected = null;
        banks = null;
        return "/maintenance/banks";
    }

    public List<Institute> getList(InstituteType type) {
        String j;
        Map m = new HashMap();
        j = "select i "
                + " from Institute i "
                + " where i.instituteType=:t "
                + " order by i.name";
        m.put("t", type);
        return getFacade().findBySQL(j, m);
    }

    public List<Institute> getList(InstituteType type, String qry) {
        String j;
        Map m = new HashMap();
        j = "select i "
                + " from Institute i "
                + " where i.instituteType=:t "
                + " and lower(i.name) like :n "
                + " order by i.name";
        m.put("t", type);
        m.put("n", "%" + qry.toLowerCase() + "%");
        return getFacade().findBySQL(j, m);
    }

    public List<Institute> getList(List<InstituteType> types, String qry) {
        String j;
        Map m = new HashMap();
        j = "select i "
                + " from Institute i "
                + " where i.instituteType in :t "
                + " and lower(i.name) like :n "
                + " order by i.name";
        m.put("t", types);
        m.put("n", "%" + qry.toLowerCase() + "%");
        return getFacade().findBySQL(j, m);
    }

    public List<Institute> completeWholesalers(String qry) {
        return getList(InstituteType.Wholesaler, qry);
    }

    public List<Institute> completeCustomers(String qry) {
        return getList(InstituteType.Customer, qry);
    }

    public List<Institute> completeDealers(String qry) {
        return getList(InstituteType.Dealer, qry);
    }

    public List<Institute> completeRoutes(String qry) {
        return getList(InstituteType.Route, qry);
    }

    public List<Institute> completeVehiclesOrStores(String qry) {
        List<InstituteType> its = new ArrayList<InstituteType>();
        its.add(InstituteType.Vehicle);
        its.add(InstituteType.Stores);
        return getList(its, qry);
    }

    public List<Institute> completeVehicles(String qry) {
        return getList(InstituteType.Vehicle, qry);
    }

    public List<Institute> completeStores(String qry) {
        return getList(InstituteType.Stores, qry);
    }

    public List<Institute> completeBanks(String qry) {
        return getList(InstituteType.Bank, qry);
    }

    public void addNewCustomer() {
        selected = new Institute();
        selected.setInstituteType(InstituteType.Customer);
    }

    public void addNewWholesaler() {
        selected = new Institute();
        selected.setInstituteType(InstituteType.Wholesaler);
    }

    public void addNewDealer() {
        selected = new Institute();
        selected.setInstituteType(InstituteType.Dealer);
    }

    public void addNewRoute() {
        selected = new Institute();
        selected.setInstituteType(InstituteType.Route);
    }

    public void addNewVehicle() {
        selected = new Institute();
        selected.setInstituteType(InstituteType.Vehicle);
    }

    public void addNewStore() {
        selected = new Institute();
        selected.setInstituteType(InstituteType.Stores);
    }
    
    public void addNewBank() {
        selected = new Institute();
        selected.setInstituteType(InstituteType.Bank);
    }

    public void saveInstitute() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to save");
            return;
        }
        if (selected.getId() == null) {
            getFacade().create(selected);
            JsfUtil.addSuccessMessage("Created");
        } else {
            getFacade().edit(selected);
            JsfUtil.addSuccessMessage("Updated");
        }
        items = null;
        dealers=null;
        banks=null;
        stores=null;
        vehicles=null;
        getItems();
    }

    public void deleteInstitute() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to delete");
            return;
        }
        if (selected.getId() == null) {
            JsfUtil.addErrorMessage("Nothing to delete");
        } else {
            getFacade().remove(selected);
            JsfUtil.addSuccessMessage("Deleted");
        }
        selected = null;
        items = null;
        dealers=null;
        banks=null;
        stores=null;
        vehicles=null;
        getItems();
    }

    public Institute getSelected() {
        return selected;
    }

    public void setSelected(Institute selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InstituteFacade getFacade() {
        return ejbFacade;
    }

    public Institute prepareCreate() {
        selected = new Institute();
        initializeEmbeddableKey();
        return selected;
    }

    public void createOrUpdate(Institute ins) {
        try {
            if (ins.getId() == null) {
                getFacade().create(ins);
                JsfUtil.addSuccessMessage(ins + " created.");
            } else {
                getFacade().edit(ins);
                JsfUtil.addSuccessMessage(ins + " updated.");
            }
            items = null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleIns").getString("InstituteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleIns").getString("InstituteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleIns").getString("InstituteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Institute> getItems(String jpql) {
        return getFacade().findBySQL(jpql);
    }

    public List<Institute> getItems(String jpql, Map m) {
        return getFacade().findBySQL(jpql, m);
    }

    public List<Institute> getItems() {
        if (items == null) {
            String j = "select i from Institute i order by i.name";
            items = getFacade().findBySQL(j);
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleIns").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleIns").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Institute getInstitute(java.lang.Long id) {
        if (id != null) {
            return getFacade().find(id);
        } else {
            return null;
        }
    }

    public List<Institute> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<Institute> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = Institute.class)
    public static class InstituteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InstituteController controller = (InstituteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "instituteController");
            return controller.getInstitute(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            if (value != null) {
                try {
                    key = Long.valueOf(value);
                } catch (NumberFormatException e) {
                    System.out.println("e = " + e);
                    key = null;
                }
            } else {
                key = null;
            }
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Institute) {
                Institute o = (Institute) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Institute.class.getName()});
                return null;
            }
        }

    }

}
