package com.android.cs414groupnewandroid.communication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.cs414groupnewandroid.controllers.OrderEditListener;
import com.android.cs414groupnewandroid.objects.Topping;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;


/**
 * Created by darkbobo on 11/15/15.
 */
public class GetToppingsController extends Thread {

    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
    OrderEditListener view;
    String res;
    EditText etResponse;

    public GetToppingsController(OrderEditListener view) {
        this.view = view;
    }

    public ArrayList<Topping> getServerToppings() {
        return parseToppings();
    }

    private ArrayList<Topping> parseToppings() {
        ArrayList<Topping> temp = new ArrayList<Topping>();
        Toast.makeText(view.context, res, Toast.LENGTH_LONG).show();
        if (res != null) {
            String[] split = res.split("<");
            Log.v("SHIT", split[0]);
            if (split[0].contains("<toppingList>")) {
                System.out.println(split[0] + '\t' + split[1]);
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        split[0] = split[0].replaceAll("<toppinglist>", "");
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

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return res = GET("10.0.2.2:7777/menu/getToppings");
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(view.context, "Received!", Toast.LENGTH_LONG).show();
            etResponse.setText(result);
        }
    }
}