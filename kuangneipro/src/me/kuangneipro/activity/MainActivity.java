package me.kuangneipro.activity;

import me.kuangneipro.R;
import me.kuangneipro.core.HttpActivity;
import me.kuangneipro.fragment.ChannelListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.igexin.sdk.PushManager;

public class MainActivity extends HttpActivity {
	
	private static final String TAG = MainActivity.class.getSimpleName(); // tag ���ڲ���log��  

	private SectionsPagerAdapter mSectionsPagerAdapter;

	private ViewPager mViewPager;
	
	private int TAB_NUM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��������clientid,��ע����ռ���
		PushManager.getInstance().initialize(this.getApplicationContext());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		final ActionBar actionBar = getSupportActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		String[] tabNames = getResources().getStringArray(R.array.tab_names);
		
		TAB_NUM = tabNames.length;
		
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	        	mViewPager.setCurrentItem(tab.getPosition());
	        }

	        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	        }

	        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	        }
	    };
		
        for (int i = 0; i < tabNames.length; i++) {
        	Log.i(TAG, "addTab" + i);
            actionBar.addTab(actionBar.newTab()
                    		.setText(tabNames[i])
                            .setTabListener(tabListener));
        }
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                	getSupportActionBar().setSelectedNavigationItem(position);
	                }
	            });
		
		
		
		

	    

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case R.id.action_search:
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public String tag = this.getClass().getSimpleName(); // tag ���ڲ���log��  
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Log.i(tag, "getItem" + position);
			return ChannelListFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			Log.i(tag, "getCount" + TAB_NUM);
			return TAB_NUM;
		}
	}

	
	

}