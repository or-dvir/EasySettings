package or_dvir.hotmail.com;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ButtonVH>
{

    private final CharSequence[] items;
    private ItemCallback itemCallback;
    private ButtonCallback buttonCallback;

    public MyCustomAdapter(CharSequence[] items)
    {
        this.items = items;
    }

    void setCallbacks(ItemCallback itemCallback, ButtonCallback buttonCallback)
    {
        this.itemCallback = itemCallback;
        this.buttonCallback = buttonCallback;
    }

    @Override
    public ButtonVH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view =
                LayoutInflater.from(parent.getContext())
                              .inflate(R.layout.custom_list_dialog_item, parent, false);
        return new ButtonVH(view, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ButtonVH holder, int position)
    {
        holder.title.setText(items[position] + " (" + position + ")");
        holder.button.setTag(position);
    }

    @Override
    public int getItemCount()
    {
        return items.length;
    }

    interface ItemCallback
    {
        void onItemClicked(int itemIndex);
    }

    interface ButtonCallback
    {
        void onButtonClicked(int buttonIndex);
    }

    static class ButtonVH extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        final TextView title;
        final Button button;
        final MyCustomAdapter adapter;

        ButtonVH(View itemView, MyCustomAdapter adapter)
        {
            super(itemView);
            title = itemView.findViewById(R.id.md_title);
            button = itemView.findViewById(R.id.md_button);

            this.adapter = adapter;
            itemView.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (adapter.itemCallback == null)
            {
                return;
            }
            if (view instanceof Button)
            {
                adapter.buttonCallback.onButtonClicked(getAdapterPosition());
            }
            else
            {
                adapter.itemCallback.onItemClicked(getAdapterPosition());
            }
        }
    }
}
