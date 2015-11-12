package com.android.cs414groupnewandroid.objects;

import java.util.ArrayList;

/**
 * Created by darkbobo on 10/5/15.
 */
public class Register {
    private ArrayList<Order> orders;
    private Employee loggedInEmployee;
    private int storeID;
    private PizzaCatalog catalog;
    private ArrayList<Employee> employees;
    //private WindowManager manager;
    public String TOTAL_TEXT = "Total.........................";
    public Register() {
        super();
        orders = new ArrayList<>();
        employees = new ArrayList<>();
        catalog = new PizzaCatalog();
        addEmployee(new Employee("manager1", new Address("street1", "city1", "state1", "zip1"), new Phone("phone1"), "manager1", "321", ROLE.MANAGER));
        addEmployee(new Employee("chef1", new Address("street2", "city2", "state2", "zip2"), new Phone("phone2"), "chef1", "123", ROLE.CHEF));
        addEmployee(new Employee("cashier1", new Address("street3", "city3", "state3", "zip3"), new Phone("phone3"), "cashier1", "000", ROLE.CASHIER));
    }

    public int getNextOrderID(){
        int orderNumber = 0;
        for(Order o : orders){
            if(o.getOrderID() > orderNumber){
                orderNumber = o.getOrderID();
            }
        }
        return orderNumber + 1;
    }
    public PizzaCatalog getCatalog(){
        return catalog;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
    public Order getOrder(int orderID){
        for(Order o : orders){
            if(o.getOrderID() == orderID){
                System.out.println("ORDERID FOUND");
                return o;
            }
        }
        return null;
    }

    public int addOrder(Order order){
        if(this.orders == null){
            this.orders = new ArrayList<>();
        }
        order.setOrderID(getNextOrderID());
        this.orders.add(order);
        return order.getOrderID();
    }

    public void removeOrder(int orderID){
        for(Order o : orders) {
            if (o.getOrderID() == orderID) {
                orders.remove(o);
                break;
            }
        }
    }

    public void setOrders(ArrayList<Order> orders) {
        if(this.orders == null){
            this.orders = new ArrayList<>();
        }
        this.orders.addAll(orders);
    }

    public ArrayList<Pizza> getMakelinePizzas(){
        ArrayList<Pizza> pizzas = new ArrayList<>();

        for(Order o: orders){
            for(Pizza p: o.getPizzas()){

                if(p.getStatus()== PIZZA_STATUS.MAKELINE){
                    pizzas.add(p);
                }
            }
        }
        return pizzas;
    }

    public ArrayList<Integer> getMakelineID(){
        ArrayList<Integer> id = new ArrayList<>();

        for(Order o: orders){

            for(Pizza p: o.getPizzas()){

                if(p.getStatus()== PIZZA_STATUS.MAKELINE){
                    id.add(p.getOrderID());
                }

            }
            for(SideItem s: o.getSides()){
                if(s.getStatus()== PIZZA_STATUS.MAKELINE) {
                    id.add(s.getOrderID());
                }
            }
        }
        return id;
    }

    public ArrayList<String> getMakelineItems(){
        ArrayList<String> items = new ArrayList<>();

        for(Order o: orders) {
            for (Pizza p : o.getPizzas()) {
                if(p.getStatus() == PIZZA_STATUS.MAKELINE) {
                    items.add(p.getSize() + "        " + p.getSauce() + "        " + p.getToppingList().toString());
                }
            }

            for (SideItem s: o.getSides()){
                if(s.getStatus() == PIZZA_STATUS.MAKELINE){
                    items.add(o.getSides().toString());
                }
            }

        }
        return items;
    }

    public ArrayList<String> getMakelineSides(){
        ArrayList<String> sides = new ArrayList<>();

        for(Order o: orders) {
            for (SideItem s: o.getSides()){
                if(s.getStatus() == PIZZA_STATUS.MAKELINE){
                    sides.add(s.toString());
                }
            }
        }

        return sides;
    }

    public int getOrderIDByPhone(String phoneNumber){
        for(Order o : orders){
            if(o.getCustomer().getPhoneNumbers().get(0).getNumber().equals(phoneNumber)){
                return o.getOrderID();
            }
        }
        return -1;
    }

    public void updateOrder(int orderID, Order order){
        for(int i = 0; i < orders.size(); i++){
            if(orders.get(i).getOrderID() == orderID){
                orders.set(i, order);
                break;
            }
        }
    }

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void setLoggedInEmployee(Employee loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    public void clearLoggedInEmployee() {this.loggedInEmployee = null;}

    public void addEmployee(Employee employee){
        employee.setUserID(employees.size());
        employees.add(employee);
    }

    public void removeEmployee(Employee employee){
        employees.remove(employee);
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public boolean userExists(String s) {
        for(Employee e : employees){
            if(e.getAuthentication().equals(s)){
                return true;
            }
        }
       return false;
    }

    public Employee getEmployeeByAuth(String auth) {
        for(Employee e : employees){
            if(e.getAuthentication().equals(auth)){
                return e;
            }
        }
        return null;
    }

    public ArrayList<Employee> getEmployees(){
        return employees;
    }

    public void updateEmployee(int userID, Employee employee){
        for(int i = 0; i < getEmployees().size(); i++){
            if(employees.get(i).getUserID() == userID){
                employees.set(i, employee);
            }
        }
    }
}
