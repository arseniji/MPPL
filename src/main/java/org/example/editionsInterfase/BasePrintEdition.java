package org.example.editionsInterfase;

import java.util.Objects;

public abstract class BasePrintEdition implements org.example.editionsInterfase.PrintEdition {
    private Integer printingNum;
    private String language;
    private Integer numPages;

    public BasePrintEdition(Integer printingNum, String language, Integer numPages){
        this.printingNum = printingNum;
        this.language = language;
        this.numPages = numPages;
    }

    @Override
    public String toString(){
        return String.format("%s : Тираж = %d, Язык = %s, Число страниц = %d",
                getClass().getSimpleName(), printingNum, language, numPages);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        boolean result = false;
        if (o != null && o instanceof org.example.editionsInterfase.BasePrintEdition){
            org.example.editionsInterfase.BasePrintEdition d = (BasePrintEdition) o;
            if (Objects.equals(printingNum, d.printingNum) &&
                    Objects.equals(language, d.language) &&
                    Objects.equals(numPages, d.numPages))
                result = true;
        }
        return result;
    }
    @Override
    public int hashCode(){
        return Objects.hash(printingNum,language,numPages);
    }

    public Integer getPrintingNum() {
        return printingNum;
    }
    public Integer getNumPages(){
        return numPages;
    }
    public String getLanguage(){
        return language;
    }
    public void setPrintingNum(Integer printingNum) {
        this.printingNum = printingNum;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }
}
