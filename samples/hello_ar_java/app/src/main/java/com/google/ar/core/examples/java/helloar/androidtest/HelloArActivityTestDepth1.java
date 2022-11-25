package com.google.ar.core.examples.java.helloar.androidtest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import com.google.ar.core.Anchor;
import com.google.ar.core.examples.java.helloar.HelloArActivity;
import com.google.ar.core.examples.java.helloar.R;
import com.google.ar.core.examples.java.helloar.WrappedAnchor;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.List;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class HelloArActivityTestDepth1 {

    @Rule
    public ActivityTestRule<HelloArActivity> mActivityTestRule = new ActivityTestRule<>(HelloArActivity.class);

    @Test
    public void helloArActivityTest() throws InterruptedException {
        //wait(2000);
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.playback_button), withText("Playback"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());
        Thread.sleep(10000);

        // Click on the recorded video (in Downloads file)
        // Long tap (select) on a video in the gallery
        //First video OK
        //UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1000);
        //Second video OK
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 10000);
        //Third video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1500);
        //Forth video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 1500);

        // Tap on SELECT
        //Thread.sleep(1000);
        //UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(900, 300);

        String TAG = "MyActivity";

        //TEST CASE: The depth detected by the application corresponds to the real depth

        //Get the depth image
        Image depthImage = mActivityTestRule.getActivity().getDepthImage();
        ByteBuffer buffer = depthImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);

        //TODO: Show image to check it is correct
        // Use depthImage
        View v = mActivityTestRule.getActivity().getWindow().getDecorView();     //Debug
        ImageView mImageView;
        mImageView = (ImageView) v.findViewById(R.id.my_image_view);
        //new v             https://stackoverflow.com/questions/10796660/convert-image-to-bitmap
        mImageView.buildDrawingCache();
        Bitmap bmap = mImageView.getDrawingCache();
        //new ^
        mImageView.setImageBitmap(bmap);

        //TODO: Know the coordinates of two places that are at different depth at the same time
        // There should be big Color changes
        int nearX = 500, nearY = 100;   //Known in advance
        int farX = 70, farY = 100;      //Known in advance

            /*
            Distance by Color:
            R < G < B

            [ R+ | G- | B- ]
            <
            [ R+ | G+ | B- ]
            <
            [ R- | G+ | B- ]
            <
            [ R- | G+ | B+ ]
            <
            [ R- | G- | B+ ]
             */
        boolean correctDepth = false;
        String nearPredominantColor = "";
        String farPredominantColor = "";

        //Get the depth color of the pixel in the depth image
        //Getting pixel color by position x and y
        int nearColor = bitmapImage.getPixel(nearX, nearY);
        int nearRed = Color.red(nearColor);
        int nearGreen = Color.green(nearColor);
        int nearBlue = Color.blue(nearColor);

            /*
            int nearClr = depthImage.getRGB(nearX, nearY);
            int nearRed =   (nearClr & 0x00ff0000) >> 16;
            int nearGreen = (nearClr & 0x0000ff00) >> 8;
            int nearBlue =   nearClr & 0x000000ff;
            */
            /*
            Color mycolor = new Color(img.getRGB(x, y));
            int red = mycolor.getRed();
            int green = mycolor.getGreen();
            int blue = mycolor.getBlue();
            int alpha = mycolor.getAlpha();
            */

        System.out.println("Near Red Color value = " + nearRed);
        System.out.println("Near Green Color value = " + nearGreen);
        System.out.println("Near Blue Color value = " + nearBlue);

        if (nearRed > nearGreen) {
            if (nearRed > nearBlue) {
                nearPredominantColor = "red";
            } else {
                nearPredominantColor = "blue";
            }
        } else {
            if (nearGreen > nearBlue) {
                nearPredominantColor = "green";
            } else {
                nearPredominantColor = "blue";
            }
        }

        int farColor = bitmapImage.getPixel(farX, farY);
        int farRed = Color.red(farColor);
        int farGreen = Color.green(farColor);
        int farBlue = Color.blue(farColor);

        System.out.println("Far Red Color value = " + farRed);
        System.out.println("Far Green Color value = " + farGreen);
        System.out.println("Far Blue Color value = " + farBlue);

        if (farRed > farGreen) {
            if (farRed > farBlue) {
                farPredominantColor = "red";
            } else {
                farPredominantColor = "blue";
            }
        } else {
            if (farGreen > farBlue) {
                farPredominantColor = "green";
            } else {
                farPredominantColor = "blue";
            }
        }

        //Check if the depth relation is correct
        if (nearPredominantColor == "red" && farPredominantColor == "red") {
            if (nearRed != farRed) {
                if (nearRed > farRed) correctDepth = true;
            }
        } else if (nearPredominantColor == "red" && farPredominantColor == "green") {
            correctDepth = true;
        } else if (nearPredominantColor == "red" && farPredominantColor == "blue") {
            correctDepth = true;
        } else if (nearPredominantColor == "green" && farPredominantColor == "green") {
            if (nearGreen != farGreen) {
                if (nearGreen > farGreen) correctDepth = true;
            }
        } else if (nearPredominantColor == "green" && farPredominantColor == "blue") {
            correctDepth = true;
        } else if (nearPredominantColor == "blue" && farPredominantColor == "blue") {
            if (nearBlue != farBlue) {
                if (nearBlue > farBlue) correctDepth = true;
            }
        }

        //Assertions
        assertTrue(correctDepth);

        Thread.sleep(3000);
    }

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public File takeScreenshot() {
        //View view1 = mActivityTestRule.getActivity().getWindow().getDecorView().getRootView();
        View view1 = mActivityTestRule.getActivity().getWindow().getDecorView();
        view1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);

        //String filePath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString()+".jpg";
        String millis = System.currentTimeMillis() + "";
        String fileName = millis.replaceAll(":", ".") + ".jpg";
        String filePath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/" + fileName;


        File fileScreenshot = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileScreenshot;
    }

}
