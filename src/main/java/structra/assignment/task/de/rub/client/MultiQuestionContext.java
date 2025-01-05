package structra.assignment.task.de.rub.client;

import structra.assignment.framework.llm.MachineLearningModel;
import structra.assignment.framework.llm.context.specification.SystemContext;
import structra.assignment.framework.model.StringConstants;

public enum MultiQuestionContext implements SystemContext {
    FORMAT(
            "EXCLUSIVELY return JSON, NOTHING ELSE. Structure MUST follow EXACTLY, "
                    + "NEVER use \\n. Provide 1 Question and 4 Answers. Respond adhering "
                    + "EXACTLY to format: %s"),
    PROPER_EXPLANATION(
            "The explanation should explain why question is correct."
                    + "It should not contain what this questions aims to achieve");

    private final String contextMessage;

    MultiQuestionContext(String contextMessage) {
        this.contextMessage = contextMessage;
    }

    /**
     * Generates the JSON format template for the questions and answers.
     *
     * @return The formatted JSON string template.
     */
    private static String getMultiQuestionFormatTemplate() {
        final String STRING = "string";
        final String DOUBLE = "double";
        final String LONG = "long";

        return String.format(
                MachineLearningModel.DEFAULT_DELIMITER
                        + "{\"%s\": {"
                        + "\"%s\": %s, "
                        + "\"%s\": %s, "
                        + "\"%s\": %s, "
                        + "\"%s\": %s"
                        + "}, "
                        + "\"%s\": {"
                        + "\"%s\": %s, "
                        + "\"%s\": %s"
                        + "}"
                        + "}"
                        + MachineLearningModel.DEFAULT_DELIMITER
                        + ". ",
                StringConstants.Questions.QUESTIONS_NAME,
                StringConstants.Questions.QUESTION_TEXT,
                STRING,
                StringConstants.Questions.QUESTION_DIFFICULTY,
                DOUBLE,
                StringConstants.Overall.POINTS_POSSIBLE,
                LONG,
                StringConstants.Questions.QUESTION_EXPLANATION,
                STRING,
                StringConstants.Answers.ANSWERS_NAME,
                StringConstants.Answers.ANSWER_TEXT,
                STRING,
                StringConstants.Answers.EXPECTED_ANSWER,
                STRING);
    }





    /**
     * @return {@inheritDoc}
     */
    @Override
    public String getContext() {
        return String.format(contextMessage, getMultiQuestionFormatTemplate());
    }
}
