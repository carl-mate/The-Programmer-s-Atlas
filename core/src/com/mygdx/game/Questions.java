package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import util.Enums.Difficulty;
import util.QuestionsManager.TheoreticalQ;
import util.QuestionsManager;
import util.QuestionsManager.ProgrammingQ;

public class Questions {

    public static final String TAG = Questions.class.getName();

    private String question;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String correctChoice;
    private String imageFileName;
    private String questionDifficulty;


//    private String programmingVeryEasyQuestion;
//    private String programmingEasyQuestion;
//    private String programmingMediumQuestion;
//    private String programmingHardQuestion;
//    private String programmingVeryHardQuestion;

    private ArrayList<TheoreticalQ> theoreticalQArrayList;
    private TheoreticalQ[] theoreticalQuestion;
    private int theoreticalQuestionsCounter;

    private ArrayList<ProgrammingQ> programmingQArrayList;
    private ProgrammingQ[] programmingQuestion;
    private int programmingQuestionsCounter;


    //    private String theoreticalVeryHardQuestion;
//    private ArrayList<ProgrammingQ> programmingQArrayList;
    private long startTime;

    public Questions() throws IOException {
        //initialize QuestionsManager to convert the Excel File to ArrayList
        QuestionsManager.instance.init();

        //gets the generated ArrayList from QuestionsManager
        theoreticalQArrayList = QuestionsManager.instance.getTheoreticalQItemArrayList();
        programmingQArrayList = QuestionsManager.instance.getProgrammingQItemArrayList();
        /*
            questions are ordered from 0 - 4 i.e. Very Easy - Very Hard respectively
            index 0 = Very Easy
            index 1 = Easy
            index 2 = Medium
            index 3 = Hard
            index 4 = Very Hard
         */
        theoreticalQuestion = new TheoreticalQ[5];
        programmingQuestion = new ProgrammingQ[1];

        //shuffle total pool of theoretical questions to ensure randomness
        Collections.shuffle(theoreticalQArrayList);

        //shuffle total pool of programming questions to ensure randomness
//        Collections.shuffle(programmingQArrayList);
        initializeTheoreticalQuestions();
        initializeProgrammingQuestions();
    }

    public void initializeTheoreticalQuestions() {
         /*
            1 THEORETICAL VERY EASY
            1 THEORETICAL EASY
            1 THEORETICAL MEDIUM
            1 THEORETICAL HARD
            1 THEORETICAL VERY HARD
         */

        //iterate through the total pool of randomly arranged theoretical questions and store 5 questions to display
        for (TheoreticalQ x : theoreticalQArrayList) {
            if(theoreticalQuestionsCounter <= 5){
                if (x.getDifficulty().equals("VERY EASY") && theoreticalQuestion[0] == null) {
                    Gdx.app.log(TAG, "ADDED THEORETICAL VERY EASY");
                    theoreticalQuestion[0] = x;
                    theoreticalQuestionsCounter++;
                }
                if (x.getDifficulty().equals("EASY") && theoreticalQuestion[1] == null) {
                    Gdx.app.log(TAG, "ADDED THEORETICAL EASY");
                    theoreticalQuestion[1] = x;
                    theoreticalQuestionsCounter++;
                }
                if (x.getDifficulty().equals("MEDIUM") && theoreticalQuestion[2] == null) {
                    Gdx.app.log(TAG, "ADDED THEORETICAL MEDIUM");
                    theoreticalQuestion[2] = x;
                    theoreticalQuestionsCounter++;
                }
                if (x.getDifficulty().equals("HARD") && theoreticalQuestion[3] == null) {
                    Gdx.app.log(TAG, "ADDED THEORETICAL HARD");
                    theoreticalQuestion[3] = x;
                    theoreticalQuestionsCounter++;
                }
                if (x.getDifficulty().equals("VERY HARD") && theoreticalQuestion[4] == null) {
                    Gdx.app.log(TAG, "ADDED THEORETICAL VERY HARD");
                    theoreticalQuestion[4] = x;
                    theoreticalQuestionsCounter++;
                }

            }
        }

    }

    private void initializeProgrammingQuestions(){
        /*
            1 PROGRAMMING VERY EASY
            1 PROGRAMMING EASY
            1 PROGRAMMING MEDIUM
            1 PROGRAMMING HARD
            1 PROGRAMMING VERY HARD
         */

        for(ProgrammingQ x: programmingQArrayList){
            //temporary to test how image fits
            if(programmingQuestionsCounter <= 1){
                if(x.getDifficulty().equals("VERY EASY") && programmingQuestion[0] == null){
                    Gdx.app.log(TAG, "ADDED PROGRAMMING VERY EASY");
                    programmingQuestion[0] = x;
                    programmingQuestionsCounter++;
                }
            }
        }
    }

    public TheoreticalQ[] getTheoreticalQuestion(){
        return this.theoreticalQuestion;
    }

    public ProgrammingQ[] getProgrammingQuestion(){ return this.programmingQuestion; }

    public void resetStartTime() {
        this.startTime = 0;
    }


}
