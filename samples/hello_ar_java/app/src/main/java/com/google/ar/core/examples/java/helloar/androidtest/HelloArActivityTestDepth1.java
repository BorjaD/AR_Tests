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
import com.google.ar.core.examples.java.helloar.*;
//import com.google.ar.core.examples.java.helloar.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
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
        Thread.sleep(2000);

        // Click on the recorded video (in Downloads file)
        // Long tap (select) on a video in the gallery
        //First video OK
        //UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1000);
        //Second video OK
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 1000);
        //Third video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1500);
        //Forth video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 1500);

        // Tap on SELECT
        //Thread.sleep(1000);
        //UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(900, 300);

        Thread.sleep(40000);

        //Tap the screen to place an item
        //onView(withId(R.id.surfaceview)).perform(touchDownAndUp(50, 50));
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(300, 1500);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(600, 1500);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(400, 1000);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(500, 1000);
        Thread.sleep(5000);

        String TAG = "MyActivity";
        List<WrappedAnchor> anchorsList = mActivityTestRule.getActivity().getAnchors();
        Log.d(TAG, "Number of anchors: " + anchorsList.size());
        for(WrappedAnchor wAnchor : anchorsList) {
            Anchor anchor = wAnchor.getAnchor();
            float qx = anchor.getPose().qx();
            float qy = anchor.getPose().qy();
            float qz = anchor.getPose().qz();
            float qw = anchor.getPose().qw();

            float tx = anchor.getPose().tx();
            float ty = anchor.getPose().ty();
            float tz = anchor.getPose().tz();

            float[] xAxis = anchor.getPose().getXAxis();
            float[] yAxis = anchor.getPose().getYAxis();
            float[] zAxis = anchor.getPose().getZAxis();

            Log.d(TAG, "qx = " + qx);
            Log.d(TAG, "qy = " + qy);
            Log.d(TAG, "qz = " + qz);
            Log.d(TAG, "qw = " + qw);
            Log.d(TAG, "tx = " + tx);
            Log.d(TAG, "ty = " + ty);
            Log.d(TAG, "tz = " + tz);

            Log.d(TAG, "xAxis:");
            for(float i : xAxis){
                Log.d(TAG, "" + i);
            }
            Log.d(TAG, "yAxis:");
            for(float i : yAxis){
                Log.d(TAG, "" + i);
            }
            Log.d(TAG, "zAxis:");
            for(float i : zAxis){
                Log.d(TAG, "" + i);
            }

            //TEST CASE: The depth detected by the application corresponds to the real depth
            wait(10000);

            //Information of a near real-world object
            int realDistance = 60;
            int realXCoordinate = 50;
            int realYCoordinate = 50;

            //Get the real world screenshot (not needed)
            //File screenshot = takeScreenshot();

            //Get the depth image
            Image depthImage = mActivityTestRule.getActivity().getDepthImage();

            //TODO: Show image to check it is correct
            View v = mActivityTestRule.getActivity().getWindow().getDecorView();     //Debug
            ImageView mImageView;
            mImageView = (ImageView) v.findViewById(R.id.my_image_view);
            //new v             https://stackoverflow.com/questions/10796660/convert-image-to-bitmap
            mImageView.buildDrawingCache();
            Bitmap bmap = mImageView.getDrawingCache();
            //new ^
            mImageView.setImageBitmap(bmap);


            //TODO: Know the coordinates of two places that are at different depth at the same time
            // There should be big colour changes
            int nearX = 0, nearY = 0;   //Known in advance
            int farX = 0, farY = 0;     //Known in advance


            //TODO: Get the depth color of the pixel in the depth image
            // Getting pixel color by position x and y (near)
            int nearClr = depthImage.getRGB(nearX, nearY);
            int nearRed =   (nearClr & 0x00ff0000) >> 16;
            int nearGreen = (nearClr & 0x0000ff00) >> 8;
            int nearBlue =   nearClr & 0x000000ff;
            System.out.println("Near Red Color value = " + nearRed);
            System.out.println("Near Green Color value = " + nearGreen);
            System.out.println("Near Blue Color value = " + nearBlue);
            //Or:
            /*
            Color mycolor = new Color(img.getRGB(x, y));
            int red = mycolor.getRed();
            int green = mycolor.getGreen();
            int blue = mycolor.getBlue();
            int alpha = mycolor.getAlpha();
             */

            // Getting pixel color by position x and y (far)
            int farClr = depthImage.getRGB(farX, farY);
            int farRed =   (farClr & 0x00ff0000) >> 16;
            int farGreen = (farClr & 0x0000ff00) >> 8;
            int farBlue =   farClr & 0x000000ff;
            System.out.println("Far Red Color value = " + farRed);
            System.out.println("Far Green Color value = " + farGreen);
            System.out.println("Far Blue Color value = " + farBlue);


            /*
            Distance by colour:
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
            String nearPredominantColour = "", farPredominantColour = "";
            if(nearRed > nearGreen) {
                if (nearRed > nearBlue) {
                    nearPredominantColour = "red";
                } else {
                    nearPredominantColour = "blue";
                }
            } else {
                if (nearGreen > nearBlue) {
                    nearPredominantColour = "green";
                } else {
                    nearPredominantColour = "blue";
                }
            }

            if(farRed > farGreen) {
                if (farRed > farBlue) {
                    farPredominantColour = "red";
                } else {
                    farPredominantColour = "blue";
                }
            } else {
                if (farGreen > farBlue) {
                    farPredominantColour = "green";
                } else {
                    farPredominantColour = "blue";
                }
            }

            if (nearPredominantColour == "red" && farPredominantColour == "red") {
                if (nearRed != farRed) {
                    if (nearRed > farRed) correctDepth = true;
                }
            } else if (nearPredominantColour == "red" && farPredominantColour == "green") {
                correctDepth = true;
            } else if (nearPredominantColour == "red" && farPredominantColour == "blue") {
                correctDepth = true;
            } else if (nearPredominantColour == "green" && farPredominantColour == "green") {
                if (nearGreen != farGreen) {
                    if (nearGreen > farGreen) correctDepth = true;
                }
            } else if (nearPredominantColour == "green" && farPredominantColour == "blue") {
                correctDepth = true;
            } else if (nearPredominantColour == "blue" && farPredominantColour == "blue") {
                if (nearBlue != farBlue) {
                    if (nearBlue > farBlue) correctDepth = true;
                }
            }

            //Assertions
            assertTrue(correctDepth);
        }

        Thread.sleep(10000);


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

    public File takeScreenshot(){
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

        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(fileScreenshot);
        intent.setDataAndType(uri,"image/jpeg");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivityTestRule.getActivity().startActivity(intent);*/

        return fileScreenshot;
    }

    public Color getDepthColor() {
        Color a = null;
        //a = new Color(255, 0, 255);

        Bitmap bm = null;
        int pixelColor1 = bm.getPixel(2, 2);

        return a;
    }
}
