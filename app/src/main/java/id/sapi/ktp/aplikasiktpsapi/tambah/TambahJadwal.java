package id.sapi.ktp.aplikasiktpsapi.tambah;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import id.sapi.ktp.aplikasiktpsapi.R;

public class TambahJadwal extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

        private Toolbar toolbar;
        ActionBar actionBar;
        ImageView imdate, imtime;
        private EditText mTitleText, txtdate, txttime;
        private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
        private AddFloatingActionButton mFAB1;
        private AddFloatingActionButton mFAB2;
        private Calendar mCalendar;
        private int mYear, mMonth, mHour, mMinute, mDay;
        private long mRepeatTime;
        private String mTitle;
        private String mTime;
        private String mDate;
        private String mRepeat;
        private String mRepeatNo;
        private String mRepeatType;
        private String mActive;

        // Values for orientation change
        private static final String KEY_TITLE = "title_key";
        private static final String KEY_TIME = "time_key";
        private static final String KEY_DATE = "date_key";
        private static final String KEY_REPEAT = "repeat_key";
        private static final String KEY_REPEAT_NO = "repeat_no_key";
        private static final String KEY_REPEAT_TYPE = "repeat_type_key";
        private static final String KEY_ACTIVE = "active_key";

        // Constant values in milliseconds
        private static final long milMinute = 60000L;
        private static final long milHour = 3600000L;
        private static final long milDay = 86400000L;
        private static final long milWeek = 604800000L;
        private static final long milMonth = 2592000000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);

        imdate = (ImageView) findViewById(R.id.set_date);
        imtime = (ImageView)findViewById(R.id.set_time);
        txtdate = (EditText)findViewById(R.id.date);
        txttime = (EditText)findViewById(R.id.time);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Tambah Jadwal");
        actionBar.setHomeButtonEnabled(true);
        // Initialize default values
        mActive = "true";
        mRepeat = "true";
        mRepeatNo = Integer.toString(1);
        mRepeatType = "Hour";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;
        txtdate.setText(mDate);
        txttime.setText(mTime);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        txtdate.setText(mDate);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        txttime.setText(mTime);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    // On clicking Date picker
    public void setDate(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    // On clicking Time picker
    public void setTime(View v){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }
    // On clicking the repeat switch
    public void onSwitchRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            mRepeat = "true";
           // mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
        } else {
            mRepeat = "false";
            //mRepeatText.setText("Tidak diulangi");
        }
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    // On clicking menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // On clicking the back arrow
            // Discard any changes
            case android.R.id.home:
                onBackPressed();
                return true;

            // On clicking save reminder button
            // Update reminder
            /*case R.id.save_reminder:
                mTitleText.setText(mTitle);

                if (mTitleText.getText().toString().length() == 0)
                    mTitleText.setError("Reminder Title cannot be blank!");

                else {
                    //saveReminder();
                }
                return true;

            // On clicking discard reminder button
            // Discard any changes
            case R.id.discard_reminder:
                Toast.makeText(getApplicationContext(), "Discarded",
                        Toast.LENGTH_SHORT).show();

                onBackPressed();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
