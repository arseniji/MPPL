package org.example;

import org.example.editions.Book;
import org.example.editions.Magazine;
import org.example.editions.Newspaper;
import org.example.editions.PrintEdition;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<PrintEdition> editions = new ArrayList<>();
        List<PrintEdition> allEditions = new ArrayList<>();
        allEditions.add(new Newspaper(10000, "ru", 16, "печатная"));
        allEditions.add(new Newspaper(10000, "ru", 16, "печатная"));
        allEditions.add(new Newspaper(5000, "en", 12, "электронная"));
        allEditions.add(new Newspaper(10000, "ru", 16, "печатная")); // Дубликат первого
        allEditions.add(new Magazine(20000, "ru", 64, 12));
        allEditions.add(new Magazine(15000, "en", 48, 6));
        allEditions.add(new Book(5000, "ru", 350, "Лев Толстой"));
        allEditions.add(new Book(3000, "en", 450, "Джордж Оруэлл"));
        allEditions.add(new Magazine(20000, "ru", 64, 12)); // Дубликат четвертого
        allEditions.add(new Book(7000, "ru", 280, "Александр Пушкин"));
        allEditions.add(new Newspaper(8000, "ru", 20, "смешанная"));

        for (PrintEdition concreteEdition : allEditions) System.out.println(concreteEdition);

        callSeparator();

        for (PrintEdition concreteEdition : allEditions){
            if (uniq(editions,concreteEdition)) editions.add(concreteEdition);
        }

        for (PrintEdition concreteEdition : editions) System.out.println(concreteEdition);

        callSeparator();

        Newspaper newspaper = new Newspaper(10000, "ru", 16, "печатная");
        Magazine magazine = new Magazine(20000, "ru", 64, 12);
        Book book = new Book(3000, "en", 450, "Джордж Оруэлл");

        System.out.println("Тест геттеров");
        System.out.println(String.format("newspaper.getPrintingNum = %d", newspaper.getPrintingNum()));
        System.out.println(String.format("newspaper.getLanguage = %s", newspaper.getLanguage()));
        System.out.println(String.format("newspaper.getNumPages = %d", newspaper.getNumPages()));
        System.out.println(String.format("newspaper.getType = %s", newspaper.getType()));
        System.out.println(String.format("magazine.getAnnualEditions = %d", magazine.getAnnualEditions()));
        System.out.println(String.format("book.getAuthor = %s", book.getAuthor()));

        callSeparator();

        System.out.println("Тест стоимости");
        System.out.println(String.format("newspaper.calculateCost(10,10) = %f" , newspaper.calculateCost(10,10)));
        System.out.println(String.format("magazine.calculateCost(10,10) = %f", magazine.calculateCost(10,10)));
        System.out.println(String.format("book.calculateCost(10) = %f", book.calculateCost(10)));

    }
    private static boolean uniq(List<PrintEdition> arrayOfEditions, PrintEdition newItem){
        for (PrintEdition concreteEdition : arrayOfEditions) {
            if (concreteEdition.equals(newItem)) return false;
        }
        return true;
    }

    private static void callSeparator(){
        System.out.println();
        System.out.println("☺ ".repeat(30));
        System.out.println();
    }

}