package es.finnapps.piggybank.piggyapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

import com.google.inject.Inject;

import es.finnapps.piggybank.bankapi.BankApi;
import es.finnapps.piggybank.model.Operation;
import es.finnapps.piggybank.model.Piggy;
import es.finnapps.piggybank.model.UserInfo;
import es.finnapps.piggybank.sharedprefs.PiggyBankPreferences;

public class PiggyApi implements PiggyApiInterface{

    private String TAG = BankApi.class.getName();
    private String API_URL = "http://elchudi.xen.prgmr.com:8080";
    
    private String GET_ACCOUNT_AMOUNT = API_URL + "/account_amount";
    private String GET_ACCOUNT = API_URL + "/get_account";
    private String GET_ACCOUNTS_FOR_TELEPHONE = API_URL +"/accounts_for_telephone";
    private String UPDATE_ACCOUNT_AMOUNT = API_URL +"/update_account_amount";
    private String ADD_USER_TO_ACCOUNT = API_URL +"/add_user_to_account";
    private String ADD_USER = API_URL +"/add_user";
    private String GET_MY_ACCOUNTS = API_URL +"/my_accounts";
    private String ADD_ACCOUNT = API_URL +"/add_account";
 
    @Inject
    private PiggyBankPreferences prefs;

    protected static final String JSON_TYPE = "application/json";
    protected static final String XML_TYPE = "text/xml";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TELEPHONE = "telephone";
    private static final String KEY_ACCOUNT_NUMBER  = "account_number";
    private static final String KEY_AMOUNT_NEDDED  = "amount_needed";
    private static final String KEY_NAME  = "name";
    
    private static final int BUFFERSIZE = 1024;
    private String CONTENT = "Content-Type";
    private JSONObject responseJson;

    public enum HttpRequestType {
        get, post, put, delete,
    }

    protected StringEntity createJSONRequestForRegister(String[] names, Object[] values) {
        try {
            JSONObject json = createJsonFromParams(names, values);
            StringEntity entity = new StringEntity(json.toString());
            entity.setContentType(XML_TYPE);
            return entity;
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "error creating entity from json Encodign", e);
        } catch (JSONException e) {
            Log.d(TAG, "error creating entity from json json", e);
        }
        return null;
    }

    private JSONObject createJsonFromParams(String[] names, Object[] values) throws JSONException {
        JSONObject json = new JSONObject();
        for (int i = 0; i < values.length; i++) {
            json.put(names[i], values[i]);
        }
        return json;
    }

    protected StringEntity createJSONRequest(String[] names, String[] values) {
        try {
            JSONObject json = new JSONObject();
            for (int i = 0; i < values.length; i++) {
                json.put(names[i], values[i]);
            }
            StringEntity entity = new StringEntity(json.toString());
            entity.setContentType(XML_TYPE);
            return entity;
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "error creating entity from json Encodign", e);
        } catch (JSONException e) {
            Log.d(TAG, "error creating entity from json json", e);
        }
        return null;
    }

    protected Map<String, String> getBasicHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(CONTENT, JSON_TYPE);
        return headers;
    }

    
    protected HttpResponse callApi(String url, Map<String, String> headers, HttpRequestType type,
            AbstractHttpEntity reqEntity, boolean authorization) {
        Log.d(TAG, "callApi " + url);

        HttpClient httpclient = new DefaultHttpClient();

        HttpRequestBase request = null;
        if (type == HttpRequestType.post) {
            request = new HttpPost(url);
            if (authorization) {
                String userPass = prefs.getUserName() + ":" + prefs.getPassword();
                request.setHeader("Authorization",
                        "Basic " + Base64.encodeToString(userPass.getBytes(), Base64.NO_WRAP));
            }
            if (reqEntity != null) {
                ((HttpPost) request).setEntity(reqEntity);
            }
        } else if (type == HttpRequestType.get) {
            request = new HttpGet(url);
            if (authorization) {
                String userPass = prefs.getUserName() + ":" + prefs.getPassword();
                request.setHeader("Authorization",
                        "Basic " + Base64.encodeToString(userPass.getBytes(), Base64.NO_WRAP));
            }
        } else if (type == HttpRequestType.put) {
            request = new HttpPut(url);
            if (reqEntity != null) {
                ((HttpPut) request).setEntity(reqEntity);
            }
        } else if (type == HttpRequestType.delete) {
            request = new HttpDelete(url);
        } else {
            assert false;
        }

        if (headers != null) {
            for (String key : headers.keySet()) {
                request.addHeader(key, headers.get(key));
            }
        }

        HttpResponse response = null;

        try {
            response = httpclient.execute(request);
        } catch (IOException e) {
            Log.d(TAG, "Error requesting url", e);
        }

//        Log.v(TAG, "Response received " + response.getStatusLine());
//
//        int resultado = response.getStatusLine().getStatusCode();
//        if (resultado == 400) {
//            Log.d(TAG, "error in petition");
//        }
           return response;
    }

    public JSONObject getResponseInfo(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        try {
            responseJson = getJSONObject(entity);
            Log.i(TAG, responseJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    protected JSONObject getJSONObject(HttpEntity entity) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);
            json = new JSONObject(result);
            System.gc();
            instream.close();
        }
        return json;
    }

    private String convertStreamToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();
        char[] buffer = new char[BUFFERSIZE];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
    }

    public boolean registerUser(String telephone, String token_push) {
        String[] userKeys = { KEY_TELEPHONE, KEY_TOKEN};
        String[] userValues = { telephone, token_push};
        JSONObject userJson = null;
        try {
            userJson = createJsonFromParams(userKeys, userValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = createJSONRequestForRegister(userKeys, userValues);
        HttpResponse response = callApi(ADD_USER, getBasicHeaders(), HttpRequestType.post, entity, false);

        if (response.getStatusLine().getStatusCode() == 200) {
            return true;
        } else {
            return false;
        }
    }
/*
    public String getToken() {
        HttpResponse response = callApi(GET_TOKEN_URL, null, HttpRequestType.get, null, true);

        JSONObject responseJson = null;
        try {
            responseJson = getResponseInfo(response);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        try {
            String token = responseJson.getString(KEY_TOKEN);
            return token;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
*/
    
    
    public List<Piggy> getSharedPiggys(String phoneNumber) {
        List<Piggy> to_ret = new ArrayList<Piggy>();
        String[] userKeys = { KEY_TELEPHONE};
        String[] userValues = { phoneNumber};
        JSONObject userJson = null;
        try {
            userJson = createJsonFromParams(userKeys, userValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = createJSONRequestForRegister(userKeys, userValues);
        HttpResponse response = callApi(GET_ACCOUNTS_FOR_TELEPHONE, getBasicHeaders(), HttpRequestType.post, entity, false);

        if (response.getStatusLine().getStatusCode() == 200) {
            JSONObject info = getResponseInfo(response);
            try {
                JSONArray array = info.getJSONArray("piggy");
                for(int i = 0; i<array.length(); i++){
                    JSONObject o = (JSONObject) array.get(i);
                    JSONArray phone_array = o.getJSONArray("telephones");
                    List<String> phones = new ArrayList<String>();
                    for (int j = 0; j <phone_array.length(); j++){
                        phones.add(((JSONObject)phone_array.get(j)).getString(KEY_TELEPHONE));
                    }
                    
                        
                    Piggy pig = new Piggy(o.getString(KEY_NAME), o.getString(KEY_ACCOUNT_NUMBER), o.getLong(KEY_AMOUNT), null, "",0, phones, o.getLong(KEY_AMOUNT_NEDDED));
                    to_ret.add(pig);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         
            return to_ret;
        } else {
            return to_ret;
        }       
    }

    public boolean notifyMoneySavedOnPiggy(Piggy piggy, float amountSavedTotal) {
        String[] userKeys = { KEY_ACCOUNT_NUMBER, KEY_AMOUNT};
        String[] userValues = { piggy.getNumber(), Float.toString(amountSavedTotal)};
        JSONObject userJson = null;
        try {
            userJson = createJsonFromParams(userKeys, userValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = createJSONRequestForRegister(userKeys, userValues);
        HttpResponse response = callApi(UPDATE_ACCOUNT_AMOUNT, getBasicHeaders(), HttpRequestType.post, entity, false);

        if (response.getStatusLine().getStatusCode() == 200) {
            return true;
        } else {
            return false;
        }
        
    }

    public boolean sharePiggyWith(Piggy piggy) {
        List<String> telephones = piggy.getShared();
        for(int i = 0; i <telephones.size(); i++){
            
            String[] userKeys = { KEY_TELEPHONE, KEY_ACCOUNT_NUMBER};
            String[] userValues = { telephones.get(i),piggy.getNumber()};
            JSONObject userJson = null;
            try {
                userJson = createJsonFromParams(userKeys, userValues);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringEntity entity = createJSONRequestForRegister(userKeys, userValues);
            HttpResponse response = callApi(ADD_USER_TO_ACCOUNT, getBasicHeaders(), HttpRequestType.post, entity, false);
    
            if (response.getStatusLine().getStatusCode() == 200) {
                
            } else {
                return false;
            }
        }
        return true;
        
        
    }

    public boolean createPiggy(Piggy piggy, String telephoneOwner) {
        String[] userKeys = { KEY_ACCOUNT_NUMBER, KEY_AMOUNT, KEY_AMOUNT_NEDDED, KEY_NAME, KEY_TELEPHONE};
        String[] userValues = { piggy.getNumber(), Float.toString(piggy.getAmount()), Float.toString(piggy.getObjectiveAmount()),piggy.getName(), telephoneOwner};
        JSONObject userJson = null;
        try {
            userJson = createJsonFromParams(userKeys, userValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = createJSONRequestForRegister(userKeys, userValues);
        HttpResponse response = callApi(ADD_ACCOUNT, getBasicHeaders(), HttpRequestType.post, entity, false);

        if (response.getStatusLine().getStatusCode() == 200) {
            return true;
        } else {
            return false;
        }
       }

    public List<Piggy> getMyPiggys(String phoneNumber) {
        List<Piggy> to_ret = new ArrayList<Piggy>();
        String[] userKeys = { KEY_TELEPHONE};
        String[] userValues = { phoneNumber};
        JSONObject userJson = null;
        try {
            userJson = createJsonFromParams(userKeys, userValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = createJSONRequestForRegister(userKeys, userValues);
        HttpResponse response = callApi(GET_MY_ACCOUNTS, getBasicHeaders(), HttpRequestType.post, entity, false);

        if (response.getStatusLine().getStatusCode() == 200) {
            JSONObject info = getResponseInfo(response);
            try {
                JSONArray array = info.getJSONArray("piggy");
                for(int i = 0; i<array.length(); i++){
                    JSONObject o = (JSONObject) array.get(i);
                    JSONArray phone_array = o.getJSONArray("telephones");
                    List<String> phones = new ArrayList<String>();
                    for (int j = 0; j <phone_array.length(); j++){
                        phones.add(((JSONObject)phone_array.get(j)).getString(KEY_TELEPHONE));
                    }
                    
                        
                    Piggy pig = new Piggy(o.getString(KEY_NAME), o.getString(KEY_ACCOUNT_NUMBER), o.getLong(KEY_AMOUNT), null, "",0, phones, o.getLong(KEY_AMOUNT_NEDDED));
                    to_ret.add(pig);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         
            return to_ret;
        } else {
            return to_ret;
        }       
    }

}
