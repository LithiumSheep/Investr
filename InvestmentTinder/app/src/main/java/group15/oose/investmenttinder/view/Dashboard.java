package group15.oose.investmenttinder.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import group15.oose.investmenttinder.R;
import group15.oose.investmenttinder.UserPreferences;
import group15.oose.investmenttinder.view.fragments.HomeFragment;
import group15.oose.investmenttinder.view.fragments.PreferencesFragment;
import group15.oose.investmenttinder.view.fragments.LikesFragment;
import group15.oose.investmenttinder.helpers.AppConst;


/**
 * This is the main activity that handles navigation drawer and all fragments
 * (i.e. Home, My Stocks, Preferences, etc are handled by this activity)
 */
public class Dashboard extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Gets intent that launched the activity
        final Intent intent = getIntent();
        activate(intent, savedInstanceState, false);

        // Finding those components in the current layout that is set by "setContentView" method
        // Then cast them to the right component, else we'd have only view without specific behaviour
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // Set title for primary fragment
        if (savedInstanceState == null) {
            mToolbar.setTitle(R.string.drawer_home);
        }

        // Add item selected listener to our drawer
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Every time any item is clicked this method will be invoked
                menuItem.setChecked(true);// Set the item to checked

                mDrawerLayout.closeDrawers(); // Close our drawer after click

                // Get id of each item and connect them to the right action
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(AppConst.HOME_ACTION));
                        break;
                    case R.id.action_stocks:
                        startActivity(new Intent(AppConst.STOCKS_ACTION));
                        break;
                    case R.id.action_preferences:
                        startActivity(new Intent(AppConst.PREFERENCES_ACTION));
                        break;
                    case R.id.action_help:
                        // Simple intent to open some help desk website
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                        startActivity(browserIntent);
                        break;
                    case R.id.action_logout:
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                        UserPreferences userPreferences = new UserPreferences(Dashboard.this);
                        userPreferences.clear();
                        startActivity(new Intent(Dashboard.this, MainActivity.class));
                        break;
                    default:
                        throw new RuntimeException("Unknown item id " + menuItem.getItemId()); // Crash with specific message if impossible action happens
                }

                return false;
            }
        });

        mDrawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        // Implement ActionBarDrawerToggle and Set it to our layout
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set it as listener
        mDrawerLayout.setDrawerListener(drawerToggle);
        // Call syncState() to get the hamburger icon to work
        drawerToggle.syncState();

        // Navigation drawer should pretty much work at this point -- we'll set user data, some listeners
        // then implement fragment transitions and tinder fragment

        // First, bind some data to header, remove useless action bar and add another library to easily display user photo
        // Starts with binding views first

        final View drawerHeader = LayoutInflater.from(this).inflate(R.layout.drawer_header, null, false);
        mNavigationView.addHeaderView(drawerHeader);

        final TextView userName = (TextView) drawerHeader.findViewById(R.id.user_name);
        final UserPreferences userPreferences = new UserPreferences(getApplicationContext());
        userName.setText(userPreferences.getLoggedUser().getUsername());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // This method has to be overridden to receive intents
        setIntent(intent);
        activate(intent, null, true);
    }

    private void activate(Intent intent, Bundle savedInstanceState, boolean secondCall) {
        final FragmentManager fm = getSupportFragmentManager(); // Getting instance of fragment manager

        if (savedInstanceState == null) {
            final FragmentTransaction ft = fm.beginTransaction(); // Every fragment timePeriod has to be in a transaction
            getFragment(ft, intent);

            if (!secondCall) {
                ft.disallowAddToBackStack(); // If it is second call, disallow adding fragment to back stack
            }
            if (ft.isAddToBackStackAllowed()) {
                ft.addToBackStack(null); // If allowed to add fragment to backStack, do so - name is null because it doesn't matter
            }
            ft.commit();
        } else {
            // Empty
        }
    }

    private void getFragment(FragmentTransaction ft, Intent intent) {
        // Get intent action
        final String action = intent.getAction();

        Fragment fragment;

        switch (action) {
            // Hidden because we don't want this launched till sign up/login is over <case Intent.ACTION_MAIN>:
            // This is main action when application is launched
            case AppConst.HOME_ACTION:
                fragment = HomeFragment.newInstance();
                break;
            case AppConst.STOCKS_ACTION:
                fragment = LikesFragment.newInstance();
                break;
            case AppConst.PREFERENCES_ACTION:
                fragment = PreferencesFragment.newInstance();
                break;

            default:
                throw new RuntimeException("Unknown action " + action);
        }
        ft.replace(R.id.container, fragment, null); // Replacing fragment on our transaction
    }

    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    // This method calls public method from activity - needed for setting toolbar title
    public static Dashboard fromActivity(FragmentActivity activity) {
        return (Dashboard) activity;
    }
}