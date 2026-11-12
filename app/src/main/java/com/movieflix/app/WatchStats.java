package com.movieflix.app;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public final class WatchStats {
    private static final String PREFS = "movieflix_stats";
    private WatchStats() {}
    private static SharedPreferences sp(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
    public static void addWatchSeconds(Context c, long sec) {
        if (sec <= 0) return;
        SharedPreferences p = sp(c);
        p.edit().putLong("total_sec", p.getLong("total_sec", 0) + sec)
                .putInt("sessions", p.getInt("sessions", 0) + 1).apply();
    }
    public static void markFinished(Context c) {
        SharedPreferences p = sp(c);
        p.edit().putInt("finished", p.getInt("finished", 0) + 1).apply();
    }
    public static long totalMinutes(Context c) { return sp(c).getLong("total_sec", 0) / 60; }
    public static int finished(Context c) { return sp(c).getInt("finished", 0); }
    public static int sessions(Context c) { return sp(c).getInt("sessions", 0); }

    public static void saveProgress(Context c, String id, String title, String poster, long pos, long dur) {
        if (id == null || pos < 5000) return;
        try {
            SharedPreferences p = sp(c);
            JSONArray arr = new JSONArray(p.getString("progress", "[]"));
            JSONArray next = new JSONArray();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.optJSONObject(i);
                if (o != null && !id.equals(o.optString("id"))) next.put(o);
            }
            if (dur <= 0 || pos < dur - 15000) {
                JSONObject o = new JSONObject();
                o.put("id", id);
                o.put("title", title == null ? "" : title);
                o.put("poster", poster == null ? "" : poster);
                o.put("pos", pos);
                o.put("dur", dur);
                o.put("ts", System.currentTimeMillis());
                next.put(o);
            }
            // keep last 20
            JSONArray trimmed = new JSONArray();
            int start = Math.max(0, next.length() - 20);
            for (int i = start; i < next.length(); i++) trimmed.put(next.get(i));
            p.edit().putString("progress", trimmed.toString()).apply();
        } catch (Exception ignored) {}
    }
    public static List<JSONObject> progressList(Context c) {
        List<JSONObject> out = new ArrayList<JSONObject>();
        try {
            JSONArray arr = new JSONArray(sp(c).getString("progress", "[]"));
            for (int i = arr.length() - 1; i >= 0; i--) {
                JSONObject o = arr.optJSONObject(i);
                if (o != null) out.add(o);
            }
        } catch (Exception ignored) {}
        return out;
    }
    public static long getPos(Context c, String id) {
        try {
            JSONArray arr = new JSONArray(sp(c).getString("progress", "[]"));
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.optJSONObject(i);
                if (o != null && id.equals(o.optString("id"))) return o.optLong("pos", 0);
            }
        } catch (Exception ignored) {}
        return 0;
    }
}
