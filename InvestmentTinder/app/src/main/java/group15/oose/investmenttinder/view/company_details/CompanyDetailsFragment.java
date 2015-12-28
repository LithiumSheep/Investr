package group15.oose.investmenttinder.view.company_details;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.api.ApiService;
import group15.oose.investmenttinder.helpers.RetrofitCustomBuilder;
import group15.oose.investmenttinder.UserPreferences;
import group15.oose.investmenttinder.api.models.error_handler.ErrorHelper;
import group15.oose.investmenttinder.api.models.error_handler.ErrorResponseModel;
import group15.oose.investmenttinder.api.models.response_models.TickerDetailsResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class CompanyDetailsFragment extends Fragment {

    // Value to indicate our position variable
    private static final String TICKER_NAME = "position";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    /*@Bind(R.id.label_ticker)
    TextView labelTicker;*/

    @Bind(R.id.label_logo)
    ImageView labelLogo;

    @Bind(R.id.label_name)
    TextView labelName;
    @Bind(R.id.label_industry)
    TextView labelIndustry;
    @Bind(R.id.label_pe)
    TextView labelPe;
    @Bind(R.id.label_roe)
    TextView labelRoe;
    @Bind(R.id.label_dy)
    TextView labelDy;
    @Bind(R.id.label_de)
    TextView labelDe;
    @Bind(R.id.label_pb)
    TextView labelPb;
    @Bind(R.id.label_npm)
    TextView labelNpm;
    @Bind(R.id.label_pcf)
    TextView labelPcf;

    private String tickerName;
    private UserPreferences userPreferences;
    private ApiService apiService;

    // Helper method to receive instance of company details fragment
    // Here, we pass our position and store it
    public static CompanyDetailsFragment newInstance(String tickerName) {
        final CompanyDetailsFragment fragment = new CompanyDetailsFragment();
        final Bundle args = new Bundle(); // We store our value in bundle and we set those args to fragment
        args.putString(TICKER_NAME, tickerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // This method does all other view related work

        apiService = new RetrofitCustomBuilder().getApiService();
        userPreferences = new UserPreferences(getActivity());

        final Bundle arguments = getArguments(); // we are getting arguments that we have set in newInstance method
        tickerName = arguments.getString(TICKER_NAME);

        toolbar.setTitle(tickerName);

        // Set listener to toolbar navigation icon - to close activity when clicked
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish(); // getting activity and calling finish on it
            }
        });

        Call<TickerDetailsResponse> likedResponse = apiService.getTicker(tickerName);
        likedResponse.enqueue(new Callback<TickerDetailsResponse>() {
            @Override
            public void onResponse(Response<TickerDetailsResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    fillData(response.body());
                } else {
                    ErrorResponseModel errorHelper = ErrorHelper.parseError(response.errorBody(), retrofit);
                    Toast.makeText(getActivity(), errorHelper.getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), R.string.error_api_issue, Toast.LENGTH_LONG).show();
            }
        });

        // Now, we can retrieve data by the position in the list - however, when I figure out how we'll persist the data, we'll know what to do here
    }

    private void fillData(TickerDetailsResponse tickerDetails) {
        //labelTicker.setText(TextUtils.isEmpty(tickerDetails.getTicker()) ? "Ticker Details" : tickerDetails.getTicker());
        Picasso.with(getContext()).load(tickerDetails.getLogo()).resize(800,800).into(labelLogo);
        labelName.setTypeface(null, Typeface.BOLD);
        labelName.setText(TextUtils.isEmpty(tickerDetails.getName()) ? "Ticker Name" : tickerDetails.getName());
        labelIndustry.setText(TextUtils.isEmpty(tickerDetails.getIndustry()) ? "Ticker Industry" : tickerDetails.getIndustry());
        labelPe.setText(String.format("%s : %s", "PE ", String.valueOf(tickerDetails.getPE())));
        labelRoe.setText(String.format("%s : %s", "ROE ", String.valueOf(tickerDetails.getROE())));
        labelDy.setText(String.format("%s : %s", "DY ", String.valueOf(tickerDetails.getDY())));
        labelDe.setText(String.format("%s : %s", "DE ", String.valueOf(tickerDetails.getDE())));
        labelPb.setText(String.format("%s : %s", "PB ", String.valueOf(tickerDetails.getPB())));
        labelNpm.setText(String.format("%s : %s", "NPM ", String.valueOf(tickerDetails.getNPM())));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}