package com.hotmail.or_dvir.easysettings.pojos;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import com.hotmail.or_dvir.easysettings.R;
import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;

import java.io.Serializable;

/**
 * a settings object which represents a header
 */
@SuppressWarnings("PointlessBooleanExpression")
public class HeaderSettingsObject extends SettingsObject<Void> implements Serializable
{
	public HeaderSettingsObject(Builder builder)
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
	}

	@Override
	public int getLayout()
	{
		return R.layout.header_settings_object;
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
		//just a header - nothing else to do other than initialize the text
		super.initializeViews(root);
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
		 * @param title the title of this header
		 */
		public Builder(String title)
		{
			//this is just a header.
			//no key needed
			super("",
				  title,
				  null,
				  R.id.textView_headerSettingsObject_title,
				  null,
				  ESettingsTypes.VOID,
				  null);
		}

		/**
		 * a header does not contain an icon.
		 * this method does nothing
		 * @param iconId
		 * @return
		 */
		@Override
		public Builder setIcon(Integer iconId)
		{
			return this;
		}

		/**
		 * a header does not contain a summary.
		 * this method does nothing
		 * @param summary
		 * @return
		 */
		//this is just a header - no summary
		@Override
		public Builder setSummary(String summary)
		{
			return this;
		}

		/**
		 * a header does not contain a summary.
		 * this method does nothing
		 */
		@Override
		public Builder setUseValueAsSummary()
		{
			return this;
		}

		@Override
		public HeaderSettingsObject build()
		{
			return new HeaderSettingsObject(this);
		}
	}
}
