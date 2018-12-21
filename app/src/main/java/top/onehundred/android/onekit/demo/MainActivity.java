package top.onehundred.android.onekit.demo;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.onehundred.android.onekit.demo.activities.AppSystemActivity;
import top.onehundred.android.onekit.demo.activities.DatetimeActivity;
import top.onehundred.android.onekit.demo.activities.DensityActivity;
import top.onehundred.android.onekit.demo.activities.DialogActivity;
import top.onehundred.android.onekit.demo.activities.EncryptActivity;
import top.onehundred.android.onekit.demo.activities.IntentActivity;
import top.onehundred.android.onekit.ok;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_app)
    Button btnApp;
    @BindView(R.id.btn_dialog)
    Button btnDialog;
    @BindView(R.id.btn_datetime)
    Button btnDatetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ok.init(getApplication());

        BluetoothAdapter adapter;
    }

    @OnClick({R.id.btn_app, R.id.btn_dialog, R.id.btn_datetime, R.id.btn_intents, R.id.btn_encytpt, R.id.btn_density})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_app:
                startActivity(new Intent(this, AppSystemActivity.class));
                break;
            case R.id.btn_dialog:
                startActivity(new Intent(this, DialogActivity.class));
                break;
            case R.id.btn_datetime:
                startActivity(new Intent(this, DatetimeActivity.class));
                break;
            case R.id.btn_density:
                startActivity(new Intent(this, DensityActivity.class));
                break;
            case R.id.btn_intents:
                startActivity(new Intent(this, IntentActivity.class));
                break;
            case R.id.btn_encytpt:
                startActivity(new Intent(this, EncryptActivity.class));
                break;
        }
    }
}
