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
![Login Page]()
![Wrong Credentials]()
Login Page             |  Wrong Credentials
:-------------------------:|:-------------------------:
![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/login.png?raw=true)  |  ![](https://github.com/ItsMeVikash/TVSAutomobileSolutions/blob/master/Screenshots/login1.png?raw=true)

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


###Details-Page

###Graph-Page

