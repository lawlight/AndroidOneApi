package top.onehundred.android.onekit.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidException;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.onehundred.android.onekit.demo.R;
import top.onehundred.android.onekit.ok;


public class DensityActivity extends AppCompatActivity {

    @BindView(R.id.et_from)
    EditText etFrom;
    @BindView(R.id.spinner_from)
    Spinner spinnerFrom;
    @BindView(R.id.tv_switch)
    TextView tvSwitch;
    @BindView(R.id.et_to)
    EditText etTo;
    @BindView(R.id.spinner_to)
    Spinner spinnerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
        ButterKnife.bind(this);

        spinnerFrom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,android.R.id.text1, new String[]{"dp","px","sp"}));
        spinnerTo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,android.R.id.text1, new String[]{"dp","px","sp"}));

        spinnerFrom.setSelection(0);
        spinnerTo.setSelection(1);
    }

    @OnClick(R.id.tv_switch)
    public void onViewClicked() {
        String from = spinnerFrom.getSelectedItem().toString();
        switch (from){
            case "dp":
                dp2x();
                break;
            case "px":
                px2x();
                break;
            case "sp":
                sp2x();
                break;
        }
    }

    private void dp2x(){
        float dpval = Float.parseFloat(etFrom.getText().toString());
        String to = spinnerTo.getSelectedItem().toString();
        switch (to){
            case "dp":
                setText(dpval);
                break;
            case "px":
                setText(ok.densityKit().dp2px(dpval));
                break;
            case "sp":
                setText(ok.densityKit().px2sp(ok.densityKit().dp2px(dpval)));
                break;
        }
    }

    private void px2x(){
        float pxval = Float.parseFloat(etFrom.getText().toString());
        String to = spinnerTo.getSelectedItem().toString();
        switch (to){
            case "dp":
                setText(ok.densityKit().px2dp(pxval));
                break;
            case "px":
                setText(pxval);
                break;
            case "sp":
                setText(ok.densityKit().px2sp(pxval));
                break;
        }
    }

    private void sp2x(){
        float spval = Float.parseFloat(etFrom.getText().toString());
        String to = spinnerTo.getSelectedItem().toString();
        switch (to){
            case "dp":
                setText(ok.densityKit().px2dp(ok.densityKit().sp2px(spval)));
                break;
            case "px":
                setText(ok.densityKit().sp2px(spval));
                break;
            case "sp":
                setText(spval);
                break;
        }
    }

    private void setText(float value){
        etTo.setText(String.format("%.1f", value));
    }


}
