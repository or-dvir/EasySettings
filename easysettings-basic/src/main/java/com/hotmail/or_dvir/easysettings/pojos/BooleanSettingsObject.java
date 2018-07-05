package com.hotmail.or_dvir.easysettings.pojos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;
import com.hotmail.or_dvir.easysettings.events.CheckBoxSettingsClickEvent;
import com.hotmail.or_dvir.easysettings.events.SwitchSettingsClickEvent;

import org.greenrobot.eventbus.EventBus;
import java.io.Serializable;

/**
 * an object representing any settings object which saves a boolean value,
 * such as {@link CheckBoxSettingsObject} and {@link SwitchSettingsObject}
 */
@SuppressWarnings("PointlessBooleanExpression")
public abstract class BooleanSettingsObject extends SettingsObject<Boolean> implements Serializable
{
	//////////////////////////////////
	//mandatory variables			//
	@IdRes							//
	private int compoundButtonId;	//
	//////////////////////////////////

	//////////////////////////
	//optional variables	//
	private String onText;	//
	private String offText; //
	//////////////////////////

	public BooleanSettingsObject(Builder builder)
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

		this.onText = builder.getOnText();
		this.offText = builder.getOffText();
		this.compoundButtonId = builder.getCompoundButtonId();
	}

	/**
	 *
	 * @return the id of the {@link CompoundButton} which
	 * this {@link BooleanSettingsObject} holds
	 * (e.g. {@link android.widget.CheckBox} and {@link android.widget.Switch})
	 */
	@IdRes
	public int getCompoundButtonId()
	{
		return compoundButtonId;
	}

	/**
	 *
	 * @return the text to display when this {@link BooleanSettingsObject}
	 * is "on" (mening it's value is "true")
	 */
	public String getOnText()
	{
		return onText;
	}

	/**
	 *
	 * @return the text to display when this {@link BooleanSettingsObject}
	 * is "off" (mening it's value is "false")
	 */
	public String getOffText()
	{
		return offText;
	}

	/**
	 *
	 * @param compoundButton changes the text of the given {@link CompoundButton}
	 *                       according if it's "on" or "off"
	 */
	public void setTextAccordingToState(CompoundButton compoundButton)
	{
		if(compoundButton.isChecked())
		{
			compoundButton.setText(onText);
		}

		else if(compoundButton.isChecked() == false)
		{
			compoundButton.setText(offText);
		}
	}

	@Override
	public Boolean checkDataValidity(Context context, SharedPreferences prefs)
	{
		//in this case, there are only 2 possible values.
		//so no need for validity check.
		//however we still return the previously saved value.
		return prefs.getBoolean(getKey(), getDefaultValue());
	}

	/**
	 *
	 * @return "On" if this {@link BooleanSettingsObject}s'
	 * value is "true", or "off" if the value is "false"
	 */
	@Override
	public String getValueHumanReadable()
	{
		return getValue() == true ? "On" : "Off";
	}

	@Override
	public void initializeViews(View root)
	{
		super.initializeViews(root);
		final CompoundButton cb = root.findViewById(getCompoundButtonId());
		SharedPreferences prefs = EasySettings.retrieveSettingsSharedPrefs(cb.getContext());
		TextView tvSummary = null;

		Integer summaryId = getTextViewSummaryId();
		if(summaryId != null)
		{
			tvSummary = root.findViewById(summaryId);
		}

		final TextView finalTvSummary = tvSummary;

		//initialize our compoundButton with its' previously saved value
		cb.setChecked(prefs.getBoolean(getKey(), getDefaultValue()));

		//NOTE:
		//this line MUST come AFTER initializing the compoundButton above with setChecked()
		setTextAccordingToState(cb);

		//IMPORTANT NOTE!!!
		//setting the OnCheckedChangeListener MUST come AFTER setting the initial
		//state of the compoundButton with cb.setChecked().
		//otherwise, this listener WILL BE TRIGGERED during initialization
		//and we DON'T want that!
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				setTextAccordingToState(cb);
				setValueAndSaveSetting(buttonView.getContext(), isChecked);

				//NOTE:
				//this if statement MUST come AFTER setting the new value
				if (finalTvSummary != null &&
					useValueAsSummary())
				{
					finalTvSummary.setText(getValueHumanReadable());
				}

				if (BooleanSettingsObject.this instanceof CheckBoxSettingsObject)
				{
					//IMPORTANT NOTE!!!!
					//this event posting MUST come AFTER the new value has been
					//inserted into the shared preferences
					//so that that subscribed method will receive the UPDATED state
					//of this settings object!!!
					EventBus.getDefault()
							.post(new CheckBoxSettingsClickEvent((CheckBoxSettingsObject)BooleanSettingsObject.this));
				}

				else if (BooleanSettingsObject.this instanceof SwitchSettingsObject)

				{
					//IMPORTANT NOTE!!!!
					//this event posting MUST come AFTER the new value has been
					//inserted into the shared preferences
					//so that that subscribed method will receive the UPDATED state
					//of this settings object!!!
					EventBus.getDefault()
							.post(new SwitchSettingsClickEvent((SwitchSettingsObject)BooleanSettingsObject.this));
				}
			}
		});

		root.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				cb.setChecked(!cb.isChecked());
			}
		});
	}

	@Override
	public abstract int getLayout();

	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////

	public static abstract class Builder extends SettingsObject.Builder<Builder, Boolean>
	{
		//////////////////////////////////
		//mandatory variables			//
		@IdRes							//
		private int compoundButtonId;	//
		//////////////////////////////////

		//////////////////////////////////
		//optional variables			//
		private String onText = "";		//
		private String offText = "";	//
		//////////////////////////////////

		/**
		 *
		 * @param key the key for this {@link BooleanSettingsObject}
		 *            to be saved in the apps' {@link SharedPreferences}
		 * @param title the title for this {@link BooleanSettingsObject}
		 * @param defaultValue
		 * @param textViewTitleId the id of the {@link TextView} which
		 *                        is being used as the title for this
		 *                 		  {@link BooleanSettingsObject}
		 * @param textViewSummaryId the id of the {@link TextView} which
		 *                        is being used as the summary for this
		 *                 		  {@link BooleanSettingsObject}
		 *                 		    (if no summary is needed, pass null)
		 * @param compoundButtonId the id of the {@link CompoundButton}
		 *                         of this {@link BooleanSettingsObject}
		 * @param imageViewIconId the id of the {@link android.widget.ImageView} that
		 *                        is being used as the icon for this {@link BooleanSettingsObject}
		 *                        (if no icon is needed, pass null)
		 */
		public Builder(String key,
					   String title,
					   boolean defaultValue,
					   @IdRes int textViewTitleId,
					   @Nullable @IdRes Integer textViewSummaryId,
					   @IdRes int compoundButtonId,
					   @Nullable @IdRes Integer imageViewIconId)
		{
			super(key,
				  title,
				  defaultValue,
				  textViewTitleId,
				  textViewSummaryId,
				  ESettingsTypes.BOOLEAN,
				  imageViewIconId);

			this.compoundButtonId = compoundButtonId;
		}

		public int getCompoundButtonId()
		{
			return compoundButtonId;
		}

		public String getOnText()
		{
			return onText;
		}

		public String getOffText()
		{
			return offText;
		}

		/**
		 *
		 * @param onText the text to display when this {@link BooleanSettingsObject}s'
		 *                     value is set to "true"
		 */
		public Builder setOnText(String onText)
		{
			this.onText = onText;
			return this;
		}

		/**
		 *
		 * @param offText the text to display when this {@link BooleanSettingsObject}s'
		 *                     value is set to "false"
		 */
		public Builder setOffText(String offText)
		{
			this.offText = offText;
			return this;
		}

		@Override
		public abstract BooleanSettingsObject build();
	}
}
