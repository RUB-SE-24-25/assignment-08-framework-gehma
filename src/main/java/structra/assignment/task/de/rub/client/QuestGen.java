package structra.assignment.task.de.rub.client;

import structra.assignment.framework.llm.MachineLearningModel;
import structra.assignment.framework.llm.gen.questions.OpenQuestionTarget;
import structra.assignment.framework.llm.gen.questions.QuestionGenerationTarget;
import structra.assignment.framework.llm.model.Mimic;
import structra.assignment.framework.model.question.base.Question;
import structra.assignment.framework.provide.ModelQuestionProvider;

import java.util.Collection;
import java.util.List;

public class QuestGen {

    public static String questionGen() {
        MachineLearningModel mimic = new Mimic(new KeyProvide());

        QuestionGenerationTarget<?> qGT = new OpenQuestionTarget(mimic.execute("open_answer").join());
        Collection<Question<?>> contextQ = List.of();



        ModelQuestionProvider mQP = new ModelQuestionProvider(mimic, qGT, contextQ );
        Question<?> genQest = mQP.next().join();
        String text = genQest.getText();

        return text;
    }


}
