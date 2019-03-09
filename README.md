# TVSAutomobileSolutions

This project consists of 4 Screens :-
--------------------------------------
1. [Login Page](#Login-Page),
2. [List Page](#List-Page),
3. [Details Page](#Details-Page),
4. [Graph Page](#Graph-Page),

###Login-Page
--------------
This Page consists of two edittext each wrapped inside textInputLayout to show error and hint. This is full SCreen Activity. We can manage full screen activity at runtime alseo by
```
 requestWindowFeature(Window.FEATURE_NO_TITLE);
 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

```
I have used Progress Dialog when user click on Login Button. During this time the user is authenticated by using RestApi.Use below code for ProgressDialog
```
progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("We are verifying your credentials ");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        
```

For sending POST request to the given Api i have used [ION Library](https://github.com/koush/ion). You can use it like
```
JsonObject json = new JsonObject();
json.addProperty("username", "username");

Ion.with(context)
.load(link)
.setJsonObjectBody(json)
.asJsonObject()
.setCallback(new FutureCallback<JsonObject>() {
   @Override
    public void onCompleted(Exception e, JsonObject result) {
       
      }
});

```
If User provides wrong credentials then Snackbar will be displayed showing wrong credentials using
```
 View parentLayout = findViewById(android.R.id.content);
 Snackbar.make(parentLayout, "Your Credentials is wrong\n", Snackbar.LENGTH_LONG)
                   .setAction("Try Again", new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {

                          }
                        })
                      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                  .show();
```
Login Page                                       |  Wrong Credentials
:-----------------------------------------------:|:-----------------------------------------:
![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/login.png?raw=true)                         |  ![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/login1.png?raw=true)

The response that is returned in the form of JSON.First key is TABLE_DATA so to get the List for the specific key i created one new class with following code

```
public class TableDataResponse {

    @SerializedName("TABLE_DATA")
    @Expose
    private String tABLEDATA;

    public String getTABLEDATA() {
        return tABLEDATA;
    }

    public void setTABLEDATA(String tABLEDATA) {
        this.tABLEDATA = tABLEDATA;
    }
}
```
and to get Final List for data key i have created one new class
```
public class DataResponse {

    @SerializedName("data")
    @Expose
    private List<List<String>> data = null;

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
```
Finally i have created the ArrayList<GenericType> where i have stored the retuened data from RestAPi.which will be displayed in next List Page.
 
###List-Page
-------------
All returned JSON data returned is displayed in this Page using ListView. ListView Adapter is made for displaying the items to the list
```
public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<DataModel> arraylist;

    public ListViewAdapter(Context context ) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<DataModel>();
        this.arraylist.addAll(MainActivity.staffNameArrayList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return MainActivity.staffNameArrayList.size();
    }

    @Override
    public DataModel getItem(int position) {
        return MainActivity.staffNameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
 }
 ```
 List Page                                       |  SearchView
:-----------------------------------------------:|:-----------------------------------------:
![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/list.png?raw=true)                         |  ![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/list2.png?raw=true)

 SearchView is implemented in this activity to filter the text inside searchView.
 ```
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
 ```

###Details-Page

###Graph-Page

