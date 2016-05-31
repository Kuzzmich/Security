package com.company;

/**
 * Created by Алексей on 07.03.2016.
 */
public class Learner extends LocalPerson {
    private String trainingPrograms;
    private String dateOfBeginTraining;
    {
        trainingPrograms ="default";
    }
    public Learner(){}

    public Learner(String name, String trainProgram, String beginTraining){
        super(name, "default", "default");
        trainingPrograms = trainProgram;
        dateOfBeginTraining=beginTraining;
    }

    public String fullName(){
        return super.getName();
    }
    //--------getters generated--------
    public String getTrainingPrograms() {
        return trainingPrograms;
    }

    public String getDateOfBeginTraining() {
        return dateOfBeginTraining;
    }

    //--------setters generated--------
    public void setTrainingPrograms(String trainingPrograms) {
        this.trainingPrograms = trainingPrograms;
    }

    public void setDateOfBeginTraining(String beginTraining) {
        this.dateOfBeginTraining = beginTraining;
    }
}
