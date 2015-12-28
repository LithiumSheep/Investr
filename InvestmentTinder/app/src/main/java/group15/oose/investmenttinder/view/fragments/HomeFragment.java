package group15.oose.investmenttinder.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.UserPreferences;
import group15.oose.investmenttinder.adapters.InvestmentTinderAdapter;
import group15.oose.investmenttinder.api.ApiService;
import group15.oose.investmenttinder.api.models.error_handler.ErrorHelper;
import group15.oose.investmenttinder.api.models.error_handler.ErrorResponseModel;
import group15.oose.investmenttinder.api.models.request_models.TickerSwipeRequest;
import group15.oose.investmenttinder.api.models.response_models.RecommendationResponse;
import group15.oose.investmenttinder.helpers.RetrofitCustomBuilder;
import group15.oose.investmenttinder.view.Dashboard;
import group15.oose.investmenttinder.view.company_details.CompanyDetailsActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class HomeFragment extends Fragment {

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.tinder_action_container)
    LinearLayout tinderActionContainer;

    private ApiService apiService;
    private UserPreferences userPreferences;
    private List<RecommendationResponse> recommendationResponses;
    private InvestmentTinderAdapter adapter;


    // Static factory method for our fragment instantiation
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // This method does all other view related work
        apiService = new RetrofitCustomBuilder().getApiService();
        userPreferences = new UserPreferences(getActivity());

        final SwipeFlingAdapterView tinderComponent = (SwipeFlingAdapterView) getActivity().findViewById(R.id.tinder_component);

        // We need adapter for our cards and object that will have details
        adapter = new InvestmentTinderAdapter(getActivity());

        // We need some data
        getRecommendation();

        // Setting adapter on our component
        tinderComponent.setAdapter(adapter);

        tinderComponent.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // We have to remove data from adapter and swap it again
                recommendationResponses.remove(0);
                adapter.swapData(recommendationResponses);
            }

            @Override
            public void onLeftCardExit(Object o) {
                // All logic regarding cards that are left - dismissed
                RecommendationResponse ticker = (RecommendationResponse) o;
                Call<ResponseBody> likedResponse = apiService.swipeLeft(userPreferences.getLoggedUser().getUserID(), new TickerSwipeRequest(ticker.getTicker()));  //THIS API call requires some additional information about e.g. card id which has been swiped
                likedResponse.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                        if (!response.isSuccess()) {
                            ErrorResponseModel errorHelper = ErrorHelper.parseError(response.errorBody(), retrofit);
                            Toast.makeText(getActivity(), errorHelper.getReason(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getActivity(), R.string.error_api_issue, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onRightCardExit(Object o) {
                // All logic regarding cards that are successfully chosen
                RecommendationResponse ticker = (RecommendationResponse) o;
                Call<ResponseBody> likedResponse = apiService.swipeRight(userPreferences.getLoggedUser().getUserID(), new TickerSwipeRequest(ticker.getTicker())); //THIS API call requires some additional information about e.g. card id which has been swiped
                likedResponse.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                        if (!response.isSuccess()) {
                            ErrorResponseModel errorHelper = ErrorHelper.parseError(response.errorBody(), retrofit);
                            Toast.makeText(getActivity(), errorHelper.getReason(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getActivity(), R.string.error_api_issue, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                // Request more data or show some kind of placeholder
            }

            @Override
            public void onScroll(float v) {
            }
        });

        //To trigger swipe functionality by tapping Yes or No
        tinderComponent.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, Object o) {
                // We start our new activity
                Toast.makeText(getActivity(), "position" + position, Toast.LENGTH_SHORT);
                System.out.println("testing position: " + position);
                /**
                 * Something wrong with position value here
                 */
                startActivity(CompanyDetailsActivity.newIntent(recommendationResponses.get(position).getTicker()));
            }
        });

        // We find our buttons first
        final TextView dismiss = (TextView) view.findViewById(R.id.action_dismiss);
        final TextView accept = (TextView) view.findViewById(R.id.action_accept);

        // Now we add listeners to them
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinderComponent.getTopCardListener().selectLeft();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinderComponent.getTopCardListener().selectRight();
            }
        });

        Dashboard.fromActivity(getActivity()).setToolbarTitle(getString(R.string.drawer_home));
    }

    public List<RecommendationResponse> getRecommendation() {
        recommendationResponses = new ArrayList<>();
        Call<List<RecommendationResponse>> recommendation = apiService.getRecommendation(userPreferences.getLoggedUser().getUserID());
        recommendation.enqueue(new Callback<List<RecommendationResponse>>() {
            @Override
            public void onResponse(Response<List<RecommendationResponse>> response, Retrofit retrofit) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (response.isSuccess()) {
                    recommendationResponses.addAll(response.body());
                    adapter.swapData(recommendationResponses);
                    if (tinderActionContainer != null) {
                        tinderActionContainer.setVisibility(View.VISIBLE);
                    }
                } else {
                    ErrorResponseModel errorHelper = ErrorHelper.parseError(response.errorBody(), retrofit);
                    Toast.makeText(getActivity(), errorHelper.getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                Toast.makeText(getActivity(), R.string.error_api_issue, Toast.LENGTH_LONG).show();
            }
        });
        return recommendationResponses;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}