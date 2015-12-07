package sabria.nopreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvName = (TextView) findViewById(R.id.tvName);
        final PersonProxy proxy = MyApplication.getProxy();

        //storage
        proxy.setMyName("M欸里神奇");

        tvName.setText("存储完毕");

        //then get from SP 4 seconds later
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvName.setText(proxy.getMyName());
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    tvName.setText("InterruptedException");
                }


            }
        }).start();


    }


}
