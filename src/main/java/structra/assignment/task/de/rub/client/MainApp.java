//matrickel nummern :
// Johanna Lütke Entrup - 10802306870
//Melanie Waldikowski -  108023211670
//Leonhard Herlitz -   108023208379

package structra.assignment.task.de.rub.client;
import structra.assignment.framework.llm.MachineLearningModel;
import structra.assignment.framework.llm.gen.questions.OpenQuestionTarget;
import structra.assignment.framework.llm.gen.questions.RandomTargetProvider;
import structra.assignment.framework.llm.model.Mimic;
import structra.assignment.framework.model.answer.base.Answer;
import structra.assignment.framework.model.question.base.Question;
import structra.assignment.framework.model.question.concrete.MultiCheckboxQuestion;
import structra.assignment.framework.provide.ModelQuestionProvider;
import structra.assignment.framework.provide.QuestionProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;


public class MainApp{
    // the  Answers to the questions are displayed in the IDE console

    public static void main(String[] args) {
        OurGUI gui = new OurGUI();

    }
}

