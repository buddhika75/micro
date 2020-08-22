package com.sss.wc.controllers;

import com.sss.wc.entity.WebUser;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.UserPrivilege;
import com.sss.wc.enums.Privilege;
import com.sss.wc.enums.UserType;
import com.sss.wc.facades.WebUserFacade;

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
import javax.enterprise.context.SessionScoped;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@Named
@SessionScoped
public class WebUserController implements Serializable {

    @EJB
    private com.sss.wc.facades.WebUserFacade ejbFacade;

    @Inject
    UserPrivilegeController userPrivilegeController;

    private List<WebUser> items = null;
    private WebUser selected;
    List<UserPrivilege> loggedUserPrivileges;

    String userName;
    String password;

    WebUser loggedUser;
    boolean canAddSale;
    boolean canCencel;
    boolean canViewRecentReports;
    boolean canVIewAllReports;
    boolean canLoadAndUnload;
    boolean canAdjustStocks;
    boolean canManageCredit;
    boolean canManagement;
    boolean canSystemAdministration;
    
    public List<WebUser> completeRep(String qry){
        String j ="select w from WebUser w "
                + " where lower(w.name) like :s "
                + " and w.active=true "
                + " order by w.name";
        Map m = new HashMap();
        m.put("s", "%" + qry.toLowerCase() + "%");
        return getFacade().findBySQL(j, m);
    }

    public String toManageUsers() {
        return "/webUser/index";
    }

    public String toAddNewUser() {
        selected = new WebUser();
        selected.setActive(true);
        selected.setUserType(UserType.System_User);
        return "/admin/add_new_user";
    }

    public String toAddNewRep() {
        selected = new WebUser();
        selected.setActive(true);
        selected.setUserType(UserType.Sales_Representative);
        return "/admin/add_new_rep";
    }
    
    public String toManagePrivilege() {
        userPrivilegeController.setWebUser(selected);
        return "/admin/manage_privileges";
    }

    public String saveNewUser() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Error");
            return "";
        }
        if (selected.getUserName() == null || selected.getUserName().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a username");
            return "";
        }
        if (selected.getName() == null || selected.getName().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a Name");
            return "";
        }
        if (selected.getPassword() == null || selected.getPassword().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a Password");
            return "";
        }
        if (!selected.getPassword().equals(selected.getRepeatPassword())) {
            JsfUtil.addErrorMessage("Passwords are NOT maching");
            return "";
        }
        if (selected.getUserName().length() < 5) {
            JsfUtil.addErrorMessage("Please use a longer username");
            return "";
        }
        if (selected.getPassword().length() < 6) {
            JsfUtil.addErrorMessage("Please use a longer password");
        }
        try {
            getFacade().create(selected);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("That username already exists. Please use another username");
            System.out.println("e = " + e);
            return "";
        }

        UserPrivilege ui1 = new UserPrivilege();
        ui1.setWebUser(selected);
        ui1.setPrivilege(Privilege.Registered_User_By_System);
        getUserPrivilegeController().createOrUpdate(ui1);


        return "/webUser/index";
    }
    
    
     public String selfRegisterUser() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Error");
            return "";
        }
        if (selected.getUserName() == null || selected.getUserName().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a username");
            return "";
        }
        if (selected.getName() == null || selected.getName().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a Name");
            return "";
        }
        if (selected.getPassword() == null || selected.getPassword().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a Password");
            return "";
        }
        if (!selected.getPassword().equals(selected.getRepeatPassword())) {
            JsfUtil.addErrorMessage("Passwords are NOT maching");
            return "";
        }
        if (selected.getUserName().length() < 5) {
            JsfUtil.addErrorMessage("Please use a longer username");
            return "";
        }
        if (selected.getPassword().length() < 6) {
            JsfUtil.addErrorMessage("Please use a longer password");
        }
        try {
            getFacade().create(selected);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("That username already exists. Please use another username");
            System.out.println("e = " + e);
            return "";
        }

        UserPrivilege ui1 = new UserPrivilege();
        ui1.setWebUser(selected);
        ui1.setPrivilege(Privilege.Self_Registered_user);
        getUserPrivilegeController().createOrUpdate(ui1);



        return "/mobile/registered";
    }
    
    public String saveNewUserBySystemAdmin() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Error");
            return "";
        }
        if (selected.getUserName() == null || selected.getUserName().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a username");
            return "";
        }
        if (selected.getName() == null || selected.getName().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a Name");
            return "";
        }
        if (selected.getPassword() == null || selected.getPassword().trim().equals("")) {
            JsfUtil.addErrorMessage("Please enter a Password");
            return "";
        }
        if (!selected.getPassword().equals(selected.getRepeatPassword())) {
            JsfUtil.addErrorMessage("Passwords are NOT maching");
            return "";
        }
        if (selected.getUserName().length() < 5) {
            JsfUtil.addErrorMessage("Please use a longer username");
            return "";
        }
        if (selected.getPassword().length() < 6) {
            JsfUtil.addErrorMessage("Please use a longer password");
        }
        try {
            getFacade().create(selected);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("That username already exists. Please use another username");
            System.out.println("e = " + e);
            return "";
        }

        UserPrivilege ui1 = new UserPrivilege();
        ui1.setWebUser(selected);
        ui1.setPrivilege(Privilege.Registered_User_By_System);
        getUserPrivilegeController().createOrUpdate(ui1);



        return "/webUser/index";
    }

    public String toViewUsers() {
        items = null;
        getItems();
        return "/admin/view_users";
    }

    private void createLoginData() {
        if (loggedUser == null) {
            return;
        }
        loggedUserPrivileges = getUserPrivilegeController().getItems(loggedUser);

        canAddSale = hasPrivilege(Privilege.Add_Sale);
        canVIewAllReports = hasPrivilege(Privilege.View_All_Reports);
        canViewRecentReports = hasPrivilege(Privilege.View_Recent_Reports);
        canManageCredit = hasPrivilege(Privilege.Manage_Credit);
        canAdjustStocks = hasPrivilege(Privilege.Adjust_Stocks);
        canManagement = hasPrivilege(Privilege.System_Management);
        canLoadAndUnload = hasPrivilege(Privilege.Loading_Unloading);
        canCencel = hasPrivilege(Privilege.Cancel_Sale);
        canSystemAdministration = hasPrivilege(Privilege.System_Administration);

    }

    private boolean hasPrivilege(Privilege p) {
        boolean b = false;
        for (UserPrivilege up : getLoggedUserPrivileges()) {
            if (p.equals(up.getPrivilege())) {
                b = true;
            }
        }
        return b;
    }

    public boolean isCanAddSale() {
        return canAddSale;
    }

    public void setCanAddSale(boolean canAddSale) {
        this.canAddSale = canAddSale;
    }

    public boolean isCanCencel() {
        return canCencel;
    }

    public void setCanCencel(boolean canCencel) {
        this.canCencel = canCencel;
    }

    public boolean isCanViewRecentReports() {
        return canViewRecentReports;
    }

    public void setCanViewRecentReports(boolean canViewRecentReports) {
        this.canViewRecentReports = canViewRecentReports;
    }

    public boolean isCanVIewAllReports() {
        return canVIewAllReports;
    }

    public void setCanVIewAllReports(boolean canVIewAllReports) {
        this.canVIewAllReports = canVIewAllReports;
    }

    public boolean isCanLoadAndUnload() {
        return canLoadAndUnload;
    }

    public void setCanLoadAndUnload(boolean canLoadAndUnload) {
        this.canLoadAndUnload = canLoadAndUnload;
    }

    public boolean isCanAdjustStocks() {
        return canAdjustStocks;
    }

    public void setCanAdjustStocks(boolean canAdjustStocks) {
        this.canAdjustStocks = canAdjustStocks;
    }

    public boolean isCanManageCredit() {
        return canManageCredit;
    }

    public void setCanManageCredit(boolean canManageCredit) {
        this.canManageCredit = canManageCredit;
    }

    public boolean isCanSystemManagement() {
        return canManagement;
    }

    public void setCanSystemManagement(boolean canSystem_Management) {
        this.canManagement = canSystem_Management;
    }

    public boolean isCanSystemAdministration() {
        return canSystemAdministration;
    }

    public void setCanSystemAdministration(boolean canSystem_administration) {
        this.canSystemAdministration = canSystem_administration;
    }

    public List<UserPrivilege> getLoggedUserPrivileges() {
        return loggedUserPrivileges;
    }

    public void setLoggedUserPrivileges(List<UserPrivilege> loggedUserPrivileges) {
        this.loggedUserPrivileges = loggedUserPrivileges;
    }

    private void resetLoginData() {
        loggedUser = null;

        canAddSale = false;
        canCencel = false;
        canViewRecentReports = false;
        canVIewAllReports = false;
        canLoadAndUnload = false;
        canAdjustStocks = false;
        canManageCredit = false;
        canManagement = false;
        canSystemAdministration = false;

        loggedUserPrivileges = new ArrayList<UserPrivilege>();

    }

    public String login() {
        resetLoginData();
        if (userName.trim().equals("")) {
            JsfUtil.addErrorMessage("Enter a Username");
            return "";
        }
        if (password.trim().equals("")) {
            JsfUtil.addErrorMessage("Enter a Password");
            return "";
        }
        String j = "select w from WebUser w where w.userName=:un and w.password=:pw";
        Map m = new HashMap();
        m.put("un", userName);
        m.put("pw", password);
        loggedUser = getFacade().findFirstBySQL(j, m);
        if (loggedUser == null) {
            if (getFacade().count() == 0) {

                loggedUser = new WebUser();
                loggedUser.setActive(true);
                loggedUser.setEmail("buddhika.ari@gmail.com");
                loggedUser.setExecutiveOfficer(true);
                loggedUser.setName("Buddhika");
                loggedUser.setPassword("buddhika123@");
                loggedUser.setUserName("bud");

                getFacade().create(loggedUser);

                UserPrivilege ui1 = new UserPrivilege();
                ui1.setWebUser(loggedUser);
                ui1.setPrivilege(Privilege.Add_Sale);
                getUserPrivilegeController().createOrUpdate(ui1);

                UserPrivilege ui2 = new UserPrivilege();
                ui2.setWebUser(loggedUser);
                ui2.setPrivilege(Privilege.View_All_Reports);
                getUserPrivilegeController().createOrUpdate(ui2);

                UserPrivilege ui3 = new UserPrivilege();
                ui3.setWebUser(loggedUser);
                ui3.setPrivilege(Privilege.View_Recent_Reports);
                getUserPrivilegeController().createOrUpdate(ui3);

                UserPrivilege ui4 = new UserPrivilege();
                ui4.setWebUser(loggedUser);
                ui4.setPrivilege(Privilege.Manage_Credit);
                getUserPrivilegeController().createOrUpdate(ui4);

                UserPrivilege ui5 = new UserPrivilege();
                ui5.setWebUser(loggedUser);
                ui5.setPrivilege(Privilege.Adjust_Stocks);
                getUserPrivilegeController().createOrUpdate(ui5);

                UserPrivilege ui6 = new UserPrivilege();
                ui6.setWebUser(loggedUser);
                ui6.setPrivilege(Privilege.System_Management);
                getUserPrivilegeController().createOrUpdate(ui6);

                UserPrivilege ui7 = new UserPrivilege();
                ui7.setWebUser(loggedUser);
                ui7.setPrivilege(Privilege.Cancel_Sale);
                getUserPrivilegeController().createOrUpdate(ui7);

                UserPrivilege ui8 = new UserPrivilege();
                ui8.setWebUser(loggedUser);
                ui8.setPrivilege(Privilege.System_Administration);
                getUserPrivilegeController().createOrUpdate(ui8);

                UserPrivilege ui9 = new UserPrivilege();
                ui9.setWebUser(loggedUser);
                ui9.setPrivilege(Privilege.Loading_Unloading);
                getUserPrivilegeController().createOrUpdate(ui9);

            } else {
                JsfUtil.addErrorMessage("Wrong Credentials. Please try again.");
                return "";
            }
        }
        createLoginData();
        JsfUtil.addErrorMessage("Logged Successfully.");
        return "";
    }

    public String logOut() {
        loggedUser = null;
        return "";
    }

    public WebUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(WebUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WebUserController() {
    }

    public WebUser getSelected() {
        if(selected==null){
            selected = new WebUser();
        }
        return selected;
    }

    public void setSelected(WebUser selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private WebUserFacade getFacade() {
        return ejbFacade;
    }

    public WebUser prepareCreate() {
        selected = new WebUser();
        initializeEmbeddableKey();
        return selected;
    }

    public String registerNewUser(){
        if(selected==null){
            JsfUtil.addErrorMessage("Error");
            return "";
        }
        if(selected.getId()==null){
            getFacade().create(selected);
            return "/login";
        }else{
            getFacade().edit(selected);
            return "/login";
        }
    }
    
    public void createNewUser(){
        if(selected==null){
            JsfUtil.addErrorMessage("No User");
            return;
        }
        
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("WebUserCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("WebUserUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("WebUserDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<WebUser> getItems() {
        if (items == null) {
            String j;
            j = "select w "
                    + " from WebUser w "
                    + " order by w.name";
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

    public List<WebUser> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<WebUser> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public UserPrivilegeController getUserPrivilegeController() {
        return userPrivilegeController;
    }


    @FacesConverter(forClass = WebUser.class)
    public static class WebUserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            WebUserController controller = (WebUserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "webUserController");
            return controller.getFacade().find(getKey(value));
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
            if (object instanceof WebUser) {
                WebUser o = (WebUser) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), WebUser.class.getName()});
                return null;
            }
        }

    }

}
