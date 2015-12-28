package group15.oose.investmenttinder.view.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.UserPreferences;
import group15.oose.investmenttinder.api.ApiService;
import group15.oose.investmenttinder.helpers.AppConst;
import group15.oose.investmenttinder.helpers.RetrofitCustomBuilder;
import group15.oose.investmenttinder.api.models.error_handler.ErrorHelper;
import group15.oose.investmenttinder.api.models.error_handler.ErrorResponseModel;
import group15.oose.investmenttinder.api.models.request_models.LoginUserRequest;
import group15.oose.investmenttinder.api.models.response_models.LoginResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginFragment extends Fragment {
    @Bind(R.id.login_email)
    EditText loginEmail;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.btn_login)
    AppCompatButton btnLogin;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // Added by Goke
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Just for testing purpose
        loginEmail.setText("likeTest");
        loginPassword.setText("password");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Checks for login information, can refactor to separate method
                 */
                progressBar.setVisibility(View.VISIBLE);
                final String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                /**
                 * Create LoginObject, send to backend, start activity on confirm
                 */
                ApiService apiService = new RetrofitCustomBuilder().getApiService();
                Call<LoginResponse> loginResponseCall = apiService.loginUser(new LoginUserRequest(email, password));
                loginResponseCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccess()) {
                            Toast.makeText(getActivity(), "Login with " + loginEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                            LoginResponse loginResponse = response.body();
                            UserPreferences userPreferences = new UserPreferences(getActivity());
                            userPreferences.saveLoggedUser(loginResponse);
                            startActivity(new Intent(AppConst.HOME_ACTION));
                            getActivity().finish();
                        } else {
                            ErrorResponseModel errorHelper = ErrorHelper.parseError(response.errorBody(), retrofit);
                            Toast.makeText(getActivity(), errorHelper.getReason(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getActivity(), getString(R.string.error_api_issue), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
