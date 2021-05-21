package tbc.uncagedmist.rationcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    TextView txtTheme;
    CardView cardTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_setting);

        cardTheme = findViewById(R.id.cardTheme);
        txtTheme = findViewById(R.id.txtTheme);

        cardTheme.setOnClickListener(view -> {
            Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT).show();
        });

    }
}