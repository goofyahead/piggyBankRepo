package es.finnapps.piggybank.bankapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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

import es.finnapps.piggybank.model.Operation;
import es.finnapps.piggybank.model.Piggy;
import es.finnapps.piggybank.model.UserInfo;

import android.os.AsyncTask;
import android.util.Log;

public class BankApi implements BankApiInterface {
    private String TAG = BankApi.class.getName();
    private String API_URL = "http://finappsapi.bdigital.org/api/2012/";
    private String API_KEY = "c6ab8d3240";
    private String CREATE_CLIENT_URL = API_URL + API_KEY + "/access/client";

    protected static final String JSON_TYPE = "application/json";
    protected static final String XML_TYPE = "text/xml";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FIRSTNAME = "firstName";
    private static final String KEY_LASTNAME = "lastName";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_STREET = "street";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_CITY = "city";
    private static final String KEY_POSTALCODE = "postalCode";
    private static final String KEY_COUNTRY = "country";
    private static final int BUFFERSIZE = 1024;
    private String CONTENT = "Content-Type";

    public enum HttpRequestType {
        get, post, put, delete,
    }

    protected StringEntity createJSONRequestForRegister (String[] names, Object[] values) {
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

    protected void callApi(String url, Map<String, String> headers, HttpRequestType type, AbstractHttpEntity reqEntity) {
        Log.d(TAG, "callApi " + url);

        HttpClient httpclient = new DefaultHttpClient();

        HttpRequestBase request = null;
        if (type == HttpRequestType.post) {
            request = new HttpPost(url);
            if (reqEntity != null) {
                ((HttpPost) request).setEntity(reqEntity);
            }
        } else if (type == HttpRequestType.get) {
            request = new HttpGet(url);
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

        for (String key : headers.keySet()) {
            request.addHeader(key, headers.get(key));
        }
        new Petition(httpclient, request).execute();
        // Should return or create the thing
    }

    public void getResponseInfo(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        JSONObject json = null;
        try {
            json = getJSONObject(entity);
            Log.i(TAG, json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public boolean registerClient(UserInfo userInfo) {
        String[] street = {KEY_STREET, KEY_NUMBER, KEY_CITY, KEY_POSTALCODE, KEY_COUNTRY };
        String[] streetValues = {userInfo.getStreet(), userInfo.getCity(), userInfo.getPostalCode(), userInfo.getCountry()};
        JSONObject streetJson = null;
        try {
            streetJson = createJsonFromParams(street, streetValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] names = { KEY_USERNAME, KEY_PASSWORD, KEY_FIRSTNAME, KEY_LASTNAME, KEY_ADDRESS};
        Object[] values = { userInfo.getUserName(), userInfo.getPassword(), userInfo.getFirstName(),
                userInfo.getLastName(), streetJson };
        StringEntity entity = createJSONRequestForRegister(names, values);
        callApi(CREATE_CLIENT_URL, getBasicHeaders(), HttpRequestType.post, entity);
        return false;
    }

    public boolean createAccount(String token) {
        // TODO Auto-generated method stub
        return false;
    }

    public List<Operation> getOperations(String account) {
        // TODO Auto-generated method stub
        return null;
    }

    public float getAccountAmount(String account) {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<Piggy> getAccounts(String token) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getAccountStatus(String account) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean transferFunds(String tokenFrom, String tokenTo) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean depositFunds(String token, float amount) {
        // TODO Auto-generated method stub
        return false;
    }

    class Petition extends AsyncTask<Void, Void, Void> {
        private HttpClient httpClient;
        private HttpRequestBase request;
        HttpResponse response;

        public Petition(HttpClient httpClient, HttpRequestBase request) {
            this.httpClient = httpClient;
            this.request = request;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                response = httpClient.execute(request);
            } catch (IOException e) {
                Log.d(TAG, "Error requesting url", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.v(TAG, "Response received " + response.getStatusLine());

            int resultado = response.getStatusLine().getStatusCode();
            if (resultado == 400) {
                Log.d(TAG, "error in petition");
            }
            try {
                getResponseInfo(response);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }
}
