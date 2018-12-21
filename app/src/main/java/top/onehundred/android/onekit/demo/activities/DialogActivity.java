package top.onehundred.android.onekit.demo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import top.onehundred.android.onekit.demo.R;
import top.onehundred.android.onekit.ok;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        setTitle("DialogKit");
    }

    @OnClick({R.id.btn_commit, R.id.btn_toast, R.id.btn_progress, R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                ok.dialogKit(this).showCommitDialog("commit", "a simple commit dialog");
                break;
            case R.id.btn_toast:
                ok.dialogKit(this).showShortToast("a short time toast on " + new Date().getTime());
                break;
            case R.id.btn_progress:
                ok.dialogKit(this).showProgressDialog("progress", "please wait...", false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ok.dialogKit(DialogActivity.this).dismissProgressDialog();
                    }
                }, 3000);
                break;
            case R.id.btn_finish:
                ok.dialogKit(this).showCommitDialogWithFinish("commit","press to finish");
                break;
        }
    }
}
