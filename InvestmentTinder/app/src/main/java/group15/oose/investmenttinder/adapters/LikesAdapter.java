package group15.oose.investmenttinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import group15.oose.investmenttinder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LikesAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return likes.size();
    }

    @Override
    public String getItem(int position) {
        return likes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return likes.get(position).hashCode() + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String ticker = getItem(position);

        if (convertView == null) {
            convertView = createView(parent);
        }

        bindView(convertView, ticker);

        return convertView;
    }

    private View createView(ViewGroup parent) {
        final View convertView = inflater.inflate(R.layout.item_like, parent, false); // We are inflating the view that was created
        final ViewHolder holder = new ViewHolder(convertView); // Just creating card view holder with instance of our view

        convertView.setTag(holder);
        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.ticker)
        TextView ticker;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    private void bindView(View convertView, String ticker) {
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.ticker.setText(ticker);
    }

    private LayoutInflater inflater;
    private List<String> likes;

    public LikesAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.likes = new ArrayList<>();
    }

    public void bindDataToAdapter(List<String> likes) {
        this.likes = likes;
        notifyDataSetChanged();
    }
}