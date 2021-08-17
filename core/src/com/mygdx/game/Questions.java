package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import util.QuestionsManager;
import util.QuestionsManager.ProgrammingQ;
import util.QuestionsManager.TheoreticalQ;
/**
 *  This class communicates with the QuestionsManager and retrieves the TheoreticalQ and ProgrammingQ questions
 *  to be displayed in the Game.
 */
public class Questions {

    public static final String TAG = Questions.class.getName();

    private ArrayList<TheoreticalQ> theoreticalQArrayList;
    private TheoreticalQ[] theoreticalQuestion;
    private int theoreticalQuestionsCounter;

    private ArrayList<ProgrammingQ> programmingQArrayList;
    private ProgrammingQ[] programmingQuestion;
    private int programmingQuestionsCounter;

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
        programmingQuestion = new ProgrammingQ[5];

        //shuffle total pool of theoretical questions to ensure randomness
        Collections.shuffle(theoreticalQArrayList);
        //shuffle total pool of programming questions to ensure randomness
        Collections.shuffle(programmingQArrayList);

        initializeTheoreticalQuestions();
        initializeProgrammingQuestions();
    }

    /**
     * Getter function for the mystery question.
     * Randomly selects between the two major categories TheoreticalQ and ProgrammingQ
     * After one major category is randomly selected, it then randomly selects one question from its total pool of questions.
     */
    public Object getMysteryQuestion(){
        Random rand = new Random();
        int pick = rand.nextInt(2);

        if(pick == 0){ //TheoreticalQ
            return theoreticalQArrayList.get(rand.nextInt(theoreticalQArrayList.size()));

        } else if(pick == 1){ //ProgrammingQ
            return programmingQArrayList.get(rand.nextInt(programmingQArrayList.size()));
        }

        return null;
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
            if(programmingQuestionsCounter <= 5){
                if(x.getDifficulty().equals("VERY EASY") && programmingQuestion[0] == null){
                    Gdx.app.log(TAG, "ADDED PROGRAMMING VERY EASY");
                    programmingQuestion[0] = x;
                    programmingQuestionsCounter++;
//                    Gdx.app.log(TAG, programmingQuestion[0].getQuestion());
                }
                if(x.getDifficulty().equals("EASY") && programmingQuestion[1] == null){
                    Gdx.app.log(TAG, "ADDED PROGRAMMING EASY");
                    programmingQuestion[1] = x;
                    programmingQuestionsCounter++;
//                    Gdx.app.log(TAG, programmingQuestion[1].getQuestion());
                }
                if(x.getDifficulty().equals("MEDIUM") && programmingQuestion[2] == null){
                    Gdx.app.log(TAG, "ADDED PROGRAMMING MEDIUM");
                    programmingQuestion[2] = x;
                    programmingQuestionsCounter++;
//                    Gdx.app.log(TAG, programmingQuestion[2].getQuestion());
                }
                if(x.getDifficulty().equals("HARD") && programmingQuestion[3] == null){
                    Gdx.app.log(TAG, "ADDED PROGRAMMING HARD");
                    programmingQuestion[3] = x;
                    programmingQuestionsCounter++;
//                    Gdx.app.log(TAG, programmingQuestion[3].getQuestion());
                }
                if(x.getDifficulty().equals("VERY HARD") && programmingQuestion[4] == null){
                    Gdx.app.log(TAG, "ADDED PROGRAMMING HARD");
                    programmingQuestion[4] = x;
                    programmingQuestionsCounter++;
//                    Gdx.app.log(TAG, programmingQuestion[4].getQuestion());
                }
            }
        }
    }

    public TheoreticalQ[] getTheoreticalQuestion(){
        return this.theoreticalQuestion;
    }

    public ProgrammingQ[] getProgrammingQuestion(){ return this.programmingQuestion; }


}
