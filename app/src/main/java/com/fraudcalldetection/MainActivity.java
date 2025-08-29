package com.example.fraudcalldetection;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTranscription, tvThreatResult, tvThreatDetail, tvTimer;
    private Button btnAnalysis;
    private boolean isAnalysisRunning = false;
    private boolean isCallActive = false; // Mock call state
    private int seconds = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTranscription = findViewById(R.id.tvTranscription);
        tvThreatResult = findViewById(R.id.tvThreatResult);
        tvThreatDetail = findViewById(R.id.tvThreatDetail);
        tvTimer = findViewById(R.id.tvTimer);
        btnAnalysis = findViewById(R.id.btnAnalysis);

        // Initially grey & disabled
        btnAnalysis.setEnabled(false);
        btnAnalysis.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));

        // Simulate call starting after 3 sec
        handler.postDelayed(() -> onCallStarted(), 3000);

        btnAnalysis.setOnClickListener(v -> {
            if (!isAnalysisRunning) {
                startAnalysis();
            } else {
                stopAnalysis();
            }
        });
    }

    private void onCallStarted() {
        isCallActive = true;
        btnAnalysis.setEnabled(true);
        btnAnalysis.setText("Start Analysis");
        btnAnalysis.setBackgroundTintList(getColorStateList(android.R.color.holo_red_dark));
    }

    private void startAnalysis() {
        isAnalysisRunning = true;
        btnAnalysis.setText("End Analysis");

        // Start timer
        handler.postDelayed(updateTimer, 1000);
    }

    private void stopAnalysis() {
        isAnalysisRunning = false;
        btnAnalysis.setText("Start Analysis");

        // Reset timer
        handler.removeCallbacks(updateTimer);
        seconds = 0;
        tvTimer.setText("00:00");

        // Show results after end
        tvTranscription.setText("Caller: Hello, this is John from the technical support team...");
        tvThreatResult.setText("High Risk Detected 95%");
        tvThreatDetail.setText("Potential Phishing Attempt | Confidence");
        tvThreatResult.setTextColor(getColor(android.R.color.holo_red_dark));
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            seconds++;
            int minutes = seconds / 60;
            int sec = seconds % 60;
            tvTimer.setText(String.format("%02d:%02d", minutes, sec));
            handler.postDelayed(this, 1000);
        }
    };
}
