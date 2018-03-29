package com.hotmail.or_dvir.easysettings.pojos;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import com.hotmail.or_dvir.easysettings.R;
import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;
import com.hotmail.or_dvir.easysettings.events.BasicSettingsClickEvent;
import org.greenrobot.eventbus.EventBus;
import java.io.Serializable;

/**
 * a simple settings object which simply sends an event when clicked
 */
@SuppressWarnings("PointlessBooleanExpression")
public class BasicSettingsObject extends SettingsObject<Void> implements Serializable
{
	public BasicSettingsObject(Builder builder)
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
			  builder.getIconDrawableId());
	}

	@Override
	public int getLayout()
	{
		return R.layout.basic_settings_object;
	}

	@Override
	public Void checkDataValidity(Context context, SharedPreferences prefs)
	{
		//in this case, there is no actual value saved,
		//so no validity check needed and we return null.
		return null;
	}

	@Override
	public String getValueHumanReadable()
	{
		//in this case, there is no actual value save,
		//so just return null
		return null;
	}

	@Override
	public void initializeViews(View root)
	{
		super.initializeViews(root);

		root.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				EventBus.getDefault().post(new BasicSettingsClickEvent(BasicSettingsObject.this));
			}
		});
	}

	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////

	public static class Builder extends SettingsObject.Builder<Builder, Void>
	{
		/**
		 *
		 * @param key the key for this {@link BasicSettingsObject}
		 *            to be saved in the apps' {@link SharedPreferences}
		 * @param title the title for this {@link BasicSettingsObject}
		 */
		public Builder(String key,
					   String title)
		{
			super(key,
				  title,
				  null,
				  R.id.textView_basicSettingsObject_title,
				  R.id.textView_basicSettingsObject_summary,
				  ESettingsTypes.VOID,
				  R.id.imageView_basicSettingsObject_icon);
		}

		@Override
		public BasicSettingsObject build()
		{
			return new BasicSettingsObject(this);
		}
	}
}
