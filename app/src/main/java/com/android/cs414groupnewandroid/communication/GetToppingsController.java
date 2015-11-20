package com.android.cs414groupnewandroid.communication;

import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.controllers.OrderEditListener;
import com.android.cs414groupnewandroid.objects.Topping;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.util.ArrayList;


/**
 * Created by darkbobo on 11/15/15.
 */
public class GetToppingsController {

    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
    OrderEditListener view;

    public GetToppingsController(OrderEditListener view) {
        this.view = view;
    }

    public ArrayList<Topping> parseToppings(String res) {
        ArrayList<Topping> temp = new ArrayList<Topping>();
        if (res != null) {
            Toast.makeText(view.context, res, Toast.LENGTH_LONG).show();
            String[] split = res.split("<");
            if (split[0].contains("toppingList>")) {
                System.out.println(split[0] + '\t' + split[1]);
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        split[0] = split[0].replaceAll("toppinglist>", "");
                    }
                    createToppingFromXML(split[i]);
                }
            }
        }
        return temp;
    }

    private Topping createToppingFromXML(String s) {
        String[] temp = s.split("/");
        temp[0] = temp[0].replaceAll("topping><id>", "");
        temp[0] = temp[0].substring(0, temp[0].length() - 1);
        temp[1] = temp[1].replaceAll("id><short>", "");
        temp[1] = temp[1].substring(0, temp[1].length() - 1);
        temp[2] = temp[2].replaceAll("short><full>", "");
        temp[2] = temp[2].substring(0, temp[2].length() - 1);
        Topping t = new Topping(temp[1], temp[2]);
        t.setItemID(Integer.parseInt(temp[0]));
        return t;
    }

    public String GET(String url){
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            String temp = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            // convert inputstream to string
            if(temp != null)
                return temp;
        } catch (Exception e) {
            Log.d("InputStream", e.toString());
        }
        return "Toppings server-connect error";

    }
}
