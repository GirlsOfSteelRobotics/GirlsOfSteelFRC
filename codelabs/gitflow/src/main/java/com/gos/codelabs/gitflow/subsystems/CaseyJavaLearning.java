package com.gos.codelabs.gitflow.subsystems;

public class CaseyJavaLearning {
    private boolean m_isTired;
    private int m_temperature;
    private String m_school;

    public CaseyJavaLearning(boolean isTired, int temperature, String school){
        m_isTired = isTired;
        m_temperature = temperature;
        m_school = school;
    }

    public int getTemperature(){
        return m_temperature;
    }

    public void setTemperature(int temp){
        m_temperature = temp;
    }
    public static int convertTemp(int celsiusTemp){
        return (int)(celsiusTemp*1.8+32);
    }


    public static void main(String[] args){
        CaseyJavaLearning casey = new CaseyJavaLearning(true, 99, "North Allegheny");

        System.out.println(casey.getTemperature());
        casey.setTemperature(convertTemp(38));
    }
}
