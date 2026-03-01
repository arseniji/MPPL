package org.lr5;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        printHeader("🎵 ЗАПУСК ТЕСТИРОВАНИЯ MUSIC LIBRARY 🎵");

        // 1. Инициализация процессора
        CompositionListProcessor processor = new CompositionListProcessor();

        // 2. Генерация данных
        System.out.println("⏳ Генерация библиотеки композиций...");
        generateData(processor);
        System.out.println("✅ Добавлено композиций: " + processor.getSize() + "\n");

        // 3. Тестирование Группировки
        testGrouping(processor);

        // 4. Тестирование Сортировки
        testSorting(processor);

        // 5. Тестирование Фильтрации
        testFiltering(processor);

        // 6. Тестирование Сложных выборок
        testComplexQueries(processor);

        // 7. 🔥 НОВОЕ: Тестирование параметризации (Predicate & Comparator)
        testParameterization(processor);

        printFooter("🏁 ТЕСТИРОВАНИЕ ЗАВЕРШЕНО УСПЕШНО 🏁");
    }

    // ... [метод generateData оставляем без изменений] ...
    private static void generateData(CompositionListProcessor processor) {
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

    // ... [методы testGrouping, testSorting, testFiltering, testComplexQueries оставляем без изменений] ...
    private static void testGrouping(CompositionListProcessor processor) {
        printSection("📂 ТЕСТИРОВАНИЕ ГРУППИРОВКИ ПО ЖАНРАМ");
        Map<Genres, List<Composition>> streamMap = processor.streamGroupByGenre();
        printMap("Stream API Grouping", streamMap);
        Map<Genres, List<Composition>> legacyMap = processor.legacyGroupByGenre();
        boolean match = streamMap.size() == legacyMap.size();
        printVerification("Сравнение Legacy vs Stream (размер карт)", match);
        System.out.println();
    }

    private static void testSorting(CompositionListProcessor processor) {
        printSection("📈 ТЕСТИРОВАНИЕ СОРТИРОВКИ");
        List<Composition> sortedByDate = processor.streamSortByDate();
        printList("Сортировка по дате выпуска (Stream)", sortedByDate);
        List<Composition> sortedByAuthor = processor.streamSortByAuthor();
        printList("Сортировка по имени автора (Stream)", sortedByAuthor);
        List<Composition> legacySorted = processor.legacyFinalSortByDate();
        boolean sortMatch = sortedByDate.size() == legacySorted.size()
                && sortedByDate.get(0).getReleaseDate().equals(legacySorted.get(0).getReleaseDate());
        printVerification("Сравнение сортировки Legacy vs Stream", sortMatch);
        System.out.println();
    }

    private static void testFiltering(CompositionListProcessor processor) {
        printSection("🔍 ТЕСТИРОВАНИЕ ФИЛЬТРАЦИИ");
        Genres targetGenre = Genres.rock;
        List<Composition> rockStream = processor.streamGetByGenre(targetGenre);
        List<Composition> rockLegacy = processor.legacyGetByGenre(targetGenre);
        printList("Жанр: ROCK (Stream)", rockStream);
        printVerification("Количество ROCK композиций (Legacy vs Stream)", rockStream.size() == rockLegacy.size());
        System.out.println();
    }

    private static void testComplexQueries(CompositionListProcessor processor) {
        printSection("🧩 ТЕСТИРОВАНИЕ СЛОЖНЫХ ВЫБОРОК");
        List<Composition> rockChrono = processor.streamChronologicalByGenre(Genres.rock);
        printList("Rock композиции (Хронологически)", rockChrono);
        LocalDate from = LocalDate.of(1980, 1, 1);
        LocalDate to = LocalDate.of(1990, 12, 31);
        List<Composition> rangeLegacy = processor.legacyGenreAndYearRange(Genres.pop, from, to);
        List<Composition> rangeStream = processor.streamGenreAndYearRange(Genres.pop, from, to);
        printList(String.format("Pop композиции (%d - %d)", from.getYear(), to.getYear()), rangeStream);
        printVerification("Сравнение диапазона дат (Legacy vs Stream)", rangeLegacy.size() == rangeStream.size());
        System.out.println();
    }

    // =======================================================================
    // 🔥 НОВЫЙ РАЗДЕЛ: Тестирование параметризации (Predicate & Comparator)
    // =======================================================================
    private static void testParameterization(CompositionListProcessor processor) {
        printSection("⚙️ ТЕСТИРОВАНИЕ ПАРАМЕТРИЗАЦИИ (Predicate & Comparator)");

        // -------------------------------------------------------------------
        // 1. ФИЛЬТРАЦИЯ через Predicate: Лямбда vs Анонимный класс
        // -------------------------------------------------------------------
        printSubSection("1️⃣ Фильтрация: Predicate<T>");

        // (б) Лямбда-выражение: композиции после 2000 года
        System.out.println("  ▶ Лямбда: композиции после 2000 года");
        List<Composition> after2000Lambda = processor.streamFilterByPredicate(
                c -> c.getReleaseDate().getYear() > 2000
        );
        printList("Результат (Stream + Lambda)", after2000Lambda);

        // (а) Анонимный класс: композиции после 2000 года
        System.out.println("  ▶ Анонимный класс: композиции после 2000 года");
        List<Composition> after2000Anonymous = processor.legacyFilterByPredicate(
                new Predicate<Composition>() {
                    @Override
                    public boolean test(Composition c) {
                        return c.getReleaseDate().getYear() > 2000;
                    }
                }
        );
        printList("Результат (Legacy + Anonymous)", after2000Anonymous);
        printVerification("Lambda vs Anonymous (размер)",
                after2000Lambda.size() == after2000Anonymous.size());

        // -------------------------------------------------------------------
        // 2. Комбинирование Predicate (and/or/negate)
        // -------------------------------------------------------------------
        printSubSection("2️⃣ Комбинация Predicate (and/or/negate)");

        Predicate<Composition> isRock = c -> c.getGenre() == Genres.rock;
        Predicate<Composition> isOld = c -> c.getReleaseDate().getYear() < 1990;

        // Rock И старые (and)
        List<Composition> oldRock = processor.streamFilterByPredicate(
                isRock.and(isOld)
        );
        printList("Rock AND до 1990 года", oldRock);

        // Rock ИЛИ Classic (or)
        Predicate<Composition> isClassic = c -> c.getGenre() == Genres.classic;
        List<Composition> rockOrClassic = processor.streamFilterByPredicate(
                isRock.or(isClassic)
        );
        printList("Rock OR Classic", rockOrClassic);

        // НЕ Pop (negate)
        List<Composition> notPop = processor.streamFilterByPredicate(
                c -> c.getGenre() != Genres.pop  // или: isPop.negate()
        );
        printList("НЕ Pop жанры", notPop);

        // -------------------------------------------------------------------
        // 3. СОРТИРОВКА через Comparator: Лямбда vs Анонимный класс
        // -------------------------------------------------------------------
        printSubSection("3️⃣ Сортировка: Comparator<T>");

        // (б) Лямбда: по длине названия
        System.out.println("  ▶ Лямбда: сортировка по длине названия");
        List<Composition> sortByLengthLambda = processor.streamSortByComparator(
                (c1, c2) -> Integer.compare(c1.getName().length(), c2.getName().length())
        );
        printList("По длине названия (Lambda)", sortByLengthLambda);

        // (а) Анонимный класс: по длине названия
        System.out.println("  ▶ Анонимный класс: сортировка по длине названия");
        List<Composition> sortByLengthAnonymous = processor.legacySortByComparator(
                new Comparator<Composition>() {
                    @Override
                    public int compare(Composition c1, Composition c2) {
                        return Integer.compare(c1.getName().length(), c2.getName().length());
                    }
                }
        );
        printList("По длине названия (Anonymous)", sortByLengthAnonymous);
        printVerification("Lambda vs Anonymous (сортировка)",
                sortByLengthLambda.size() == sortByLengthAnonymous.size());

        // -------------------------------------------------------------------
        // 4. Метод-референс (Method Reference) — бонус
        // -------------------------------------------------------------------
        printSubSection("4️⃣ Бонус: Method Reference");

        // Сортировка по дате через метод-референс
        List<Composition> sortedByRef = processor.streamSortByComparator(
                Comparator.comparing(Composition::getReleaseDate)
        );
        printList("Сортировка по дате (Method Reference)", sortedByRef);

        // -------------------------------------------------------------------
        // 5. Сравнение Stream vs Legacy с одинаковым Predicate
        // -------------------------------------------------------------------
        printSubSection("5️⃣ Сверка: Stream vs Legacy с одним Predicate");

        Predicate<Composition> isEminem = c -> c.getAuthor().getName().equals("Eminem");

        List<Composition> eminemStream = processor.streamFilterByPredicate(isEminem);
        List<Composition> eminemLegacy = processor.legacyFilterByPredicate(isEminem);

        printList("Eminem (Stream)", eminemStream);
        printList("Eminem (Legacy)", eminemLegacy);
        printVerification("Stream vs Legacy результат",
                eminemStream.size() == eminemLegacy.size());

        System.out.println();
    }

    // -----------------------------------------------------------------------
    // Вспомогательные методы для вывода (оставляем без изменений)
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

    private static void printSubSection(String title) {
        System.out.println("  ┌─ " + title + " ─────────────────────────────┐");
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
        String colorCode = isSuccess ? "\u001B[32m" : "\u001B[31m";
        String reset = "\u001B[0m";
        System.out.println("  🔎 " + checkName + ": " + colorCode + status + reset);
    }
}