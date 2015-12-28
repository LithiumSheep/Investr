package group15.oose.investmenttinder.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import group15.oose.investmenttinder.view.Dashboard;
import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.UserPreferences;
import group15.oose.investmenttinder.adapters.LikesAdapter;
import group15.oose.investmenttinder.api.ApiService;
import group15.oose.investmenttinder.helpers.RetrofitCustomBuilder;
import group15.oose.investmenttinder.api.models.error_handler.ErrorHelper;
import group15.oose.investmenttinder.api.models.error_handler.ErrorResponseModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class LikesFragment extends Fragment {

    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.items_count)
    TextView itemCounter;

    public static LikesFragment newInstance() {
        return new LikesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // This method does all other view related work

        Dashboard.fromActivity(getActivity()).setToolbarTitle(getString(R.string.drawer_likes));

        final LikesAdapter likesAdapter = new LikesAdapter(getLayoutInflater(savedInstanceState));
        listView.setAdapter(likesAdapter);

        ApiService apiService = new RetrofitCustomBuilder().getApiService();
        UserPreferences userPreferences = new UserPreferences(getActivity());
        Call<List<String>> likedResponse = apiService.getLikedList(userPreferences.getLoggedUser().getUserID());
        likedResponse.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Response<List<String>> response, Retrofit retrofit) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccess()) {
                    likesAdapter.bindDataToAdapter(response.body());
                    itemCounter.setText(getResources().getQuantityString(R.plurals.items, response.body().size(), response.body().size()) + " " + String.valueOf(response.body().size()));
                } else {
                    ErrorResponseModel errorHelper = ErrorHelper.parseError(response.errorBody(), retrofit);
                    Toast.makeText(getActivity(), errorHelper.getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.error_api_issue, Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}