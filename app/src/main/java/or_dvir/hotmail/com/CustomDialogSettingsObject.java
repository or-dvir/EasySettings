package or_dvir.hotmail.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hotmail.or_dvir.easysettings.enums.ESettingsTypes;
import com.hotmail.or_dvir.easysettings_dialogs.pojos.DialogSettingsObject;
import java.io.Serializable;

public class CustomDialogSettingsObject extends DialogSettingsObject<CustomDialogSettingsObject.Builder, Void>
		implements Serializable
{
	@LayoutRes
	private int customViewId;
	private boolean wrapInScrollView;

    private CustomDialogSettingsObject(Builder builder)
    {
        super(builder);
        this.customViewId = builder.customViewId;
        this.wrapInScrollView = builder.wrapInScrollView;

		//todo if you don't want to use the builder pattern,
		//todo you can also use a regular constructor
    }

	public boolean shouldWrapInScrollView()
	{
		return wrapInScrollView;
	}

	@LayoutRes
	public int getCustomViewId()
	{
		return customViewId;
	}

	@Override
    public int getLayout()
    {
        //todo in this case i am using the same layout as a basic settings object
        //todo which simply contains a title text view, and a summary text view
        //todo you can put your own custom layout here
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
        //todo because we are using the R.layout.layout basic_settings_object,
        //todo we need to call the super method here to initialize the title and summary text views.
        //todo if you are making a completely custom layout here, no need to call the super method
        super.initializeViews(root);

        root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showDialog(view);

                //todo if you'd like, you can also send a custom event here to notify
                //todo the settings activity of the click.
                //todo NOTE: this specific line is only an example and is copy-pasted
                //todo from the class BasicSettingsObject.
                //todo you need to make your own custom event here
//                EventBus.getDefault().post(new BasicSettingsClickEvent(BasicSettingsObject.this));
            }
        });
    }

    private void showDialog(View root)
	{
		Context context = root.getContext();

		MaterialDialog.Builder builder = getBasicMaterialDialogBuilder(context);
		MaterialDialog dialog = builder.customView(getCustomViewId(), shouldWrapInScrollView())
									   .show();

		View view = dialog.getCustomView();
		Button btn1 = view.findViewById(R.id.button1);
		Button btn2 = view.findViewById(R.id.button2);

		btn1.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Toast.makeText(view.getContext(), "button 1 clicked", Toast.LENGTH_SHORT).show();

				//todo if you'd like, you can also send a custom event here to notify
				//todo the settings activity of the click.
				//todo NOTE: this specific line is only an example and is copy-pasted
				//todo from the class BasicSettingsObject.
				//todo you need to make your own custom event here
//                EventBus.getDefault().post(new BasicSettingsClickEvent(BasicSettingsObject.this));
			}
		});

		btn2.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Toast.makeText(view.getContext(), "button 2 clicked", Toast.LENGTH_SHORT).show();

				//todo if you'd like, you can also send a custom event here to notify
				//todo the settings activity of the click.
				//todo NOTE: this specific line is only an example and is copy-pasted
				//todo from the class BasicSettingsObject.
				//todo you need to make your own custom event here
//                EventBus.getDefault().post(new BasicSettingsClickEvent(BasicSettingsObject.this));
			}
		});
	}

    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////

    public static class Builder extends DialogSettingsObject.Builder<Builder, Void>
    {
    	@LayoutRes
    	private int customViewId;
    	private boolean wrapInScrollView = false;

        public Builder(String key,
                       String title,
					   @LayoutRes int customDialogViewId)
        {
            //todo don't forget to pass your own id's here!
            super(key,
				  title,
				  null,
				  R.id.textView_basicSettingsObject_title,
				  R.id.textView_basicSettingsObject_summary,
				  ESettingsTypes.VOID,
				  R.id.imageView_basicSettingsObject_icon);

            this.customViewId = customDialogViewId;
        }

		public Builder setWrapInScrollView()
		{
			this.wrapInScrollView = true;
			return this;
		}

		@Override
        public CustomDialogSettingsObject build()
        {
            return new CustomDialogSettingsObject(this);
        }
    }
}
