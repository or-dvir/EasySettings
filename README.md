
# EasySettings
EasySettings is a library to help you add and maintain settings (AKA preferences) to your Android app.
This library is designed to be as similar as possible to the "standard" way of creating and maintaining settings but much easier to implement and includes some extra features.

<img src="https://github.com/or-dvir/EasySettings/blob/master/gifs/gif%201.gif" width="240" height="427"/> <img src="https://github.com/or-dvir/EasySettings/blob/master/gifs/gif%202.gif" width="240" height="427"/> <img src="https://github.com/or-dvir/EasySettings/blob/master/gifs/gif%203.gif" width="240" height="427"/>

# Gradle Dependency
In your root build.gradle, at the end of repositories, add this:

    repositories {
        ...
    	maven {url "https://jitpack.io" }
	
	}

For the basic module, add this as a dependency in your app's build.gradle:

    implementation 'com.github.or-dvir.EasySettings:easysettings-basic:[latest release]'

For the dialogs module, **which depends on the basic module**, also add this:

    implementation 'com.github.or-dvir.EasySettings:easysettings-dialogs:[latest release]'



# Why Use This Library?
Here are some of the drawbacks of using the Google's "standard" way ([as described here](https://developer.android.com/guide/topics/ui/settings.html)):

1. Using a specialized subclass of Activity or Fragment ([PreferenceActivity](https://developer.android.com/reference/android/preference/PreferenceActivity.html) and [PreferenceFragment](https://developer.android.com/reference/android/preference/PreferenceFragment.html))
2. Creating and maintaining multiple XML and Java files.
3. Lack of basic features. For example, the inability to add a neutral button to a DialogPreference. 
4. Unnecessary complications trying to create custom settings. For example, instead of using a simple Dialog that we all know and love, we must use a specialized dialog class with new callback methods and limited functionality (see point 3).
5. Just feels cumbersome and overly complicated to use.

On the other hand, with this library:
1. Using a good ol` regular Activity.
2. Requiring a minimal amount of files and file maintenance.
3. Using standard, non-specialized Views including full functionality and all the features you are already familiar with.
4. Creating custom settings is simple and easy.
5. Easy to understand, use, and expand.

# Things You Should be Aware of

 - The layouts of the settings are inflated at run-time (unless used on very old devices, performance should not be affected in a serious way).
 - Since this library uses regular Views and each View is wrapped in a container, the settings activity will have nested layouts (unless used on very old devices, performance should not be affected in a serious way).
 - This library uses other 3rd party libraries:
	 - The basic module uses EventBus by greenrobot. Please familiarize yourself with how to use it [
here](https://github.com/greenrobot/EventBus)
	- The dialogs module uses material-dialogs by afollestad. You don't need to know how to use it, but you can learn more about it [here](https://github.com/afollestad/material-dialogs)
- Currently there is no built-in support for Master/Detail.
- Currently there is no built-in support for "categories" AKA "subscreens" (clicking on a setting which opens another page of settings).

# Available Settings (Quick Overview)
 1. BasicSettingsObject
 2. CheckBoxSettingsObject
 3. SwitchSettingsObject
 4. HeaderSettingsObject
 5. SeekBarSettingsObject
 6. EditTextSettingsDialog - **available in dialogs module**
 7. ListSettingsDialog (can be single-choice or multi-choice) - **available in dialogs module**

See further details for each object below.

# How to Use This Library
## 1. Creating your settings
The first thing you need to do is create your settings. You can use the method `EasySettings.createSettingsArray(SettingsObject...)` like this:

    ArrayList<SettingsObject> mySettings List = EasySettings.createSettingsArray(
    				new BasicSettingsObject.Builder("basicSettingsKey1", "fancy title 1")
    						.setSummary("fancy summary")
    						.build(),
    				new BasicSettingsObject.Builder("basicSettingsKey2","fancy title 2")
    						.setSummary("not so fancy summary")
    						.build(),
    				new CheckBoxSettingsObject.Builder("cehckBoxKey", "checkbox title", false)
    						.setSummary("checkbox summary")
    						.build());
    						
Or you can also create it in any other way. For example:

	BasicSettingsObject setting1 =
			new BasicSettingsObject.Builder("fancy title 1")
					.setSummary("fancy summary")
					.build();
	CheckBoxSettingsObject setting2 =
			new CheckBoxSettingsObject.Builder("checkboxkey", "checkbox title", false)
					.setSummary("checkbox summary")
					.build();
	SwitchSettingsObject setting3 =
			new SwitchSettingsObject.Builder("switchkey", "switch title", true)
					.setSummary("switch summary")
					.build();
	mySettingsList = new ArrayList<>();
	mySettingsList.add(setting1);
	mySettingsList.add(setting2);
	mySettingsList.add(setting3);
	
			
**Note:** This does not actually create the settings but simply creates an array of settings to be created and initialized in step 2.

## 2. Initializing your settings
Immediately  after creating your settings, you must initialize them by calling the method `EasySettings.initializeSettings(context, mySettingsList);`

This method does the following:
 1. Initializes all of the settings objects with their default values.
 2. Actually creates (and saves) the settings in the apps' `SharedPreferences` (unless the setting already exists).
 3. Checks the validity of previously saved values and, if needed, corrects them. For instance,
you have a `SeekBarSettingsObject` with the range of 1-10 and the user sets it to 10. In your next version for some reason you change the range to be 1-5, so now the previously saved value (10) is no longer valid. By default, this method will change the saved value to the default one so it is now valid within the newly created range.

**Note:**  Steps 1 and 2 should be done ASAP in the `OnCreate()` method of your apps' **main activity**. The reason is that we want access to our settings as soon as possible and we want the values to be valid (see `SeekBarSettingsObject` example above).

A full example should look like this:

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//this is the main activity
	   	
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        
       	ArrayList<SettingsObject> mySettings List = EasySettings.createSettingsArray(
    			new BasicSettingsObject.Builder("basicSettingsKey1", "fancy title 1")
    					.setSummary("fancy summary")
    					.build(),
    			new BasicSettingsObject.Builder("basicSettingsKey2","fancy title 2")
    					.setSummary("not so fancy summary")
    					.build(),
    			new CheckBoxSettingsObject.Builder("cehckBoxKey", "checkbox title", false)
    					.setSummary("checkbox summary")
    					.build());
    
    	EasySettings.initializeSettings(this, mySettingsList);
    		
    	//rest of your code
    }


## 3. Creating the settings activity

We're going to need that `ArrayList<SettingsObject>` we created earlier in the settings activity and maybe you'd also want to have it available in other places in your app. It is up to ***you*** to decide how to make that list available to other components of your app. Here are some options:

* Creating a singleton.
* Create a static list in a base activity which all other activities inherit.
* Simply transfer it via an Intent (requires some maintenance - see the sample for an example).

### 3.1 The layout
The layout simply needs a container onto which the settings will be inflated. Most likely this will be a vertical `LinearLayout`. It is recommended to wrap this container with a `ScrollView` so all your settings will fit on smaller screens (unnecessary if you only have a few settings).

    <?xml version="1.0" encoding="utf-8"?>
    <ScrollView
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    
        <LinearLayout
            android:id="@+id/settingsContainer"
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">
    
        </LinearLayout>
    
    </ScrollView>

### 3.2 The activity
In the `onCreate()` method of your **settings activity**, you will need to call the method 

    EasySettings.inflateSettingsLayout(Context, ViewGroup, ArrayList<SettingsObject>);

where `ViewGroup` is the settings container onto which the settings will be inflated. 
A full example should look like this:

    @Override
   	protected void onCreate(Bundle savedInstanceState)
   	{
    	//this is the settings activity
    	
   		super.onCreate(savedInstanceState);
   		setContentView(R.layout.activity_settings);
   		LinearLayout container = findViewById(R.id.settingsContainer);
   		settingsList = (ArrayList<SettingsObject>) getIntent().getSerializableExtra(ActivityMain.EXTRA_SETTINGS_LIST);
   
   		EasySettings.inflateSettingsLayout(this, container, settingsList);
   	}

## 4. Accessing the settings
**Note:** The built-in objects **automatically** save the value to `SharedPreferences` when changed and usually there is no need to manually save them (see exceptions below and in the sample app).

   Retrieving a setting can be done like so:

    boolean value = EasySettings.retrieveSettingsSharedPrefs(this).getBoolean("checkBoxKey", false);
We can also manually edit a value:

    EasySettings.retrieveSettingsSharedPrefs(this)
    		.edit()
    		.putBoolean("checkBoxKey", false)
    		.apply();

**Warning:** We must be careful when manually editing a value like this because our `ArrayList<SettingsObject>` must also be updated in order to prevent bugs.

Another way of accessing settings can be done like this:

    SettingsObject mySettObj = EasySettings.findSettingsObject(String, ArrayList<SettingsObject>);

If manually editing `mySettObj`, please note that the method `setValue(value)` simply changes the value of the object and does **NOT** save it in `SharedPreferences`. In order to change the value of the object **AND** save it to `SharedPreferences`, use `setValueAndSaveSetting(context, value)`.

**Warning:** The method `setValueAndSaveSetting(context, value)` does not perform validity checks on the given value! For example, if this method is used on a `SeekBarSettingsObject`, it is assumed that the given value is within the range of the `SeekBarSettingsObject`.

**Note:** In most cases there is no need to manually edit the values! In order to prevent bugs, you should avoid manually editing values as much as possible.

# Available Settings (In Detail)

**Note:** It is possible that there are some features which were overlooked the readme file. Please see the java docs and source code for full details.

All objects in this library extend `SettingsObject` and all of them have the following **optional** features via their builders (they are self-explanatory - see java docs for full details):

 - `setIcon(@Nullable @DrawableRes Integer iconId)`.
Not using this method at all will align the setting to the start of the container.
 Passing null means there is no icon, but the setting will be aligned as if it has one. This is useful if we have some settings which have icons and some that don't and they should be visually aligned.
 If used, this will override `setIconDrawable(@Nullable Drawable iconDrawable)`
 - `setIconDrawable(@Nullable Drawable iconDrawable)`.
 same as `setIcon(@Nullable @DrawableRes Integer iconId)`, except it takes a `Drawable`.
 NOTE: this `Drawable` is overridden by `setIcon(@Nullable @DrawableRes Integer iconId)`
 - `setSummary(String summary)`
 - `setUseValueAsSummary()` 
 If used, this will override  `setSummary(String summary)`
 - `addDivider()`

### BasicSettingsObject
A settings object which simply sends an event when clicked.

**Type of value saved:** none (Void).

**Optional features:** none.

**Event:** `BasicSettingsClickEvent`

If we want a `BasicSettingsObject` to save a value, this must be done manually (see "Notification Sound" setting in the sample app).

---------

### CheckBoxSettingsObject and SwitchSettingsObject
Setting objects which contain a CheckBox/Switch

**Type of value saved:** Boolean.

**Optional features:**
 - `setOnText(String onText)`
 - `setOffText(String offText)`

**Events:**
 - `CheckBoxSettingsClickEvent`
 - `SwitchSettingsClickEvent`


----------

### HeaderSettingsObject
**Type of value saved:** none (Void).

**Optional features:** none.

**Event:** none.

----------

### SeekBarSettingsObject
A settings object which contains a `SeekBar`.
The value of this setting will be saved **only** when the user **stops** dragging the `SeekBar` handle.

**Type of value saved:** Integer.

**Optional features:** none.

**Events:**
 - `SeekBarSettingsProgressChangedEvent` 
 This is sent whenever the user drags the `SeekBar` handle.
 
 **Note:** If the user drags the handle too fast, some values will be skipped.
 - `SeekBarSettingsValueChangedEvent` 
 This is sent whenever the user lifts his finger from the `SeekBar` handle and the new value has been saved.

**Special notes:**
When performing validity checks, if the previously saved value is not valid (e.g. greater than the maximum), the **default value** will be returned.

----------

### Custom settings objects
See `CustomSettingsObject` in the sample app.


## Dialogs

**Disclaimer:** some code in the sample app is copied directly from the [material-dialogs](https://github.com/afollestad/material-dialogs) library

All dialog objects in this library extend `DialogSettingsObject`.
All dialog objects require **both** the basic module **and** the dialogs module (which uses the [material-dialogs](https://github.com/afollestad/material-dialogs)) library.
All dialog objects have the following default behaviour:

 - Positive button saves the value and sends an event.
 - Negative button does nothing.
 - Neutral button sends an event.

All dialog objects have the following **optional** features via their builders (they are self-explanatory - see java docs for full details):

 - `setDialogTitle(String dialogTitle)`
 - `setDialogContent(String dialogContent)`
 - `setNegativeBtnText(String negativeBtnText)`
 - `setNeutralBtnText(String neutralBtnText)`

### EditTextSettingsObject
A settings object that when clicked opens a dialog containing an `EditText`.

**Type of value saved:** String (saved value is trimmed).

**Optional features:**
 - `setHint(String hint)`
 - `setPrefillText(String prefillText)`
 - `setUseValueAsPrefillText()`
  If used, this will override  `setSummary(String summary)`
 

**Events:**
 - `EditTextSettingsNeutralButtonClickedEvent`
 - `EditTextSettingsValueChangedEvent`

----------

### ListSettingsObject
A settings object that when clicked opens a list dialog.
By default the list is single-choice.

**Type of value saved:** String (saved value is trimmed).
Single-choice: the string value of the element that was selected.
Multi-choice: the string values of all the elements that were selected, separated by a delimiter.

**Optional features:**
 - `setMultiChoice()`

**Events:**
 - `ListSettingsNeutralButtonClickedEvent`
 - `ListSettingsValueChangedEvent`
	 - If single-choice list, the arrays of this event are of size 1.
	 - If multi-choice list, the arrays of this event contain all the values that were selected.

**Special notes:**
- If you have a **multi-choice** list, the default values **must** be created using the methods `ListSettingsObject.prepareValuesAsSingleString(String... values)` or `ListSettingsObject.prepareValuesAsSingleString(ArrayList<String> values)` like this:

```
String listMultiChoiceDefaultItems =
		ListSettingsObject.prepareValuesAsSingleString("one",
   								"four",
   								"five");
```

We do this so the library can properly read those values and mark them as "selected" by separating them  with the library's delimiter.
**Note:** This is **not** needed if your list is single-choice.

- When performing validity checks:
	- If the previously saved value contains elements which are no longer a part of this list, those elements will simply be erased. For example, imagine we have a list with values A, B, C, D and the user chooses A, B, and C. For some reason, in the next version we remove option A from the list. In this case the saved value will be corrected to "B[delimiter]C".
	- In case none of the previously saved values match any of the current list values, the default values are restored.

----------

### Custom dialog settings objects
See `CustomDialogSettingsObject` and `CustomListDialogSettingsObject` in the sample app.


# Switching to this Library in a Pre-Existing App
If you already have an app with some settings and you are sick of handling them the "standard" way, you can easily switch to this library by creating a string in your `strings.xml` named "sharedPreferencesSettingsName". The method `EasySettings.retrieveSettingsSharedPrefs(Context context)` returns the `SharedPreference` file with that name.

Here is an example:

    <resources>
        <string name="app_name">My Application</string>
    
        <!-- if you already have an app with settings and you'd like to switch to this library,
        define this string (with this EXACT NAME) and the value of your own shared preferences file-->
        <string name="sharedPreferencesSettingsName">your own shared preferences file name</string>
    </resources>


# Good to Know

- It is possible to set a text to the summary `TextView` directly (as shown in `ActivitySettings` in the sample app), but beware that if `setSummary()` is not used on a `SettingsObject`, then the summary `TextView`'s visibility will be set to `View.GONE`. In order to fix that, just provide any non-empty value using `setSummary()`.
- As much as possible, avoid reading settings before calling `EasySettings.initializeSettings()`. This will help prevent bugs as some of the settings may not yet exist, or their previous values are not valid and they need to be corrected by the library.
 - To change some of the default values of the library (such as icon size, icon margins, etc...),  simply override the appropriate value inside the `dimes.xml` file.
 - To change the default summary values of some `SettingsObject`, simply extend it and override the method `getValueHumanReadable()`. For example, to get `CheckBoxSettingsObject` to say "yes"/"no" instead of "on"/"off," simply do this:
 ```
public class CheckBoxCustomSummaryValue extends CheckBoxSettingsObject
{
    public CheckBoxCustomSummaryValue(Builder builder)
    {
        super(builder);
    }
    
    @override
    public String getValueHumanReadable()
    {
        return getValue() == true ? "Yes" : "No";
    }
}
```
 - Sometimes it's not as simple to create a custom `SettingsObject` and we have to do things manually. 
For example, the "Notification Sound" setting must be handled and maintained manually inside `ActivitySettings`. In order to display the ringtone picker, we must use the methods `startActivityForResult()` and `OnActivityResult()` which are only available inside an activity (see sample app for further details).
- To access a certain View inside a setting (for example the summary `TextView`):
	- It **must** be done **after** calling `EasySettings.inflateSettingsLayout()`. Otherwise `findViewById()` will return null because the view is not yet inflated.
	-  We **must** call `findViewById()` on the **root container of that** `SettingsObject` and **NOT** on the activity.  All `SettingsObject`s of the same type have the same ID for their summary `TextView` so rather than accessing the second or third `View`, calling `findViewById()` on the activity will return the first one that it finds. This is the proper way to do it (for the full version see the sample app):
```
SettingsObject notificationSetting =
			EasySettings.findSettingsObject(ActivityMain.SETTINGS_KEY_RINGTONE, settingsList);

			View root = findViewById(notificationSetting.getRootId());
			TextView notificationSummary = root.findViewById(notificationSetting.getTextViewSummaryId());
			notificationSummary.setText("my summary text");
```
- If you want to extend any of the objects in this library, here are some notes:
	- You need to familiarize yourself with the methods `checkDataValidity(Context, SharedPreferences)`,  and `initializeViews(View)`. These 2 methods are **very important** when extending an object from this library. Please carefully look at the source code and read the java docs. Please also note that while `checkDataValidity(Context, SharedPreferences)` is abstract, `initializeViews(View)` is **not** and you **must** override it and you **must** call `super.initializeViews(root);`.
	- You **must** provide a `TextView` that will be used as a title.
	- It is highly recommended that you use the library's styles with this `TextView` and any other views in your custom object so it looks as similar as possible to the other settings. For further details on these styles, please see `styles.xml`.


# License

    Copyright 2018 Or Dvir
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



