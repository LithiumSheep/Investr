package group15.oose.investmenttinder.view.login_signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.UserPreferences;
import group15.oose.investmenttinder.api.ApiService;
import group15.oose.investmenttinder.helpers.AppConst;
import group15.oose.investmenttinder.helpers.RetrofitCustomBuilder;
import group15.oose.investmenttinder.api.models.error_handler.ErrorHelper;
import group15.oose.investmenttinder.api.models.error_handler.ErrorResponseModel;
import group15.oose.investmenttinder.api.models.request_models.ClientProfile;
import group15.oose.investmenttinder.api.models.request_models.RegisterUserRequest;
import group15.oose.investmenttinder.api.models.response_models.LoginResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SignupFragment extends Fragment {

    @Bind(R.id.capital_label)
    Switch capitalLabel;
    @Bind(R.id.growth_label)
    Switch growthLabel;
    @Bind(R.id.income_label)
    Switch incomeLabel;
    @Bind(R.id.deferral_label)
    Switch deferralLabel;
    @Bind(R.id.speculation_label)
    Switch speculationLabel;
    @Bind(R.id.horizon_label)
    EditText horizonLabel;
    @Bind(R.id.risktol_label)
    EditText riskTolLabel;
    @Bind(R.id.liq_label)
    EditText liqLabel;
    @Bind(R.id.dep_label)
    Switch depLabel;
    @Bind(R.id.tax_bracket_label)
    EditText taxBracketLabel;
    @Bind(R.id.signup_email)
    EditText signupEmail;
    @Bind(R.id.signup_password)
    EditText signupPassword;
    @Bind(R.id.signup_confirm)
    EditText signupConfirm;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // Added by Goke
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_signup)
    public void signupClick() {
        final String userName = signupEmail.getText().toString();
        final String password = signupPassword.getText().toString();
        final String passwordConfirmText = signupConfirm.getText().toString();
        if (!password.equals(passwordConfirmText)) {
            signupConfirm.setError(getString(R.string.password_do_not_match));
            return;
        }

        ApiService apiService = new RetrofitCustomBuilder().getApiService();

        ClientProfile clientProfile = new ClientProfile(
                capitalLabel.isChecked(),
                growthLabel.isChecked(),
                incomeLabel.isChecked(),
                deferralLabel.isChecked(),
                speculationLabel.isChecked(),
                TextUtils.isEmpty(horizonLabel.getText().toString()) ? 0 : Integer.parseInt(horizonLabel.getText().toString()),
                TextUtils.isEmpty(riskTolLabel.getText().toString()) ? 0 : Integer.parseInt(riskTolLabel.getText().toString()),
                TextUtils.isEmpty(liqLabel.getText().toString()) ? 0 : Integer.parseInt(liqLabel.getText().toString()),
                depLabel.isChecked(),
                TextUtils.isEmpty(taxBracketLabel.getText().toString()) ? 0 : Integer.parseInt(taxBracketLabel.getText().toString()));

        progressBar.setVisibility(View.VISIBLE);
        Call<LoginResponse> loginResponseCall = apiService.createUser(new RegisterUserRequest(userName, password, clientProfile));
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccess()) {
                    Toast.makeText(getActivity(), "Signup with " + userName, Toast.LENGTH_SHORT).show();
                    UserPreferences userPreferences = new UserPreferences(getContext());
                    userPreferences.saveLoggedUser(response.body());
                    startActivity(new Intent(AppConst.HOME_ACTION));
                    getActivity().finish();
                } else {
                    ErrorResponseModel errorHelper = ErrorHelper.parseError(response.errorBody(), retrofit);
                    Toast.makeText(getActivity(), errorHelper.getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getString(R.string.error_api_issue), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
