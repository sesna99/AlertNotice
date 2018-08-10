package trycatch.ex.alertnotice.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import trycatch.ex.alertnotice.R;
import trycatch.ex.alertnotice.fragment.CommunityFragment;
import trycatch.ex.alertnotice.fragment.NoticeFragment;
import trycatch.ex.alertnotice.fragment.SettingFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    public void init(){
        if(getIntent().getStringExtra("id") != null){
            Intent intent = new Intent(MainActivity.this, NoticeListActivity.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            intent.putExtra("provider", getIntent().getStringExtra("provider"));
            intent.putExtra("icon", getIntent().getStringExtra("icon"));
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        fragmentManager = getSupportFragmentManager();

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.notice:
                        fragmentTransaction.replace(R.id.fragment, new NoticeFragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.community:
                        fragmentTransaction.replace(R.id.fragment, new CommunityFragment());
                        fragmentTransaction.commit();
                        return true;
                    case R.id.setting:
                        fragmentTransaction.replace(R.id.fragment, new SettingFragment());
                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });

        bottom_navigation.setSelectedItemId(R.id.notice);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }
}
