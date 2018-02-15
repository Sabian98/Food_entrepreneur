/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class MainActivity extends Activity {

    private final String mainActivityTag = "MainActivity";
    private JSONObject jObject;
    private SignInDbAdapter mSignInDbHelper;
    private ProgressDialog mProgressDialog;
    String loginmessage = null;
    Thread t;
    private SharedPreferences mPreferences;
    Button signin;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        mSignInDbHelper = new SignInDbAdapter(this);
        mSignInDbHelper.open();

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        if (!checkLoginInfo()) {//if credentials are not stored in sharedpreferences,prompt the user to enter credentials
            signin = (Button) findViewById(R.id.btn_sign_in);
            signin.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    showDialog(0);
                    t = new Thread() {
                        public void run() {
                            try {
                                authenticate();
                            } catch (ClientProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    t.start();
                }
            });
        } else {
            /*
			 * Directly opens the Welcome page, if the username and password is
			 * already available in the SharedPreferences
             */

            // Trying to minimize the number of screens
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0: {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Please wait while signing in ...");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(true);
                return mProgressDialog;
            }
        }
        return null;
    }

    private void authenticate() throws ClientProtocolException, IOException {
        try {
            String pin = "";
            HashMap<String, String> sessionTokens = signIn();

        } catch (Exception e) {
            Intent intent = new Intent(getApplicationContext(), LoginError.class);
            intent.putExtra("LoginMessage", "Unable to login");
            startActivity(intent);
            removeDialog(0);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String loginmsg = (String) msg.obj;
            if (loginmsg.equals("SUCCESS")) {
                removeDialog(0);
                Intent intent = new Intent(getApplicationContext(), WorkoutDetailsListActivity.class);
                //Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    private HashMap<String, String> signIn() {

        HashMap<String, String> sessionTokens = null;

        EditText mEmailField = (EditText) findViewById(R.id.email_field);
        EditText mPasswordField = (EditText) findViewById(R.id.password_field);

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://yoursite.com/sessions"); //address of the server
        JSONObject holder = new JSONObject();
        JSONObject userObj = new JSONObject();

        try {
            userObj.put("password", password);
            userObj.put("email", email);
            holder.put("user", userObj);
            StringEntity se = new StringEntity(holder.toString());
            post.setEntity(se);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException js) {
            js.printStackTrace();
        }

        String response = null;
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = client.execute(post, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ParsedLoginDataSet parsedLoginDataSet = new ParsedLoginDataSet();
        //ParsedLoginDataSet parsedLoginDataSet = myLoginHandler.getParsedLoginData();
        try {
                sessionTokens = parseToken(response);
		if(sesssionTokens.get("auth_token")!="null" && sesssionTokens.get("status")!="null json Response" )
		{
                    mSignInDbHelper.createSession(mEmailField.getText().toString(),
                    sessionTokens.get("auth_token"));
		}
		else
		{
              //show no password/email invalid message//null json response message
		}
            // now = Long.valueOf(System.currentTimeMillis());
            // mSignInDbHelper.createSession(mEmailField.getText().toString(),mAuthToken,now);
        } catch (Exception e) {
            e.printStackTrace();//if no auth_token found
        }
        parsedLoginDataSet.setExtractedString(sessionTokens.get("status"));
        if (parsedLoginDataSet.getExtractedString().equals("success")) {
            // Store the username and password in SharedPreferences after the successful login
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("UserName", email);
            editor.putString("PassWord", password);
            editor.commit();
            Message myMessage = new Message();
            myMessage.obj = "SUCCESS";
            handler.sendMessage(myMessage);
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginError.class);
            intent.putExtra("LoginMessage", "Invalid Login");
            startActivity(intent);
            removeDialog(0);
        }

        return sessionTokens;

    }

    public HashMap<String, String> parseToken(String jsonResponse)
            throws Exception {
        HashMap<String, String> sessionTokens = new HashMap<String, String>();
       if(jsonResponse != null) {
            jObject = new JSONObject(jsonResponse);
            JSONObject sessionObject = jObject.getJSONObject("output");
            String attributeStatus = sessionObject.getString("status");
            String attributeToken = sessionObject.getString("auth_token");
           // String attributeConsumerKey = sessionObject.getString("consumer_key");
           // String attributeConsumerSecret = sessionObject
                   // .getString("consumer_secret");
            sessionTokens.put("status", attributeStatus);
            sessionTokens.put("auth_token", attributeToken);
            //sessionTokens.put("consumer_key", attributeConsumerKey);
           // sessionTokens.put("consumer_secret", attributeConsumerSecret);
        } else {
	    String attributeStatus="null json Response"; 
	    sessionTokens.put("status", attributeStatus);
            //sessionTokens.put("error", "Error");
        }
        return sessionTokens;
    }

    // Checking whether the username and password has stored already or not
    private final boolean checkLoginInfo() {
        boolean username_set = mPreferences.contains("UserName");
        boolean password_set = mPreferences.contains("PassWord");
        if (username_set || password_set) {
            return true;
        }
        return false;
    }

}
