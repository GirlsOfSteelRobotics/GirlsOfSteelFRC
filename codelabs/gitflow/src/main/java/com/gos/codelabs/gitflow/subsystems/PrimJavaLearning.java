package com.gos.codelabs.gitflow.subsystems;

public class PrimJavaLearning {

    private boolean m_isTired;
    private int m_temperature;
    private String m_school;

    public PrimJavaLearning(boolean isTired, int temperature, String school) {
        m_isTired = isTired;
        m_temperature = temperature;
        m_school = school;
    }

    public static int convertTemp(int celsiusTemp) {
        return (int) (celsiusTemp * 1.8 + 32);
    }

    public int getTemperature() {
        return m_temperature;
    }

    public void setTemperature(int newTemp) {
        m_temperature = newTemp;
    }

    public static void main(String[] args) {
        PrimJavaLearning Prim = new PrimJavaLearning(true, 200, "Oakland Catholic");
        PrimJavaLearning Lienna = new PrimJavaLearning(true, 150, "Oakland Catholic");

        System.out.println(Prim.getTemperature());
        System.out.println(convertTemp(30));
    }
}
