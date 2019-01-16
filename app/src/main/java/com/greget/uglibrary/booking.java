package com.greget.uglibrary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.greget.uglibrary.Common.Common;

import org.w3c.dom.Text;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class booking extends AppCompatActivity {
    public final static int QRcodeWidth = 600;
    Bitmap bitmap;
    ImageView barcode;
    TextView id_booking,id_jam;
    TimePickerDialog timePickerDialog;
    TimePicker myTimePicker;
    Button btTimePicker, btGenerate;
    ProgressBar prog_barcode;
    int jam,menit;
    String user="";
    String lokerID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        user = Common.currentUsers.getNpm().toString();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        lokerID = bundle.getString("lokerID");

        id_booking = (TextView)findViewById(R.id.id_booking);
        id_jam = (TextView)findViewById(R.id.id_jam);
        btTimePicker = (Button)findViewById(R.id.timepick);
        btGenerate = (Button)findViewById(R.id.btn_booking);
        barcode = (ImageView)findViewById(R.id.barcode);
        prog_barcode = (ProgressBar)findViewById(R.id.progress_barcode);

        prog_barcode.setVisibility(View.INVISIBLE);
        id_booking.setText(lokerID);

        user = Common.currentUsers.getNpm().toString();

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
                prog_barcode.setVisibility(View.INVISIBLE);
            }
        });

        btTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
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
        prog_barcode.setVisibility(View.VISIBLE);
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

        bitmap.setPixels(pixels, 0, 600, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
