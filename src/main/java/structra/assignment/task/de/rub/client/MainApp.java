package structra.assignment.task.de.rub.client;
import structra.assignment.framework.llm.MachineLearningModel;
import structra.assignment.framework.llm.gen.questions.OpenQuestionTarget;
import structra.assignment.framework.llm.model.Mimic;
import structra.assignment.framework.model.question.base.Question;
import structra.assignment.framework.provide.ModelQuestionProvider;

import java.util.Collection;
import java.util.List;


public class MainApp{

    public static void main(String[] args) {

        OurGUI gui = new OurGUI();
        MachineLearningModel mimic = new Mimic(new KeyProvide());
        OpenQuestionTarget oQt= new OpenQuestionTarget(mimic.execute("open_answer").join());
        String newT =mimic.execute("open_answer").join();

        System.out.println(newT);
        Question<?> newQ = oQt.parse(newT);
        String  text = newQ.getText();
        System.out.println(text);
        Collection<Question<?>> contextQ = List.of();


        ModelQuestionProvider mQP = new ModelQuestionProvider(mimic, oQt, contextQ );
        Question<?> genQest = mQP.next().join();


    }
}

