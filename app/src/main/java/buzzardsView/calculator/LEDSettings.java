package buzzardsView.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LEDSettings extends AppCompatActivity {

    private EditText light1Name;
    private EditText light1Price;
    private EditText light1Watts;
    private EditText light2Name;
    private EditText light2Price;
    private EditText light2Watts;
    private EditText light3Name;
    private EditText light3Price;
    private EditText light3Watts;
    private EditText light4Name;
    private EditText light4Price;
    private EditText light4Watts;
    private Button applyBtn;
    private Button resetBtn;

    public double doubleFigure(String string) {
        double answer;
        answer = Double.parseDouble(string);
        return answer;
    }

    public void openCalc() {
        saveData();
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp(){ //makes back button work
        saveData();
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledsettings);
        getSupportActionBar().setTitle(getResources().getString(R.string.LED_settings)); //changes title of page to settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //shows a back button
        light1Name = findViewById(R.id.light1Name);
        light1Price = findViewById(R.id.light1Price);
        light1Watts = findViewById(R.id.light1Watts);
        light2Name = findViewById(R.id.light2Name);
        light2Price = findViewById(R.id.light2Price);
        light2Watts = findViewById(R.id.light2Watts);
        light3Name = findViewById(R.id.light3Name);
        light3Price = findViewById(R.id.light3Price);
        light3Watts = findViewById(R.id.light3Watts);
        light4Name = findViewById(R.id.light4Name);
        light4Price = findViewById(R.id.light4Price);
        light4Watts = findViewById(R.id.light4Watts);
        applyBtn = findViewById(R.id.applyBtn);
        resetBtn = findViewById(R.id.resetBtn);



        final EditText[] editTextNameArray = {
                light1Name,
                light2Name,
                light3Name,
                light4Name,
        };
        final EditText[] editTextPriceArray = {
                light1Price,
                light2Price,
                light3Price,
                light4Price,
        };
        final EditText[] editTextWattsArray = {
                light1Watts,
                light2Watts,
                light3Watts,
                light4Watts
        };

        for (int i = 0; i < editTextNameArray.length; i++) {
            editTextNameArray[i].setHint(Activity2.lightArray[i].getName());
        }

        for (int i = 0; i < editTextPriceArray.length; i++) {
            editTextPriceArray[i].setHint(getResources().getString(R.string.price_per_foot_hint) + Activity2.lightArray[i].getPrice());
        }

        for (int i = 0; i < editTextWattsArray.length; i++) {
            editTextWattsArray[i].setHint(getResources().getString(R.string.watts_per_foot_hint) + Activity2.lightArray[i].getWatts());
        }

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < editTextNameArray.length; i++) {
                    if (TextUtils.isEmpty(editTextNameArray[i].getText())) {
                        Activity2.lightArray[i].getName();
                    } else {
                       Activity2.lightArray[i].setName("" + editTextNameArray[i].getText());
                    }
                }

                for (int i = 0; i < editTextPriceArray.length; i++) {
                    if (TextUtils.isEmpty(editTextPriceArray[i].getText())) {
                        Activity2.lightArray[i].getPrice();
                    } else {
                        Activity2.lightArray[i].setPrice(doubleFigure(editTextPriceArray[i].getText().toString()));
                    }
                }

                for (int i = 0; i < editTextWattsArray.length; i++) {
                    if (TextUtils.isEmpty(editTextWattsArray[i].getText())) {
                        Activity2.lightArray[i]. getWatts();
                    } else {
                        Activity2.lightArray[i].setWatts(doubleFigure(editTextWattsArray[i].getText().toString()));
                    }
                }
                openCalc();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Activity2.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < Activity2.LEDNamesPrefs.length; i++) {
            editor.putString(Activity2.LEDNamesPrefs[i], Activity2.lightArray[i].getName());
        }
        for (int i = 0; i < Activity2.LEDPricesPrefs.length; i++) {
            putDouble(editor, Activity2.LEDPricesPrefs[i], Activity2.lightArray[i].getPrice());
        }
        for (int i= 0; i < Activity2.LEDWattagesPrefs.length; i++) {
            putDouble(editor, Activity2.LEDWattagesPrefs[i], Activity2.lightArray[i].getWatts());
        }
        editor.apply();
    }

    public void reset() {
        SharedPreferences sharedPreferences = getSharedPreferences(Activity2.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.apply();
        Light[] lightArray = new Light[]{
                new Light("C7", .94, .8),
                new Light("Mini", 7.9, 2.4),
                new Light("Net", 23.4,6.9),
                new Light("Timer", 9.97, 0)
        };
        Activity2.lightArray = lightArray;
        openCalc();
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }
}
