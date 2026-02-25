package org.example.editionsInterfase;

import java.util.Objects;

public class Newspaper extends BasePrintEdition implements Calculatable{
    private String type;
    public Newspaper(Integer printingNum, String language, Integer numPages, String type){
        super(printingNum, language, numPages);
        this.type = type;
    }
    @Override
    public Double calculateCost(Object... additionalParams){
        Integer baseSum = (Integer) additionalParams[0];
        Integer wordCount = (Integer) additionalParams[1];
        Integer circulation = getPrintingNum();
        return (double) (baseSum + 5 * wordCount) / circulation;
    }

    @Override
    public String toString(){
        return super.toString() + String.format(", Тип = %s", type);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof org.example.editionsInterfase.Newspaper)) return false;
        org.example.editionsInterfase.Newspaper np = (org.example.editionsInterfase.Newspaper) o;
        if (!super.equals(o)) return false;
        return Objects.equals(type, np.type);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),type);
    }

    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
