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
public class Parse_app {

    /**
     * @param args the command line arguments
     */
    SharedPreferences mPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);

    if (!checkLoginInfo() 
        ) {
   login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  String urlAddArray;
                StringRequest request = new StringRequest(Request.Method.POST, urlLoginArray, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.trim.equals("fail")) {
                            authToken = String.valueOf(response.get("auth_token"));
                            //  System.out.println(response.toString());
                            // SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                            mPreferences.edit().putString("token", authToken).commit();
                            mPreferences.edit().putString("UserName", name2.getText().toString());
                             mPreferences.edit().putString("PassWord", pw.getText().toString());
                            mPreferences.edit().commit();
                        } else {
                            //show error messgae
                        }
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

    private final boolean checkLoginInfo() {
        boolean username_set = mPreferences.contains("UserName");
        boolean password_set = mPreferences.contains("PassWord");
        if (username_set || password_set) {
            return true;
        }
        return false;
    }

}//end app
