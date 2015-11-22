package com.android.cs414groupnewandroid.communication;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.objects.PizzaCatalog;
import com.android.cs414groupnewandroid.objects.Register;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


/**
 * Created by darkbobo on 11/15/15.
 */
public class GetToppingsController implements Runnable {

    private final int PORT_NUMBER = 7777;
    HttpURLConnection con;
	Context context;
    Register model;

    public GetToppingsController(Context context, Register model) {
		this.context = context;
        this.model = model;
    }

    @Override
    public void run() {
		Looper.prepare();
		String url = "http://10.0.2.2:7777/menu";
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        // make GET request to the given URL
        HttpResponse httpResponse;
        try {
            httpResponse = httpclient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                result = convertToString(in);
                Log.e("GetToppingsController", result);
                ObjectMapper map = new ObjectMapper();
                PizzaCatalog pc = map.readValue(result, PizzaCatalog.class);
                model.setCatalog(pc);
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
			Log.e("GetToppingsController", e.toString());
        }
		OrderFragment.syncHandler.sendEmptyMessage(2);
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
            Toast.makeText(context, "!@#!@#!@#", Toast.LENGTH_LONG).show();
        }
        return buff.toString();
    }
}
