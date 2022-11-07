package com.google.ar.core.examples.java.helloar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertFalse;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HelloArActivityTest2 {


    @Rule
    public ActivityTestRule<HelloArActivity> mActivityTestRule = new ActivityTestRule<>(HelloArActivity.class);

    @Test
    public void helloArActivityTest() throws InterruptedException {

        //FOR A CERTAIN 3D OBJECT
        double xLength = 0, yLength = 0, zLength = 0;
        boolean thereIsCollision = false;
        List<double[]> hitboxList = new ArrayList<double[]>();

        // Hitbox of 3D object
        // Max and min values of X, Y, Z coordinates
        try {
            boolean vertexReached = false;
            double[] maxCoordinates = {0, 0, 0};
            double[] minCoordinates = {0, 0, 0};
            boolean firstIteration = true;

            InputStreamReader inputStream = new InputStreamReader(mActivityTestRule.getActivity().getAssets().open("pawn.txt"));
            BufferedReader in = new BufferedReader(inputStream);

            String line = in.readLine();

            while (line != null) {
                if(!vertexReached) {
                    line = in.readLine();
                    if (line.startsWith("v ")) vertexReached = true;
                }
                else {
                    if (!line.startsWith("v ")) break;

                    // Use lines that correspond to vertexes

                    List<double[]> maxAndMinList = new ArrayList<double[]>();
                    maxAndMinList.add(maxCoordinates);
                    maxAndMinList.add(minCoordinates);

                    // If it is not the first iteration,
                    // check if the coordinates are the largest or the smallest
                    maxAndMinList = updateMaxAndMin(maxAndMinList, line, firstIteration);
                    maxCoordinates = maxAndMinList.get(0);
                    minCoordinates = maxAndMinList.get(1);

                    if (firstIteration) firstIteration = false;
                    line = in.readLine();
                }
            }

            System.out.println("^^^^ maxCoordinates: [" + maxCoordinates[0] + ", " + maxCoordinates[1] + ", " + maxCoordinates[2] + "]");
            System.out.println("^^^^ minCoordinates: [" + minCoordinates[0] + ", " + minCoordinates[1] + ", " + minCoordinates[2] + "]");

            xLength = maxCoordinates[0] - minCoordinates[0];
            yLength = maxCoordinates[0] - minCoordinates[0];
            zLength = maxCoordinates[0] - minCoordinates[0];

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Select video, tap screen, etc
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
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1000);

        //Second video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 1000);
        //Third video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(200, 1500);
        //Forth video OK
//        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(800, 1500);

        // Tap on SELECT
        /*Thread.sleep(1000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(900, 300);*/

        Thread.sleep(40000);

        //Tap the screen to place an item
        //onView(withId(R.id.surfaceview)).perform(touchDownAndUp(50, 50));

        //NEWCODE: DELETE
        /*UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(300, 1500);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(600, 1500);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(400, 1000);
        Thread.sleep(3000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(500, 1000);
        Thread.sleep(5000);
         */

        // Check position of 3D objects (translation)
        String TAG = "MyActivity";
        List<WrappedAnchor> anchorsList = mActivityTestRule.getActivity().getAnchors();
        Log.d(TAG, "Number of anchors: " + anchorsList.size());

        for(WrappedAnchor wAnchor : anchorsList) {
            if (thereIsCollision) break;

            Anchor anchor = wAnchor.getAnchor();

            //Item coordinates
            float tx = anchor.getPose().tx();
            float ty = anchor.getPose().ty();
            float tz = anchor.getPose().tz();

            System.out.println("^^^^ Translation X: " + tx);
            System.out.println("^^^^ Translation Y: " + ty);
            System.out.println("^^^^ Translation Z: " + tz);

            //Calculate max and min positions in real space for each coordinate (hitbox in real space)
            double hitboxMinX = tx - (xLength/2);
            double hitboxMaxX = tx + (xLength/2);
            double hitboxMinY = ty - (yLength/2);
            double hitboxMaxY = ty + (yLength/2);
            double hitboxMinZ = tz - (zLength/2);
            double hitboxMaxZ = tz + (zLength/2);

            // Upload the hitbox list
            double[] hitbox = new double[6];
            hitbox[0] = hitboxMinX;
            hitbox[1] = hitboxMaxX;
            hitbox[2] = hitboxMinY;
            hitbox[3] = hitboxMaxY;
            hitbox[4] = hitboxMinZ;
            hitbox[5] = hitboxMaxZ;
            hitboxList.add(hitbox);

            System.out.println("^^^^ Hitbox of the 3D object:");
            System.out.println("^^^^ hitboxMinX: " + hitbox[0]);
            System.out.println("^^^^ hitboxMaxX: " + hitbox[1]);
            System.out.println("^^^^ hitboxMinY: " + hitbox[2]);
            System.out.println("^^^^ hitboxMaxY: " + hitbox[3]);
            System.out.println("^^^^ hitboxMinZ: " + hitbox[4]);
            System.out.println("^^^^ hitboxMaxZ: " + hitbox[5]);

            //Check if there are intersections of hitboxes between the last added and the existing ones
            if (hitboxList.size() > 1) {
                double[] lastHitboxAdded = hitboxList.get(hitboxList.size() - 1);

                for (int i = 0; i < hitboxList.size() - 1; i ++) {
                    double[] hitboxInList = hitboxList.get(i);

                    //Collision cases:
                    //If MinX(B) is between MinX(A) and MaxX(A)
                    //and MinY(B) is between MinY(A) and MaxY(A)
                    //
                    //If MinX(B) is between MinX(A) and MaxX(A)
                    //and MaxY(B) is between MinY(A) and MaxY(A)
                    //
                    //If MaxX(B) is between MinX(A) and MaxX(A)
                    //and MinY(B) is between MinY(A) and MaxY(A)
                    //
                    //If MaxX(B) is between MinX(A) and MaxX(A)
                    //and MaxY(B) is between MinY(A) and MaxY(A)
                    //
                    //If MinX(B) <= MinX(A) and MaxX(B) >= MaxX(A)
                    //and MinY(B) is between MinY(A) and MaxY(A)
                    //
                    //If MinX(B) <= MinX(A) and MaxX(B) >= MaxX(A)
                    //and MaxY(B) is between MinY(A) and MaxY(A)
                    //
                    //If MinX(B) >= MinX(A) and MinX(B) <= MaxX(A)
                    //and MinY(B) <= MinY(A) and MaxY(B) >= MaxY(A)
                    //
                    //If MaxX(B) >= MinX(A) and MaxX(B) <= MaxX(A)
                    //and MinY(B) <= MinY(A) and MaxY(B) >= MaxY(A)
                    //
                    //If MinX(B) <= MinX(A) and MaxX(B) >= MaxX(A)
                    //and MinY(B) <= MinY(A) and MaxY(B) >= MaxY(A)
                    //TODO: Debug collision posibilities
                    if (
                            (
                                    (lastHitboxAdded[0] >= hitboxInList[0] && lastHitboxAdded[0] <= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[2] >= hitboxInList[2] && lastHitboxAdded[2] <= hitboxInList[3])
                            )
                            ||
                            (
                                    (lastHitboxAdded[0] >= hitboxInList[0] && lastHitboxAdded[0] <= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[3] >= hitboxInList[2] && lastHitboxAdded[3] <= hitboxInList[3])
                            )
                            ||
                            (
                                    (lastHitboxAdded[1] >= hitboxInList[0] && lastHitboxAdded[1] <= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[2] >= hitboxInList[2] && lastHitboxAdded[2] <= hitboxInList[3])
                            )
                            ||
                            (
                                    (lastHitboxAdded[0] <= hitboxInList[0] && lastHitboxAdded[1] >= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[2] >= hitboxInList[2] && lastHitboxAdded[2] <= hitboxInList[3])
                            )
                            ||
                            (
                                    (lastHitboxAdded[0] <= hitboxInList[0] && lastHitboxAdded[1] >= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[3] >= hitboxInList[2] && lastHitboxAdded[3] <= hitboxInList[3])
                            )
                            ||
                            (
                                    (lastHitboxAdded[0] >= hitboxInList[0] && lastHitboxAdded[0] <= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[2] <= hitboxInList[2] && lastHitboxAdded[3] >= hitboxInList[3])
                            )
                            ||
                            (
                                    (lastHitboxAdded[1] >= hitboxInList[0] && lastHitboxAdded[1] <= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[2] <= hitboxInList[2] && lastHitboxAdded[3] >= hitboxInList[3])
                            )
                            ||
                            (
                                    (lastHitboxAdded[0] <= hitboxInList[0] && lastHitboxAdded[1] >= hitboxInList[1])
                                    &&
                                    (lastHitboxAdded[2] <= hitboxInList[2] && lastHitboxAdded[3] >= hitboxInList[3])
                            )
                    ) {
                        System.out.println("^^^^ THERE IS COLLISION!");
                        thereIsCollision = true;
                        break;
                    }
                }
            }
        }

        assertFalse(thereIsCollision);

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

    public ArrayList<double[]> updateMaxAndMin(List<double[]> maxAndMinList, String line, boolean firstIteration) {
        ArrayList<double[]> resultList = new ArrayList<double[]>();
        String[] coordinates = line.split(" ");

        // Assign the coordinates from a line to variables
        String xStr = coordinates[1];
        String yStr = coordinates[2];
        String zStr = coordinates[3];
        double xDou = Double.parseDouble(xStr);
        double yDou = Double.parseDouble(yStr);
        double zDou = Double.parseDouble(zStr);

        double[] maxCoordinates = maxAndMinList.get(0);
        double[] minCoordinates = maxAndMinList.get(1);

        if (firstIteration) {
            maxCoordinates[0] = xDou;
            maxCoordinates[1] = yDou;
            maxCoordinates[2] = zDou;
            minCoordinates[0] = xDou;
            minCoordinates[1] = yDou;
            minCoordinates[2] = zDou;
        } else {
            if (xDou > maxCoordinates[0]) maxCoordinates[0] = xDou;
            if (xDou < minCoordinates[0]) minCoordinates[0] = xDou;
            if (yDou > maxCoordinates[1]) maxCoordinates[1] = yDou;
            if (yDou < minCoordinates[1]) minCoordinates[1] = yDou;
            if (zDou > maxCoordinates[2]) maxCoordinates[2] = zDou;
            if (zDou < minCoordinates[2]) minCoordinates[2] = zDou;
        }

        resultList.add(maxCoordinates);
        resultList.add(minCoordinates);

        return resultList;
    }
}
