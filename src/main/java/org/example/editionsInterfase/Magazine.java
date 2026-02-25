package org.example.editionsInterfase;

import java.util.Objects;

public class Magazine extends BasePrintEdition implements Calculatable {
    private Integer annualEditions;
    public Magazine(Integer printingNum, String language, Integer numPages, Integer annualEditions){
        super(printingNum,language,numPages);
        this.annualEditions = annualEditions;
    }
    @Override
    public Double calculateCost(Object... additionalParams){
        Integer baseSum = (Integer) additionalParams[0];
        Integer articleCount  = (Integer) additionalParams[1];
        Integer circulation = getPrintingNum();
        return (double) (baseSum + 50 * articleCount ) / circulation;
    }
    @Override
    public String toString(){
        return super.toString() + String.format(", Число выпусков в год = %s", annualEditions);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof org.example.editionsInterfase.Magazine)) return false;
        org.example.editionsInterfase.Magazine m = (org.example.editionsInterfase.Magazine) o;
        if (!super.equals(o)) return false;
        return Objects.equals(annualEditions,m.annualEditions);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),annualEditions);
    }

    public Integer getAnnualEditions() {
        return annualEditions;
    }

    public void setAnnualEditions(Integer annualEditions) {
        this.annualEditions = annualEditions;
    }
}
