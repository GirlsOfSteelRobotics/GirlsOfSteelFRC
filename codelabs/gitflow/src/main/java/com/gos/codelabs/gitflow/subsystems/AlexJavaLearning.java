package com.gos.codelabs.gitflow.subsystems;

public class AlexJavaLearning {

    private boolean m_isTired;
    private int m_temperature;
    private String m_school;

    public AlexJavaLearning(boolean isTired, int temperature, String school) {
        m_isTired = isTired;
        m_temperature = temperature;
        m_school = school;
    }

    public static int convertTemp(int celsiusTemp) {
        int fahrenheitTemp = (int) (celsiusTemp * 1.8 + 32);
        return fahrenheitTemp;
    }

    public int getTemperature() {
        return m_temperature;
    }

    public void setTemperature(int newTemp) {
        m_temperature = newTemp;
    }


    public static void main(String[] args) {
        AlexJavaLearning Alex = new AlexJavaLearning(false, 97, "Hampton High!!! :D");

        System.out.println(Alex.getTemperature());
        System.out.println(convertTemp(18));

    }
}
