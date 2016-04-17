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

    public Learner(String trainProgram, String beginTraining){
        trainingPrograms = trainProgram;
        dateOfBeginTraining=beginTraining;
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
