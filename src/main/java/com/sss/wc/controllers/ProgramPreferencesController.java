package com.sss.wc.controllers;

import com.sss.wc.entity.ProgramPreferences;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.facades.ProgramPreferencesFacade;

import java.io.Serializable;
import java.util.List;
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

@Named("programPreferencesController")
@SessionScoped
public class ProgramPreferencesController implements Serializable {

    @EJB
    private com.sss.wc.facades.ProgramPreferencesFacade ejbFacade;
    private List<ProgramPreferences> items = null;
    private ProgramPreferences selected;

    public ProgramPreferencesController() {
    }

    public ProgramPreferences getSelected() {
        if(selected==null){
            String j ="select pp "
                    + " from ProgramPreferences pp ";
                  
            selected = getFacade().findFirstBySQL(j);
            if(selected==null){
                selected = new ProgramPreferences();
                getFacade().create(selected);
            }
        }
        return selected;
    }

    public void setSelected(ProgramPreferences selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProgramPreferencesFacade getFacade() {
        return ejbFacade;
    }

    public ProgramPreferences prepareCreate() {
        selected = new ProgramPreferences();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProgramPreferencesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProgramPreferencesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProgramPreferencesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProgramPreferences> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ProgramPreferences getProgramPreferences(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ProgramPreferences> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProgramPreferences> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProgramPreferences.class)
    public static class ProgramPreferencesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProgramPreferencesController controller = (ProgramPreferencesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "programPreferencesController");
            return controller.getProgramPreferences(getKey(value));
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
            if (object instanceof ProgramPreferences) {
                ProgramPreferences o = (ProgramPreferences) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProgramPreferences.class.getName()});
                return null;
            }
        }

    }

}
