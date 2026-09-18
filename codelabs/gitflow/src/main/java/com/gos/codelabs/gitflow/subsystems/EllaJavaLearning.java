package com.gos.codelabs.gitflow.subsystems;

public class EllaJavaLearning {

    private boolean m_isTired;
    private int m_temperature;
    private String m_school;

    public EllaJavaLearning(boolean isTired, int temperature, String school){
        m_isTired = isTired;
        m_temperature = temperature;
        m_school = school;
    }

    public static int convertTemp(int celsiusTemp) {
        int fahrenheitTemp = (int) (celsiusTemp * 1.8 + 32);
        return (int) fahrenheitTemp;
    }

    public int getTemperature(){
        return m_temperature;
    }

    public void setTemperature (int newTemp){
        m_temperature = newTemp;
    }

    public static void main(String[] args) {
        EllaJavaLearning Ella = new EllaJavaLearning(true, 200, "None");
        EllaJavaLearning Casey = new EllaJavaLearning(true,5, "NA");

        System.out.println(Ella.getTemperature());
        System.out.println(convertTemp(30));
    }
}
