package com.tossapon.stadiumfinder.GroupActivity.Splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Api.AuthInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.Model.Response.AuthResponse;
import com.tossapon.stadiumfinder.GroupActivity.MainActivity.MainActivity;
import com.tossapon.stadiumfinder.Network.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class Splash extends AppCompatActivity{
    private static final String TAG = "debug_Splash";
    private static final boolean debug = true;

    @Bind(R.id.splash_login_button)
    LoginButton login;
    @Bind(R.id.splash_logo)
    TextView logo;
    @Bind(R.id.splash_coordinate)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.splash_profile)
    CircleImageView profile;

    private CallbackManager callbackManager;
    private String name;
    private String picurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());


        setContentView(R.layout.activity_splash2);
        ButterKnife.bind(this);
        AccessToken.refreshCurrentAccessTokenAsync();
        login.animate().alpha(0.0f);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    login.animate().start();
                    requestProfile();
//                    getFriendList();

                    sendAuthToServerWithDelay(
                            loginResult.getAccessToken().getUserId(),
                            loginResult.getAccessToken().getToken(),
                            name,
                            picurl
                    );
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (debug)
                    Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onCancel() {
                if (debug)
                    Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                if (debug)
                    Log.d(TAG, "onError: ");
            }
        });

        if(AccessToken.getCurrentAccessToken()!=null){
            try {
                login.setVisibility(View.INVISIBLE);
                requestProfile();
                Picasso.with(getApplicationContext()).load(picurl).into(profile);
                profile.animate().start();
//                getFriendList();

                sendAuthToServer(
                        AccessToken.getCurrentAccessToken().getUserId(),
                        AccessToken.getCurrentAccessToken().getToken(),
                        name,
                        picurl
                );
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        OnInitialAnimation();
    }

//    private void getFriendList() {
//        GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
//            @Override
//            public void onCompleted(JSONArray objects, GraphResponse response) {
//                try {
//                    FileUtil.writeFile(getApplicationContext(), "friends", objects.toString());
//                    Log.d(TAG, "onCompleted: " + FileUtil.readFile(getApplicationContext(), "friends"));
//                } catch (FileNotFoundException e) {
//                    Log.d(TAG, "onCompleted: " + e.getMessage());
//                } catch (IOException e) {
//                    Log.d(TAG, "onCompleted: " + e.getMessage());
//                }
//            }
//        });
//        request.executeAsync();
//    }

    private void sendAuthToServerWithDelay(String userId, String token, String name, String picurl) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Server.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthInterface service = client.create(AuthInterface.class);
        Call<AuthResponse> call = service.Auth(
                userId,
                token,
                name,
                picurl
        );
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(retrofit.Response<AuthResponse> response, Retrofit retrofit) {
                if(debug)
                    Log.d(TAG, "onResponse: " + response.message());
                if (response.body().getStatus().equals("ok")) {
                    Snackbar.make(coordinatorLayout, "การยืนยันตัวตนเรียบร้อย กำลังนำท่านเข้าสู่ Stadium Finder", Snackbar.LENGTH_LONG).show();
                    AppUser.setInstance(response.body().getData());
                    (Executors.newSingleThreadScheduledExecutor()).schedule(new Runnable() {
                        @Override
                        public void run() {
                            startMainActivity();
                        }
                    }, 4, TimeUnit.SECONDS);
                }

                if (debug)
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, t.getMessage(), Snackbar.LENGTH_LONG).show();
                if(debug)
                    Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void sendAuthToServer(String userId, String token, String name, String picurl) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Server.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthInterface service = client.create(AuthInterface.class);
        Call<AuthResponse> call = service.Auth(
                userId,
                token,
                name,
                picurl
        );
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(retrofit.Response<AuthResponse> response, Retrofit retrofit) {
                if(debug)
                    Log.d(TAG, "onResponse: " + response.message());
                if (response.body().getStatus().equals("ok")) {
                    Snackbar.make(coordinatorLayout, "การยืนยันตัวตนเรียบร้อย กำลังนำท่านเข้าสู่ Stadium Finder", Snackbar.LENGTH_LONG).show();
                    AppUser.setInstance(response.body().getData());
                    startMainActivity();
                }

                if (debug)
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, t.getMessage(), Snackbar.LENGTH_LONG).show();
                if(debug)
                    Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void startMainActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OnStartAnimation();
        logo.animate().start();
        login.animate().start();

    }

    private void OnStartAnimation() {
        logo.animate().setStartDelay(1000).alpha(100f).setDuration(5000);
        logo.animate().setStartDelay(1000).translationY(-400f).setDuration(1000);
        login.animate().setStartDelay(2000).alpha(100f).setDuration(5000);
        profile.animate().alphaBy(100f).setStartDelay(2000).setDuration(2000);
    }

    private void OnInitialAnimation() {
        login.setAlpha(0.0f);
        logo.setAlpha(0.0f);
        profile.setAlpha(0.0f);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void requestProfile() throws ExecutionException, InterruptedException, JSONException {
        JSONObject jsonRes = (new AsyncTask<Void, Void, JSONObject>(){
            private JSONObject jsonResponse = null;
            @Override
            protected JSONObject doInBackground(Void... params) {
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        jsonResponse = jsonObject;
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "id, name, picture");
                request.setParameters(bundle);
                request.executeAndWait();
                return jsonResponse;
            }
        }).execute().get();
        name = jsonRes.getString("name");
        picurl = "http://graph.facebook.com/" + AccessToken.getCurrentAccessToken().getUserId() + "/picture?type=large&redirect=true&width=400&height=400";
    }
}
