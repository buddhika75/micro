package com.sss.wc.controllers;

import com.sss.wc.entity.Bill;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.BillItem;
import com.sss.wc.entity.Institute;
import com.sss.wc.entity.Item;
import com.sss.wc.entity.ItemStock;
import com.sss.wc.entity.Payment;
import com.sss.wc.enums.Agency;
import com.sss.wc.enums.BillCategory;
import com.sss.wc.enums.BillType;
import com.sss.wc.enums.PaymentMethod;
import com.sss.wc.facades.BillFacade;
import com.sss.wc.facades.BillItemFacade;
import com.sss.wc.facades.ItemFacade;
import com.sss.wc.facades.PaymentFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.inject.Inject;
import javax.persistence.TemporalType;
import org.primefaces.event.SelectEvent;

@Named("billController")
@SessionScoped
public class BillController implements Serializable {

    @Inject
    WebUserController webUserController;
    @Inject
    ItemController itemController;
    @Inject
    BillItemController billItemController;
    @Inject
    ApplicationController applicationController;
    @EJB
    private com.sss.wc.facades.BillFacade ejbFacade;
    @EJB
    BillItemFacade billItemFacade;
    @EJB
    PaymentFacade paymentFacade;
    @EJB
    ItemFacade itemFacade;
    private List<Bill> items = null;
    List<Bill> listOfLoadedVehicles = null;
    private Bill selected;
    BillItem selectedBillItem;
    BillItem removingBillItem;
    Institute route;

    private Date fromDate;
    private Date toDate;
    private BillType billType;
    private BillCategory billCategory;

    Agency agency;
    
    Boolean selectedItemsBill = true;

    public BillController() {
    }

    public void fillListOfLoadedBillsForLoggedUser() {
        System.out.println("fillListOfLoadedBills"
                + "");
        String j = "select b from "
                + " Bill b "
                + " where b.billCategory=:cat "
                + " and b.billType=:type "
                + " and b.salesRep.id=:rep ";
        Map m = new HashMap();
        m.put("cat", BillCategory.Loading);
        m.put("type", BillType.Billed_Bill);
        m.put("rep", getWebUserController().getLoggedUser().getId());
        System.out.println("getWebUserController().getLoggedUser() = " + getWebUserController().getLoggedUser());
        listOfLoadedVehicles = getFacade().findBySQL(j, m, TemporalType.DATE, 10);
        System.out.println("listOfLoadedVehicles = " + listOfLoadedVehicles);
        List<Bill> toRemove = new ArrayList<Bill>();
        for (Bill b : listOfLoadedVehicles) {
            if (b.getLoadingBill() != null) {
                toRemove.add(b);
            }
        }
        listOfLoadedVehicles.removeAll(toRemove);
        System.out.println("listOfLoadedVehicles = " + listOfLoadedVehicles);
    }

    public void fillListOfLoadedBillsForAllUsers() {
        System.out.println("fillListOfLoadedBills"
                + "");
        String j = "select b from "
                + " Bill b "
                + " where b.billCategory=:cat "
                + " and b.billType=:type ";
        Map m = new HashMap();
        m.put("cat", BillCategory.Loading);
        m.put("type", BillType.Billed_Bill);
        System.out.println("getWebUserController().getLoggedUser() = " + getWebUserController().getLoggedUser());
        listOfLoadedVehicles = getFacade().findBySQL(j, m, TemporalType.DATE, 10);
        System.out.println("listOfLoadedVehicles = " + listOfLoadedVehicles);
        List<Bill> toRemove = new ArrayList<Bill>();
        for (Bill b : listOfLoadedVehicles) {
            if (b.getLoadingBill() != null) {
                toRemove.add(b);
            }
        }
        listOfLoadedVehicles.removeAll(toRemove);
        System.out.println("listOfLoadedVehicles = " + listOfLoadedVehicles);
    }

    public String toViewBill() {
        return "/reports/bill";
    }

    public String toNewCustomerBill() {
        prepareForNewCustomerBill();
        return "/bill/customer_bill";
    }

    public String toNewGoodReceiveBillCrysbroAllItems() {
        agency = Agency.Crysbro;
        selectedItemsBill=false;
        prepareForNewGoodReceiveBill(agency);
        return "/bill/good_receive_bill";
    }

    public String toNewGoodReceiveBillCrysbroSingleItem() {
        agency = Agency.Crysbro;
        selectedItemsBill=true;
        prepareForNewGoodReceiveBill(agency);
        return "/bill/good_receive_bill_selected";
    }

    public String toNewGoodReceiveBillKeellsAllItems() {
        agency = Agency.Keells;
        selectedItemsBill=false;
        prepareForNewGoodReceiveBill(agency);
        return "/bill/good_receive_bill";
    }

    public String toNewGoodReceiveBillKeellsSingleItem() {
        agency = Agency.Keells;
        selectedItemsBill=true;
        prepareForNewGoodReceiveBill(agency);
        return "/bill/good_receive_bill_selected";
    }

    public String toNewGoodReceiveBillEhAllItems() {
        agency = Agency.EH;
        selectedItemsBill=false;
        prepareForNewGoodReceiveBill(agency);
        return "/bill/good_receive_bill";
    }

    public String toNewGoodReceiveBillEhSingleItem() {
        agency = Agency.EH;
        selectedItemsBill=true;
        prepareForNewGoodReceiveBill(agency);
        return "/bill/good_receive_bill_selected";
    }

    public String toNewGoodReceiveBill() {
        prepareForNewGoodReceiveBill(agency);
        return "/bill/good_receive_bill";
    }

    public String toNewLoadingBill() {
        prepareForNewLoadingBill();
        return "/bill/loading_bill";
    }

    public String toNewUnloadingBill() {
        prepareForNewUnloadingBill();
        return "/bill/unloading_bill";
    }

    public void prepareBillItemWhenVehicleChangesForCustomerBill() {
        System.out.println("prepareBillItemWhenVehicleChangesForCustomerBill");
        if (selected == null) {
            System.out.println("selecte bill");
            return;
        }
        if (selected.getLoadingBillForCustomerBills() == null) {
            System.out.println("getLoadingBillForCustomerBills is null");
            return;
        }
        if (selected.getBillItems() != null || !selected.getBillItems().isEmpty()) {
            for (BillItem bi : selected.getBillItems()) {
                if (bi.getId() != null) {
                    getBillItemFacade().remove(bi);
                }
            }
        }

        selected.setBillItems(new ArrayList<BillItem>());
        List<ItemStock> stocks = applicationController.fillAllItemStocks(selected.getLoadingBillForCustomerBills().getVehicle());
        System.out.println("stocks = " + stocks);
        int count = 1;
        for (ItemStock is : stocks) {
            BillItem bi = new BillItem();
            bi.setBill(selected);
            bi.setItem(is.getItem());
            bi.setRate(is.getItem().getRetailerRate());
            bi.setRetailRate(is.getItem().getRetailRate());
            bi.setItemStock(is);
            bi.setSerial(count);
            bi.calculateNetValue();
            selected.getBillItems().add(bi);
            count++;
            System.out.println("count = " + count);
        }
    }

    public void prepareBillItemWhenVehicleChangesForUnloadingBill() {
        System.out.println("prepareBillItemWhenVehicleChangesForCustomerBill");
        if (selected == null) {
            System.out.println("selecte bill");
            return;
        }
        if (selected.getLoadingBill() == null) {
            JsfUtil.addErrorMessage("Select Vehicle");
            System.out.println("getLoadingBillForCustomerBills is null");
            return;
        } else {
            selected.setVehicle(selected.getLoadingBill().getVehicle());
        }
        if (selected.getBillItems() != null || !selected.getBillItems().isEmpty()) {
            for (BillItem bi : selected.getBillItems()) {
                if (bi.getId() != null) {
                    getBillItemFacade().remove(bi);
                }
            }
        }

        selected.setBillItems(new ArrayList<BillItem>());
        List<ItemStock> stocks = applicationController.fillAllItemStocks(selected.getVehicle());
        System.out.println("stocks = " + stocks);
        int count = 1;
        for (ItemStock is : stocks) {
            BillItem bi = new BillItem();
            bi.setBill(selected);
            bi.setItem(is.getItem());
            bi.setItemStock(is);
            bi.setQuentity(is.getStock());
            bi.setExpectedQuentity(is.getStock());
            bi.setSerial(count);
            selected.getBillItems().add(bi);
            count++;
            System.out.println("count = " + count);
        }
    }

    public void prepareForNewCustomerBill() {

        selected = new Bill();
        selected.setBillType(BillType.Pre_Bill);
        selected.setBillCategory(BillCategory.Customer_Sale);
        selected.setBilledUser(getWebUserController().getLoggedUser());
        getFacade().create(selected);

        List<Payment> payments = new ArrayList<Payment>();

        Payment cash = new Payment();
        cash.setPaymentMethod(PaymentMethod.Cash);
        getPaymentFacade().create(cash);
        payments.add(cash);
        cash.setBill(selected);

        Payment credit = new Payment();
        credit.setPaymentMethod(PaymentMethod.Cheque);
        getPaymentFacade().create(credit);
        payments.add(credit);
        credit.setBill(selected);

        Payment cheque = new Payment();
        cheque.setPaymentMethod(PaymentMethod.Credit);
        getPaymentFacade().create(cheque);
        payments.add(cheque);
        cheque.setBill(selected);

        selected.setPayments(payments);
        System.out.println("prepareForNewCustomerBill = " + selected);
        fillListOfLoadedBillsForLoggedUser();
        getFacade().edit(selected);
    }

    public void prepareForNewLoadingBill() {
        selected = new Bill();
        selected.setBillType(BillType.Pre_Bill);
        selected.setBillCategory(BillCategory.Loading);
        selected.setBilledUser(getWebUserController().getLoggedUser());
        getFacade().create(selected);

    }

    public void prepareForNewUnloadingBill() {
        selected = new Bill();
        selected.setBillType(BillType.Pre_Bill);
        selected.setBillCategory(BillCategory.Unloading);
        selected.setBilledUser(getWebUserController().getLoggedUser());
        fillListOfLoadedBillsForAllUsers();
        getFacade().create(selected);
    }

    public void deleteBill() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to delete");
            return;
        }
        getFacade().remove(selected);
        JsfUtil.addErrorMessage("Delete");
        items = null;
    }

    public void deleteAllBills() {
        String j = "select b from Bill b";
        items = getFacade().findBySQL(j);
        for (Bill b : items) {
            getFacade().remove(b);
        }
        JsfUtil.addErrorMessage("Deleted all bills");
        items = null;
    }

    public void prepareBillItemWhenStoreChangesForLoadingBill() {
        if (selected == null) {
            System.out.println("selected bull");
            return;
        }
        if (selected.getFromInstitute() == null) {
            return;
        }
        if (selected.getBillItems() != null || !selected.getBillItems().isEmpty()) {
            for (BillItem bi : selected.getBillItems()) {
                if (bi.getId() != null) {
                    getBillItemFacade().remove(bi);
                }
            }
        }

        selected.setBillItems(new ArrayList<BillItem>());
        List<ItemStock> stocks = applicationController.fillAllItemStocks(selected.getFromInstitute());
        int count = 1;
        for (ItemStock is : stocks) {
            BillItem bi = new BillItem();
            bi.setBill(selected);
            bi.setItem(is.getItem());
            bi.setRate(is.getItem().getWholesaleRate());
            bi.setRetailRate(is.getItem().getRetailRate());
            bi.setItemStock(is);
            bi.setSerial(count);
            selected.getBillItems().add(bi);
            count++;
        }
    }

     public void prepareForNewGoodReceiveBill(Agency agency) {
         if(selectedItemsBill){
             prepareForNewGoodReceiveBillSingleItem(agency);
         }else{
             prepareForNewGoodReceiveBillAllItems(agency);
         }
     }
    
    
    public void prepareForNewGoodReceiveBillAllItems(Agency agency) {
      
        selected = new Bill();
        selected.setBillType(BillType.Pre_Bill);
        selected.setBillCategory(BillCategory.Good_Receive);
        selected.setBilledUser(getWebUserController().getLoggedUser());
        List<Item> tis = getItemController().getAgencyItems(agency);
        int count = 1;
        for (Item i : tis) {
            BillItem bi = new BillItem();
            bi.setBill(selected);
            bi.setItem(i);
            if (i.getDealerRate() != null && i.getDealerRate() != 0.0) {
                bi.setRate(i.getDealerRate());
            }
            if (i.getRetailRate() != null && i.getRetailRate() != 0.0) {
                bi.setRetailRate(i.getRetailRate());
            }
            bi.setSerial(count);
            selected.getBillItems().add(bi);
            count++;
        }
        getFacade().create(selected);

        List<Payment> payments = new ArrayList<Payment>();

        Payment cash = new Payment();
        cash.setPaymentMethod(PaymentMethod.Cash);
        cash.setBill(selected);
        cash.setReceiving(true);
        cash.setPaying(false);
        getPaymentFacade().create(cash);
        payments.add(cash);

        Payment credit = new Payment();
        credit.setPaymentMethod(PaymentMethod.Cheque);
        credit.setReceiving(true);
        credit.setPaying(false);
        credit.setBill(selected);
        getPaymentFacade().create(credit);
        payments.add(credit);

        Payment cheque = new Payment();
        cheque.setPaymentMethod(PaymentMethod.Credit);
        cheque.setBill(selected);
        cheque.setReceiving(true);
        cheque.setPaying(false);
        getPaymentFacade().create(cheque);
        payments.add(cheque);

        selected.setPayments(payments);

        getFacade().edit(selected);
    }

    public void onItemSelect(SelectEvent event) {
        if (selectedBillItem == null) {
            JsfUtil.addErrorMessage("No Selected Bill Item.");
            return;
        }
        if(selectedBillItem.getItem()==null){
            JsfUtil.addErrorMessage("No Selected Bill Item.");
            return;
        }
        if (selectedBillItem.getItem().getDealerRate() != null && selectedBillItem.getItem().getDealerRate() != 0.0) {
            selectedBillItem.setRate(selectedBillItem.getItem().getDealerRate());
        }
        if (selectedBillItem.getItem().getRetailRate() != null && selectedBillItem.getItem().getRetailRate() != 0.0) {
            selectedBillItem.setRetailRate(selectedBillItem.getItem().getRetailRate());
        }
        
    }
    
    public void addItemToGoodReceiveBill() {
        if (selectedBillItem == null) {
            JsfUtil.addErrorMessage("No Selected Bill Item.");
            return;
        }
        if(selectedBillItem.getItem()==null){
            JsfUtil.addErrorMessage("No Selected Bill Item.");
            return;
        }
        selectedBillItem.setBill(selected);
        
        
        selectedBillItem.setSerial(selected.getBillItems().size() + 1);
        selected.getBillItems().add(selectedBillItem);
        
        selected.calculateTotals();
        
        selectedBillItem = null;
        
    }

    public void prepareForNewGoodReceiveBillSingleItem(Agency agency) {
        selected = new Bill();
        selected.setBillType(BillType.Pre_Bill);
        selected.setBillCategory(BillCategory.Good_Receive);
        selected.setBilledUser(getWebUserController().getLoggedUser());
        getFacade().create(selected);

        List<Payment> payments = new ArrayList<Payment>();

        Payment cash = new Payment();
        cash.setPaymentMethod(PaymentMethod.Cash);
        cash.setBill(selected);
        cash.setReceiving(true);
        cash.setPaying(false);
        getPaymentFacade().create(cash);
        payments.add(cash);

        Payment credit = new Payment();
        credit.setPaymentMethod(PaymentMethod.Cheque);
        credit.setReceiving(true);
        credit.setPaying(false);
        credit.setBill(selected);
        getPaymentFacade().create(credit);
        payments.add(credit);

        Payment cheque = new Payment();
        cheque.setPaymentMethod(PaymentMethod.Credit);
        cheque.setBill(selected);
        cheque.setReceiving(true);
        cheque.setPaying(false);
        getPaymentFacade().create(cheque);
        payments.add(cheque);

        selected.setPayments(payments);

        getFacade().edit(selected);
    }

    public String settleLoadingBill() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to save");
            return "";
        }
        if (selected.getVehicle() == null) {
            JsfUtil.addErrorMessage("Select Vehicle");
            return "";
        }
        if (selected.getSalesRep() == null) {
            JsfUtil.addErrorMessage("Select Sales Rep");
            return "";
        }
        if (selected.getFromInstitute() == null) {
            JsfUtil.addErrorMessage("Select Stores");
            return "";
        }

        for (BillItem bi : selected.getBillItems()) {
            System.out.println("bi = " + bi);
            ItemStock itemStock = bi.getItemStock();
            System.out.println("itemStock = " + itemStock);
            double inStock = itemStock.getStock();
            System.out.println("inStock = " + inStock);
            System.out.println("bi.getQuentity() = " + bi.getQuentity());
            if ((bi.getQuentity()) > inStock) {
                JsfUtil.addErrorMessage("No Enough Stocks in Stores");
                return "";
            }
        }

        for (BillItem bi : selected.getBillItems()) {
            ItemStock isfrom = bi.getItemStock();
            applicationController.deductFromStock(isfrom, (bi.getQuentity()));
            ItemStock isTo = getApplicationController().findItemStock(bi.getItem(), bi.getItemStock().getRetailRate(),
                    selected.getVehicle());
            applicationController.addToStock(isTo, (bi.getQuentity()));
        }

        selected.setBillAt(new Date());
        selected.setBillDate(new Date());
        selected.setBillTime(new Date());
        selected.setBillType(BillType.Billed_Bill);

        getFacade().edit(selected);

        return "/bill/loading_bill_print";
    }

    public String settleUnloadingBill() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to save");
            return "";
        }
        if (selected.getLoadingBill() == null) {
            JsfUtil.addErrorMessage("Select Vehicle");
            return "";
        }

        if (selected.getToInstitute() == null) {
            JsfUtil.addErrorMessage("Select Stores");
            return "";
        }
        if (selected.getSalesRep() == null) {
            selected.setSalesRep(selected.getLoadingBill().getSalesRep());
        }
        if (selected.getVehicle() == null) {
            selected.setVehicle(selected.getLoadingBill().getVehicle());
        }

        for (BillItem bi : selected.getBillItems()) {
            ItemStock isfrom = bi.getItemStock();
            applicationController.adjustStock(isfrom, 0);
            ItemStock isTo = getApplicationController().findItemStock(bi.getItem(), bi.getItemStock().getRetailRate(),
                    selected.getToInstitute());
            applicationController.addToStock(isTo, (bi.getQuentity()));
        }

        selected.setBillAt(new Date());
        selected.setBillDate(new Date());
        selected.setBillTime(new Date());
        selected.setBillType(BillType.Billed_Bill);

        getFacade().edit(selected);

        return "/bill/unloading_bill_print";
    }

    public String settleCustomerBill() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to save");
            return "";
        }
        if (getRoute() == null) {
            JsfUtil.addErrorMessage("Select Route");
            return "";
        } else {
            selected.setRoute(route);
        }
        if (selected.getToInstitute() == null) {
            JsfUtil.addErrorMessage("Select Customer");
            return "";
        }
        if (selected.getLoadingBillForCustomerBills() == null) {
            JsfUtil.addErrorMessage("Select Vehicle");
            return "";
        } else {
            selected.setVehicle(selected.getLoadingBillForCustomerBills().getVehicle());
        }

        for (BillItem bi : selected.getBillItems()) {
            double inStock = getApplicationController().findItemStock(bi.getItem(), bi.getItem().getRetailRate(),
                    selected.getVehicle()).getStock();
            if ((bi.getQuentity() + bi.getFreeQuentity()) > inStock) {
                JsfUtil.addErrorMessage("No Enough Stocks");
                return "";
            }
        }

        for (BillItem bi : selected.getBillItems()) {
            ItemStock is = getApplicationController().findItemStock(bi.getItem(), bi.getItem().getRetailRate(),
                    selected.getVehicle());
            applicationController.deductFromStock(is, (bi.getQuentity() + bi.getFreeQuentity()));
        }

        selected.setBillAt(new Date());
        selected.setBillDate(new Date());
        selected.setBillTime(new Date());
        selected.calculateTotalsForCustomerBills();

        if (null == selected.getPaymentMethod()) {
            selected.setSettled(Boolean.FALSE);
            selected.setSettledValue(0.0);
            selected.setToSettleValue(selected.getBillNetTotal());
        } else {
            switch (selected.getPaymentMethod()) {
                case Cash:
                case Credit_Card:
                case Bank_Transfer:
                    selected.setSettled(Boolean.TRUE);
                    selected.setSettledValue(selected.getBillNetTotal());
                    selected.setToSettleValue(0.0);
                    break;
                case Cheque:
                case Credit:
                default:
                    selected.setSettled(Boolean.FALSE);
                    selected.setSettledValue(0.0);
                    selected.setToSettleValue(selected.getBillNetTotal());
                    break;
            }
        }
        selected.setBillType(BillType.Billed_Bill);
        for (Payment p : selected.getPayments()) {
            p.setCompleted(true);
            getPaymentFacade().edit(p);
        }
        getFacade().edit(selected);
        return "/bill/customer_bill_print";
    }

    public String settleGoodReceiveBill() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to save");
            return "";
        }
        if (selected.getToInstitute() == null) {
            JsfUtil.addErrorMessage("Select Stores");
            return "";
        }
        int count = 0;
        for (BillItem bi : selected.getBillItems()) {
            count++;
            System.out.println("count = " + count);
            System.out.println("bi.getItem() = " + bi.getItem().getName());
            if (bi.getItem() == null) {
                continue;
            }
            System.out.println("bi.getItem().getRetailRate() = " + bi.getRetailRate());
            if (bi.getRetailRate() == null) {
                continue;
            } else {
                if (bi.getItem().getRetailRate() != null) {
                    if (!bi.getItem().getRetailRate().equals(bi.getRetailRate())) {
                        bi.getItem().setRetailRate(bi.getRetailRate());
                        getItemFacade().edit(bi.getItem());
                    }
                } else {
                    bi.getItem().setRetailRate(bi.getRetailRate());
                    getItemFacade().edit(bi.getItem());
                }
            }
            if (bi.getRate() == null) {
                continue;
            } else {
                if (bi.getItem().getDealerRate() != null) {
                    if (!bi.getItem().getDealerRate().equals(bi.getRate())) {
                        bi.getItem().setDealerRate(bi.getRate());
                        getItemFacade().edit(bi.getItem());
                    }
                } else {
                    bi.getItem().setDealerRate(bi.getRate());
                    getItemFacade().edit(bi.getItem());
                }
            }
            System.out.println("selected.getToInstitute() = " + selected.getToInstitute());
            if (selected.getToInstitute() == null) {
                continue;
            }
            ItemStock is = getApplicationController().findItemStock(
                    bi.getItem(),
                    bi.getRetailRate(),
                    bi.getRate(),
                    selected.getToInstitute());
            System.out.println("is = " + is);

            if (is == null) {
                continue;
            }

            System.out.println("is.getStock() = " + is.getStock());

            double addingQty = 0.0;

            if (bi.getQuentity() == null) {
                System.out.println("Bill QUentity is null");
            } else {
                addingQty = bi.getQuentity();
                if (bi.getFreeQuentity() != null) {
                    addingQty += bi.getFreeQuentity();
                }
                if (bi.getReturnQuentity() != null) {
                    addingQty -= bi.getReturnQuentity();
                }
                applicationController.addToStock(is, addingQty);
            }

            System.out.println("is.getStock() = " + is.getStock());

        }

        selected.setBillAt(new Date());
        selected.setBillDate(new Date());
        selected.setBillTime(new Date());
        selected.calculateTotalsForGoodReceiveBills();

        selected.setBillType(BillType.Billed_Bill);
        getFacade().edit(selected);
        return "/bill/good_receive_bill_print";
    }

    public void fillBills() {
        String j = "select b "
                + " from Bill b "
                + " where b.billCategory =:bc "
                + " and b.billDate between :fd and :td "
                + " order by b.id";
        Map m = new HashMap();
        m.put("bc", billCategory);
        m.put("fd", fromDate);
        m.put("td", toDate);
        items = getFacade().findBySQL(j, m);
    }

    public void fillAllBills() {
        String j = "select b "
                + " from Bill b";
        Map m = new HashMap();
        items = getFacade().findBySQL(j);
    }

    public List<Item> completeAgencyItems(String qry) {
        Map m = new HashMap();
        m.put("a", agency);
        m.put("q", "%" + qry.toLowerCase() + "%");
        String j;
        j = "select i "
                + " from Item i "
                + " where i.agency=:a "
                + " and lower(i.name) like :q "
                + " order by i.name";
        return getFacade().findBySQL(j, m);
    }
    
    public BillItemController getBillItemController() {
        return billItemController;
    }

    public Institute getRoute() {
        return route;
    }

    public WebUserController getWebUserController() {
        return webUserController;
    }

    public void setRoute(Institute route) {
        this.route = route;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public Bill getSelected() {
        return selected;
    }

    public void setSelected(Bill selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public BillItemFacade getBillItemFacade() {
        return billItemFacade;
    }

    public void setBillItemFacade(BillItemFacade billItemFacade) {
        this.billItemFacade = billItemFacade;
    }

    private BillFacade getFacade() {
        return ejbFacade;
    }

    public Bill prepareCreate() {
        selected = new Bill();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleBills").getString("BillCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleBills").getString("BillUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleBills").getString("BillDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Bill> getItems() {
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

    public Bill getBill(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Bill> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Bill> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public ApplicationController getApplicationController() {
        return applicationController;
    }

    public void setApplicationController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    public BillFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(BillFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Date getFromDate() {
        if (fromDate == null) {
            fromDate = new Date();
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            toDate = new Date();
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public BillCategory getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(BillCategory billCategory) {
        this.billCategory = billCategory;
    }

    public List<Bill> getListOfLoadedVehicles() {
        return listOfLoadedVehicles;
    }

    public void setListOfLoadedVehicles(List<Bill> listOfLoadedVehicles) {
        this.listOfLoadedVehicles = listOfLoadedVehicles;
    }

    public PaymentFacade getPaymentFacade() {
        return paymentFacade;
    }

    public void setPaymentFacade(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public ItemFacade getItemFacade() {
        return itemFacade;
    }

    public BillItem getSelectedBillItem() {
        if (selectedBillItem == null) {
            selectedBillItem = new BillItem();
            selectedBillItem.setBill(selected);
        }
        return selectedBillItem;
    }

    public void setSelectedBillItem(BillItem selectedBillItem) {
        this.selectedBillItem = selectedBillItem;
    }

    public BillItem getRemovingBillItem() {
        return removingBillItem;
    }

    public void setRemovingBillItem(BillItem removingBillItem) {
        this.removingBillItem = removingBillItem;
    }

    @FacesConverter(forClass = Bill.class)
    public static class BillControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BillController controller = (BillController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "billController");
            return controller.getBill(getKey(value));
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
            if (object instanceof Bill) {
                Bill o = (Bill) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Bill.class.getName()});
                return null;
            }
        }

    }

}
