package structra.assignment.task.impl;
import structra.assignment.framework.llm.*;
import structra.assignment.framework.llm.model.Mimic;


public class MainApp{

    public static void main(String[] args) {

    OurGUI gui = new OurGUI();


    MachineLearningModel mimic= new Mimic(new KeyProvide());

    }
}

