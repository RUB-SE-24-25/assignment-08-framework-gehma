//matrickel nummern :
// Johanna Lütke Entrup - 10802306870
//Melanie Waldikowski -  108023211670
//Leonhard Herlitz -   108023208379

package structra.assignment.task.de.rub.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.NonNull;
import structra.assignment.framework.llm.MachineLearningModel;
import structra.assignment.framework.llm.context.SystemContextBuilder;
import structra.assignment.framework.llm.gen.questions.QuestionGenerationTarget;
import structra.assignment.framework.model.StringConstants;
import structra.assignment.framework.model.answer.AnswerData;
import structra.assignment.framework.model.answer.concrete.BooleanAnswer;
import structra.assignment.framework.model.answer.concrete.TextAnswer;
import structra.assignment.framework.model.gen.QuizzMaker;
import structra.assignment.framework.model.question.QuestionData;
import structra.assignment.framework.model.question.QuestionType;
import structra.assignment.framework.model.question.base.MultiAnswerQuestion;
import structra.assignment.framework.model.question.concrete.MultiCheckboxQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MultiQuestionTarget implements QuestionGenerationTarget<MultiCheckboxQuestion> {

    private final String prompt;

    /**
     * Creates a new {@code OpenQuestionTarget} instance, with the given prompt.
     *
     * @param prompt the prompt used by the implementation of {@link MachineLearningModel}
     * @see MultiAnswerQuestion
     */
    public MultiQuestionTarget(String prompt) {
        Objects.requireNonNull(prompt, "Prompt can not be null");
        this.prompt = prompt;
    }

    /**
     * {@inheritDoc}
     *
     * @return the prompt
     */
    @Override
    public String getBasePrompt() {
        return prompt;
    }

    /**
     * Formats the input JSON object to produce an OpenAnswerQuestion.
     *
     * @param input The input JSON object containing question and answer data
     * @return The formatted OpenAnswerQuestion, or an error question if parsing fails
     * @throws IllegalArgumentException if the input is in an invalid format
     * @throws NullPointerException if the input is null
     */
    public MultiCheckboxQuestion parse(String input) {
        Objects.requireNonNull(input, "Input string cannot be null");

        try {
            JsonObject object = JsonParser.parseString(input).getAsJsonObject();
            AnswerData[] answerData = constructAnswerData(object);
            QuestionData questionData = parseQuestionData(object, answerData);
            return (MultiCheckboxQuestion) QuizzMaker.createQuestion(questionData);
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return createErrorQuestion(); // Return error question on parsing failure
        }
    }

    /**
     * Parses the answer data from a JSON object.
     *
     * @param answerObject The JSON object containing the answer data
     * @return An AnswerData object constructed from the parsed data
     * @throws JsonIOException If there's an error parsing the JSON object
     */
    private AnswerData[] constructAnswerData(JsonObject answerObject) throws JsonIOException {
        AnswerData[] answerData = new AnswerData[4];
        JsonArray answer= answerObject.getAsJsonArray(StringConstants.Answers.ANSWERS_NAME);
        for (int i = 0; i < 4; i++) {
            JsonObject answerObj = answer.get(i).getAsJsonObject();

            answerData[i]= new AnswerData(
                    BooleanAnswer.class.getName(),
                    answerObj.get(StringConstants.Answers.ANSWER_TEXT).getAsString(),
                    answerObj.get(StringConstants.Answers.EXPECTED_ANSWER).getAsString(),
                    "");

            //System.out.println(answerData[i].getText()+" "+answerData[i].getExpected());

        }


        return answerData;
    }

    /**
     * Parses the question data from a JSON object and combines it with the given answer data.
     *
     * @param questionObject The JSON object containing the question data
     * @param answerData The AnswerData object to be associated with this question
     * @return A QuestionData object constructed from the parsed data and the given answer
     * @throws JsonIOException If there's an error parsing the JSON object
     */
    private QuestionData parseQuestionData(JsonObject questionObject, AnswerData[] answerData)
            throws JsonIOException {
        JsonObject question =
                questionObject.getAsJsonObject(StringConstants.Questions.QUESTIONS_NAME);

        return new QuestionData(
                QuestionType.MULTIPLE_CHOICE.toString(),
                question.get(StringConstants.Questions.QUESTION_TEXT).toString(),
                question.get(StringConstants.Questions.QUESTION_DIFFICULTY).getAsDouble(),
                question.get(StringConstants.Overall.POINTS_POSSIBLE).getAsInt(),
                question.get(StringConstants.Questions.QUESTION_EXPLANATION).getAsString(),
                "",
                List.of(answerData),
                false);
    }

    /**
     * Creates a default error question; to be used when parsing fails.
     *
     * @return An MultiAnswerQuestion object representing an error state
     */
    private MultiCheckboxQuestion createErrorQuestion() {
        AnswerData errorAnswer =
                new AnswerData(
                        TextAnswer.class.getName(),
                        "not correctly generated", // Adjust error message as needed
                        "",
                        "");

        QuestionData errorQuestion =
                new QuestionData(
                        QuestionType.MULTIPLE_CHOICE.toString(),
                        "An error occurred while generating the question. Please try again.",
                        0.0,
                        0,
                        "Error in question generation",
                        "",
                        Collections.singletonList(errorAnswer),
                        false);

        return (MultiCheckboxQuestion) QuizzMaker.createQuestion(errorQuestion);
    }

    @Override
    public @NonNull String getTargetContext() {
        return new SystemContextBuilder()
                .addContext(MultiQuestionContext.FORMAT)
                .addContext(MultiQuestionContext.PROPER_EXPLANATION)
                .build();
    }


}
