package com.gos.codelabs.gitflow.subsystems;

import java.security.KeyStore.TrustedCertificateEntry;

public class MiaJavaLearning {

    pirvate boolean m_isTired;
    private int m_temperature;
    private String m_school;

    public MiaJavaLearning (boolean isTired, int temperature, String school){
        m_isTired = isTired;
        m_temperature = temperature;
        m_school = school;
    }

    public static int convertTemp (int celsiusTemp){
        return (int) (celsiusTemp * 1.8 + 32);
    }

    public int getTemperature (){
        return m_temperature;
    }

    public void setTemperature (int newTemp){
       m_temperature = newTemp;
    }

    public static void main (String[] args) {
        MiaJavaLearning Mia = new MiaJavaLearning(true, 200, "North Allegheny");
        MiaJavaLearning Lienna = new MiaJavaLearning(true, 67, "Oakland Catholic");

        System.out.println(Mia.getTemperature());
        System.out.println(convertTemp(30));
    }
}
