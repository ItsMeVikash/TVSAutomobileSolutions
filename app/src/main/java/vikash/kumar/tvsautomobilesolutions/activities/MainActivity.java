package vikash.kumar.tvsautomobilesolutions.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vikash.kumar.tvsautomobilesolutions.R;
import vikash.kumar.tvsautomobilesolutions.adapter.ListViewAdapter;
import vikash.kumar.tvsautomobilesolutions.model_pojo.DataModel;
import vikash.kumar.tvsautomobilesolutions.util.LoginSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private ListViewAdapter adapter;
    public static ArrayList<DataModel> staffNameArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Welcome to TVS Automobile Solution", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
        list = (ListView) findViewById(R.id.listview);
        staffNameArrayList=new ArrayList<>();

        if (LoginSharedPreferences.getArrayList(getApplicationContext())!=null)
        staffNameArrayList = LoginSharedPreferences.getArrayList(getApplicationContext());
        else {
            ArrayList<DataModel> lists=new ArrayList<>();
            LoginSharedPreferences.saveArrayList(getApplicationContext(),lists);
            Intent in = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
        }
        adapter = new ListViewAdapter(this);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,StaffDetails.class);
                intent.putExtra("name",staffNameArrayList.get(position).getName());
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {
            ArrayList<DataModel> lists=null;
            LoginSharedPreferences.saveArrayList(getApplicationContext(),lists);
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void barGraph(View view){
        Intent intent=new Intent(MainActivity.this,BarGraph.class);
        startActivity(intent);
    }
}
