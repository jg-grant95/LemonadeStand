package com.example.jerringiselle.lemonadestand;

public class Problem{

    private String question;
    private String answer;
    private int id;
    private ProblemDifficulty problemDifficulty;

    public Problem(){
        String question="";
        String answer="";
        int id=0;
        problemDifficulty=ProblemDifficulty.easy;
    }

    public Problem(String q, String a, int id, ProblemDifficulty problemDifficulty){
        this.setQuestion(q);
        this.setAnswer(a);
        this.setId(id);
        this.setProblemDifficulty(problemDifficulty);
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProblemDifficulty getProblemDifficulty() {
        return problemDifficulty;
    }

    public void setProblemDifficulty(ProblemDifficulty problemDifficulty) {
        this.problemDifficulty = problemDifficulty;
    }

    public String toString(){
        return question+"\n"+answer+"\n"+problemDifficulty ;
    }
}