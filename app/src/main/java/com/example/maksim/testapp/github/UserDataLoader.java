package com.example.maksim.testapp.github;

import android.os.AsyncTask;
import android.util.Log;

import com.example.maksim.testapp.models.GitHubUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Maksim on 2017-08-27.
 */

public class UserDataLoader extends AsyncTask<String, Void, List<GitHubUser>> {

    private final String LOG_TAG = "UserDataLoader";
    private boolean apiLimitExceeded = false;
    private OnUserLoaderCompleted listener;

    public UserDataLoader(OnUserLoaderCompleted listener) {
        super();

        this.listener = listener;
    }

    @Override
    protected List<GitHubUser> doInBackground(String... params) {
        List<GitHubUser> gitHubUsers = null;

        HttpURLConnection urlConnection;
        URL url;
        InputStream inputStream;
        String response="";

        //Method 1: To Authorize API access for all HTTP call
        //Uncomment this part of code and input your username and password
//            Authenticator.setDefault(new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication("username", "password".toCharArray());
//                }
//            });

        try{
            url = new URL("https://api.github.com/search/users?" + params[0]);
            Log.e(LOG_TAG, "url value: " + url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();

            //Method 2: To Authorize API access while making HTTP request

            //Uncomment this part of code and input your username and password
//                String basicAuth = "Basic "+Base64.encodeToString("kvipul:password".getBytes(), Base64.DEFAULT).replace("\n", "");
//                urlConnection.setRequestProperty ("Authorization", basicAuth);

            //set request type
            urlConnection.setRequestMethod("GET");

            //if you uncomment the following line GitHub API will not respond
//                urlConnection.setDoOutput(true);

            urlConnection.setDoInput(true);
            urlConnection.connect();
            //check for HTTP response
            int httpStatus = urlConnection.getResponseCode();
            Log.e(LOG_TAG, "httpstatus" + "The response is: " + httpStatus);

            //if HTTP response is 200 i.e. HTTP_OK read inputstream else read errorstream
            if (httpStatus != HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getErrorStream();
                Map<String, List<String>> map = urlConnection.getHeaderFields();
                System.out.println("Printing Response Header...\n");
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    System.out.println(entry.getKey()
                            + " : " + entry.getValue());
                }
            }
            else {
                inputStream = urlConnection.getInputStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            while((temp = bufferedReader.readLine())!=null){
                response += temp;
            }
            Log.e(LOG_TAG, "webapi json object" + response);

            if(response.contains("API rate limit exceeded")){
//                    items= new JSONArray();
//                    total_count = "0";
                apiLimitExceeded = true;
            } else {
                //convert data string into JSONObject
                JSONObject obj = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray items = obj.getJSONArray("items");
                gitHubUsers = parseResponse(items);
                String total_count = obj.getString("total_count");
                String incomplete_results = obj.getString("incomplete_results");
            }

            urlConnection.disconnect();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gitHubUsers;
    }

    private List<GitHubUser> parseResponse(JSONArray users) throws JSONException {
        List<GitHubUser> gitHubUsers = new ArrayList<>();

        for (int i = 0; i < users.length(); i++) {
            JSONObject c = users.getJSONObject(i);
            String login = c.getString("login");
            String name = "name";//c.getString("name");
            String avatarUrl = c.getString("avatar_url");
            String url = c.getString("url");
            double score = c.getDouble("score");

            gitHubUsers.add(new GitHubUser(login, name, avatarUrl, url, score));
        }

        return gitHubUsers;
    }

    @Override
    protected void onPostExecute(List<GitHubUser> gitHubUsers) {
        super.onPostExecute(gitHubUsers);
        listener.onUserLoaderCompleted(gitHubUsers);
    }

    public interface OnUserLoaderCompleted {
        void onUserLoaderCompleted(List<GitHubUser> gitHubUsers);
    }
}
