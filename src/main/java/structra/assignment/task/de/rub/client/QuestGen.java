package structra.assignment.task.de.rub.client;

import structra.assignment.framework.llm.MachineLearningModel;
import structra.assignment.framework.llm.gen.questions.OpenQuestionTarget;
import structra.assignment.framework.llm.gen.questions.RandomTargetProvider;
import structra.assignment.framework.llm.model.Mimic;
import structra.assignment.framework.model.question.QuestionType;
import structra.assignment.framework.model.question.base.Question;
import structra.assignment.framework.provide.ModelQuestionProvider;
import structra.assignment.framework.provide.QuestionProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;
/*
Helper class to generate the questions including the LLM


 */
public class QuestGen {

    public static ArrayList<Object> questionGen() {
        ArrayList<Object> ReturnArray = new ArrayList<>();
        MachineLearningModel mimic = new Mimic(new KeyProvide());

        OpenQuestionTarget oQt = new OpenQuestionTarget(Mimic.OPEN_ANSWER);
        MultiQuestionTarget mQt = new MultiQuestionTarget(Mimic.MULTIPLE_CHOICE);


        RandomTargetProvider randomTargetProvider = new RandomTargetProvider(oQt,mQt);


        QuestionProvider questionProvider = new ModelQuestionProvider(mimic, randomTargetProvider, new ArrayList<>());

        CompletableFuture<Question<?>> future =questionProvider.next();

        future.thenAccept(question -> {


            String str = "Question Text : " + question.getText() +
                    "\n" +
                    "Question Difficulty : " + question.getDifficulty() +
                    "\n" +
                    "Question Points : " + question.getPointsPossible() +
                    "\n";
            /* Check if it's a Multiple Choice question to add the answers

             */
            //System.out.println(question.getType());
            if (question.getType().equals(QuestionType.MULTIPLE_CHOICE)) {
                str += "\n" +
                        "Answers \n" +
                        "1: " + question.getAnswers()[0].getText() +
                        "\n" +
                        "2: " + question.getAnswers()[1].getText() +
                        "\n" +
                        "3: " + question.getAnswers()[2].getText() +
                        "\n" +
                        "4: " + question.getAnswers()[3].getText() +
                        "\n";


            }

            String ExplanationBuilder = "Question Explanation : " + question.getExplanation() + "\n";
            // add the strings to the retunr array
            ReturnArray.add(str);
            ReturnArray.add(ExplanationBuilder);

            if (question.getType().equals(QuestionType.MULTIPLE_CHOICE)) {
                ArrayList<String> answerBuilder=new ArrayList<>();

                for (int i = 0; i < question.getAnswers().length; i++) {
                    //System.out.println(" expected "+ question.getAnswers()[i].getExpected());
                    if (question.getAnswers()[i].getExpected().toString().equals("true")) {
                        answerBuilder.add(question.getAnswers()[i].getText());
                        //System.out.println(" add to answer Builder "+question.getAnswers()[i].getText());
                    }
                }
                ReturnArray.add(answerBuilder);
            }
            else {
                // if Open answer the first and olny answer is added to the array
                ArrayList<String> answerBuilder=new ArrayList<>();
                answerBuilder.add(question.getAnswers()[0].getText());
                ReturnArray.add(answerBuilder);

            }





        });
            //System.out.println("\n"+ReturnArray);
            return ReturnArray;
        }


    }
