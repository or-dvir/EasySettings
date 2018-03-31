package or_dvir.hotmail.com;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hotmail.or_dvir.easysettings.events.BasicSettingsClickEvent;
import com.hotmail.or_dvir.easysettings.events.CheckBoxSettingsClickEvent;
import com.hotmail.or_dvir.easysettings.events.SeekBarSettingsProgressChangedEvent;
import com.hotmail.or_dvir.easysettings.events.SeekBarSettingsValueChangedEvent;
import com.hotmail.or_dvir.easysettings.events.SwitchSettingsClickEvent;
import com.hotmail.or_dvir.easysettings.pojos.EasySettings;
import com.hotmail.or_dvir.easysettings.pojos.SettingsObject;
import com.hotmail.or_dvir.easysettings_dialogs.events.EditTextSettingsNeutralButtonClickedEvent;
import com.hotmail.or_dvir.easysettings_dialogs.events.EditTextSettingsValueChangedEvent;
import com.hotmail.or_dvir.easysettings_dialogs.events.ListSettingsNeutralButtonClickedEvent;
import com.hotmail.or_dvir.easysettings_dialogs.events.ListSettingsValueChangedEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;

public class ActivitySettings extends AppCompatActivity
{
	private static final int REQUEST_CODE_NOTIFICATION_PICKER = 1000;
	public static final String INTENT_EXTRA_RESULT = "INTENT_EXTRA_RESULT";
	public static final String DEFAULT_NOTIFICATION_SUMMARY = "Silent";
	public static final String SETTINGS_RINGTONE_SILENT_VALUE = "";

	private Toast mToast;
	private ArrayList<SettingsObject> settingsList;
	@Nullable
	private TextView tvNotificationToneSummary;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		LinearLayout container = findViewById(R.id.settingsContainer);

		//no need to check the casting.
		//i know the type i put in the intent
		//noinspection unchecked
		settingsList = (ArrayList<SettingsObject>) getIntent().getSerializableExtra(ActivityMain.EXTRA_SETTINGS_LIST);

		EasySettings.inflateSettingsLayout(this, container, settingsList);

		//todo initializing tvNotificationToneSummary MUST be done AFTER
		//todo EasySettings.inflateSettingsLayout(). otherwise findViewById() will return null
		//todo because the view has not been inflated yet
		SettingsObject notificationSetting =
				EasySettings.findSettingsObject(ActivityMain.SETTINGS_KEY_RINGTONE, settingsList);

		if(notificationSetting != null &&
		   notificationSetting.getTextViewSummaryId() != null)
		{
			//TODO VERY IMPORTANT!!!
			//TODO in order to get the correct TextView (or any other view for that matter)
			//TODO for a specific setting, you MUST call findViewById() on the root of that setting!!!
			//TODO the reason is all views (title, summary, checkbox, switch, seekBar etc...)
			//TODO have the same id's!!! so for example if you have multiple
			//TODO settings of type BasicSettingsObject and you want to get the summary id of the second one,
			//TODO but you just call findViewById on the activity and not the root of the setting,
			//TODO you will get the FIRST summary in this activity (the first BasicSettingsObject)
			//TODO and NOT the one you were actually looking for!!!
			View root = findViewById(notificationSetting.getRootId());
			tvNotificationToneSummary = root.findViewById(notificationSetting.getTextViewSummaryId());

			String notificationUriAsString = EasySettings.retrieveSettingsSharedPrefs(this)
														.getString(ActivityMain.SETTINGS_KEY_RINGTONE,
																   SETTINGS_RINGTONE_SILENT_VALUE);
			//silent was chosen
			if(notificationUriAsString.equals(SETTINGS_RINGTONE_SILENT_VALUE))
			{
				tvNotificationToneSummary.setText(DEFAULT_NOTIFICATION_SUMMARY);
			}

			else
			{
				Uri notificationUri = Uri.parse(notificationUriAsString);

				Ringtone ringtone = RingtoneManager.getRingtone(this, notificationUri);
				String soundTitle = ringtone.getTitle(this);
				tvNotificationToneSummary.setText(soundTitle);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_CODE_NOTIFICATION_PICKER &&
			resultCode == RESULT_OK)
		{
			Uri notificationToneUri = (Uri) data.getExtras().get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

			//todo save the value to shared preferences
			EasySettings.retrieveSettingsSharedPrefs(this)
						.edit()
						.putString(ActivityMain.SETTINGS_KEY_RINGTONE,
								   //if notificationToneUri is null, it means "silent"
								   //was chosen.
								   notificationToneUri == null ? SETTINGS_RINGTONE_SILENT_VALUE
															   : notificationToneUri.toString())
						.apply();

			String soundTitle;

			//"silent" was chosen
			if(notificationToneUri == null)
			{
				 soundTitle = DEFAULT_NOTIFICATION_SUMMARY;
			}

			else
			{
				Ringtone ringtone = RingtoneManager.getRingtone(this, notificationToneUri);
				soundTitle = ringtone.getTitle(this);
			}

			if(tvNotificationToneSummary != null)
			{
				tvNotificationToneSummary.setText(soundTitle);
			}
		}
	}

	@Override
	protected void onPause()
	{
		if (mToast != null)
		{
			mToast.cancel();
			mToast = null;
		}

		super.onPause();
	}

	private void makeToast(String message)
	{
		if (mToast != null)
		{
			mToast.cancel();
		}

		mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
		mToast.show();
	}

	@Override
	public void onBackPressed()
	{
		//todo we must update the calling activity with changes made to the settings list
		Intent results = new Intent();
		results.putExtra(INTENT_EXTRA_RESULT, settingsList);
		setResult(RESULT_OK, results);
		super.onBackPressed();
	}

	@Subscribe
	public void onBasicSettingsClicked(BasicSettingsClickEvent event)
	{
		if(event.getClickedSettingsObj()
				.getKey()
				.equals(ActivityMain.SETTINGS_KEY_RINGTONE))
		{
			String uriAsString = EasySettings.retrieveSettingsSharedPrefs(this)
											 .getString(ActivityMain.SETTINGS_KEY_RINGTONE,
														SETTINGS_RINGTONE_SILENT_VALUE);

			Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
				  .putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false)
				  .putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
				  .putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "");

			if(uriAsString.equals(SETTINGS_RINGTONE_SILENT_VALUE))
			{
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, 0);
			}

			else
			{
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
								Uri.parse(uriAsString));
			}

			startActivityForResult(intent, REQUEST_CODE_NOTIFICATION_PICKER);
		}

		else
		{
			makeToast(event.getClickedSettingsObj().getTitle());
		}
	}

	@Subscribe
	public void onCheckBoxSettingsClicked(CheckBoxSettingsClickEvent event)
	{
		boolean prefValue = EasySettings.retrieveSettingsSharedPrefs(this)
										.getBoolean(event.getClickedSettingsObj().getKey(),
													event.getClickedSettingsObj().getDefaultValue());

		makeToast(prefValue + "");
	}

	@Subscribe
	public void onSwitchSettingsClicked(SwitchSettingsClickEvent event)
	{
		boolean prefValue = EasySettings.retrieveSettingsSharedPrefs(this)
										.getBoolean(event.getClickedSettingsObj().getKey(),
													event.getClickedSettingsObj().getDefaultValue());

		makeToast(prefValue + "");
	}

	@Subscribe
	public void onListDialogSettingsNeutralButtonClicked(ListSettingsNeutralButtonClickedEvent event)
	{
		makeToast("list dialog neutral button click");
	}

	@Subscribe
	public void onEditTextDialogSettingsNeutralButtonClicked(EditTextSettingsNeutralButtonClickedEvent event)
	{
		makeToast("edit text neutral button click");
	}

	@Subscribe
	public void onSeekBarSettingsValueChanged(SeekBarSettingsValueChangedEvent event)
	{
		makeToast("value set to " + event.getSeekBarObj().getValue());
	}

	@Subscribe
	public void onEditTextSettingsValueChanged(EditTextSettingsValueChangedEvent event)
	{
		makeToast("value set to " + event.getEditTextSettingsObj().getValue());
	}

	@Subscribe
	public void onListSettingsValueChanged(ListSettingsValueChangedEvent event)
	{
		//todo remember: event.getNewValueAsSaved() returns the value with the delimiter
		makeToast("value set to " + event.getNewValueAsSaved());

		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < event.getNewValues().length; i++)
		{
			temp.append("value: ")
				.append(event.getNewValues()[i])
				.append(", index: ")
				.append(event.getNewValuesIndices()[i])
				.append("\n");
		}
	}

	@Subscribe
	public void onSeekBarSettingsProgressChanged(SeekBarSettingsProgressChangedEvent event)
	{
		//todo do something
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		EventBus.getDefault().unregister(this);
	}
}
