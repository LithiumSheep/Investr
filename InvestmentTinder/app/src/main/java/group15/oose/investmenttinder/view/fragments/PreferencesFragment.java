package group15.oose.investmenttinder.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import group15.oose.investmenttinder.view.Dashboard;
import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.UserPreferences;
import group15.oose.investmenttinder.api.models.request_models.ClientProfile;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreferencesFragment extends Fragment {

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

    private UserPreferences userPreferences;

    public static PreferencesFragment newInstance() {
        return new PreferencesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // This method does all other view related work

        Dashboard.fromActivity(getActivity()).setToolbarTitle(getString(R.string.drawer_preferences));

        userPreferences = new UserPreferences(getActivity());

        final ClientProfile profile = userPreferences.getLoggedUser().getProfile();
        capitalLabel.setChecked(profile.isCapital());
        growthLabel.setChecked(profile.isGrowth());
        incomeLabel.setChecked(profile.isIncome());
        deferralLabel.setChecked(profile.isDeferral());
        speculationLabel.setChecked(profile.isSpeculation());

        horizonLabel.setText(String.valueOf(profile.getHorizon()));
        riskTolLabel.setText(String.valueOf(profile.getRiskTol()));
        liqLabel.setText(String.valueOf(profile.getLiq()));

        depLabel.setChecked(profile.isDep());

        taxBracketLabel.setText(String.valueOf(profile.getTaxBracket()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}