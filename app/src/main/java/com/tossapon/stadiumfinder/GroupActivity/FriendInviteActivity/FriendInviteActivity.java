package com.tossapon.stadiumfinder.GroupActivity.FriendInviteActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.FriendInviteAdapter;
import com.tossapon.stadiumfinder.Api.FacebookServiceInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.Model.Advance.MyReserve;
import com.tossapon.stadiumfinder.Model.Facebook.Friend;
import com.tossapon.stadiumfinder.Model.FacebookResponse.FacebookNotificationResponse;
import com.tossapon.stadiumfinder.Network.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class FriendInviteActivity extends AppCompatActivity {

    private static final String TAG = "FriendInviteActivity";
    MyReserve myReserve = null;

    @Bind(R.id.activity_friend_invite_recycler_view)
    RecyclerView mRecycler;

    @Bind(R.id.activity_friend_invite_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_friend_invite_button)
    Button sendButton;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<String> friendsId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_invite);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ชวนเพื่อน");

        myReserve = Parcels.unwrap(getIntent().getParcelableExtra("reserve"));

        mRecycler.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray objects, GraphResponse response) {
                Log.d(TAG, "onCompleted: " + objects.toString());

                ArrayList<Friend> friends = new ArrayList<>();
                for(int i = 0 ; i < objects.length() ; i++){
                    try {
                        friends.add(new Friend(objects.getJSONObject(i)));
                    } catch (JSONException e) {
                        Log.d(TAG, "onFacebookSendFriendAndTransformToFriendArrayList: " + e.getMessage());
                    }
                }
                mAdapter = new FriendInviteAdapter(friends, friendsId);
                mRecycler.setAdapter(mAdapter);
            }
        });
        setParameter(request);
        request.executeAsync();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Server.FacebookGraphApi)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                FacebookServiceInterface service = retrofit.create(FacebookServiceInterface.class);
                if(!friendsId.isEmpty()){
                    for(int i = 0 ; i < friendsId.size() ; i++){
                        Call<FacebookNotificationResponse> call = service.notificate(
                                friendsId.get(i),
                                AccessToken.getCurrentAccessToken().getToken(),
                                "http://www.google.com",
                                AppUser.getInstance().name + "ได้ชวนคุณเข้าร่วมเล่น");
                        call.enqueue(new Callback<FacebookNotificationResponse>() {
                            @Override
                            public void onResponse(Response<FacebookNotificationResponse> response, Retrofit retrofit) {
                                Log.d(TAG, "onResponse: " + response.message());
                                Log.d(TAG, "onResponse: " + response.raw().message());
                                Log.d(TAG, "onResponse: " + response.code());
                                Log.d(TAG, "onResponse: " + response.isSuccess());
                                try {
                                    Log.d(TAG, "onResponse: " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                    }
                }
            }
        });
    }

    private void setParameter(GraphRequest request) {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, picture");
        request.setParameters(parameters);
    }
}
