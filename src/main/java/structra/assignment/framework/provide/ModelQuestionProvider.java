/*
 * MIT License
 *
 * Copyright (c) 2024 Riko Torun, Artur Rausch, Lennart Köhler, Moritz Wiedemann, Tim Stöcker, Souren Ishkhanian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package structra.assignment.framework.provide;

import lombok.NonNull;
import structra.assignment.framework.llm.MachineLearningModel;
import structra.assignment.framework.llm.context.SystemContextBuilder;
import structra.assignment.framework.llm.context.specification.GenericContext;
import structra.assignment.framework.llm.gen.questions.QuestionGenerationTarget;
import structra.assignment.framework.llm.gen.questions.TargetProvider;
import structra.assignment.framework.llm.model.Mimic;
import structra.assignment.framework.model.question.base.Question;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Provider for the LLM Game mode in which a new {@link Question} is generated by a {@link
 * MachineLearningModel} based on the contents of the provided questions.
 *
 * @author Riko Torun
 * @author Lennart Köhler
 * @see QuestionProvider
 */
public class ModelQuestionProvider implements QuestionProvider {

    public static final String TYPE = "llm";

    private final MachineLearningModel model;
    private final TargetProvider targetProvider;
    private final String initialQuestionContext;

    /**
     * Creates a new ModelQuestionProvider with the given Questions. The QuestionGenerationTarget
     * will only provide the given target.
     *
     * @param model The {@link MachineLearningModel} to use for the generation of new questions.
     * @param target the target question type being used for requesting the next question
     * @param contextQuestions The Questions to use for context.
     * @throws NullPointerException if questions or the target is null
     */
    public ModelQuestionProvider(
            MachineLearningModel model,
            @NonNull QuestionGenerationTarget<?> target,
            @NonNull Collection<Question<?>> contextQuestions) {
        this(model, () -> target, contextQuestions);
        Objects.requireNonNull(target);
    }

    /**
     * Creates a new ModelQuestionProvider with the given Questions.
     *
     * @param model The {@link MachineLearningModel} to use for the generation of new questions.
     * @param targetProvider The provider used for getting the next QuestionGenerationTarget being
     *     used.
     * @param contextQuestions The Questions to use for context.
     * @throws NullPointerException if questions or the TargetProvider is null
     */
    public ModelQuestionProvider(
            @NonNull MachineLearningModel model,
            @NonNull TargetProvider targetProvider,
            @NonNull Collection<Question<?>> contextQuestions) {
        Objects.requireNonNull(contextQuestions);

        this.model = model;
        this.targetProvider = Objects.requireNonNull(targetProvider);
        this.initialQuestionContext = getInitialQuestionContext(contextQuestions);
    }



    /** {@inheritDoc} */
    @Override
    public CompletableFuture<Question<?>> next() {
        QuestionGenerationTarget<?> target = targetProvider.provide();

        String context =
                new SystemContextBuilder()
                        .addRawContext("You are question-answer provider for quiz app.")
                        .addRawContext(target.getTargetContext())
                        .addRawContext(initialQuestionContext)
                        .build();

        this.model.setContext(context);

        CompletableFuture<Question<?>> future = new CompletableFuture<>();

        model.execute(target.getBasePrompt())
                .thenAccept(
                        response -> {
                            // Process the response from the model
                            String processed = postProcessModelOutput(response);

                            // Format the processed response into a Question object
                            Question<?> question = target.parse(processed);

                            // Complete with the formatted question
                            future.complete(question);
                        })
                .exceptionally(
                        throwable -> {
                            future.completeExceptionally(throwable);
                            return null;
                        });
        return future;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * resets the QuestionProvider to its initial state.
     *
     * @implNote The initial state is implementation dependent and might change over time
     */
    @Override
    public void reset() {}

    /** {@inheritDoc} */
    @Override
    public int getMaxProvisions() {
        return INFINITE_PROVISIONS;
    }

    /**
     * Post-processes the model output by extracting and cleaning the content between specified
     * delimiter characters.
     *
     * <p>This method performs the following operations:
     *
     * <ul>
     *   <li>Checks if the input string is null or empty.
     *   <li>Finds the first and last occurrence of the specified delimiter character.
     *   <li>If valid delimiters are found, extracts the content between them.
     *   <li>Removes all newline characters and backslashes from the extracted content.
     * </ul>
     *
     * @param response The raw response string from the model. If null or empty, an empty string is
     *     returned.
     * @return The processed string with content between delimiters extracted and cleaned. If no
     *     valid delimited content is found, returns the original response string. If the input is
     *     null or empty, returns an empty string.
     * @throws IllegalArgumentException if the delimiter is a whitespace character
     */
    @SuppressWarnings("all")
    @NonNull
    private String postProcessModelOutput(String response) {

        if (response == null || response.isEmpty()) return "";

        if (Character.isWhitespace(MachineLearningModel.DEFAULT_DELIMITER)) {
            throw new IllegalArgumentException("Delimiter cannot be a whitespace character");
        }

        int startIndex = response.indexOf(MachineLearningModel.DEFAULT_DELIMITER);
        int endIndex = response.lastIndexOf(MachineLearningModel.DEFAULT_DELIMITER);

        if (startIndex > -1 && startIndex < endIndex) {
            return response.substring(startIndex + 1, endIndex).replaceAll("[\n\\\\]", "");
        }

        return response;
    }

    /** Initializes the {@link MachineLearningModel}. */
    private static String getInitialQuestionContext(Collection<Question<?>> contextQuestions) {
        if (contextQuestions.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("Stick to the topic of following questions");

        contextQuestions.forEach(question -> sb.append(question.getText()).append(". "));

        return new SystemContextBuilder()
                .addRawContext(sb.toString())
                .addContext(GenericContext.UNIQUENESS)
                .addContext(GenericContext.DIFFICULTY)
                .addContext(GenericContext.LANGUAGE)
                .build();
    }
}
