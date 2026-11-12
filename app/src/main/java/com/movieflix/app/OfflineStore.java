package com.movieflix.app;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public final class OfflineStore {
    private static final String PREFS = "movieflix_offline";
    private OfflineStore() {}
    private static SharedPreferences sp(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
    public static void add(Context c, String id, String title, String url, String path) {
        try {
            SharedPreferences p = sp(c);
            JSONArray arr = new JSONArray(p.getString("items", "[]"));
            JSONObject o = new JSONObject();
            o.put("id", id);
            o.put("title", title);
            o.put("url", url);
            o.put("path", path);
            o.put("ts", System.currentTimeMillis());
            arr.put(o);
            p.edit().putString("items", arr.toString()).apply();
        } catch (Exception ignored) {}
    }
    public static List<JSONObject> list(Context c) {
        List<JSONObject> out = new ArrayList<JSONObject>();
        try {
            JSONArray arr = new JSONArray(sp(c).getString("items", "[]"));
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.optJSONObject(i);
                if (o != null) out.add(o);
            }
        } catch (Exception ignored) {}
        return out;
    }
    public static int count(Context c) { return list(c).size(); }
}
