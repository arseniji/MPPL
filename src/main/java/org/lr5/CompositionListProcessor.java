package org.lr5;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CompositionListProcessor {
    private List<Composition> enterList;

    public CompositionListProcessor(){
        enterList = new ArrayList<>();
    }

    public void add(Composition comp){
        enterList.add(comp);
    }

    public int getSize(){
        return enterList.size();
    }

    public List<Composition> streamFilterByPredicate(Predicate<Composition> predicate) {
        return enterList.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public List<Composition> legacyFilterByPredicate(Predicate<Composition> predicate) {
        List<Composition> exitList = new ArrayList<>();
        for(Composition c : enterList){
            if (predicate.test(c)) exitList.add(c);
        }
        return exitList;
    }

    public List<Composition> streamSortByComparator(Comparator<Composition> comparator) {
        return enterList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Composition> legacySortByComparator(Comparator<Composition> comparator){
        List<Composition> exitList = new ArrayList<>(enterList);
        exitList.sort(comparator);
        return exitList;
    }


    public Map<Genres, List<Composition>> legacyGroupByGenre(){
        Map<Genres, List<Composition>> map = new HashMap<>();
        for (Composition c : enterList){
            Genres currentGenre = c.getGenre();
            if (!map.containsKey(currentGenre)) map.put(currentGenre,new ArrayList<>());
            map.get(currentGenre).add(c);
        }
        return map;
    }

    public Map<Genres, List<Composition>> noStreamGroupByGenre(){
        Map<Genres, List<Composition>> map = new HashMap<>();
        for (Composition c : enterList){
            map.computeIfAbsent(c.getGenre(), genre -> new ArrayList<>()).add(c);
        }
        return map;
    }

    public Map<Genres, List<Composition>> streamGroupByGenre(){
        return enterList.stream()
                .collect(Collectors.groupingBy(Composition::getGenre));
    }

    public List<Composition> legacyGetByGenre(Genres genre){
        List<Composition> exitList = new ArrayList<>();
        for (Composition c : enterList){
            if(c.getGenre() == genre) exitList.add(c);
        }
        return exitList;
    }

    public List<Composition> streamGetByGenre(Genres genre){
        return enterList.stream()
                .filter(k -> k.getGenre() == genre)
                .collect(Collectors.toList());
    }

    public List<Composition> legacyFirstSortByDate(){
        List<Composition> exitList = new ArrayList<>(enterList);
        Collections.sort(exitList, new Comparator<Composition>() {
            @Override
            public int compare(Composition o1, Composition o2) {
                return o1.getReleaseDate().compareTo(o2.getReleaseDate());
            }
        });
        return exitList;
    }

    public List<Composition> legacySecondSortByDate(){
        List<Composition> exitList = new ArrayList<>(enterList);
        exitList.sort(new Comparator<Composition>() {
            @Override
            public int compare(Composition o1, Composition o2) {
                return o1.getReleaseDate().compareTo(o2.getReleaseDate());
            }
        });
        return exitList;
    }

    public List<Composition> legacyThirdSortByDate(){
        List<Composition> exitList = new ArrayList<>(enterList);
        exitList.sort((o1, o2) -> o1.getReleaseDate().compareTo(o2.getReleaseDate()));
        return exitList;
    }

    public List<Composition> legacyFinalSortByDate(){
        List<Composition> exitList = new ArrayList<>(enterList);
        exitList.sort(Comparator.comparing(Composition::getReleaseDate));
        return exitList;
    }

    public List<Composition> streamSortByDate(){
        return enterList.stream()
                .sorted(Comparator.comparing(Composition::getReleaseDate))
                .collect(Collectors.toList());
    }

    public List<Composition> streamSortByAuthor(){
        return enterList.stream()
                .sorted(Comparator.comparing(comp -> comp.getAuthor().getName()))
                .collect(Collectors.toList());
    }

    public List<Composition> streamChronologicalByGenre(Genres genre){
        return enterList.stream()
                .filter(c -> c.getGenre() == genre)
                .sorted(Comparator.comparing(Composition::getReleaseDate))
                .collect(Collectors.toList());
    }

    public List<Composition> streamGenreAndYearRange(Genres genre, LocalDate from, LocalDate to){
        return enterList.stream()
                .filter(c -> c.getGenre() == genre)
                .filter(c -> !c.getReleaseDate().isBefore(from))
                .filter(c -> !c.getReleaseDate().isAfter(to))
                .collect(Collectors.toList());
    }

    public List<Composition> legacyChronologicalByGenre(Genres genre){
        List<Composition> exitList = new ArrayList<>();
        for(Composition c:enterList){
            if (c.getGenre() == genre) exitList.add(c);
        }
        exitList.sort(Comparator.comparing(Composition::getReleaseDate));
        return exitList;
    }

    public List<Composition> legacyGenreAndYearRange(Genres genre, LocalDate from, LocalDate to){
        List<Composition> exitList = new ArrayList<>();
        for(Composition c:enterList){
            if (c.getGenre() == genre && !c.getReleaseDate().isBefore(from) && !c.getReleaseDate().isAfter(to)) exitList.add(c);
        }
        return exitList;
    }


}
