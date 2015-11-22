package com.android.cs414groupnewandroid.objects;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by darkbobo on 10/22/15.
 */
public class PizzaCatalog{
    ArrayList<Topping> toppings;
    ArrayList<Sauce> sauces;
    ArrayList<PizzaSize> sizes;
    ArrayList<SideItem> sides;
    ArrayList<Special> specials;

    public PizzaCatalog(){
        toppings = new ArrayList<>();
        sauces = new ArrayList<>();
        sizes = new ArrayList<>();
        sides = new ArrayList<>();
        specials = new ArrayList<>();
        initData();
    }

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    public ArrayList<Sauce> getSauces() {
        return sauces;
    }

    public void setSauces(ArrayList<Sauce> sauces) {
        this.sauces = sauces;
    }

    public ArrayList<PizzaSize> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<PizzaSize> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<Side> getSides(){
        ArrayList<Side> tmpSides = new ArrayList<>();
        for(SideItem item : sides){
            if(item.getClass().equals(Side.class)){
                tmpSides.add((Side)item);
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

    public ArrayList<Object> getEntireCatalog(){
        ArrayList<Object> items = new ArrayList<>();
        items.addAll(toppings);
        items.addAll(sizes);
        items.addAll(sauces);
        items.addAll(sides);
        return items;
    }

    public void deleteItem(Object o){
        if(o instanceof PizzaSize){
            sizes.remove(o);
        }else if(o instanceof Sauce){
            sauces.remove(o);
        }else if(o instanceof Topping){
            toppings.remove(o);
        }else if(o instanceof SideItem){
            sides.remove(o);
        }
    }

    public void updateItem(Object o){
        System.out.println(o);
        if(o instanceof PizzaSize){
            for(int i = 0; i < sizes.size(); i++){
                if(sizes.get(i).getItemID() == ((PizzaSize) o).getItemID()){
                    sizes.set(i, (PizzaSize)o);
                    break;
                }
            }
        }else if(o instanceof Sauce){
            for(int i = 0; i < sauces.size(); i++){
                if(sauces.get(i).getItemID() == ((Sauce) o).getItemID()){
                    sauces.set(i, (Sauce)o);
                    break;
                }
            }
        }else if(o instanceof Topping){
            for(int i = 0; i < toppings.size(); i++){
                if(toppings.get(i).getItemID() == ((Topping) o).getItemID()){
                    toppings.set(i, (Topping)o);
                    break;
                }
            }
        }else if(o instanceof SideItem){
            for(int i = 0; i < sides.size(); i++){
                if(sides.get(i).getItemID() == ((SideItem) o).getItemID()){
                    sides.set(i, (SideItem)o);
                    break;
                }
            }
        }
    }

    @JsonIgnore
    public int getNextItemID(){
        int nextItemID = 0;
        for(Object o : getEntireCatalog()){
            if(o instanceof PizzaSize){
                if(((PizzaSize) o).getItemID() > nextItemID){
                    nextItemID = ((PizzaSize) o).getItemID();
                }
            }else if(o instanceof Sauce){
                if(((Sauce) o).getItemID() > nextItemID){
                    nextItemID = ((Sauce) o).getItemID();
                }
            } else if(o instanceof Topping){
                if(((Topping) o).getItemID() > nextItemID){
                    nextItemID = ((Topping) o).getItemID();
                }
            }else if(o instanceof SideItem){
                if(((SideItem) o).getItemID() > nextItemID){
                    nextItemID = ((SideItem) o).getItemID();
                }
            }
        }
        return nextItemID + 1;
    }

    public void addItem(Object o){
        int nextID = getNextItemID();
        if(o instanceof PizzaSize){
            ((PizzaSize)o).setItemID(nextID);
            sizes.add((PizzaSize)o);
        }else if(o instanceof Sauce){
            ((Sauce)o).setItemID(nextID);
            sauces.add((Sauce)o);
        }else if(o instanceof Topping){
            ((Topping)o).setItemID(nextID);
            toppings.add((Topping)o);
        }else if(o instanceof Side){
            ((Side)o).setItemID(nextID);
            sides.add((Side)o);
        }else if(o instanceof Drink){
            ((Drink)o).setItemID(nextID);
            sides.add((Drink)o);
        }
    }

    public Object getItem(String item){
        Object object = null;
        for(Object o : getEntireCatalog()){
            //System.out.println("O: " + o + "    item: " + item);
            if(o.toString().equals(item)){
                object = o;
                //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^");
                break;
            }
        }
        return object;
    }

    public ArrayList<Special> getSpecials(){
        return specials;
    }

    public void addSpecial(Special special){
        int nextID = -1;
        for(Special s : specials){
            if(s.getSpecialID() > nextID){
                nextID = s.getSpecialID();
            }
        }
        special.setSpecialID(nextID + 1);
        specials.add(special);
    }

    public void updateSpecial(Special special){
        for(int i = 0; i < specials.size(); i++){
            if(special.getSpecialID() == specials.get(i).getSpecialID()){
                specials.set(i, special);
            }
        }
    }

    public PizzaSize getSizeByFullName(String fullName){
        for(PizzaSize size : sizes){
            if(size.getFullName().equals(fullName)){
                return size;
            }
        }
        return null;
    }

    public Sauce getSauceByFullName(String fullName){
        for(Sauce s : sauces){
            if(s.getFullName().equals(fullName)){
                return s;
            }
        }
        return null;
    }

    public SideItem getSideItemByName(String name){
        for(SideItem sideItem : getSides()){
            if(sideItem.getName().equals(name)){
                return sideItem;
            }
        }
        for(SideItem sideItem : getDrinks()){
            if(sideItem.getName().equals(name)){
                return sideItem;
            }
        }
        return null;
    }

    public Special getSpecial(int specialID){
        for(Special s : specials){
            if(s.getSpecialID() == specialID){
                return s;
            }
        }
        return null;
    }

    public void deleteSpecial(Special special){
        specials.remove(special);
    }

    private void initData(){/*
        addItem(new Topping("A", "Anchovies"));
        addItem(new Topping("B", "Beef"));
        addItem(new Topping("C", "Xtra Cheese"));
        addItem(new Topping("D", "Banana Peppers"));
        addItem(new Topping("E", "Green Chilis"));
        addItem(new Topping("F", "Tomatoes"));
        addItem(new Topping("G", "Green Pepper"));
        addItem(new Topping("I", "Artichokes"));
        addItem(new Topping("J", "Jalapenos"));
        addItem(new Topping("K", "Bacon"));
        addItem(new Topping("L", "Green Olives"));
        addItem(new Topping("M", "Mushrooms"));
        addItem(new Topping("N", "Chicken"));
        addItem(new Topping("O", "Onion"));
        addItem(new Topping("P", "Pepperoni"));
        addItem(new Topping("R", "Black Olive"));
        addItem(new Topping("S", "Sausage"));
        addItem(new Topping("U", "Sundried Tomatoes"));
        addItem(new Topping("V", "Feta"));
        addItem(new Topping("W", "Garlic"));

        addItem(new PizzaSize("S", "10\" Small", 3.99));
        addItem(new PizzaSize("M", "12\" Medium", 4.99));
        addItem(new PizzaSize("L", "14\" Large", 6.99));
        addItem(new PizzaSize("XL", "16\"Extra Large", 7.99));

        addItem(new Sauce("M", "Marinara"));
        addItem(new Sauce("O", "Olive Oil"));
        addItem(new Sauce("R", "Ranch"));

        addItem(new Side("Bread Stick", 2.99));
        addItem(new Side("Salad", 2.99));
        addItem(new Side("Ice Cream", 4.99));
        addItem(new Drink("Pepsi", 1.99));
        addItem(new Drink("Sierra Mist", 1.99));
        addItem(new Drink("Diet Pepsi", 1.99));
*/
    }

    public Topping getToppingByName(String s) {
        for(Topping t : toppings){
            if(t.getFullName().equals(s)){
                return t;
            }
        }
        return null;
    }

    public Drink getDrinksByName(String s) {
        for(Drink d : getDrinks()){
            if(d.getName().equals(s)){
                return d;
            }
        }
        return null;
    }
}
