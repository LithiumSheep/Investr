package group15.oose.investmenttinder.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.view.login_signup.LoginFragment;
import group15.oose.investmenttinder.view.login_signup.SignupFragment;

public class MainActivity extends AppCompatActivity {

    TwoTabPagerAdapter startupPagerAdapter;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        startupPagerAdapter = new TwoTabPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(startupPagerAdapter);
        tabLayout.setupWithViewPager(pager);
    }

    public class TwoTabPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 2;

        public TwoTabPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show LoginFragment
                    return LoginFragment.newInstance();
                case 1: // Fragment # 1 - This will show SignupFragment
                    return SignupFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.login_title);
                case 1:
                    return getString(R.string.signup_title);
                default:
                    return null;
            }
        }
    }
}
