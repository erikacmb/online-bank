package br.com.radixeng;
import java.util.ArrayList;

public class Client implements IClient {

    private String name;
    private String document;
    private double income;
    private ArrayList<Double> incomeHistory;

    Client(String name, String document, double income) {
        this.name = name;
        this.document = document;
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public double getIncome() {
        return income;
    }

    public ArrayList<Double> getIncomeHistory() {
        return incomeHistory;
    }

    @Override
    public void updateIncome(double newIncome) {
        this.income = newIncome;
        this.incomeHistory.add(newIncome);
    }

}