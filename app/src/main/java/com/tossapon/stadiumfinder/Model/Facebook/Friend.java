package com.tossapon.stadiumfinder.Model.Facebook;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class Friend {
    private String id;
    private String picture;
    private String name;

    public Friend(String id, String picture, String name) {
        this.id = id;
        this.picture = picture;
        this.name = name;
    }

    public Friend(JSONObject object) throws JSONException {
        id = object.getString("id");
        name = object.getString("name");
        picture = object.getJSONObject("picture").getJSONObject("data").getString("url");
    }

    public String getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }
}
