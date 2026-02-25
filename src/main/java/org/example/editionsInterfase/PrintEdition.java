package org.example.editionsInterfase;

public interface PrintEdition {
    Integer getPrintingNum();
    Integer getNumPages();
    String getLanguage();
    void setPrintingNum(Integer printingNum);
    void setLanguage(String language);
    void setNumPages(Integer numPages);
    @Override
    String toString();
    @Override
    boolean equals(Object o);
    @Override
    int hashCode();
}
