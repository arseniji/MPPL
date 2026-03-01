package org.lr5;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        printHeader("🎵 ЗАПУСК ТЕСТИРОВАНИЯ MUSIC LIBRARY 🎵");

        // 1. Инициализация процессора
        CompositionListProcessor processor = new CompositionListProcessor();

        // 2. Генерация данных (Куча объектов)
        System.out.println("⏳ Генерация библиотеки композиций...");
        generateData(processor);
        System.out.println("✅ Добавлено композиций: " + processor.getSize() + "\n");

        // 3. Тестирование Группировки (Group By)
        testGrouping(processor);

        // 4. Тестирование Сортировки (Sorting)
        testSorting(processor);

        // 5. Тестирование Фильтрации (Filtering)
        testFiltering(processor);

        // 6. Тестирование Сложных выборок (Complex Queries)
        testComplexQueries(processor);

        printFooter("🏁 ТЕСТИРОВАНИЕ ЗАВЕРШЕНО УСПЕШНО 🏁");
    }

    // -----------------------------------------------------------------------
    // Метод генерации тестовых данных
    // -----------------------------------------------------------------------
    private static void generateData(CompositionListProcessor processor) {
        // Авторы
        Author queen = new Author.AuthorBuilder("Queen", true)
                .setCountry("UK")
                .setDate(LocalDate.of(1970, 1, 1))
                .addMember("Freddie Mercury")
                .build();

        Author mj = new Author.AuthorBuilder("Michael Jackson", false)
                .setCountry("USA")
                .setDate(LocalDate.of(1958, 8, 29))
                .build();

        Author eminem = new Author.AuthorBuilder("Eminem", false)
                .setCountry("USA")
                .setDate(LocalDate.of(1972, 10, 17))
                .build();

        Author beethoven = new Author.AuthorBuilder("Ludwig van Beethoven", false)
                .setCountry("Germany")
                .setDate(LocalDate.of(1770, 12, 17))
                .build();

        Author linkinPark = new Author.AuthorBuilder("Linkin Park", true)
                .setCountry("USA")
                .setDate(LocalDate.of(1996, 1, 1))
                .addMember("Chester Bennington")
                .build();

        Author tsoi = new Author.AuthorBuilder("Kino", true)
                .setCountry("USSR")
                .setDate(LocalDate.of(1982, 1, 1))
                .addMember("Viktor Tsoi")
                .build();

        // Композиции (Разные жанры и даты для проверки сортировки)
        processor.add(new Composition("Bohemian Rhapsody", queen, Genres.rock, LocalDate.of(1975, 10, 31)));
        processor.add(new Composition("We Will Rock You", queen, Genres.rock, LocalDate.of(1977, 10, 7)));
        processor.add(new Composition("Another One Bites the Dust", queen, Genres.rock, LocalDate.of(1980, 8, 22)));

        processor.add(new Composition("Billie Jean", mj, Genres.pop, LocalDate.of(1982, 1, 2)));
        processor.add(new Composition("Beat It", mj, Genres.pop, LocalDate.of(1982, 10, 18)));
        processor.add(new Composition("Smooth Criminal", mj, Genres.pop, LocalDate.of(1987, 11, 14)));

        processor.add(new Composition("Lose Yourself", eminem, Genres.rap, LocalDate.of(2002, 10, 28)));
        processor.add(new Composition("The Real Slim Shady", eminem, Genres.rap, LocalDate.of(2000, 5, 15)));
        processor.add(new Composition("Without Me", eminem, Genres.hipHop, LocalDate.of(2002, 5, 14)));

        processor.add(new Composition("Symphony No. 5", beethoven, Genres.classic, LocalDate.of(1808, 12, 22)));
        processor.add(new Composition("Moonlight Sonata", beethoven, Genres.classic, LocalDate.of(1801, 1, 1)));

        processor.add(new Composition("Numb", linkinPark, Genres.rock, LocalDate.of(2003, 9, 8)));
        processor.add(new Composition("In The End", linkinPark, Genres.rock, LocalDate.of(2000, 10, 9)));

        processor.add(new Composition("Peremen!", tsoi, Genres.rock, LocalDate.of(1986, 5, 10)));
        processor.add(new Composition("Gruppa Krovi", tsoi, Genres.rock, LocalDate.of(1988, 1, 1)));
    }

    // -----------------------------------------------------------------------
    // Тесты Группировки
    // -----------------------------------------------------------------------
    private static void testGrouping(CompositionListProcessor processor) {
        printSection("📂 ТЕСТИРОВАНИЕ ГРУППИРОВКИ ПО ЖАНРАМ");

        // Stream версия
        Map<Genres, List<Composition>> streamMap = processor.streamGroupByGenre();
        printMap("Stream API Grouping", streamMap);

        // Legacy версия (для сверки)
        Map<Genres, List<Composition>> legacyMap = processor.legacyGroupByGenre();

        boolean match = streamMap.size() == legacyMap.size();
        printVerification("Сравнение Legacy vs Stream (размер карт)", match);
        System.out.println();
    }

    // -----------------------------------------------------------------------
    // Тесты Сортировки
    // -----------------------------------------------------------------------
    private static void testSorting(CompositionListProcessor processor) {
        printSection("📈 ТЕСТИРОВАНИЕ СОРТИРОВКИ");

        // Сортировка по дате (Stream)
        List<Composition> sortedByDate = processor.streamSortByDate();
        printList("Сортировка по дате выпуска (Stream)", sortedByDate);

        // Сортировка по автору (Stream)
        List<Composition> sortedByAuthor = processor.streamSortByAuthor();
        printList("Сортировка по имени автора (Stream)", sortedByAuthor);

        // Сверка Legacy сортировки
        List<Composition> legacySorted = processor.legacyFinalSortByDate();
        boolean sortMatch = sortedByDate.size() == legacySorted.size()
                && sortedByDate.get(0).getReleaseDate().equals(legacySorted.get(0).getReleaseDate());
        printVerification("Сравнение сортировки Legacy vs Stream", sortMatch);
        System.out.println();
    }

    // -----------------------------------------------------------------------
    // Тесты Фильтрации
    // -----------------------------------------------------------------------
    private static void testFiltering(CompositionListProcessor processor) {
        printSection("🔍 ТЕСТИРОВАНИЕ ФИЛЬТРАЦИИ");

        Genres targetGenre = Genres.rock;

        // Получение по жанру
        List<Composition> rockStream = processor.streamGetByGenre(targetGenre);
        List<Composition> rockLegacy = processor.legacyGetByGenre(targetGenre);

        printList("Жанр: ROCK (Stream)", rockStream);
        printVerification("Количество ROCK композиций (Legacy vs Stream)", rockStream.size() == rockLegacy.size());
        System.out.println();
    }

    // -----------------------------------------------------------------------
    // Тесты Сложных выборок
    // -----------------------------------------------------------------------
    private static void testComplexQueries(CompositionListProcessor processor) {
        printSection("🧩 ТЕСТИРОВАНИЕ СЛОЖНЫХ ВЫБОРОК");

        // Хронология по жанру
        List<Composition> rockChrono = processor.streamChronologicalByGenre(Genres.rock);
        printList("Rock композиции (Хронологически)", rockChrono);

        // Диапазон дат
        LocalDate from = LocalDate.of(1980, 1, 1);
        LocalDate to = LocalDate.of(1990, 12, 31);

        List<Composition> rangeLegacy = processor.legacyGenreAndYearRange(Genres.pop, from, to);
        List<Composition> rangeStream = processor.streamGenreAndYearRange(Genres.pop, from, to);

        printList(String.format("Pop композиции (%d - %d)", from.getYear(), to.getYear()), rangeStream);
        printVerification("Сравнение диапазона дат (Legacy vs Stream)", rangeLegacy.size() == rangeStream.size());
        System.out.println();
    }

    // -----------------------------------------------------------------------
    // Вспомогательные методы для красивого вывода
    // -----------------------------------------------------------------------

    private static void printHeader(String text) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  " + String.format("%-55s", text) + "  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }

    private static void printSection(String title) {
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│ " + title + "    │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
    }

    private static void printFooter(String text) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  " + String.format("%-55s", text) + "   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }

    private static void printList(String title, List<Composition> list) {
        System.out.println("  📄 " + title + " [" + list.size() + "]:");
        if (list.isEmpty()) {
            System.out.println("     (пусто)");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Composition c = list.get(i);
            System.out.printf("     %2d. %-30s | %10s | %s%n",
                    i + 1,
                    c.getName(),
                    c.getReleaseDate(),
                    c.getAuthor().getName()
            );
        }
        System.out.println();
    }

    private static void printMap(String title, Map<Genres, List<Composition>> map) {
        System.out.println("  📊 " + title + ":");
        for (Map.Entry<Genres, List<Composition>> entry : map.entrySet()) {
            System.out.printf("     [%-8s] -> %d композиций%n", entry.getKey(), entry.getValue().size());
        }
        System.out.println();
    }

    private static void printVerification(String checkName, boolean isSuccess) {
        String status = isSuccess ? "✅ OK" : "❌ FAIL";
        String colorCode = isSuccess ? "\u001B[32m" : "\u001B[31m"; // Green or Red
        String reset = "\u001B[0m";
        System.out.println("  🔎 " + checkName + ": " + colorCode + status + reset);
    }
}