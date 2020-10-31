package buzzardsView.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button calculateBtn;
    private Button activity2Btn;
    private Button settingsBtn;
    private EditText light1EditText;
    private EditText light2EditText;
    private EditText light3EditText;
    private EditText light4EditText;
    private TextView light1SetText;
    private TextView light2SetText;
    private TextView light3SetText;
    private TextView light4SetText;
    private TextView totalCostText;
    private TextView totalWattsText;
    private TextView warningText;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String[] incanNamesPrefs = {
            "light1Name",
            "light2Name",
            "light3Name",
            "light4Name"
    };
    public static final String[] incanPricesPrefs = {
            "light1Price",
            "light2Price",
            "light3Price",
            "light4Price"
    };
    public static final String[] incanWattagesPrefs = {
            "light1Wattage",
            "light2Wattage",
            "light3Wattage",
            "light4Wattage",
    };


    public int figure(EditText txt){ //turn length inputs into integers
        int answer = Integer.parseInt(txt.getText().toString());
        return answer;
    }
    public void checkNull(EditText txt) { //checks if input boxes are empty and then gives them a value of 0
        if(TextUtils.isEmpty(txt.getText())) {
            txt.setText("0");
        }
    }


    public void openActivity2(){ //opens LED page
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void openSettings(){ //opens settings page
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public static Light[] lightArray = new Light[]{
            new Light("C7", .41, 5),
            new Light("Mini", 3.5, 16),
            new Light("Net", 12.4,63.75),
            new Light("Timer", 9.97, 0)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity2Btn = findViewById(R.id.activity1);
        activity2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openSettings();
            }
        });

        final DecimalFormat numberFormat = new DecimalFormat("#.00");

        totalCostText = findViewById(R.id.totalCostTextView);
        totalWattsText = findViewById(R.id.totalWattTextView);
        warningText = findViewById(R.id.warningText);
        light1EditText = findViewById(R.id.light1EditText);
        light2EditText = findViewById(R.id.light2EditText);
        light3EditText = findViewById(R.id.light3EditText);
        light4EditText  = findViewById(R.id.light4ditText);
        light1SetText = findViewById(R.id.light1SetText);
        light2SetText = findViewById(R.id.light2SetText);
        light3SetText = findViewById(R.id.light3SetText);
        light4SetText = findViewById(R.id.light4SetText);

        loadData();


        TextView[] setTextArray = {light1SetText, light2SetText, light3SetText, light4SetText};

        for (int i = 0; i < setTextArray.length; i++) {
            setTextArray[i].setText(lightArray[i].getName());
        }

        calculateBtn = findViewById(R.id.calcBtn);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkNull(light1EditText);
                checkNull(light2EditText);
                checkNull(light3EditText);
                checkNull(light4EditText);

                lightArray[0].setLength(figure(light1EditText));
                lightArray[1].setLength(figure(light2EditText));
                lightArray[2].setLength(figure(light3EditText));
                lightArray[3].setLength(figure(light4EditText));

                double totalCost = 0;
                double totalWatts = 0;

                for (int i = 0; i < lightArray.length; i++) {
                    totalCost += lightArray[i].cost();
                }

                for (int i = 0; i < lightArray.length; i++) {
                    totalWatts += lightArray[i].wattage();
                }

                if(totalWatts > 1440) {
                    warningText.setText(getString(R.string.warningText));
                }

                String totalCostString = "    " + numberFormat.format(totalCost);
                String totalWattString = "     " + numberFormat.format(totalWatts);

                totalCostText.setText(totalCostString);
                totalWattsText.setText(totalWattString);

            }
        });

    }

    public void onStop(){
        super.onStop();
        saveData();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < incanNamesPrefs.length; i++) {
            editor.putString(incanNamesPrefs[i], lightArray[i].getName());
        }
        for (int i = 0; i < incanPricesPrefs.length; i++) {
            putDouble(editor, incanPricesPrefs[i], lightArray[i].getPrice());
        }
        for (int i= 0; i < incanWattagesPrefs.length; i++) {
            putDouble(editor, incanWattagesPrefs[i], lightArray[i].getWatts());
        }
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        for (int i = 0; i < lightArray.length; i++) {
            lightArray[i].setName(sharedPreferences.getString(incanNamesPrefs[i], lightArray[i].getName()));
        }
        for (int i = 0; i < lightArray.length; i++) {
            lightArray[i].setPrice(getDouble(sharedPreferences, incanPricesPrefs[i], lightArray[i].getPrice()));
        }
        for (int i = 0; i < lightArray.length; i++) {
            lightArray[i].setWatts(getDouble(sharedPreferences, incanWattagesPrefs[i], lightArray[i].getWatts()));
        }
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

}

