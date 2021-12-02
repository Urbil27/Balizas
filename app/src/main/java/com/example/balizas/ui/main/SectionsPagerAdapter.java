package com.example.balizas.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.balizas.database.AppDatabase;
import com.example.balizas.fragmentoBalizas.BalizasFragment;
import com.example.balizas.DatosFragment;
import com.example.balizas.MapaFragment;
import com.example.balizas.R;

import org.json.JSONArray;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private JSONArray jsonArray;
    private AppDatabase db;

    public SectionsPagerAdapter(Context context, FragmentManager fm, JSONArray jsonArray, AppDatabase db) {
        super(fm);
        mContext = context;
        this.jsonArray = jsonArray;
        this.db = db;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new BalizasFragment(mContext,jsonArray);
                break;
            case 1:
                fragment = new DatosFragment();
                break;
            case 2:
                fragment = new MapaFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}