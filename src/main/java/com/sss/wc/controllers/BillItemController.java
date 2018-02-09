package com.sss.wc.controllers;

import com.sss.wc.entity.BillItem;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.Institute;
import com.sss.wc.entity.Item;
import com.sss.wc.facades.BillItemFacade;

import java.io.Serializable;
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

@Named("billItemController")
@SessionScoped
public class BillItemController implements Serializable {

    @EJB
    private com.sss.wc.facades.BillItemFacade ejbFacade;
    private List<BillItem> items = null;
    private BillItem selected;

    public BillItem getLstBillItem(Institute ins, Item item) {
        String j;
        Map m = new HashMap();
        j = "select bi "
                + " from BillItem bi "
                + " where bi.item=:i "
                + " and bi.bill.toInstitute=:ins"
                + " order by bi.id desc";
        m.put("i", item);
        m.put("ins", ins);
        return getFacade().findFirstBySQL(j, m);
    }

    public BillItemController() {
    }

    public BillItem getSelected() {
        return selected;
    }

    public void setSelected(BillItem selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BillItemFacade getFacade() {
        return ejbFacade;
    }

    public BillItem prepareCreate() {
        selected = new BillItem();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleBills").getString("BillItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleBills").getString("BillItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleBills").getString("BillItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<BillItem> getItems() {
        if (items == null) {
            items = getFacade().findAll();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleBills").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleBills").getString("PersistenceErrorOccured"));
            }
        }
    }

    public BillItem getBillItem(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<BillItem> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<BillItem> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = BillItem.class)
    public static class BillItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BillItemController controller = (BillItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "billItemController");
            return controller.getBillItem(getKey(value));
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
            if (object instanceof BillItem) {
                BillItem o = (BillItem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BillItem.class.getName()});
                return null;
            }
        }

    }

}