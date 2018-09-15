package com.sss.wc.controllers;

import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.Item;
import com.sss.wc.enums.ItemType;
import com.sss.wc.facades.ItemFacade;
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

@Named("itemController")
@SessionScoped
public class ItemController implements Serializable {

    @EJB
    private com.sss.wc.facades.ItemFacade ejbFacade;
    private List<Item> items = null;
    private List<Item> conditions = null;
    private List<Item> systems = null;
    List<Item> antibiotics = null;
    private List<Item> rootConditions = null;
    

    private Item selected;
    List<Item> itemItems;

    public ItemController() {

    }

    public List<Item> getRootConditions() {
        rootConditions = getList(ItemType.Condition, true, true);
        return rootConditions;
    }

    public void setRootConditions(List<Item> rootConditions) {
        this.rootConditions = rootConditions;
    }
    
    public String toPreviousPageInConditionNavigation(){
        if(selected.getParentItem()==null){
            return "/conditions";
        }else{
            selected = selected.getParentItem();
            return "/selected_condition";
        }
    }

    public String toSelectedAntibiotic() {
        if (selected == null) {
            return "/mobile";
        }
        return "/selected_antibiotic";
    }

    public String toSelectedCondition() {
        if (selected == null) {
            return "/mobile";
        }
        for(Item i : selected.getChildItems()){
            System.out.println("i = " + i);
        }
        return "/selected_condition";
    }

    public List<Item> getAntibiotics() {
        if (antibiotics == null) {
            antibiotics = getList(ItemType.Antibiotic);
        }
        return antibiotics;
    }

    public void setItemItems(List<Item> itemItems) {
        this.itemItems = itemItems;
    }

    public List<Item> completeAntiobiotics(String qry) {
        return getList(ItemType.Antibiotic, qry);
    }

    public List<Item> completeItems(String qry) {
        return getList(null, qry);
    }

    public List<Item> getList(ItemType type, String qry) {
        String j = "select i "
                + " from Item i "
                + " where lower(i.name) like :q ";
        Map m = new HashMap();
        if (type != null) {
            j += " and i.itemType=:t ";
            m.put("t", type);
        }
        j += " order by i.name";
        m.put("q", "%" + qry.toLowerCase() + "%");
        return getFacade().findBySQL(j, m);
    }

    public String toListConditions() {
        conditions = getList(ItemType.Condition, true);
        return "/maintenance/conditions";
    }

    public String toListAntibiotics() {
        antibiotics = getList(ItemType.Antibiotic);
        return "/maintenance/antibiotics";
    }

    public String toListSystems() {
        systems = getList(ItemType.System, true);
        return "/maintenance/systems";
    }

    public void addNewItem() {
        selected = new Item();
    }

    public String toAddNewAntibiotic() {
        selected = new Item();
        selected.setItemType(ItemType.Antibiotic);
        return toManageAntibiotic();
    }

    public String toAddNewCondition() {
        selected = new Item();
        selected.setItemType(ItemType.Condition);
        return toManageCondition();
    }

    public String toAddNewSystem() {
        selected = new Item();
        selected.setItemType(ItemType.System);
        return toManageSystem();
    }

    public String toManageAntibiotic() {
        return "/maintenance/mx_antibiotics";
    }

    public String toManageSystem() {
        return "/maintenance/mx_systems";
    }

    public String toManageCondition() {
        return "/maintenance/mx_conditions";
    }

    public List<Item> getList(ItemType type) {
        return getList(type, false);
    }

    public List<Item> getList(ItemType type, boolean byOrderNo) {
        String j = "select i "
                + " from Item i "
                + " where i.itemType=:t ";
        if (byOrderNo) {
            j += " order by i.orderNo";
        } else {
            j += " order by i.name";
        }
        Map m = new HashMap();
        m.put("t", type);
        return getFacade().findBySQL(j, m);
    }
    
    public List<Item> getList(ItemType type, boolean byOrderNo, boolean rootElementasOnly) {
        String j = "select i "
                + " from Item i "
                + " where i.itemType=:t ";
         if (rootElementasOnly) {
            j += " and i.parentItem is null ";
        }
        if (byOrderNo) {
            j += " order by i.orderNo";
        } else {
            j += " order by i.name";
        }
        Map m = new HashMap();
        m.put("t", type);
        return getFacade().findBySQL(j, m);
    }

    public String toItems() {
        selected = null;
        return "/maintenance/items";
    }

    public String deleteItemFromList() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to Delete");
            return "";
        }
        try {
            getFacade().remove(selected);
            JsfUtil.addSuccessMessage("Deleted");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Can't delete.\n" + e.getMessage());
            return "";
        }
        return toItems();
    }

    public void saveItem() {
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
        itemItems = null;
        getItems();
    }

    public void deleteItem() {
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
        getItems();
    }

    public Item getSelected() {
        return selected;
    }

    public void setSelected(Item selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ItemFacade getFacade() {
        return ejbFacade;
    }

    public Item prepareCreate() {
        selected = new Item();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Item> getItems() {
        if (items == null) {
            String j;
            j = "select i from Item i order by i.name desc";
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Item getItem(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Item> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<Item> getItemsAvailableSelectOne() {
        return getItems();
    }

    public ItemFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ItemFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Item> getConditions() {
        if (conditions == null) {
            conditions = getList(ItemType.Condition, true);
        }
        return conditions;
    }

    public void setConditions(List<Item> conditions) {
        this.conditions = conditions;
    }

    public List<Item> getSystems() {
        if (systems == null) {
            systems = getList(ItemType.System, true);
        }
        return systems;
    }

    public void setSystems(List<Item> systems) {
        this.systems = systems;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setAntibiotics(List<Item> antibiotics) {
        this.antibiotics = antibiotics;
    }


    @FacesConverter(forClass = Item.class)
    public static class ItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItemController controller = (ItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itemController");
            return controller.getItem(getKey(value));
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
            if (object instanceof Item) {
                Item o = (Item) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Item.class.getName()});
                return null;
            }
        }

    }

}
