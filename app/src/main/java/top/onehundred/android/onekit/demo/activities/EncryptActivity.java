package top.onehundred.android.onekit.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.onehundred.android.onekit.demo.R;
import top.onehundred.android.onekit.ok;

public class EncryptActivity extends AppCompatActivity {

    @BindView(R.id.et_text)
    EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_md5, R.id.btn_sha, R.id.btn_base64_encode, R.id.btn_base64_decode, R.id.btn_url_encode, R.id.btn_url_decode, R.id.btn_unicode_encode, R.id.btn_unicode_decode, R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_md5:
                etText.setText(ok.encryptKit().md5(etText.getText().toString()));
                break;
            case R.id.btn_sha:
                etText.setText(ok.encryptKit().SHA1(etText.getText().toString()));
                break;
            case R.id.btn_base64_encode:
                etText.setText(ok.encryptKit().base64Encode(etText.getText().toString()));
                break;
            case R.id.btn_base64_decode:
                etText.setText(ok.encryptKit().base64Decode(etText.getText().toString()));
                break;
            case R.id.btn_url_encode:
                etText.setText(ok.encryptKit().urlEncode(etText.getText().toString()));
                break;
            case R.id.btn_url_decode:
                etText.setText(ok.encryptKit().urlDecode(etText.getText().toString()));
                break;
            case R.id.btn_unicode_encode:
                etText.setText(ok.encryptKit().unicodeEncode(etText.getText().toString()));
                break;
            case R.id.btn_unicode_decode:
                etText.setText(ok.encryptKit().unicodeDecode(etText.getText().toString()));
                break;
            case R.id.btn_clear:
                etText.setText("");
                break;
        }
    }
}
