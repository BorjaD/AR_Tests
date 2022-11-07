package com.google.ar.core.examples.java.helloar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import com.google.ar.core.Anchor;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HelloArActivityTest2 {

    private Context context;

    @Rule
    public ActivityTestRule<HelloArActivity> mActivityTestRule = new ActivityTestRule<>(HelloArActivity.class);
    //public IntentsTestRule<HelloArActivity> mActivityTestRule = new IntentsTestRule<>(HelloArActivity.class);
    //public ActivityScenarioRule<HelloArActivity> mActivityTestRule = new ActivityScenarioRule <>(HelloArActivity.class);

    /*
    private HelloArActivity launchedActivity;

    @Before
    public void setUp() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //this is the key part
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //this is the key part
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchedActivity = mActivityTestRule.launchActivity(intent);
    }
     */

    @Test
    public void helloArActivityTest() throws InterruptedException {
        //wait(2000);
        //NEWCODE: Next Removed
        /*ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.playback_button), withText("Playback"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());
        Thread.sleep(2000);*/

        // Click on the recorded video (in Downloads file)
        // Long tap (select) on a video in the gallery
        //First video OK

        //NEWCODE: Next Removed
        //UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1000);

        //Second video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 1000);
        //Third video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1500);
        //Forth video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 1500);

        // Tap on SELECT
        /*Thread.sleep(1000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(900, 300);*/

        //NEWCODE: Next Removed
        //Thread.sleep(40000);

        //Tap the screen to place an item
        //onView(withId(R.id.surfaceview)).perform(touchDownAndUp(50, 50));

        //NEWCODE: Next Removed
        /*UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(300, 1500);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(600, 1500);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(400, 1000);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(500, 1000);
        Thread.sleep(5000);*/

        //Validate properties of mActivityTestRule

        /*ActivityScenario scenario = mActivityTestRule.getScenario();
        mActivityTestRule.getScenario().getResult().getResultData().get
        scenario.getResult().getResultData();
        Activity activityInstance = getActivityInstance();*/
        /*assertThat(activity.getSomething()).isEqualTo("something");
        assertEquals(datosRecibidos.extras!!.getString("nombre"), "Julio")
        scenario.onActivity(activity -> {
            assertThat(activity.getSomething()).isEqualTo("something");
            assertThat(activity.activityResult.getResultData()).extras().containsKey("key");
        });*/

        String TAG = "MyActivity";
        List<WrappedAnchor> anchorsList = mActivityTestRule.getActivity().getAnchors();
        Log.d(TAG, "Number of anchors: " + anchorsList.size());

        //NEWCODE: Next Removed
        /*for(WrappedAnchor wAnchor : anchorsList) {
            Anchor anchor = wAnchor.getAnchor();
            float qx = anchor.getPose().qx();
            float qy = anchor.getPose().qy();
            float qz = anchor.getPose().qz();
            float qw = anchor.getPose().qw();

            //Item coordinates
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
            }*/
        System.out.println(" *** NEW CODE BEGINS ***");

            //Item hitbox
        /*
            try {
                //Try to print test from the .obj
                //File myObj = new File("pawn.obj");
                //File myObj = new File("app/src/main/assets/models/pawn.obj");
                //File myObj = new File("app/src/androidTest/java/com/google/ar/core/examples/java/helloar/pawn.obj");
                //File myObj = new File("app/src/androidTest/java/com/google/ar/core/examples/java/helloar/pawn.obj");

                //Use pawn.txt, that contains the text from pawn.obj
                //File myObj = new File("app/src/main/assets/models/pawn.txt");
                //File myObj = new File("pawn.txt");
                //File myObj = new File("app/assets/models/pawn.txt");
                //File myObj = new File("app/assets/models/prov.txt");
                //File myObj = new File("app/src/main/assets/models/prov.txt");
                //File myObj = new File("prov.txt");
                //File myObj = new File(new File("app/src/main/assets/models/prov.txt").getAbsolutePath());
                //File myObj = new File(path.trim());
                //File myObj = new File(Objects.requireNonNull(HelloArActivityTest2.class.getResource("app/src/main/assets/models/prov.txt")).toURI());

                //System.out.println("user.dir = " + System.getProperty("user.dir"));
                //String path = System.getProperty("user.dir") + "src/androidTest/prov.txt";
                //String path = System.getProperty("user.dir") + "\\src\\androidTest\\prov.txt";
                //MockHttpServletRequest request = new MockHttpServletRequest();
                //String path = request.getSession().getServletContext().getRealPath("/");

                File myObj = new File("app/src/main/assets/models/prov.txt");

                //myObj.setReadable(true);
                //myObj.setWritable(true);

                System.out.println("myObj.mkdirs() = " + myObj.mkdirs());   //false
                System.out.println("myObj.exists() = " + myObj.exists());   //false
                System.out.println("myObj.isDirectory() = " + myObj.isDirectory()); //false
                System.out.println("myObj.canRead() = " + myObj.canRead()); //false
                System.out.println("Current directory: " + new File(".").getAbsolutePath());    // /.

                Scanner myReader = new Scanner(myObj);
                int counter = 0;
                while (myReader.hasNextLine() && counter <= 10) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                    counter ++;
                }
                myReader.close();



                //Check if a line begins with "v ", and store the coordinates


            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            */


        /*
        try (BufferedReader in = new BufferedReader(new InputStreamReader(context.getAssets().open("prov.txt")))) {
            System.out.println("*** TRY CP1");
            in.lines().limit(10).forEach(System.out::println);
            System.out.println("*** TRY CP2");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
         */

/*
        String string = "";
        try {
            InputStream inputStream = getAssets().open("myText.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Printed String: " + string);
 */

        
        try {
            boolean vertexReached = false;
            int[] maxCoordinates = {0, 0, 0};
            int[] minCoordinates = {10, 10, 10};
            int whitespaceIndex;
            String substr;
            String xStr, yStr, zStr;
            double xDou, yDou, zDou;
            InputStreamReader inputStream = new InputStreamReader(mActivityTestRule.getActivity().getAssets().open("pawn.txt"));
            BufferedReader in = new BufferedReader(inputStream);
            //in.lines().limit(10).forEach(System.out::println);    //OK

            //OK
            String line = in.readLine();
            while (line != null) {
                System.out.println("^^");
                if(!vertexReached) {
                    line = in.readLine();
                    if (line.startsWith("v ")) vertexReached = true;
                }
                else {
                    if (!line.startsWith("v ")) break;

                    //System.out.println(line);
                    /*x = getCoordinate(line, x);
                    y = getCoordinate(line, y);
                    z = getCoordinate(line, z);*/

                    /*whitespaceIndex = line.indexOf(" ");
                    substr = line.substring(whitespaceIndex + 1);
                    whitespaceIndex = substr.indexOf(" ");
                    xStr = substr.substring(0, whitespaceIndex);*/

                    String[] coordinates = line.split(" ");
                    xStr = coordinates[1];
                    yStr = coordinates[2];
                    zStr = coordinates[3];

                    xDou = Double.parseDouble(xStr);
                    yDou = Double.parseDouble(yStr);
                    zDou = Double.parseDouble(zStr);

                    System.out.println("xDou = " + xDou + ". yDou = " + yDou + ". zDou = " + zDou + ".");

                    line = in.readLine();
                }
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



            System.out.println(" *** NEW CODE ENDS ***");


            /*assertTrue(qx == 0.0);
            assertTrue(qz == 0.0);*/
        }

        //NEWCODE: Next Removed
        /*Thread.sleep(10000);
    }*/

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

    public double getCoordinate(String line, String axis) {
        double res = 0;
        switch (axis) {
            case "x":

            case "y":
            case "z":
        }
        return res;
    }
}
