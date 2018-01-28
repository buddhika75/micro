package com.sss.wc.controllers;

import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.Item;
import com.sss.wc.enums.Agency;
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
    private Item selected;
    List<Item> itemItems;
    Agency agency;

    public ItemController() {
    }

    public List<Item> getItemItems() {
        if (itemItems == null) {
            itemItems = getList(ItemType.Item);
        }
        return itemItems;
    }

    public void setItemItems(List<Item> itemItems) {
        this.itemItems = itemItems;
    }

    public List<Item> completeItemItems(String qry) {
        return getList(ItemType.Item, qry);
    }

    public List<Item> getList(ItemType type, String qry) {
        String j = "select i "
                + " from Item i "
                + " where i.itemType=:t "
                + " and lower(i.name) like :q "
                + " order by i.name";
        Map m = new HashMap();
        m.put("t", type);
        m.put("q", "%" + qry.toLowerCase() + "%");
        return getFacade().findBySQL(j, m);
    }

    public List<Item> getList(ItemType type) {
        String j = "select i "
                + " from Item i "
                + " where i.itemType=:t "
                + " order by i.name";
        Map m = new HashMap();
        m.put("t", type);
        return getFacade().findBySQL(j, m);
    }

    public String toItems() {
        selected = null;
        return "/maintenance/items";
    }

    public String toCrysbroItems() {
        selected = null;
        items = getAgencyItems(Agency.Crysbro);
        return "/maintenance/crysbro_items";
    }

    public String toKeellsItems() {
        selected = null;
        items = getAgencyItems(Agency.Keells);
        return "/maintenance/keells_items";
    }

    public String toEhItems() {
        selected = null;
        items = getAgencyItems(Agency.EH);
        return "/maintenance/eh_items";
    }

    public String toEditItem() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to Edit");
            return "";
        }
        return "/maintenance/items";
    }

    public String toAddNewCysbroItem() {
        agency = Agency.Crysbro;
        addNewItem();
        return "/maintenance/items";
    }

    public String toAddKeellsItem() {
        agency = Agency.Keells;
        addNewItem();
        return "/maintenance/items";
    }

    public String toAddNewEhItem() {
        agency = Agency.EH;
        addNewItem();
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

    public void addNewItem() {
        selected = new Item();
        selected.setAgency(agency);
        selected.setItemType(ItemType.Item);
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
        agency = selected.getAgency();
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
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle1").getString("ItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle1").getString("ItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle1").getString("ItemDeleted"));
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

    public List<Item> getAgencyItems(Agency agency) {
        Map m = new HashMap();
        m.put("a", agency);
        String j;
        j = "select i from Item i where i.agency=:a order by i.name";
        return getFacade().findBySQL(j, m);

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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
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

    public void temConvertAgencyOfAllItemsToKeells() {
        items = null;
        List<Item> its = getItems();
        for (Item i : its) {
            i.setAgency(Agency.Keells);
            getFacade().edit(i);
        }
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
