package com.hotmail.or_dvir.easysettings.pojos;

import android.content.SharedPreferences;

import com.hotmail.or_dvir.easysettings.R;
import java.io.Serializable;

/**
 * a setting object which contains a {@link android.widget.CheckBox}
 */
@SuppressWarnings("PointlessBooleanExpression")
public class CheckBoxSettingsObject extends BooleanSettingsObject implements Serializable
{
	public CheckBoxSettingsObject(Builder builder)
	{
		super(builder);
	}

	@Override
	public int getLayout()
	{
		return R.layout.checkbox_settings_object;
	}

	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////

	public static class Builder extends BooleanSettingsObject.Builder
	{
		/**
		 *
		 * @param key the key for this {@link CheckBoxSettingsObject}
		 *            to be saved in the apps' {@link SharedPreferences}
		 * @param title the title for this {@link CheckBoxSettingsObject}
		 * @param defaultValue
		 */
        public Builder(String key,
                       String title,
                       boolean defaultValue)
        {
            super(key,
                  title,
                  defaultValue,
                  R.id.textView_checkboxSettingsObject_title,
                  R.id.textView_checkboxSettingsObject_summary,
				  R.id.checkbox_checkboxSettingsObject,
				  R.id.imageView_checkBoxSettingsObject_icon);
        }

        @Override
		public CheckBoxSettingsObject build()
		{
			return new CheckBoxSettingsObject(this);
		}
	}
}
