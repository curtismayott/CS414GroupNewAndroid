
package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;

/**
 * Created by darkbobo on 10/22/15.
 */
@XStreamAlias("PIZZACATALOG")
public class PizzaCatalog{
    @XStreamImplicit(itemFieldName="toppings")
    ArrayList<Topping> toppings;
    @XStreamImplicit(itemFieldName="sauces")
    ArrayList<Sauce> sauces;
    @XStreamImplicit(itemFieldName="sizes")
    ArrayList<PizzaSize> sizes;
    @XStreamImplicit(itemFieldName="sides")
    ArrayList<SideItem> sides;
    @XStreamImplicit(itemFieldName="specials")
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

    public void addItem(Object o){
        if(o instanceof PizzaSize){
            ((PizzaSize)o).setItemID(123);
            sizes.add((PizzaSize)o);
        }else if(o instanceof Sauce){
            ((Sauce)o).setItemID(234);
            sauces.add((Sauce)o);
        }else if(o instanceof Topping){
            ((Topping)o).setItemID(345);
            toppings.add((Topping)o);
        }else if(o instanceof Side){
            ((Side)o).setItemID(456);
            sides.add((Side)o);
        }else if(o instanceof Drink){
            ((Drink)o).setItemID(567);
            sides.add((Drink)o);
        }
    }

    public ArrayList<Special> getSpecials(){
        return specials;
    }

    private void initData(){}

    public void setSpecials(ArrayList<Special> specials) {
        this.specials = specials;
    }

    public void setSides(ArrayList<Side> sides) {
        ArrayList<SideItem> s = new ArrayList<>();
        for (Side side : sides){
            s.add((SideItem)side);
        }
    }
}
