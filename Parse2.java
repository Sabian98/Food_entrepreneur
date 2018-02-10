/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse_app;

/**
 *
 * @author sabian98
 */
public class Parse2 {
       /**
     * @param args the command line arguments
     */
    //declare variables
    SharedPreferences mPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
    ParsedLoginDataSet parsedLoginDataSet = new ParsedLoginDataSet();

    if (!checkLoginInfo() 
        ) {
   login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  String urlAddArray;
                StringRequest request = new StringRequest(Request.Method.POST, urlLoginArray, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        sessionTokens = parseToken(response);
                        // mSignInDbHelper.createSession(name2.getText().toString(),
                        // sessionTokens.get("auth_token"));
                    }//end onResponse
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("name", name2.getText().toString());
                        parameters.put("password", pw.getText().toString());
                        //  parameters.put("date",date.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }

        });
    }

    parsedLoginDataSet.setExtractedString (sessionTokens.get

    ("error"));
    if (parsedLoginDataSet.getExtractedString () 
        .equals("Success")) {
            // Store the username and password in SharedPreferences after the successful login
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("UserName", email);
        editor.putString("PassWord", password);
        editor.commit();
        //  Message myMessage = new Message();
        //myMessage.obj = "SUCCESS";
        //   handler.sendMessage(myMessage);
    }

    
        else {
            Intent intent = new Intent(getApplicationContext(), LoginError.class);
        intent.putExtra("LoginMessage", "Invalid Login");
        startActivity(intent);
        removeDialog(0);
    }

    public HashMap<String, String> parseToken(String jsonResponse)
            throws Exception {
        HashMap<String, String> sessionTokens = new HashMap<String, String>();
        if (jsonResponse != null) {
            jObject = new JSONObject(jsonResponse);
            JSONObject sessionObject = jObject.getJSONObject("output");
            //String attributeError = sessionObject.getString("error");
            String attributeToken = sessionObject.getString("auth_token");
            // String attributeConsumerKey = sessionObject.getString("consumer_key");
            // String attributeConsumerSecret = sessionObject
            //  .getString("consumer_secret");
            sessionTokens.put("error", attributeError);
            //sessionTokens.put("auth_token", attributeToken);
            // sessionTokens.put("consumer_key", attributeConsumerKey);
            // sessionTokens.put("consumer_secret", attributeConsumerSecret);
        } else {
            sessionTokens.put("error", "Error");
        }
        return sessionTokens;
    }

    private final boolean checkLoginInfo() {
        boolean username_set = mPreferences.contains("UserName");
        boolean password_set = mPreferences.contains("PassWord");
        if (username_set || password_set) {
            return true;
        }
        return false;
    }
 
}
