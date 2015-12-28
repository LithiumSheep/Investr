package group15.oose.investmenttinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.api.models.response_models.RecommendationResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// We need layout for our cards -- card_layout
public class InvestmentTinderAdapter extends BaseAdapter {

    // We need ViewHolder to keep our view objects
    static class CardViewHolder {
        public TextView companyName;
        public ImageView companyImage;
        public TextView security;
        public TextView price;
        public TextView timePeriod;

        public CardViewHolder(View view) {
            companyName = (TextView) view.findViewById(R.id.company_name);
            companyImage = (ImageView) view.findViewById(R.id.company_image);
            security = (TextView) view.findViewById(R.id.type_of_security);
            price = (TextView) view.findViewById(R.id.price);
            timePeriod = (TextView) view.findViewById(R.id.time_period);
        }
    }

    private LayoutInflater mInflater;
    private Picasso picasso;
    private List<RecommendationResponse> mCompanies = new ArrayList<>(); // Store companies in an arraylist

    public InvestmentTinderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
    }

    public void swapData(List<RecommendationResponse> companies) {
        mCompanies = companies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCompanies.size(); // Returning company count
    }

    @Override
    public RecommendationResponse getItem(int position) {
        return mCompanies.get(position); // Getting item from arraylist
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode(); // Getting unique id
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RecommendationResponse company = getItem(position);

        if (convertView == null) {
            convertView = createView(parent);
        }

        bindView(convertView, company);

        return convertView;

    }

    // This method creates our view holder if it is needed
    private View createView(ViewGroup parent) {
        final View convertView = mInflater.inflate(R.layout.card_layout, parent, false); // We are inflating the view that was created
        final CardViewHolder holder = new CardViewHolder(convertView); // Just creating card view holder with instance of our view

        convertView.setTag(holder);
        return convertView;
    }

    // This method is going to bind data to our view holder
    private void bindView(View convertView, RecommendationResponse company) {
        final CardViewHolder holder = (CardViewHolder) convertView.getTag();

        holder.companyName.setText(company.getName());
        holder.security.setText(company.getTicker());
        holder.price.setText(company.getIndustry());

        //the logo is loaded based on what is returned from back-end and is sometimes not the correct logo
        if(company.getLogo() == null ) {
            picasso.load("http://www.tele-ad.com/directory/assets/images/data/logo/no-logo.png").into(holder.companyImage);
        } else {
            picasso.load(company.getLogo()).into(holder.companyImage);
        }


    }
}