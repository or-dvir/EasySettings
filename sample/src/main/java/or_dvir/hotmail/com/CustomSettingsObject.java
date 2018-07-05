package or_dvir.hotmail.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;
import com.hotmail.or_dvir.easysettings.pojos.SettingsObject;
import java.io.Serializable;

@SuppressWarnings("PointlessBooleanExpression")
public class CustomSettingsObject extends SettingsObject<Void> implements Serializable
{
	private int number;

	public CustomSettingsObject(Builder builder)
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

	public int incrementNumber()
	{
		return number++;
	}

	@Override
	public Void checkDataValidity(Context context, SharedPreferences prefs)
	{
		//in this case, there is no actual value saved,
		//so no validity check needed and we return null.
		return null;
	}

	@Override
	public int getLayout()
	{
		return R.layout.custom_settings_object;
	}

	@Override
	public String getValueHumanReadable()
	{
		//in this case, there is no actual value save,
		//so just return null
		return null;
	}

	//todo VERY IMPORTANT!!!!
	//todo when extending SettingsObject, you MUST override this method and call
	//todo super.initializeViews(root)!
	@Override
	public void initializeViews(View root)
	{
		//todo VERY IMPORTANT!!!!
		//todo you MUST call the super method in order to initialize basic views
		//todo such as the title TextView
	    super.initializeViews(root);

	    Button btn = root.findViewById(R.id.button);
		final EditText et = root.findViewById(R.id.editText);

		btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				//todo do whatever you want here
				et.setText(incrementNumber() +"");
			}
		});

		et.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				//todo do something
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				//todo do something
			}

			@Override
			public void afterTextChanged(Editable editable)
			{
				//todo do something
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
        public Builder(String key, String title)
        {
            super(key,
				  title,
				  null,
				  R.id.textView_customSettingsObject_title,
				  R.id.textView_customSettingsObject_summary,
				  ESettingsTypes.VOID,
				  null);
		}

		@Override
		public CustomSettingsObject build()
		{
			return new CustomSettingsObject(this);
		}
	}
}
