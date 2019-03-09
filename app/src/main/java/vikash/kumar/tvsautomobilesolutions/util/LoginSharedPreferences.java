package vikash.kumar.tvsautomobilesolutions.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import vikash.kumar.tvsautomobilesolutions.model_pojo.DataModel;

public class LoginSharedPreferences {

    private static ArrayList<DataModel> arrayList=new ArrayList<>();
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void saveArrayList(Context ctx, ArrayList<DataModel> list)
    {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(ctx).edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();
    }

    public static ArrayList<DataModel> getArrayList(Context ctx)
    {
        Type type = new TypeToken<ArrayList<DataModel>>(){}.getType();
        Gson gson = new Gson();
        String json = getSharedPreferences(ctx).getString("MyObject", "");
        arrayList = gson.fromJson(json, type);
        return arrayList;
    }
}
