package com.greget.uglibrary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.greget.uglibrary.Common.Common;

import org.w3c.dom.Text;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class booking extends AppCompatActivity {
    public final static int QRcodeWidth = 500;
    Bitmap bitmap;
    ImageView barcode;
    TextView id_booking,id_jam;
    TimePickerDialog timePickerDialog;
    TimePicker myTimePicker;
    Button btTimePicker, btGenerate;
    ImageButton back;
    ProgressBar prog_barcode;
    int jam,menit;
    String user="";
    String lokerID="";

    FirebaseDatabase database;
    DatabaseReference Booking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        user = Common.currentUsers.getNpm().toString();

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        lokerID = bundle.getString("lokerID");

        id_booking = (TextView)findViewById(R.id.id_booking);
        id_jam = (TextView)findViewById(R.id.id_jam);
        btTimePicker = (Button)findViewById(R.id.timepick);
        btGenerate = (Button)findViewById(R.id.btn_booking);
        barcode = (ImageView)findViewById(R.id.barcode);
        back = (ImageButton)findViewById(R.id.back_button);

        id_booking.setText(lokerID);

        user = Common.currentUsers.getNpm().toString();


        database = FirebaseDatabase.getInstance();
        Booking = database.getReference("Booking");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_jam.getText().toString().equals("00:00")) {
                    Toast.makeText(booking.this, "Waktu belum di set", Toast.LENGTH_SHORT).show();
                } else {
                    try {

                            bitmap = TextToImageEncode("{\"npm\": \""+user+
                                    "\",\"loker\": \""+lokerID+
                                    "\",\"waktu\": \""+jam+":"+menit+"\"}");
                            barcode.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        btTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });


        Booking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Common.currentUsers.getNpm()).exists()){
                    barcode.setImageResource(R.drawable.checked);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showTimeDialog() {
        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        Calendar calendar = Calendar.getInstance();

        /**
         * Initialize TimePicker Dialog
         */

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */
                jam = hourOfDay;
                menit = minute;
                if (hourOfDay < 8 || hourOfDay > 18) {
                    Toast.makeText(booking.this, "waktu tidak tersedia", Toast.LENGTH_SHORT).show();
                } else {
                    id_jam.setText(hourOfDay + ":" + minute);
                }

            }
        },

                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }



    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
