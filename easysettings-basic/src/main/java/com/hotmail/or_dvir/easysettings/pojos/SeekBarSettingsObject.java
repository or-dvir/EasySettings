package com.hotmail.or_dvir.easysettings.pojos;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.hotmail.or_dvir.easysettings.R;
import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;
import com.hotmail.or_dvir.easysettings.events.SeekBarSettingsProgressChangedEvent;
import com.hotmail.or_dvir.easysettings.events.SeekBarSettingsValueChangedEvent;
import org.greenrobot.eventbus.EventBus;
import java.io.Serializable;

/**
 * a settings object which contains a {@link SeekBar}
 */
@SuppressWarnings("PointlessBooleanExpression")
public class SeekBarSettingsObject extends SettingsObject<Integer> implements Serializable
{
	//////////////////////////////////
	//mandatory variables			//
	private int minValue; 			//
	private int maxValue; 			//
	//////////////////////////////////

	public SeekBarSettingsObject(Builder builder)
	{
		super(builder.getKey(),
			  builder.getTitle(),
			  builder.getDefaultValue(),
			  builder.getSummary(),
              builder.getTextViewTitleId(),
              builder.getTextViewSummaryId(),
			  builder.getUseValueAsSummary(),
			  builder.hasDivider(),
			  builder.getType(),
			  builder.getImageViewIconId(),
			  builder.getIconDrawableId(),
			  builder.getIconDrawable());

		this.minValue = builder.minValue;
		this.maxValue = builder.maxValue;
	}

	/**
	 *
	 * @return the minimum value of this {@link SeekBarSettingsObject}
	 */
	public int getMinValue()
	{
		return minValue;
	}

	/**
	 *
	 * @return the maximum value of this {@link SeekBarSettingsObject}
	 */
	public int getMaxValue()
	{
		return maxValue;
	}

	/**
	 *
	 * @return the previously saved value of this {@link SeekBarSettingsObject},
	 * or the default value if the previously saved value is no longer valid
	 * (e.g. greater than the maximum or less than the minimum)
	 * @throws IllegalArgumentException if the maximum value of this {@link SeekBarSettingsObject}
	 * is less than or equal to the minimum value
	 * @throws IndexOutOfBoundsException if the default value of this {@link SeekBarSettingsObject}
	 * is greater than the maximum, or lower than the minimum
	 */
	@Override
	public Integer checkDataValidity(Context context, SharedPreferences prefs)
			throws IllegalArgumentException,
				   IndexOutOfBoundsException
	{
		if(getMaxValue() <= getMinValue())
		{
			throw new IllegalArgumentException("SeekBarSettingsObject maximum value must" +
											   " be higher than minimum value" +
											   " and they CANNOT be equal");
		}

		int defaultValue = getDefaultValue();

		if(defaultValue > getMaxValue() ||
		   defaultValue < getMinValue())
		{
			throw new IndexOutOfBoundsException("default value of SeekBarSettingsObject " +
												"with key \"" + getKey() + "\" " +
												"is either lower than the specified minimum or higher than " +
												"the specified maximum.\nspecified max value was " + getMaxValue() +
												", specified min value was " + getMinValue() +
												", specified default value was " + defaultValue);
		}

		//consider this scenario:
		//the developer has a seekBar with a max value of 10, and the user set it to that value.
		//in the next version, the developer changes the max value to 5.
		//now the previously saved value in the SharedPreferences is
		//LARGER than the seekBar max value!
		//in this case, we simply set and save the default value that was given when this object was created
		int previouslySavedValue = prefs.getInt(getKey(), defaultValue);
		int finalValidValue = previouslySavedValue;

		if(previouslySavedValue > getMaxValue() ||
		   previouslySavedValue < getMinValue())
		{
			setValueAndSaveSetting(context, defaultValue);
			finalValidValue = defaultValue;
		}

		//return the final valid value
		return finalValidValue;
	}

	@Override
	public int getLayout()
	{
		return R.layout.seekbar_settings_object;
	}

	@Override
	public String getValueHumanReadable()
	{
		return getValue() + "";
	}

	@Override
	public void initializeViews(View root)
	{
	    super.initializeViews(root);
		SeekBar bar = root.findViewById(R.id.seekBar_seekBarSettingsObject);
		SharedPreferences prefs = EasySettings.retrieveSettingsSharedPrefs(bar.getContext());
		TextView tvSummary = null;

		Integer summaryId = getTextViewSummaryId();
		if(summaryId != null)
		{
			tvSummary = root.findViewById(summaryId);
		}

		final TextView finalTvSummary = tvSummary;

		//NOTE:
		//when we get to this method we know FOR SURE
		//that maxValue is greater than minValue and that they are NOT equal.
		//we also know FOR SURE that the previously saved value is
		//between minValue and maxValue.
		//these conditions are checked when the settings are initially created

		//NOTE:
		//seekBar minimum value is always 0!
		//only possible to set another minimum value if running API 26 or higher.
		//we have to take care of this manually
		bar.setMax(maxValue - minValue);
		int savedValue = prefs.getInt(getKey(), getDefaultValue());

		//initialize our SeekBar with its' previously saved value
		//NOTE:
		//seekBar minimum value is always 0!
		//only possible to set another minimum value if running API 26 or higher.
		//we have to take care of this manually
		bar.setProgress(savedValue - minValue);

		//NOTE:
		//this call MUST come AFTER initializing the value above using bar.setProgress()
		//otherwise this listener will be triggered when initializing,
		//and we DON'T want that!
		bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				//setting the value to that the event below will get updated information
				//NOTE:
				//seekBar minimum value is always 0!
				//only possible to set another minimum value if running API 26 or higher.
				//we have to take care of this manually
				setValue(progress + minValue);

				//NOTE:
				//this if statement MUST come AFTER setting the new value
				if(finalTvSummary != null &&
				   useValueAsSummary())
				{
					finalTvSummary.setText(getValueHumanReadable());
				}

				EventBus.getDefault()
						.post(new SeekBarSettingsProgressChangedEvent(SeekBarSettingsObject.this));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
				//do nothing
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
				//NOTE:
				//seekBar minimum value is always 0!
				//only possible to set another minimum value if running API 26 or higher.
				//we have to take care of this manually
				int valueToSave = seekBar.getProgress() + minValue;
				setValueAndSaveSetting(seekBar.getContext(), valueToSave);

				//NOTE:
				//this if statement MUST come AFTER setting the new value
				if(finalTvSummary != null &&
				   useValueAsSummary())
				{
					finalTvSummary.setText(getValueHumanReadable());
				}

				//IMPORTANT NOTE!!!!
				//this event posting MUST come AFTER the new value has been
				//inserted into the shared preferences
				//so that that subscribed method will receive the UPDATED state
				//of this settings object!!!
				EventBus.getDefault()
						.post(new SeekBarSettingsValueChangedEvent(SeekBarSettingsObject.this));
			}
		});
	}

	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////

	public static class Builder extends SettingsObject.Builder<Builder, Integer>
	{
		//////////////////////////////////
		//mandatory variables			//
		private int minValue; 			//
		private int maxValue; 			//
		//////////////////////////////////

		/**
		 *
		 * @param key the key for this {@link SeekBarSettingsObject}
		 *            to be saved in the apps' {@link SharedPreferences}
		 * @param title the title for this {@link SeekBarSettingsObject}
		 * @param defaultValue
		 * @param minValue the minimum value for the {@link SeekBar} used by this
		 *                 {@link SeekBarSettingsObject}
		 * @param minValue the maximum value for the {@link SeekBar} used by this
		 *                 {@link SeekBarSettingsObject}
		 */
        public Builder(String key,
                       String title,
					   int defaultValue,
					   int minValue,
					   int maxValue)
        {
            super(key,
				  title,
				  defaultValue,
				  R.id.textView_seekBarSettingsObject_title,
				  R.id.textView_seekBarSettingsObject_summary,
				  ESettingsTypes.INTEGER,
				  R.id.imageView_seekBarSettingsObject_icon);

            this.minValue = minValue;
            this.maxValue = maxValue;
        }

		public int getMinValue()
		{
			return minValue;
		}

		public int getMaxValue()
		{
			return maxValue;
		}

		@Override
		public SeekBarSettingsObject build()
		{
			return new SeekBarSettingsObject(this);
		}
	}
}
