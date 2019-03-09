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

###List-Page

###Details-Page

###Graph-Page

