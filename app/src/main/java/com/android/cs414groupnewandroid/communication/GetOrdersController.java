package com.android.cs414groupnewandroid.communication;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.cs414groupnewandroid.controllers.OrderEditListener;
import com.android.cs414groupnewandroid.objects.Drink;
import com.android.cs414groupnewandroid.objects.Order;
import com.android.cs414groupnewandroid.objects.Pizza;
import com.android.cs414groupnewandroid.objects.Side;
import com.android.cs414groupnewandroid.objects.Topping;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jim on 11/20/2015.
 */
public class GetOrdersController  extends AsyncTask {
    OrderEditListener view;
    Order order;

    public GetOrdersController(OrderEditListener orderEditListener, Order order) {
        view = orderEditListener;
        this.order = order;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String url = "http://10.0.2.2:7777/order/order?";
        url += "order=" + order.getOrderID() + "&name=" + order.getCustomer().getName() + "&street=" +
                order.getCustomer().getAddresses().get(0) + "&city=" + order.getCustomer().getAddress(1) +
                "&state=" + order.getCustomer().getAddress(2) + "&zip=" + order.getCustomer().getAddress(3) +
                "&phone=" + order.getCustomer().getPhoneNumbers().get(0) + "&type=";
        if (!order.getPizzas().isEmpty()){
            for (Pizza p : order.getPizzas()){
                url += "pizza&cost=" + p.getPrice() + "&id=" + p.getOrderID() + "&size=" + p.getSize().getFullName() +
                        "&sauce=" + p.getSauce().getFullName();
                if (!p.getToppingList().isEmpty()) {
                    for (Topping t : p.getToppingList()) {
                        url += "&top=" + t.getFullName();
                    }
                }
                url += "&PIZZA_DONE=PIZZA_DONE";
            }
        }
        if (!order.getOrderItems().isEmpty()){
            for (Side s : order.getSides()) {
                url += "&item=" + s.getName();
            }
            url+= "&ITEM_DONE=ITEM_DONE";
        }
        if (!order.getDrinks().isEmpty()){
            for (Drink d : order.getDrinks()){
                url += "&drink=" + d.getName();
            }
            url += "&DRINK_DONE=DRINK_DONE";
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse httpResponse;
        String result = "";
        try {
            httpResponse = httpclient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                result = convertToString(in);
                Toast.makeText(view.context, result, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(view.context, e.toString(), Toast.LENGTH_LONG).show();
            result = "SendOrder error";
        }
        return result;
    }

    private String convertToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buff = new StringBuffer();
        String line = null;
        try {
            line = reader.readLine();
            while (line != null)
            {
                buff.append(line + "\n");
                line = reader.readLine();
            }
            is.close();
        }
        catch (Exception e)
        {
            Toast.makeText(view.context, "!@#!@#!@#", Toast.LENGTH_LONG).show();
        }
        return buff.toString();
    }
}
