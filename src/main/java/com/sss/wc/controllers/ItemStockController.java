package com.sss.wc.controllers;

import com.sss.wc.entity.ItemStock;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.Institute;
import com.sss.wc.entity.Item;
import com.sss.wc.enums.Agency;
import com.sss.wc.facades.ItemStockFacade;

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

@Named("itemStockController")
@SessionScoped
public class ItemStockController implements Serializable {

    @EJB
    private com.sss.wc.facades.ItemStockFacade ejbFacade;
    private List<ItemStock> items = null;
    private ItemStock selected;
    Institute institute;
    Agency agency;
    Double totalPurchaseValue;
    Double totalSaleValue;
    Double totalProfit;

    public String fillCurrentStocks() {
        Map m = new HashMap();
        String j = "select itms "
                + " from ItemStock itms "
                + " where itms.institute=:ins ";
        if (agency != null) {
            j += " and itms.item.agency=:agency ";
            m.put("agency", agency);
        }
        j += " order by itms.item.name";

        m.put("ins", institute);
        items = getFacade().findBySQL(j, m);
        totalSaleValue = 0.0;
        totalPurchaseValue = 0.0;
        totalProfit = 0.0;
        for (ItemStock is : items) {
            totalSaleValue += is.getTransSaleValue();
            totalPurchaseValue += is.getTransPurchaseValue();
        }
        totalProfit = totalSaleValue - totalPurchaseValue;
        return "/reports/current_stock";
    }

    public String fillCurrentStocksAll() {
        String j = "select itms "
                + " from ItemStock itms "
                + " order by itms.institute.name, itms.item.name";
        Map m = new HashMap();
        items = getFacade().findBySQL(j, m);
        return "/reports/current_all_stock";
    }

    public double findCurrentStocks(Institute ins, Item item) {
        String j = "select itms "
                + " from ItemStock itms "
                + " where itms.institute=:ins "
                + " and itms.item=:item "
                + " order by itms.item.name";
        Map m = new HashMap();
        m.put("ins", ins);
        m.put("item", item);
        ItemStock is = getFacade().findFirstBySQL(j, m);
        if (is == null) {
            return 0.0;
        } else {
            return is.getStock();
        }
    }

    public void deleteAllStocks() {
        String j = "select b from ItemStock b";
        items = getFacade().findBySQL(j);
        for (ItemStock b : items) {
            getFacade().remove(b);
        }
        JsfUtil.addErrorMessage("Deleted all Stocks");
        items = null;
    }

    public void deleteStock() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to delete");
            return;
        }
        getFacade().remove(selected);
        JsfUtil.addErrorMessage("Deleted");
        setInstitute(null);
    }

    public ItemStockController() {
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public ItemStock getSelected() {
        return selected;
    }

    public void setSelected(ItemStock selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ItemStockFacade getFacade() {
        return ejbFacade;
    }

    public ItemStock prepareCreate() {
        selected = new ItemStock();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleS").getString("ItemStockCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleS").getString("ItemStockUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleS").getString("ItemStockDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ItemStock> getItems() {
        if (items == null) {
            items = new ArrayList<ItemStock>(); // getFacade().findAll();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleS").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleS").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ItemStock getItemStock(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ItemStock> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ItemStock> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Double getTotalPurchaseValue() {
        return totalPurchaseValue;
    }

    public void setTotalPurchaseValue(Double totalPurchaseValue) {
        this.totalPurchaseValue = totalPurchaseValue;
    }

    public Double getTotalSaleValue() {
        return totalSaleValue;
    }

    public void setTotalSaleValue(Double totalSaleValue) {
        this.totalSaleValue = totalSaleValue;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @FacesConverter(forClass = ItemStock.class)
    public static class ItemStockControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItemStockController controller = (ItemStockController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itemStockController");
            return controller.getItemStock(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
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
            if (object instanceof ItemStock) {
                ItemStock o = (ItemStock) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ItemStock.class.getName()});
                return null;
            }
        }

    }

}
