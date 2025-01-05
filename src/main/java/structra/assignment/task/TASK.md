# 🌟 **Quiz App Development Plan**

## ⚠️ Implementation details ⚠️

Your implementation should be placed in the package `src/main/java/structra/assignment/task/impl`. You may use Example.java
as a reference, but you don't have to.

## a). **Plugin Creation**

- **Objective**: Create a class that implements the `KeyProvider.java` interface. Its purpose is to allow for simple
  retrieval of an API key via the `getApiKey` method. The API key can be the one required by `Mimic.java` or a genuine
  one used for the OpenAI chat completions endpoint.

- **Resources**: Use the following API key if you want to use `Mimic.java` (Our mock implementation of
  `ChatGPTModel.java`)
    - `structra-1343abnc-dGhpcyBpcyBub3Qgb3VyIGFwaSBrZXksIG5pY2UgdHJ5IHRobyA6KQ==`

## b) **GUI Implementation**

- **Objective**: Develop an interactive Graphical User Interface (GUI) to display questions dynamically, powered by the
  `ModelQuestionProvider.java`.

- **Technologies**: **Swing** (Java GUI toolkit). We provide example code in Example.java. You can also reference
  the [official documentation](https://docs.oracle.com/javase/tutorial/uiswing/) for guidance, but here the point is not to become a GUI developer, just to practice using a framework based on example code.

- **Components to Include**:
    - **Main Window**: The main container (e.g `JFrame`) of all other elements.
    - **Next Question Button**: A button that, when pressed, retrieves and displays the next question using
      `ModelQuestionProvider.next()`.
    - **Question Display Area**: A `TextArea` or `Label` that will display the question text. This should account for
      different question types (e.g. multiple-choice questions).

- **Non-blocking Design**:
    - Ensure the GUI remains responsive while tasks are being processed (Properly use `CompletableFuture`).

- **Handling Different Question Types**:
    - Display the question properly formatted in non JSON form
    - Example: <p>
      ![GUI Example](/src/main/resources/GUI_example.png) </p>

## c). **Multiple Choice Formatter**

- **Objective**: Add your implementation to generate and format multiple-choice questions, using the model
  `ChatGPTModel.java` (or its mock, `Mimic.java`).

- **Steps**:
    - Create a concrete implementation for `QuestionGenerationTarget.java` and `SystemContext.java`.
    - Use the above classes to process inputs from the chosen model and generate instances of
      `MultiCheckboxQuestion.java` using `Quizzmaker.java`.
        - Refer to `OpenQuestionTarget.java` and `OpenQuestionContext.java` for guidance [^1].
    - Format the question and answer options to be displayed in the GUI.

## d) **Refactoring**

- **Objective**: Propose a refactoring of the `QuestionTypes.java` enumeration, making it more modular, maintainable, and
  extensible.

- **Steps**:
    - Analyze the current `QuestionTypes.java`.
    - Create a UML [^2] diagram that outlines how to improve `QuestionTypes.java`.

## e). **Documentation Improvements**

- **Objective**: Suggest improvements for any documentation inside `structra/assignment/framework`. This includes
  docstrings as well, as UML diagrams (if you feel like creating one).

- **Steps**:
    - Analyze the current documentation.
    - Suggest changes by adding documentation inside the respective files / packages.
    - Write a *short* text justifying your changes.

## f). **Reflection**

- **Objective**: Recap the lectures and check if the framework adheres to the principles and suggestions for architecture and implementation.

- **Steps**:
    - Have a look at the lecture material again.
    - Familiarize yourself with the design patterns used and design choices made.
    - Implement one improvement in the code and document it.
    - If you cannot come up with an improvement to the framework code, discuss whether and how your application code adheres to the SOLID principles (based on one example) and whether a design pattern from the lecture could be used..

[^1]: Additional documentation can be found in `structra/assignment/framework/docs`.
[^2]: PlantUML
renderer [PlantUML](https://www.plantuml.com/plantuml/duml/SoWkIImgAStDuNBAJrBGjLDmpCbCJbMmKiX8pSd9vt98pKi1IW80).
