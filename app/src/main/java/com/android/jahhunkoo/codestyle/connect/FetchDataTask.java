package com.android.jahhunkoo.codestyle.connect;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.android.jahhunkoo.codestyle.R;
import com.android.jahhunkoo.codestyle.constants.BasicConstant;
import com.android.jahhunkoo.codestyle.dto.gson.RawData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Jahun Koo on 2015-03-10.
 */
public class FetchDataTask extends AsyncTask<Void, Void, Void>{

    private final String TAG = FetchDataTask.class.getSimpleName();

    private final String JSON_KEY_NETWORK = BasicConstant.JSON_KEY_NETWORK;
    private final String JSON_VALUE_SUCCESS = BasicConstant.JSON_VALUE_SUCCESS;
    private final String JSON_VALUE_ERROR = BasicConstant.JSON_VALUE_ERROR;

    private final Context mContext;
    private String mServerUrl;
    private String mMethodUrl;
    private Bundle mParams;
    private AsyncCallback mCallback;

    private boolean isCanceled;
    private boolean hasException;
    private LoadingViewHandler mHandler;
    private Message mMessage = new Message();
    private Bundle mData = new Bundle();
    private Exception mException;

    private RawData mResult;

    public FetchDataTask(Context context, String serverUrl, String methodUrl, Bundle params, AsyncCallback callback){
        mContext = context;
        mServerUrl = serverUrl;
        mMethodUrl = methodUrl;
        mParams = params;
        mCallback = callback;
        mHandler = new LoadingViewHandler(context);
    }

    @Override
         protected void onPreExecute() {
        super.onPreExecute();
        mHandler.sendEmptyMessage(LoadingViewHandler.SHOW_LOADING_LAYOUT);
    }

    private HttpURLConnection getPostHttpURLConnection(URL url, Bundle params) throws IOException {
        HttpURLConnection urlConnection = null;

        urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setConnectTimeout(15000);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        if(params != null){
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQueryParameter(params));
            writer.flush();
            writer.close();
            os.close();
        }

        return urlConnection;
    }

    private String getQueryParameter(Bundle params) throws UnsupportedEncodingException {
        Iterator<String> iter = params.keySet().iterator();
        StringBuilder result = new StringBuilder();
        while(iter.hasNext()){
            String key = iter.next();
            result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(params.getString(key), "UTF-8"));
        }
        result.deleteCharAt(0); // 맨 처음 &를 지운다.

        return result.toString();
    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJsonStr = null;

        final String BASE_URL = mServerUrl + mMethodUrl;

        try {
            URL url = new URL(BASE_URL);
            urlConnection = getPostHttpURLConnection(url, mParams);

            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if(inputStream == null){
                isCanceled = true;
                return null;
            }

            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line);
                buffer.append("\n");
            }

            if(buffer.length() == 0){
                // Stream was empty.  No point in parsing.
                isCanceled = true;
                return null;
            }
            resultJsonStr = buffer.toString();
            if(!isNetworkSuccess(resultJsonStr)){
                hasException = true;
                return null;
            }
            Gson gson = new Gson();
            mResult = gson.fromJson(resultJsonStr, RawData.class);
            if(mResult == null){
                hasException = true;
            }

        } catch (IOException e) {
            hasException = true;
            mException = e;
            return null;
        } catch (JSONException e) {
            hasException = true;
            mException = e;
            return null;
        } finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    hasException = true;
                    mException = e;
                    return null;
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        mHandler.sendEmptyMessage(LoadingViewHandler.HIDE_LOADING_LAYOUT);

        if(isCanceled){
            onCancelled();
            return;
        }
        if(hasException){
            onException(mException);
            return;
        }

        mCallback.onResult(mResult.getBigRegionList());
    }

    private void onException(Exception e){
        mHandler.sendEmptyMessage(LoadingViewHandler.HIDE_LOADING_LAYOUT);
        showToast(mContext.getResources().getString(R.string.please_check_network_connection));
        mCallback.exceptionOccured(e);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mHandler.sendEmptyMessage(LoadingViewHandler.HIDE_LOADING_LAYOUT);
        showToast(mContext.getResources().getString(R.string.cancel_connection));
        mCallback.cancelled();
    }

    private void showToast(String message){
        mData.putString(LoadingViewHandler.DATA_KEY, message);
        mMessage.what = LoadingViewHandler.SHOW_TOAST;
        mMessage.setData(mData);
        mHandler.handleMessage(mMessage);
    }


    private boolean isNetworkSuccess(String resultJsonStr) throws JSONException {
        boolean isSuccess = false;

        JSONObject jsonObj = new JSONObject(resultJsonStr);
        String networkResponse = jsonObj.getString(JSON_KEY_NETWORK);
        if(networkResponse.equals(JSON_VALUE_SUCCESS)){
            isSuccess = true;
        }else if(networkResponse.equals(JSON_VALUE_ERROR)){
            isSuccess = false;
        }
        return isSuccess;
    }

}
