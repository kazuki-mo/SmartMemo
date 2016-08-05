package com.ubi.kazuki_mo.smartmemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.util.Log;
import android.content.Context;

public class MainActivity extends AppCompatActivity{

    // 定数,変数,インスタンスなど
    private static final int REQUEST_CODE = 0;
    private TextView TV_VoiceRecogResult;
    private ListView LV_Memo;
    private Button BT_RecogStart;
    private List<String> memoList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();
    private Util util = new Util();
    private Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TV_VoiceRecogResult = (TextView) findViewById(R.id.TV_VoiceRecogResult);
        LV_Memo = (ListView) findViewById(R.id.LV_Memo);
        BT_RecogStart = (Button) findViewById(R.id.BT_RecogStart);

        // 「音声認識開始」ボタンが押されたら呼び出される関数
        BT_RecogStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 音声認識を開始
                speechRecog_start();
            }
        });

        // 要素が長押しされたら呼び出される関数
        LV_Memo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // リストから長押しされた要素を削除
                memoList.remove(position);
                dateList.remove(position);
                LV_Memo.setAdapter(util.getMemoAdapter(memoList,dateList,thisContext));

                return false;
            }
        });
    }

    // 音声認識開始
    private void speechRecog_start() {
        // 音声認識が使えるか確認する
        try {
            // 音声認識の　Intent インスタンス
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力");
            // インテント発行
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            TV_VoiceRecogResult.setText("No Activity ");
        }

    }

    // 音声認識結果を受け取るために onActivityResult を設置
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Speech", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // 認識結果を ArrayList で取得
            ArrayList<String> candidates = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            // 認識結果が存在するなら
            if (candidates.size() > 0) {

                // 認識結果と現在時刻を取得して、ListViewに追加
                TV_VoiceRecogResult.setText(candidates.get(0));
                memoList.add(candidates.get(0));
                dateList.add(util.getNowDate());
                LV_Memo.setAdapter(util.getMemoAdapter(memoList,dateList,thisContext));

            }
        }
    }

}
