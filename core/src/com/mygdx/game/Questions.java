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
import util.QuestionsManager.ProgrammingQ;
import util.QuestionsManager;

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

//    private String theoreticalVeryEasyQuestion;
//    private String theoreticalEasyQuestion;
//    private String theoreticalMediumQuestion;
//    private String theoreticalHardQuestion;
//    private String theoreticalVeryHardQuestion;
//    private String programmingVeryEasyQuestion;
//    private String programmingEasyQuestion;
//    private String programmingMediumQuestion;
//    private String programmingHardQuestion;
//    private String programmingVeryHardQuestion;

    private HashMap<QuestionTheoretical, ArrayList<Choice>> theoreticalQArrayListHashMap;

    private ArrayList<TheoreticalQ> theoreticalQArrayList;
    private ArrayList<ProgrammingQ> programmingQArrayList;
    private long startTime;

    public Questions() throws IOException {
        QuestionsManager.instance.init();
        theoreticalQArrayList = QuestionsManager.instance.getTheoreticalQItemArrayList();
//        programmingQArrayList = QuestionsManager.instance.getP


        //shuffle questions to ensure randomness
        Collections.shuffle(theoreticalQArrayList);

        initializeTheoreticalQuestions();

    }

    public void initializeTheoreticalQuestions(){
         /*
            1 THEORETICAL VERY EASY
            1 THEORETICAL EASY
            1 THEORETICAL MEDIUM
            1 THEORETICAL HARD
            1 THEORETICAL VERY HARD
            1 PROGRAMMING VERY EASY
            1 PROGRAMMING EASY
            1 PROGRAMMING MEDIUM
            1 PROGRAMMING HARD
            1 PROGRAMMING VERY HARD
            1 MYSTERY QUESTION (CHOSEN FROM THE TOTAL POOL OF QUESTIONS)
         */
        for(TheoreticalQ x: theoreticalQArrayList){
            if(x.getDifficulty().equals("VERY EASY")){
//                theoreticalQArrayListHashMap.put(new QuestionTheoretical(x.getQuestion()), )
            }
        }

    }

//    public void setQuestionDifficulty(Difficulty difficulty){
//        this.difficulty = difficulty;
//    }


    public String getCorrectChoice(){
        return this.correctChoice;
    }

    public String getQuestionDifficulty(){
        return this.questionDifficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public String getImageFileName(){
        return imageFileName;
    }

    public void resetStartTime(){
        this.startTime = 0;
    }



}
