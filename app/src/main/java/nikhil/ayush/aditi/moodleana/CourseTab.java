package nikhil.ayush.aditi.moodleana;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import layout.Course_Assignments;
import layout.Course_Grades;
import layout.Course_Overview;
import layout.Course_Resources;
import layout.Course_Threads;
import nikhil.ayush.aditi.moodleana.R;

public class CourseTab extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Course_Overview(), "Overview");
        adapter.addFragment(new Course_Assignments(), "Assignments");
        adapter.addFragment(new Course_Resources(), "Resources");
        adapter.addFragment(new Course_Threads(), "Threads");
        adapter.addFragment(new Course_Grades(), "Grades");
        adapter.addFragment(new Course_Grades(), "Grades");
        adapter.addFragment(new Course_Grades(), "Grades");
        adapter.addFragment(new Course_Assignments(), "Assignments");
        adapter.addFragment(new Course_Resources(), "Resources");
        adapter.addFragment(new Course_Assignments(), "Assignments");
        adapter.addFragment(new Course_Resources(), "Resources");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}