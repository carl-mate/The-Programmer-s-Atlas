package util;

import com.badlogic.gdx.Gdx;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import util.Enums.Difficulty;

public class QuestionsManager {
    public static final String TAG = QuestionsManager.class.getName();
    public static final QuestionsManager instance = new QuestionsManager();
    public ArrayList<TheoreticalQ> theoreticalQArrayList;
    public ArrayList<ProgrammingQ> programmingQArrayList;

    private QuestionsManager(){
    }

    public ArrayList<TheoreticalQ> getTheoreticalQItemArrayList(){
        Gdx.app.log(TAG, "GET THEORETICALQ ARRAYLIST");
        return theoreticalQArrayList;
    }

    public ArrayList<ProgrammingQ> getProgrammingQItemArrayList(){
        Gdx.app.log(TAG, "GET PROGRAMMINGQ ARRAYLIST");
        return programmingQArrayList;
    }

    public void init() throws IOException {
        //fetch questions
        theoreticalQArrayList = ExcelFileToArrayList.convertTheoreticalQ();
        programmingQArrayList = ExcelFileToArrayList.convertProgrammingQ();
    }

    public static class ExcelFileToArrayList {

        public static ArrayList<TheoreticalQ> convertTheoreticalQ() throws IOException {
            ArrayList<TheoreticalQ> temp = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(Assets.instance.resourcesFilePath.theoreticalQuestionsInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(1).getLastCellNum();

            String topic = null;
            String question = null;
            String choiceA = null;
            String choiceB = null;
            String choiceC = null;
            String choiceD = null;
            String correctChoice = null;
            String imageFilename = null;
            String difficulty = null;

            for (int r = 1; r <= rows; r++) {
                XSSFRow tempRow = sheet.getRow(r);

                for (int c = 1; c <= cols; c++) {
                    XSSFCell cell = tempRow.getCell(c);

                    switch (c) {
                        case 1:
                            topic = cell.toString();
                            break;
                        case 2:
                            question = cell.toString();
                            break;
                        case 3:
                            choiceA = cell.toString();
                            break;
                        case 4:
                            choiceB = cell.toString();
                            break;
                        case 5:
                            choiceC = cell.toString();
                            break;
                        case 6:
                            choiceD = cell.toString();
                            break;
                        case 7:
                            correctChoice = cell.toString();
                            break;
                        case 8:
                            if (cell == null) {
                                imageFilename = "null";
                            } else {
                                imageFilename = cell.toString();
                            }
                            break;
                        case 9:
                            difficulty = cell.toString();
                            break;
                    }
                }
                temp.add(new TheoreticalQ(topic, question, choiceA, choiceB, choiceC, choiceD, correctChoice, imageFilename, difficulty));
            }

            return temp;
        }

        public static ArrayList<ProgrammingQ> convertProgrammingQ() throws IOException {
            ArrayList<ProgrammingQ> temp = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(Assets.instance.resourcesFilePath.programmingQuestionsInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(1).getLastCellNum();

            String topic = null;
            String question = null;
            String choiceA = null;
            String choiceB = null;
            String choiceC = null;
            String choiceD = null;
            String correctChoice = null;
            String imageFilename = null;
            String difficulty = null;

            for (int r = 1; r <= rows; r++) {
                XSSFRow tempRow = sheet.getRow(r);

                for (int c = 1; c <= cols; c++) {
                    XSSFCell cell = tempRow.getCell(c);

                    switch (c) {
                        case 1:
                            topic = cell.toString();
                            break;
                        case 2:
                            question = cell.toString();
                            break;
                        case 3:
                            choiceA = cell.toString();
                            break;
                        case 4:
                            choiceB = cell.toString();
                            break;
                        case 5:
                            choiceC = cell.toString();
                            break;
                        case 6:
                            choiceD = cell.toString();
                            break;
                        case 7:
                            correctChoice = cell.toString();
                            break;
                        case 8:
                            if (cell == null) {
                                imageFilename = "null";
                            } else {
                                imageFilename = cell.toString();
                            }
                            break;
                        case 9:
                            difficulty = cell.toString();
                            break;
                    }
                }
                temp.add(new ProgrammingQ(topic, question, choiceA, choiceB, choiceC, choiceD, correctChoice, imageFilename, difficulty));
            }

            return temp;
        }
    }

    public class Choice{
        private String choice;
        private boolean isCorrectChoice;

        public Choice(String choice, boolean isCorrectChoice){
            this.choice = choice;
            this.isCorrectChoice = isCorrectChoice;
        }

        public String getChoice() {
            return choice;
        }

        public void setChoice(String choice) {
            this.choice = choice;
        }

        public boolean isCorrectChoice() {
            return isCorrectChoice;
        }

        public void setCorrectChoice(boolean correctChoice) {
            isCorrectChoice = correctChoice;
        }


    }

    public static class TheoreticalQ {
        private final String topic;
        private final String question;
        private final String choiceA;
        private final String choiceB;
        private final String choiceC;
        private final String choiceD;
        private final String correctChoice;
        private final String imageFilename;
        private final String difficulty;
        private boolean isAnswered;

        public TheoreticalQ(String topic, String question, String choiceA, String choiceB, String choiceC,
                    String choiceD, String correctChoice, String imageFilename, String difficulty) {
            this.topic = topic;
            this.question = checkQuestionFormat(question);
            this.choiceA = checkChoiceFormat(choiceA);
            this.choiceB = checkChoiceFormat(choiceB);
            this.choiceC = checkChoiceFormat(choiceC);
            this.choiceD = checkChoiceFormat(choiceD);
            this.correctChoice = correctChoice;
            this.imageFilename = imageFilename;
            this.difficulty = difficulty;
            this.isAnswered = false;
        }

        public void changeIsAnsweredToTrue() {
            this.isAnswered = true;
        }

        public String getTopic() {
            return topic;
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

        public String getCorrectChoice() {
            return correctChoice;
        }

        public String getImageFilename() {
            return imageFilename;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public boolean getIsAnswered() {
            return isAnswered;
        }
    }

    public static class ProgrammingQ {
        private final String topic;
        private final String question;
        private final String choiceA;
        private final String choiceB;
        private final String choiceC;
        private final String choiceD;
        private final String correctChoice;
        private final String imageFilename;
        private final String difficulty;
        private boolean isAnswered;

        public ProgrammingQ(String topic, String question, String choiceA, String choiceB, String choiceC,
                            String choiceD, String correctChoice, String imageFilename, String difficulty) {
            this.topic = topic;
            this.question = checkQuestionFormat(question);
            this.choiceA = checkChoiceFormat(choiceA);
            this.choiceB = checkChoiceFormat(choiceB);
            this.choiceC = checkChoiceFormat(choiceC);
            this.choiceD = checkChoiceFormat(choiceD);
            this.correctChoice = correctChoice;
            this.imageFilename = imageFilename;
            this.difficulty = difficulty;
            this.isAnswered = false;
        }

        public void changeIsAnsweredToTrue() {
            this.isAnswered = true;
        }

        public String getTopic() {
            return topic;
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

        public String getCorrectChoice() {
            return correctChoice;
        }

        public String getImageFilename() {
            return imageFilename;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public boolean getIsAnswered() {
            return isAnswered;
        }
    }

    //util
    private static String checkQuestionFormat(String question){
        StringAlignUtils util = new StringAlignUtils(52, StringAlignUtils.Alignment.CENTER);
        return util.format(question);
    }

    private static String checkChoiceFormat(String choice){
        StringAlignUtils util = new StringAlignUtils(30, StringAlignUtils.Alignment.CENTER);
        return util.format(choice);
    }

}




