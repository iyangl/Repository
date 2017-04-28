package com.ly.example.myapplication2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ly.example.myapplication2.utils.StringFormat;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ly.example.myapplication2", appContext.getPackageName());
    }

    @Test
    public void testStringFormat() {
        String newsDate = StringFormat.formatNewsDate("20170426");
        System.out.println("" + newsDate);
        String dateBefore = StringFormat.getDateDaysBefore(3);
        System.out.println("" + dateBefore);
    }
}
