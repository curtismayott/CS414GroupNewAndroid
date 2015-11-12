package com.android.cs414groupnewandroid.objects;

import java.util.ArrayList;

/**
 * Created by darkbobo on 9/28/15.
 */

/*
TODO functionality of updating order : have totalPaid, when user edits pizza, total should be OrderTotal - TotalPaid
*/


public class Order {
    private int orderID;
    private Person customer;
    boolean isPaidFor;
    double amountPaid;
    private ArrayList<Pizza> pizzas;
    private ArrayList<SideItem> sides;
    private ORDER_TYPE orderType;
    public Order() {
        pizzas = new ArrayList<>();
        sides = new ArrayList<>();
    }

    public Order(Person customer, Address address) {
        this.customer = customer;
        this.pizzas = new ArrayList<>();
        sides = new ArrayList<>();
        setIsPaidFor(false);
    }

    public Order(int orderID, Person customer) {
        this.orderID = orderID;
        this.customer = customer;
        sides = new ArrayList<>();
        setIsPaidFor(false);
    }

    public Order(Person customer, ArrayList<Pizza> pizzas) {
        this.customer = customer;
        this.pizzas = pizzas;
        sides = new ArrayList<>();
        setIsPaidFor(false);
    }

    public Order(int orderID, Person customer, ArrayList<Pizza> pizzas) {
        this.orderID = orderID;
        this.customer = customer;
        this.pizzas = pizzas;
        sides = new ArrayList<>();
        setIsPaidFor(false);
    }

    @Override
    public String toString(){
        return getOrderID() + "   "
                + getCustomer().getPhoneNumbers().get(0)
                + "   " + getCustomer().getName()
                + "   " + getOrderTotal()
                + "   " + getOrderType().toString();
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customerName) {
        this.customer = customerName;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public Pizza getPizza(int index){
        return this.pizzas.get(index);
    }

    public SideItem getSide(int index){
        return  this.sides.get(index);
    }

    public void addPizza(Pizza pizza){
        if(this.pizzas == null){
            this.pizzas = new ArrayList<>();
        }
        pizza.setStatus(PIZZA_STATUS.NEW);
        this.pizzas.add(pizza);
    }

    public void removePizza(Pizza pizza){
        if(pizzas.contains(pizza)){
            pizzas.remove(pizza);
        }
    }
    public void removeAllPizzas(){
        this.pizzas.clear();
    }

    public void setPizzas(ArrayList<Pizza> pizzas) {
        if(this.pizzas == null){
            this.pizzas = new ArrayList<>();
        }
        this.pizzas.addAll(pizzas);
    }

    public void updatePizza(int index, Pizza pizza){
        pizzas.set(index, pizza);
    }

    public void sendPizzasToMakeLine(){
        for(Pizza pizza : pizzas){
            pizza.setOrderID(orderID);
            pizza.sendPizzaToMakeline();
        }
    }

    public void sendSidesToMakeLine(){
        for(SideItem side: sides){
            side.setOrderID(orderID);
            side.sendSideToMakeline();
        }
    }

    public double getOrderTotal(){
        double totalPrice = 0.0;
        for(Pizza pizza : pizzas){
            totalPrice += pizza.getPrice();
        }
        for(SideItem item : sides){
            totalPrice += item.getPrice();
        }
        int tmpPrice = (int)(totalPrice * 100);
        totalPrice = tmpPrice / 100.0;
        return totalPrice;
    }

    public boolean isPaidFor() {
        return isPaidFor;
    }

    public void setIsPaidFor(boolean isPaidFor) {
        this.isPaidFor = isPaidFor;
    }

    public void payForOrder(double amount){
        this.amountPaid += amount;
        this.isPaidFor = true;
    }

    public double getAmountPaid(){
        return amountPaid;
    }

    public double getAmountDue(){
        double orderTotal = getOrderTotal();
        return orderTotal - getAmountPaid();
    }
/*
    public double testingGetOrderTotal(){
        return 12.99;
    }
*/
    public void addSide(SideItem side){
        this.sides.add(side);
    }

    public ArrayList<Side> getSides(){
        ArrayList<Side> tmpSides = new ArrayList<>();
        for(SideItem item : sides){
            if(item.getClass().equals(Side.class)){
                tmpSides.add((Side)item);
                //item.setStatus(SIDE_STATUS.NEW);
            }
        }
        return tmpSides;
    }
    public ArrayList<Drink> getDrinks(){
        ArrayList<Drink> tmpSides = new ArrayList<>();
        for(SideItem item : sides){
            if(item.getClass().equals(Drink.class)){
                tmpSides.add((Drink)item);
            }
        }
        return tmpSides;
    }
    public ArrayList<OrderItem> getOrderItems(){
        ArrayList<OrderItem> items = new ArrayList<>();
        items.addAll(pizzas);
        items.addAll(sides);
        return items;
    }
    public void removeItem(OrderItem item){
        if( item instanceof SideItem){
            sides.remove(item);
        }
    }

    public ORDER_TYPE getOrderType(){ return this.orderType; }

    public void setOrderType(ORDER_TYPE orderType){ this.orderType = orderType; }
}
