package sabria.nopreferences;

import android.content.Context;

import sabria.noperferences.library.anno.SharePreference;
import sabria.noperferences.library.anno.SpKey;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2015-12-07  10:54
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
@SharePreference(name = "person",Mode = Context.MODE_PRIVATE)
public interface PersonProxy {

    String key = "MYNAME";

    @SpKey(key = key)
    void setMyName(String name);

    @SpKey(key = key)
    String getMyName();

}
