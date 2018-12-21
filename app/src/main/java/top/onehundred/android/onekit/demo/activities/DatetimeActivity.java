package top.onehundred.android.onekit.demo.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.onehundred.android.onekit.demo.R;
import top.onehundred.android.onekit.ok;

/**
 * 日期格式转换demo
 */
public class DatetimeActivity extends AppCompatActivity {

    @BindView(R.id.spinner_mode)
    Spinner spinnerMode;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_string)
    LinearLayout llString;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.et_datetime)
    EditText etDatetime;
    @BindView(R.id.et_format_from)
    EditText etFormatFrom;
    @BindView(R.id.ll_format_from)
    LinearLayout llFormatFrom;
    @BindView(R.id.et_format_to)
    EditText etFormatTo;
    @BindView(R.id.ll_format_to)
    LinearLayout llFormatTo;
    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.tv_show)
    TextView tvShow;

    Calendar calendar;
    Date date;

    String[] mode = new String[]{
            "Date to String", "String to Date",
            "Calendar to String", "String to Calendar",
            "Date to Calendar", "Calendar to Date",
            "Format String", "What time is it now?", "Get time description"};

    int currentMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);
        ButterKnife.bind(this);

        //初始化
        spinnerMode.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, mode));

        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentMode = position;
                llDate.setVisibility(View.GONE);
                llString.setVisibility(View.GONE);
                llFormatFrom.setVisibility(View.GONE);
                llFormatTo.setVisibility(View.GONE);
                switch (position){
                    case 0:
                    case 2:
                        llDate.setVisibility(View.VISIBLE);
                        llFormatTo.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                    case 3:
                        llString.setVisibility(View.VISIBLE);
                        llFormatFrom.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                    case 5:
                    case 8:
                        llDate.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        llString.setVisibility(View.VISIBLE);
                        llFormatFrom.setVisibility(View.VISIBLE);
                        llFormatTo.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calendar = Calendar.getInstance();
        date = ok.datetimeKit().calendar2Date(calendar);

        tvDate.setText(ok.datetimeKit().calendar2Str(calendar, "yyyy-MM-dd"));
        tvTime.setText(ok.datetimeKit().calendar2Str(calendar, "HH:mm"));

        etDatetime.setText(ok.datetimeKit().calendar2Str(calendar, etFormatFrom.getText().toString()));

        llDate.setVisibility(View.VISIBLE);
        llString.setVisibility(View.GONE);
        llFormatFrom.setVisibility(View.GONE);
        llFormatTo.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_date)
    public void onTvDateClicked() {
        ok.datetimeKit().showDatePickerDialog(this, calendar, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                date = ok.datetimeKit().calendar2Date(calendar);

                tvDate.setText(ok.datetimeKit().calendar2Str(calendar, "yyyy-MM-dd"));
            }
        });
    }

    @OnClick(R.id.tv_time)
    public void onTvTimeClicked() {
        ok.datetimeKit().showTimePickerDialog(this, calendar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                date = ok.datetimeKit().calendar2Date(calendar);

                tvTime.setText(ok.datetimeKit().calendar2Str(calendar, "HH:mm"));
            }
        });
    }

    @OnClick(R.id.btn_go)
    public void onBtnGoClicked() {
        tvShow.setText("");
        switch (currentMode){
            case 0:
                date2String();
                break;
            case 1:
                string2Date();
                break;
            case 2:
                calendar2String();
                break;
            case 3:
                string2Calendar();
                break;
            case 4:
                date2Calendar();
                break;
            case 5:
                calendar2Date();
                break;
            case 6:
                formatString();
                break;
            case 7:
                getTime();
                break;
            case 8:
                getTimeDesc();
                break;
        }
    }

    private void date2String(){
        tvShow.append("Origin Date:\n" + date.toString());
        tvShow.append("\n=============================\n");
        tvShow.append("to String: \n");
        tvShow.append(ok.datetimeKit().date2Str(date, etFormatTo.getText().toString()));
    }

    private void string2Date(){
        tvShow.append("Origin String is :" + etDatetime.getText().toString() + "\n");
        tvShow.append("to Date: \n");
        tvShow.append(ok.datetimeKit().str2Date(etDatetime.getText().toString(), etFormatFrom.getText().toString()).toString());
    }

    private void calendar2String(){
        tvShow.append("Origin Calendar:\n" + calendar.toString());
        tvShow.append("\n=============================\n");
        tvShow.append("to String: \n");
        tvShow.append(ok.datetimeKit().calendar2Str(calendar, etFormatTo.getText().toString()));
    }

    private void string2Calendar(){
        tvShow.append("Origin String is :" + etDatetime.getText().toString() + "\n");
        tvShow.append("to Calendar: \n");
        tvShow.append(ok.datetimeKit().str2Calendar(etDatetime.getText().toString(), etFormatFrom.getText().toString()).toString());
    }

    private void date2Calendar(){
        tvShow.append("Origin Date:\n" + date.toString());
        tvShow.append("\n=============================\n");
        tvShow.append("to Calendar: \n");
        tvShow.append(ok.datetimeKit().date2Calendar(date).toString());
    }

    private void calendar2Date(){
        tvShow.append("Origin Calendar:\n" + calendar.toString());
        tvShow.append("\n=============================\n");
        tvShow.append("to Date: \n");
        tvShow.append(ok.datetimeKit().calendar2Date(calendar).toString());
    }

    private void formatString(){
        tvShow.append("Format string: \n");
        tvShow.append(ok.datetimeKit().formatDatetime(etDatetime.getText().toString(), etFormatFrom.getText().toString(), etFormatTo.getText().toString()));
    }

    private void getTime(){
        tvShow.append("Now time stamp is: " + ok.datetimeKit().getTime() + "\n");
        tvShow.append("Now time UNIX stamp is: " + ok.datetimeKit().getUnixTime() + "\n");
    }

    private void getTimeDesc(){
        tvShow.append("time description:" + ok.datetimeKit().getTimeDesc(date, "yyyy年M月d日"));
    }
}
