package com.example.cristian.appestudiante.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.cristian.appestudiante.R;

import java.util.Calendar;

/**
 * Created by Cristian on 14/02/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView fechaNac = (TextView) getActivity().findViewById(R.id.textFechaNac);

        if(month <= 8){
            fechaNac.setText(dayOfMonth + "/0" +(month+1) + "/" + year);
        }else{
            fechaNac.setText(dayOfMonth + "/" +(month+1) + "/" + year);
        }
    }
}
