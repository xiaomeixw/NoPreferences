package sabria.noperferences.library.anno;

import android.content.Context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xiongwei,An Android project Engineer.
 * Date:2015-12-07  10:40
 * Base on Meilimei.com (PHP Service)
 * Describe:
 * Version:1.0
 * Open source
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SharePreference {

    //sp xml name
    String name();

    //default int MODE_PRIVATE = 0x0000
    int Mode() default Context.MODE_PRIVATE;

}
