package com.hotmail.or_dvir.easysettings.pojos;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @param <V> the type of value for this object to save (String/Integer/Float...)
 *           NOTE: must be a value which can be saved in {@link android.content.SharedPreferences}
 */
@SuppressWarnings("PointlessBooleanExpression")
public abstract class SettingsObject<V> implements Serializable
{
	private static final int NO_ICON_DONT_ALIGN = -1;

	//////////////////////////////////////////////////////////
	//mandatory variables									//
	private final String key;								//
	private final String title;								//
	private final V defaultValue;							//
	@IdRes													//
	private final int textViewTitleId;						//
	@Nullable @IdRes										//
	private final Integer textViewSummaryId;				//
	@Nullable @IdRes										//
	private final Integer imageViewIconId;					//
	private final ESettingsTypes type;						//
	//////////////////////////////////////////////////////////

	//////////////////////////////////////////////
	//optional variables						//
	private String summary;						//
	private V value;							//
	private boolean useValueAsSummary;			//
	private boolean addDivider;					//
	@Nullable @DrawableRes						//
	private Integer iconDrawableId;				//
	@Nullable									//
	private Drawable iconDrawable;				//
	//////////////////////////////////////////////

	@IdRes
	private int individualSettingsRootId;

	/**
	 *
	 * @param key the key for this {@link SettingsObject}
	 *            to be saved in the apps' {@link SharedPreferences}
	 * @param title the title for this {@link SettingsObject}
	 * @param defaultValue the default value of this {@link SettingsObject}
	 * @param summary the summary of this {@link SettingsObject}
	 * @param textViewTitleId the id of the {@link TextView} which is being used
	 *                        as the title for this {@link SettingsObject}
	 * @param textViewSummaryId the id of the {@link TextView} which is being used
	 *                          as the summary for this {@link SettingsObject}
	 * @param useValueAsSummary whether or not the value of this
	 *                          {@link SettingsObject} should be displayed
	 *                          in the summary
	 * @param addDivider whether or not this {@link SettingsObject} should have
	 *                   a divider underneath it
	 * @param type the type of value this {@link SettingsObject} is saving
	 * @param imageViewIconId the id of the {@link ImageView} which is being used
	 *                        as this {@link SettingsObject}s' icon
	 * @param iconDrawableId  the id of the {@link android.graphics.drawable.Drawable}
	 *                        which is being used as the icon
	 *                        for this {@link SettingsObject}
	 * @param  iconDrawable the {@link android.graphics.drawable.Drawable}
	 *                        which is being used as the icon
	 *                        for this {@link SettingsObject}
	 */
	public SettingsObject(String key,
						  String title,
						  V defaultValue,
						  String summary,
						  @IdRes int textViewTitleId,
						  @Nullable @IdRes Integer textViewSummaryId,
						  boolean useValueAsSummary,
						  boolean addDivider,
						  ESettingsTypes type,
						  @Nullable @IdRes Integer imageViewIconId,
						  @Nullable @DrawableRes Integer iconDrawableId,
						  @Nullable Drawable iconDrawable)
	{
		this.key = key;
		this.title = title;
		this.defaultValue = defaultValue;
		this.summary = summary;
		this.textViewTitleId = textViewTitleId;
		this.textViewSummaryId = textViewSummaryId;
		this.useValueAsSummary = useValueAsSummary;
		this.addDivider = addDivider;
		this.type = type;
		this.imageViewIconId = imageViewIconId;
		this.iconDrawableId = iconDrawableId;
		this.iconDrawable = iconDrawable;
	}

	/**
	 *
	 * @return the id of the root view which contains this entire {@link SettingsObject}.
	 * (the type of view depends on the {@link SettingsObject}).<br></br>
	 * use this method to gain access to individual views inside this {@link SettingsObject}
	 * e.g. View root = findViewById(mySettingsObject.getRootId());
	 * textViewSummary = root.findViewById(mySettingsObject.getTextViewSummaryId());
	 */
	@IdRes
	public int getRootId()
	{
		return individualSettingsRootId;
	}

	/**
	 *
	 * @return the id of the drawable which is being used as this {@link SettingsObject}'s
	 * icon,<br><br/>
	 * or {@link SettingsObject#NO_ICON_DONT_ALIGN} if this {@link SettingsObject}
	 * does not have an icon and does NOT need to be aligned with other {@link SettingsObject}s,<br><br/>
	 * or null if this {@link SettingsObject} does not have an icon but it DOES
	 * need to be aligned with other {@link SettingsObject}s
	 */
	@Nullable @DrawableRes
	public Integer getIconDrawableId()
	{
		return iconDrawableId;
	}

	/**
	 * @return same as {@link #getIconDrawableId()} but returns {@link Drawable} instead of Id.
	 * returns null if this {@link SettingsObject} does not have an icon
	 */
	@Nullable
	public Drawable getIconDrawable()
	{
		return iconDrawable;
	}

	/**
	 *
	 * @return the id of the {@link ImageView} which is being used as the container
	 * for this {@link SettingsObject}s' icon, or null if this {@link SettingsObject}
	 * does not have an {@link ImageView}
	 */
	@Nullable @IdRes
	public Integer getImageViewIconId()
	{
		return imageViewIconId;
	}

	/**
	 *
	 * @return the type of value this {@link SettingsObject}
	 */
	public ESettingsTypes getType()
	{
		return type;
	}

	/**
	 *
	 * @return whether this {@link SettingsObject} has a divider underneath it
	 */
	public boolean hasDivider()
	{
		return addDivider;
	}

	/**
	 *
	 * @return the key which is being used to save this {@link SettingsObject}s'
	 * value to this apps' {@link SharedPreferences}
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 *
	 * @return the title of this {@link SettingsObject}
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 *
	 * @return the summary of this {@link SettingsObject}
	 */
	public String getSummary()
	{
		return summary;
	}

	/**
	 *
	 * @return the current value of this {@link SettingsObject}
	 */
	public V getValue()
	{
		return value;
	}

	/**
	 *
	 * @return whether or not this {@link SettingsObject} is displaying
	 * its' value as the summary.<br></br>
	 * if true, the value that will be shown is obtained from {@link SettingsObject#getValueHumanReadable()}
	 */
	public boolean useValueAsSummary()
	{
		return useValueAsSummary;
	}

	/**
	 * sets the given value to this settings object.
	 * NOTE: this only sets the value of this object but does NOT save it in the apps' {@link SharedPreferences}.
	 * if in addition you would also like the value to be saved to the apps'
	 * settings, use {@link #setValueAndSaveSetting(Context, Object)}
	 * @param value
	 */
	public void setValue(V value)
	{
		this.value = value;
	}

	/**
	 * convenience method for setting the given value to this {@link SettingsObject}
	 * and also saving the value in the apps' settings.<br><br/>
	 * VERY IMPORTANT!!! <br><br/>
	 * this method does NOT perform validity checks on the given "value" and
	 * assumes that it is a valid value to be saved as a setting!
	 * for example, if this method is called from {@link SeekBarSettingsObject},
	 * it is assumed that "value" is between {@link SeekBarSettingsObject#getMinValue()}
	 * and {@link SeekBarSettingsObject#getMaxValue()}
	 * @param context the context to be used to get the app settings
	 * @param value the VALID value to be saved in the apps' settings
	 * @throws IllegalArgumentException if the given value is of a type which cannot be saved
	 *                                  to SharedPreferences
	 */
	public void setValueAndSaveSetting(Context context, V value)
			throws IllegalArgumentException
	{
		setValue(value);
		Editor editor = EasySettings.retrieveSettingsSharedPrefs(context).edit();

		switch (getType())
		{
			case VOID:
				//no actual value to save
				break;
			case BOOLEAN:
				editor.putBoolean(getKey(), (Boolean) value);
				break;
			case FLOAT:
				editor.putFloat(getKey(), (Float) value);
				break;
			case INTEGER:
				editor.putInt(getKey(), (Integer) value);
				break;
			case LONG:
				editor.putLong(getKey(), (Long) value);
				break;
			case STRING:
				editor.putString(getKey(), (String) value);
				break;
			case STRING_SET:
				editor.putStringSet(getKey(), getStringSetToSave((Set<?>) value));
				break;
			default:
				throw new IllegalArgumentException("parameter \"value\" must be of a type that " +
												   "can be saved in SharedPreferences. given type was "
												   + value.getClass().getName());
		}

		editor.apply();
	}

	private Set<String> getStringSetToSave(Set<?> givenSet)
	{
		Set<String> setToSave = new LinkedHashSet<>(givenSet.size());

		for(Object obj : givenSet)
		{
			setToSave.add((String) obj);
		}

		return setToSave;
	}

	/**
	 *
	 * @return the default value of this {@link SettingsObject}
	 */
	public V getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 *
	 * @return the id of the {@link TextView} which is being used as this {@link SettingsObject}s'
	 * title
	 */
	@IdRes
	public int getTextViewTitleId()
	{
		return textViewTitleId;
	}

	/**
	 *
	 * @return the if of the {@link TextView} which is being used as this {@link SettingsObject}s'
	 * summary, or null if this {@link SettingsObject} does not have a summary
	 */
	@Nullable @IdRes
	public Integer getTextViewSummaryId()
	{
		return textViewSummaryId;
	}

	/**
	 *
	 * @return the id of the layout of this {@link SettingsObject} to be inflated
	 */
	@LayoutRes
	public abstract int getLayout();

	/**
	 * confirms the validity of existing data and returns a valid value for
	 * this {@link SettingsObject}.
	 * e.g. imagine you have a {@link SeekBarSettingsObject} with a maximum value of 10, and the user sets
	 * it to that value. in the next version of your app, for some reason, you changed the maximum value to be 7 -
	 * the previously saved value (10) is no longer valid because it is above the new maximum.
	 * @param context to be used if needed
	 * @param prefs the shared preferences where the apps' settings are being saved (to be used if needed)
	 * @return the value AS IT SHOULD BE SAVED! <br><br/>
	 * e.g. for multi-choice list: "value1{DELIMITER}value2{DELIMITER}value3".<br><br/>
	 * e.g. for {@link SeekBarSettingsObject}, the previously saved value, or
	 * the default value if the previously saved value is no longer valid
	 */
	public abstract V checkDataValidity(Context context, SharedPreferences prefs);

	/**
	 * @return a human readable form of the value.
	 * e.g. for multi-choice list: "value1, value2, value 3"<br><br/>
	 * NOTE: if {@link SettingsObject#useValueAsSummary()} is true,
	 * this value will be used for the summary
	 */
	public abstract String getValueHumanReadable();

	/**
	 * initializes basic views for this {@link SettingsObject}
	 * such as the title, summary, and icon.
	 * you should override this method and initialize all other views your
	 * {@link SettingsObject} contains (e.g. for {@link CheckBoxSettingsObject},
	 * initialize the {@link android.widget.CheckBox})
	 * @param root the root view containing this entire {@link SettingsObject}
	 */
	public void initializeViews(View root)
	{
		int rootId = View.generateViewId();
		root.setId(rootId);
		individualSettingsRootId = rootId;

		TextView tvTitle = root.findViewById(textViewTitleId);
		tvTitle.setText(getTitle());

		if (textViewSummaryId != null)
		{
			TextView tvSummary = root.findViewById(textViewSummaryId);
			String summary;

			if (useValueAsSummary())
			{
				summary = getValueHumanReadable();
			}

			else
			{
				summary = getSummary();
			}

			if (summary != null &&
				summary.isEmpty() == false)
			{
				tvSummary.setText(summary);
			}

			else
			{
				tvSummary.setVisibility(View.GONE);
			}
		}

		if(imageViewIconId != null)
		{
			ImageView ivIcon = root.findViewById(imageViewIconId);

			if(iconDrawable != null)
			{
				ivIcon.setImageDrawable(iconDrawable);
			}

			//the user specifically set the value to null
			//which means they want the settings object
			//to align as if it has an icon
			else if(iconDrawableId == null)
			{
				ivIcon.setImageDrawable(null);
			}

			//the user does NOT want the settings object to align
			else if (iconDrawableId.equals(NO_ICON_DONT_ALIGN))
			{
				ivIcon.setVisibility(View.GONE);
			}

			//the user wants an actual icon
			else
			{
				ivIcon.setImageResource(iconDrawableId);
			}
		}
	}

	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////

	public static abstract class Builder<B extends Builder<B,V>, V>
	{
		//////////////////////////////////////////////////
		//mandatory variables							//
		private String key;								//
		private final String title;						//
		private final V defaultValue;					//
		@IdRes											//
		private final int textViewTitleId;				//
		@Nullable @IdRes								//
		private final Integer textViewSummaryId;		//
		@Nullable @IdRes								//
		private final Integer imageViewIconId;			//
		private final ESettingsTypes type;				//
		//////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////
		//optional variables												//
		private String summary;												//
		@Nullable @DrawableRes                     							//
		private Integer iconDrawableId = NO_ICON_DONT_ALIGN;				//
		private Drawable iconDrawable = null;   						    //
		private boolean useValueAsSummary = false;							//
		private boolean addDivider = false;									//
		//////////////////////////////////////////////////////////////////////

		/**
		 *
		 * @param key the key for this {@link SettingsObject}
		 *            to be saved in the apps' {@link SharedPreferences}
		 * @param title the title for this {@link SettingsObject}
		 * @param defaultValue
		 * @param textViewTitleId the id of the {@link TextView} which
		 *                        is being used as the title for this
		 *                 		  {@link SettingsObject}
		 * @param textViewSummaryId the id of the {@link TextView} which
		 *                        is being used as the summary for this
		 *                 		  {@link SettingsObject}
		 *                 		    (if no summary is needed, pass null)
		 * @param type the type of value for this object to save (String/Integer/Float...)
		 *           NOTE: must be a value which can be saved in {@link android.content.SharedPreferences}
		 * @param imageViewIconId the id of the {@link android.widget.ImageView} that
		 *                        is being used as the icon for this {@link SettingsObject}
		 *                        (if no icon is needed, pass null)
		 */
		public Builder(String key,
					   String title,
					   V defaultValue,
					   @IdRes int textViewTitleId,
					   @Nullable @IdRes Integer textViewSummaryId,
					   ESettingsTypes type,
					   @Nullable @IdRes Integer imageViewIconId)
		{
			this.key = key;
			this.title = title;
			this.defaultValue = defaultValue;
			this.textViewTitleId = textViewTitleId;
			this.textViewSummaryId = textViewSummaryId;
			this.type = type;
			this.imageViewIconId = imageViewIconId;

			verifyType();
		}

		private void verifyType()
		{
			boolean problem = false;

			switch (type)
			{
				case VOID:
					//no actual value to save - no verification needed
					break;
				case BOOLEAN:
					if(defaultValue instanceof Boolean == false) problem = true;
					break;
				case FLOAT:
					if(defaultValue instanceof Float == false) problem = true;
					break;
				case INTEGER:
					if(defaultValue instanceof Integer == false) problem = true;
					break;
				case LONG:
					if(defaultValue instanceof Long == false) problem = true;
					break;
				case STRING:
					if(defaultValue instanceof String == false) problem = true;
					break;
				case STRING_SET:
					if(defaultValue instanceof Set<?>)
					{
						checkStringSetItems((Set<?>) defaultValue);
					}
					else
					{
						problem = true;
					}
					break;
				default:
					throw new IllegalArgumentException("SettingsObject with key " + key +
													   " has an invalid type. declared type was " + type +
													   ", valid types are " + Arrays.toString(ESettingsTypes.values()));
			}

			if(problem == true)
			{
				throw new IllegalArgumentException("SettingsObject with key " + key +
												   " has declared type " + type +
												   ", but actual type was " + defaultValue.getClass()
																						  .getName());
			}
		}

		private void checkStringSetItems(Set<?> set)
		{
			for(Object obj : set)
			{
				if(obj instanceof String == false)
				{
					throw new IllegalArgumentException("SettingsObject with key " + key +
													   " has declared type STRING_SET" +
													   ", but the given set contains at least one element" +
													   " which is not a String");
				}
			}
		}

		public Drawable getIconDrawable()
		{
			return iconDrawable;
		}

		@Nullable @DrawableRes
		public Integer getIconDrawableId()
		{
			return iconDrawableId;
		}

		@Nullable @IdRes
		public Integer getImageViewIconId()
		{
			return imageViewIconId;
		}

		public ESettingsTypes getType()
		{
			return type;
		}

		public String getKey()
		{
			return key;
		}

		public String getTitle()
		{
			return title;
		}

		public V getDefaultValue()
		{
			return defaultValue;
		}

		public int getTextViewTitleId()
		{
			return textViewTitleId;
		}

		@Nullable @IdRes
		public Integer getTextViewSummaryId()
		{
			return textViewSummaryId;
		}

		public String getSummary()
		{
			return summary;
		}

		public boolean hasDivider()
		{
			return addDivider;
		}

		public boolean getUseValueAsSummary()
		{
			return useValueAsSummary;
		}

		/**
		 *
		 * @param iconId the id of the drawable to be used as an icon for
		 *               this {@link SettingsObject}. if you don't want
		 *         		 to display an icon, but still want this {@link SettingsObject}
		 *         		 to align with the rest of the settings, pass null.<br><br/>
		 *         		 NOTE: this value overrides {@link #setIconDrawable(Drawable)}
		 * @return
		 */
		public B setIcon(@Nullable @DrawableRes Integer iconId)
		{
			this.iconDrawableId = iconId;
			return (B) this;
		}

		/**
		 * same as {@link #setIcon(Integer)} but takes a {@link Drawable}
		 * instead of drawable Id.<br><br/>
		 * NOTE: this value is overridden by {@link #setIcon(Integer)}
		 */
		public B setIconDrawable(@Nullable Drawable iconDrawable)
		{
			this.iconDrawable = iconDrawable;
			return (B) this;
		}

		/**
		 *
		 * @param summary the text to be dispayed as the summary for this {@link SettingsObject}
		 * @return
		 */
		public B setSummary(String summary)
		{
			this.summary = summary;
			return (B) this;
		}

		/**
		 * if this method id called, the value of this {@link SettingsObject}
		 * will be used as the summary (overriding the value set in {@link SettingsObject.Builder#setSummary(String)})
		 * @return
		 */
		public B setUseValueAsSummary()
		{
			this.useValueAsSummary = true;
			return (B) this;
		}

		/**
		 * if this method is called, a divider will show underneath this {@link SettingsObject}
		 * @return
		 */
		public B addDivider()
		{
			this.addDivider = true;
			return (B) this;
		}

		public abstract SettingsObject<V> build();
	}
}
