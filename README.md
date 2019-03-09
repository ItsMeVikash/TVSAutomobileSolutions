# TVSAutomobileSolutions

Download This App
------------------
[Download This App](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/TVS%20Automobile%20SOlutions.apk)

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
----------------
This Page shows the details of the clicked staff members from previous page. 
For clicking pic from Camera i have used [Dexter](https://github.com/Karumi/Dexter). Which is used for taking Runtime Permission. It's very simple to use 
```
Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            } else {

                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

```
 Details Page                                       |  After Capturing Photo
:-----------------------------------------------:|:-----------------------------------------:
![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/details.png?raw=true)                         |  ![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/details2.png?raw=true)

According to the request for showing Datetime Stamp on captured image i have used WaterMark Library [AndroidWM](https://github.com/huangyz0918/AndroidWM). You can alternatively use the Canvas concept also but this library is really good and easy 
```
               WatermarkText watermarkText = new WatermarkText(dateTime)
                        .setPositionX(0)
                        .setPositionY(0)
                        .setTextColor(Color.WHITE)
                        .setTextFont(R.font.champagne)
                        .setTextShadow(0.1f, 5, 5, Color.BLUE)
                        .setTextAlpha(500)
                        .setRotation(0)
                        .setTextSize(20);
                WatermarkBuilder
                        .create(this, bitmap)
                        .loadWatermarkText(watermarkText)
                        .getWatermark()
                        .setToImageView(imageView);
```

###Graph-Page
--------------
Here i have implemeneted Pie Chart and Bar Graph. For this i have used WaterMark Library [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart). For using Bar Graph 

```
BarChart chart = (BarChart) findViewById(R.id.barchart);
 XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);
        xAxis.setSpaceBetweenLabels(0);
        BarDataSet bardataset = new BarDataSet(salary, "Salary Of Employee");
        xAxis.setLabelsToSkip(0);
        xAxis.setTextSize(2);
        chart.animateY(000);
        BarData data = new BarData(name, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
```
Bar Chart                                        |  Pie Chart
:-----------------------------------------------:|:-----------------------------------------:
![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/bar.png?raw=true)                         |  ![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/pie.png?raw=true)

For PieChart 
```
pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(false);
        PieDataSet dataSet = new PieDataSet(yvalues, "Salary Of Employee");
        PieData datas = new PieData(xVals, dataSet);
        data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(datas);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
```

This is the short description for this whole project. If you find any difficulty You can contact me.#KeepCoding
[Download This App](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/TVS%20Automobile%20SOlutions.apk)
