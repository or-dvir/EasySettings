package com.hotmail.or_dvir.easysettings_dialogs.pojos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;
import com.hotmail.or_dvir.easysettings.pojos.SettingsObject;
import com.hotmail.or_dvir.easysettings_dialogs.events.EditTextSettingsNeutralButtonClickedEvent;
import com.hotmail.or_dvir.easysettings_dialogs.events.ListSettingsNeutralButtonClickedEvent;
import org.greenrobot.eventbus.EventBus;
import java.io.Serializable;

/**
 * a settings object which when clicked, opens a dialog.
 * @param <B> a Builder class extending {@link DialogSettingsObject.Builder}
 * @param <V> the type of value for this object to save (String/Integer/Float...)
 *           NOTE: must be a value which can be saved in {@link android.content.SharedPreferences}
 */
public abstract class DialogSettingsObject<B extends DialogSettingsObject.Builder<B,V>, V>
		extends SettingsObject<V> implements Serializable
{
	private String dialogTitle;
	private String dialogContent;
	private String positiveBtnText;
	private String negativeBtnText;
	private String neutralBtnText;

	public DialogSettingsObject(Builder<B, V> builder)
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

		this.dialogTitle = builder.dialogTitle;
		this.dialogContent = builder.dialogContent;
		this.positiveBtnText = builder.positiveBtnText;
		this.negativeBtnText = builder.negativeBtnText;
		this.neutralBtnText = builder.neutralBtnText;
	}

	/**
	 *
	 * @return the title of the dialog
	 */
	public String getDialogTitle()
	{
		return dialogTitle;
	}

	/**
	 *
	 * @return the content (or message) of the dialog
	 */
	public String getDialogContent()
	{
		return dialogContent;
	}

	/**
	 *
	 * @return the text for the positive button of the dialog,
	 * or empty string if no positive button is displayed
	 */
	public String getPositiveBtnText()
	{
		return positiveBtnText;
	}

	/**
	 *
	 * @return the text for the negative button of the dialog,
	 * or empty string if no negative button is displayed
	 */
	public String getNegativeBtnText()
	{
		return negativeBtnText;
	}

	/**
	 *
	 * @return the text for the neutral button of the dialog,
	 * or empty string if no neutral button is displayed
	 */
	public String getNeutralBtnText()
	{
		return neutralBtnText;
	}

	/**
	 * initializes a general {@link MaterialDialog.Builder} with the most common
	 * values (content, buttons, etc...)
	 * @param context
	 * @return a {@link MaterialDialog.Builder} so you can keep calling more methods
	 * on it
	 */
	public MaterialDialog.Builder getBasicMaterialDialogBuilder(Context context)
	{
		MaterialDialog.Builder builder = new MaterialDialog.Builder(context);

		builder.canceledOnTouchOutside(false);

		if(getDialogTitle().isEmpty() == false)
		{
			builder.title(getDialogTitle());
		}

		if(getDialogContent().isEmpty() == false)
		{
			builder.content(getDialogContent());
		}

		if(getPositiveBtnText().isEmpty() == false)
		{
			builder.positiveText(getPositiveBtnText());
		}

		if(getNegativeBtnText().isEmpty() == false)
		{
			builder.negativeText(getNegativeBtnText());
		}

		if(getNeutralBtnText().isEmpty() == false)
		{
			builder.neutralText(getNeutralBtnText());
			builder.onNeutral(new MaterialDialog.SingleButtonCallback()
			{
				@Override
				public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
				{
					if(DialogSettingsObject.this instanceof EditTextSettingsObject)
					{
						EventBus.getDefault()
								.post(new EditTextSettingsNeutralButtonClickedEvent((EditTextSettingsObject) DialogSettingsObject.this));
					}

					else if(DialogSettingsObject.this instanceof ListSettingsObject)

					{
						EventBus.getDefault()
								.post(new ListSettingsNeutralButtonClickedEvent((ListSettingsObject) DialogSettingsObject.this));
					}
				}
			});
		}

		return builder;
	}

	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////

	public static abstract class Builder<B extends Builder<B, V>, V> extends SettingsObject.Builder<B, V>
	{
		private String dialogTitle = "";
		private String dialogContent = "";
		private String positiveBtnText = "";
		private String negativeBtnText = "";
		private String neutralBtnText = "";

		/**
		 *
		 * @param key the key for this {@link DialogSettingsObject}
		 *            to be saved in the apps' {@link SharedPreferences}
		 * @param title the title for this {@link DialogSettingsObject}
		 * @param defaultValue
		 * @param textViewTitleId the id of the {@link android.widget.TextView} that
		 *                        is being used as a title for this {@link DialogSettingsObject}
		 * @param textViewSummaryId the id of the {@link android.widget.TextView} that
		 *                        is being used as a summary for this {@link DialogSettingsObject}
		 *                        (if no summary is needed, pass null)
		 * @param type the type of value this {@link DialogSettingsObject} will save
		 * @param imageViewIconId the id of the {@link android.widget.ImageView} that
		 *                        is being used as a summary for this {@link DialogSettingsObject}
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
			super(key,
				  title,
				  defaultValue,
				  textViewTitleId,
				  textViewSummaryId,
				  type,
				  imageViewIconId);
		}

		/**
		 *
		 * @param dialogTitle the title of the dialog
		 * @return
		 */
		public B setDialogTitle(String dialogTitle)
		{
			this.dialogTitle = dialogTitle;
			return (B) this;
		}

		/**
		 *
		 * @param dialogContent the content (or message) of the dialog
		 * @return
		 */
		public B setDialogContent(String dialogContent)
		{
			this.dialogContent = dialogContent;
			return (B) this;
		}

		/**
		 *
		 * @param positiveBtnText the text of the positive button of the dialog
		 * @return
		 */
		public B setPositiveBtnText(String positiveBtnText)
		{
			this.positiveBtnText = positiveBtnText;
			return (B) this;
		}

		/**
		 *
		 * @param negativeBtnText the text of the negative button of the dialog
		 * @return
		 */
		public B setNegativeBtnText(String negativeBtnText)
		{
			this.negativeBtnText = negativeBtnText;
			return (B) this;
		}

		/**
		 *
		 * @param neutralBtnText the text of the neutral button of the dialog
		 * @return
		 */
		public B setNeutralBtnText(String neutralBtnText)
		{
			this.neutralBtnText = neutralBtnText;
			return (B) this;
		}

		public String getDialogTitle()
		{
			return dialogTitle;
		}

		public String getDialogContent()
		{
			return dialogContent;
		}

		public String getPositiveBtnText()
		{
			return positiveBtnText;
		}

		public String getNegativeBtnText()
		{
			return negativeBtnText;
		}

		public String getNeutralBtnText()
		{
			return neutralBtnText;
		}
	}
}
