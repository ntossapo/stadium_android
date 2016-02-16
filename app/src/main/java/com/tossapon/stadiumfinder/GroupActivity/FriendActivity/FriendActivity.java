package com.tossapon.stadiumfinder.GroupActivity.FriendActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.FriendAdapter;
import com.tossapon.stadiumfinder.Model.Facebook.Friend;
import com.tossapon.stadiumfinder.Util.FileUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benvo_000 on 11/2/2559.
 */
public class FriendActivity extends AppCompatActivity {

    private static final String TAG = "FriendActivity";
    private static final boolean debug = false;
    @Bind(R.id.activity_friend_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_friend_recycler_view)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> arrayListBlockedFriend = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("เพื่อน");
        if (debug)
            for (int i = 0 ; i < fileList().length ; i++)
                Log.d(TAG, "onCreate: " + fileList()[i]);

        File f = new File(getFilesDir().getPath() + "/blockFriend");
        if (debug)
            Log.d(TAG, "onCreate: " + f.getAbsolutePath());
        if(!f.exists()){
            try {
                FileUtil.writeFile(getApplicationContext(), "blockFriend", "[]");
                if(debug)
                    Log.d(TAG, "onCreate: " + "write new file");
            } catch (IOException e) {
                if(debug)
                    Log.d(TAG, "onCreate: " + e.getMessage());
            }
        }

        try {
            String stringBlockedFriend = FileUtil.readFile(getApplicationContext(), "blockFriend");
            if (debug)
                Log.d(TAG, "onCreate: read + " + stringBlockedFriend);
            JSONArray jsonArrayBlockedFriend = new JSONArray(stringBlockedFriend);
            for(int i = 0 ; i < jsonArrayBlockedFriend.length() ; i++){
                arrayListBlockedFriend.add(jsonArrayBlockedFriend.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray objects, GraphResponse response) {
                ArrayList<Friend> friends = new ArrayList<>();
                for (int i = 0; i < objects.length(); i++) {
                    try {
                        friends.add(new Friend(objects.getJSONObject(i)));
                    } catch (JSONException e) {
                    }
                }
                mAdapter = new FriendAdapter(friends, arrayListBlockedFriend);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        setParameter(request);
        request.executeAsync();
    }

    private void setParameter(GraphRequest request) {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, picture");
        request.setParameters(parameters);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONArray array = new JSONArray();
        for(int i = 0 ; i < arrayListBlockedFriend.size() ; i++){
            array.put(arrayListBlockedFriend.get(i));
        }
        if (debug)
            Log.d(TAG, "onBackPressed: " + array.toString());
        try {
            FileUtil.writeFile(getApplicationContext(), "blockFriend", array.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
