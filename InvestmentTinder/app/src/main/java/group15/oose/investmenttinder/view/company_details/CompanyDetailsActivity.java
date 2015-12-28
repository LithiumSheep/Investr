package group15.oose.investmenttinder.view.company_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.view.company_details.CompanyDetailsFragment;
import group15.oose.investmenttinder.helpers.AppConst;


public class CompanyDetailsActivity extends AppCompatActivity {


    // This is tag for our position value - to store and retrieve it
    private static final String TICKER_NAME = "position";

    // Helper method to create intent to be launched as parameter we are sending the choosen item id
    public static Intent newIntent(String tickerName) {
        return new Intent(AppConst.DETAILS_ACTION)
                .putExtra(TICKER_NAME, tickerName);
    }

    // Override just this method that is called whenever activity is created and we just switch fragment inside it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);

        final Intent intent = getIntent();
        final String ticker = intent.getStringExtra(TICKER_NAME); // We retrieve our position value;

        // Check if savedInstanceState is null to not duplicate fragments
        // This is null when first launched, after rotation change savedInstanceState is not null
        if (savedInstanceState == null) {
            final FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.container, CompanyDetailsFragment.newInstance(ticker))
                    .commit();
        }
    }
}