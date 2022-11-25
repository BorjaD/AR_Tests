package com.google.ar.core.examples.java.helloar;

import static org.junit.Assert.assertTrue;
import static nl.uu.cs.aplib.AplibEDSL.SEQ;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.ByteBuffer;

import eu.iv4xr.framework.mainConcepts.TestAgent;
import eu.iv4xr.framework.mainConcepts.WorldEntity;
import helloAr.testAgentSupport.GoalLib;
import helloAr.testAgentSupport.MyAgentEnv;
import helloAr.testAgentSupport.MyAgentState;
import nl.uu.cs.aplib.mainConcepts.GoalStructure;

//DEPTH
@LargeTest
@RunWith(AndroidJUnit4.class)
public class HelloArActivityTest_WithAgent_3 {

    @Rule
    public ActivityTestRule<HelloArActivity> mActivityTestRule = new ActivityTestRule<>(HelloArActivity.class);



    @Test
    public void helloArActivityTest() throws InterruptedException {

        TestAgent agent = new TestAgent("agentSmith","tester") ;
        MyAgentState state = new MyAgentState() ;

        agent.attachState(state)
             .attachEnvironment(new MyAgentEnv(mActivityTestRule.getActivity())) ;
        GoalLib goalLib = new GoalLib() ;
        GoalStructure G = SEQ(
                goalLib.clickButtonG(agent, "Playback", 2000),
                goalLib.selectVideoG(agent, 1, 10000)
        ) ;
        agent.setGoal(G) ;

        int k=0 ;
        while(G.getStatus().inProgress() && k < 20) {
            System.out.println(">>> k="+k) ;
            agent.update() ;
            int numberOfAnchorsDisplayed = 0;

            //Specific assertions:
            //  TEST CASE 1: The base of the item is well placed in a surface
            //  It should only be able to be rotated to left/right (qy)
            for(WorldEntity a : state.worldmodel().elements.values()) {
                if (a.type.equals("3DObj")) {
                    //Get the depth image
                    Image depthImage = mActivityTestRule.getActivity().getDepthImage();
                    ByteBuffer buffer = depthImage.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.capacity()];
                    buffer.get(bytes);
                    Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);



                    assertTrue((float) a.properties.get("qx") == 0.0) ;
                    assertTrue((float) a.properties.get("qz") == 0.0) ;
                }

                numberOfAnchorsDisplayed ++;
            }

            //There are a maximum of 4 anchors displayed
            assertTrue(numberOfAnchorsDisplayed <= 4);
        }

        assertTrue(G.getStatus().success());

    }

}
