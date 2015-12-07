package sabria.nopreferences;

import android.app.Application;
import android.widget.Toast;

import sabria.noperferences.library.core.ProxyGenerator;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2015-12-07  10:53
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
public class MyApplication extends Application {

    private static MyApplication application;
    private static PersonProxy proxy;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        initSpProxy();
    }

    public static MyApplication getApplication() {
        return application;
    }

    private void initSpProxy() {
        proxy = new ProxyGenerator().create(application, PersonProxy.class);
    }

    public static PersonProxy getProxy(){
        if(proxy==null){
            Toast.makeText(application, "proxy==null", Toast.LENGTH_LONG).show();
            proxy = new ProxyGenerator().create(application, PersonProxy.class);
        }
        Toast.makeText(application,"proxy="+proxy,Toast.LENGTH_LONG).show();
        return proxy;
    }
}
