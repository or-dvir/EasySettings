package or_dvir.hotmail.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.hotmail.or_dvir.easysettings.pojos.BasicSettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.CheckBoxSettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.EasySettings;
import com.hotmail.or_dvir.easysettings.pojos.HeaderSettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.SeekBarSettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.SettingsObject;
import com.hotmail.or_dvir.easysettings.pojos.SwitchSettingsObject;
import com.hotmail.or_dvir.easysettings_dialogs.pojos.EditTextSettingsObject;
import com.hotmail.or_dvir.easysettings_dialogs.pojos.ListSettingsObject;
import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity
{
	private static final int REQUEST_CODE_ACTIVITY_SETTINGS = 1001;

	public static final String EXTRA_SETTINGS_LIST = "EXTRA_SETTINGS_LIST";
	public static final String DEFAULT_VALUE_EDIT_TEXT = "default value";

	private static final String LIST_DIALOG_ONE = "one";
	private static final String LIST_DIALOG_TWO = "two";
	private static final String LIST_DIALOG_THREE = "three";
	private static final String LIST_DIALOG_FOUR = "four";
	private static final String LIST_DIALOG_FIVE = "five";

	//todo remember that these keys must NOT be changed because they are being used as id's!!!
	public static final String SETTINGS_KEY_BASIC = "SETTINGS_KEY_BASIC";
	public static final String SETTINGS_KEY_RINGTONE = "SETTINGS_KEY_RINGTONE";
	public static final String SETTINGS_KEY_CHECKBOX = "SETTINGS_KEY_CHECKBOX";
	public static final String SETTINGS_KEY_SWITCH = "SETTINGS_KEY_SWITCH";
	public static final String SETTINGS_KEY_SEEKBAR = "SETTINGS_KEY_SEEKBAR";
	public static final String SETTINGS_KEY_EDIT_TEXT = "SETTINGS_KEY_EDIT_TEXT";
	public static final String SETTINGS_KEY_LIST_SINGLE_CHOICE = "SETTINGS_KEY_LIST_SINGLE_CHOICE";
	public static final String SETTINGS_KEY_CUSTOM_DIALOG = "SETTINGS_KEY_CUSTOM_DIALOG";
	public static final String SETTINGS_KEY_CUSTOM_LIST_DIALOG = "SETTINGS_KEY_CUSTOM_LIST_DIALOG";
	public static final String SETTINGS_KEY_LIST_MULTI_CHOICE = "SETTINGS_KEY_LIST_MULTI_CHOICE";
	public static final String SETTINGS_KEY_CUSTOM = "SETTINGS_KEY_CUSTOM";

	private ArrayList<SettingsObject> mySettingsList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//todo use this method if your app already has some settings saved in a sharedPreference
		//todo file and you'd like to keep using it instead of the library's default one
		//todo NOTE:
		//todo you MUST call this method AS EARLY AS POSSIBLE to prevent mixing sharedPreference files
//		EasySettings.setCustomSharedPreferenceName("myPreference");

		SharedPreferences settingsSharedPrefs = EasySettings.retrieveSettingsSharedPrefs(this);

		//todo in the case of "ListSettingsObject",
		//todo the default object must be obtained using this method
		String listMultiChoiceDefaultItems =
				ListSettingsObject.prepareValuesAsSingleString(LIST_DIALOG_ONE,
															   LIST_DIALOG_FOUR,
															   LIST_DIALOG_FIVE);

		ArrayList<String> listSettingItems = new ArrayList<>();
		listSettingItems.add(LIST_DIALOG_ONE);
		listSettingItems.add(LIST_DIALOG_TWO);
		listSettingItems.add(LIST_DIALOG_THREE);
		listSettingItems.add(LIST_DIALOG_FOUR);
		listSettingItems.add(LIST_DIALOG_FIVE);

		//todo note that there might be some more methods available for the below builders.
		//todo please check docs/original code for all available options
		mySettingsList = EasySettings.createSettingsArray(
				new BasicSettingsObject.Builder(SETTINGS_KEY_BASIC, "fancy title 1")
						.setSummary("fancy summary")
						.setIcon(null)
						.addDivider()
						.build(),
				new BasicSettingsObject.Builder(SETTINGS_KEY_RINGTONE,"Notification Sound")
						//todo NOTE:
						//todo for teaching purposes, the actual summary value is set in ActivitySettings.
						//todo however we must provide a non-empty value here or otherwise the
						//todo visibility of the summary TextView will be set to View.GONE
						.setSummary("aaa")
						.setIcon(null)
						.addDivider()
						.build(),
				new HeaderSettingsObject.Builder("Boolean settings")
						.build(),
				new CheckBoxSettingsObject.Builder(SETTINGS_KEY_CHECKBOX, "checkbox title", false)
						.setOffText("off")
						.setOnText("on")
						.setSummary("checkbox summary")
						.setIcon(R.drawable.ic_5)
						.build(),
				new SwitchSettingsObject.Builder(SETTINGS_KEY_SWITCH, "switch title", true)
						.setUseValueAsSummary()
						.addDivider()
						.setIcon(R.drawable.ic_3)
						.build(),
				new SeekBarSettingsObject.Builder(SETTINGS_KEY_SEEKBAR, "seekbar title", 0, -5, 7)
						.setUseValueAsSummary()
						.addDivider()
						.setIcon(R.drawable.ic_2)
						.build(),
				new HeaderSettingsObject.Builder("Dialog Settings")
						.build(),
				new EditTextSettingsObject.Builder(SETTINGS_KEY_EDIT_TEXT, "edit text title", DEFAULT_VALUE_EDIT_TEXT, "save")
						.setDialogContent("enter new value here")
						.setDialogTitle("my dialog title")
						.setHint("i am a hint")
						.setPrefillText("pre-fill text")
						.setUseValueAsPrefillText() //todo overrides the value set above!
						.setIcon(null)
						.setNegativeBtnText("cancel")
						.setNeutralBtnText("neutral")
						.setUseValueAsSummary()
						.build(),
				new ListSettingsObject.Builder(SETTINGS_KEY_LIST_SINGLE_CHOICE, "single choice dialog", "one", listSettingItems, "save")
						.setUseValueAsSummary()
						.setNegativeBtnText("cancel")
						.setNeutralBtnText("neutral")
						.build(),
				new CustomDialogSettingsObject.Builder(SETTINGS_KEY_CUSTOM_DIALOG, "custom dialog", R.layout.custom_dialog_settings_object)
						.build(),
				new CustomListDialogSettingsObject.Builder(SETTINGS_KEY_CUSTOM_LIST_DIALOG, "custom list dialog")
						.setPositiveBtnText("ok")
						.setNegativeBtnText("cancel")
						.setDialogTitle("my custom list items")
						.build(),
				new ListSettingsObject.Builder(SETTINGS_KEY_LIST_MULTI_CHOICE, "multi choice dialog", listMultiChoiceDefaultItems, listSettingItems, "save")
 						.setUseValueAsSummary()
						.setIcon(R.drawable.ic_1)
						.setMultiChoice()
						.setNeutralBtnText("neutral")
						.addDivider()
						.build(),
				new HeaderSettingsObject.Builder("Custom Settings")
						.build(),
				new CustomSettingsObject.Builder(SETTINGS_KEY_CUSTOM, "title")
						.setSummary("summary")
						.build());

		//todo could also do it this way:
//		BasicSettingsObject setting1 =
//				new BasicSettingsObject.Builder("fancy title 1")
//						.setSummary("fancy summary")
//						.build();
//		CheckBoxSettingsObject setting2 =
//				new CheckBoxSettingsObject.Builder("checkboxkey", "checkbox title", false)
//						.setSummary("checkbox summary")
//						.setOnText("on")
//						.build();
//		SwitchSettingsObject setting3 =
//				new SwitchSettingsObject.Builder("switchkey", "switch title", true)
//						.setSummary("switch summary")
//						.setOffText("off")
//						.build();
//		mySettingsList = new ArrayList<>();
//		mySettingsList.add(setting1);
//		mySettingsList.add(setting2);
//		mySettingsList.add(setting3);

		//todo must call this method ASAP in the onCreate() method of our main activity,
		//todo and immediately after creating our list.
		//todo the reason is so we can access our settings as soon as possible.
		//todo see docs/original code for further details
		EasySettings.initializeSettings(this, mySettingsList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//todo we must update the the settings list associated with this
		//todo activity with the updated settings list.
		//todo because the list this activity holds is separate from the one in ActivitySettings
		if (requestCode == REQUEST_CODE_ACTIVITY_SETTINGS &&
			resultCode == RESULT_OK)
		{
			mySettingsList = (ArrayList<SettingsObject>) data.getSerializableExtra(ActivitySettings.INTENT_EXTRA_RESULT);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.settings:

				Intent intent = new Intent(this,ActivitySettings.class);
				intent.putExtra(EXTRA_SETTINGS_LIST, mySettingsList);
				startActivityForResult(intent, REQUEST_CODE_ACTIVITY_SETTINGS);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
