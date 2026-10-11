import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Java Stream API - Problem Solving Interview Exam
 * Solutions for all 10 questions using Java 8+ Stream API.
 */
public class JavaStreamAPIInterviewExam {

    static class Employee {
        String name;
        String department;
        double salary;

        Employee(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
    }

    public static void main(String[] args) {
        question1();
        question2();
        question3();
        question4();
        question5();
        question6();
        question7();
        question8();
        question9();
        question10();
    }

    // Q1: Find the first non-repeated character.
    private static void question1() {
        System.out.println("Q1: First non-repeated character");
        String input = "swiss";

        Character result = input.chars()
            .mapToObj(c -> (char) c)
            .filter(c -> input.indexOf(c) == input.lastIndexOf(c))
            .findFirst()
            .orElse(null);

        System.out.println(result); // w
        System.out.println();
    }

    // Q2: Find duplicate values, matching the exam's expected sorted output.
    private static void question2() {
        System.out.println("Q2: Duplicate elements");
        List<Integer> numbers =
            Arrays.asList(10, 20, 30, 20, 40, 10, 50, 30);

        Set<Integer> seen = new HashSet<>();
        List<Integer> duplicates = numbers.stream()
            .filter(n -> !seen.add(n))
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        System.out.println(duplicates); // [10, 20, 30]
        System.out.println();
    }

    // Q3: Find the second-highest distinct number.
    private static void question3() {
        System.out.println("Q3: Second highest distinct number");
        List<Integer> numbers =
            Arrays.asList(10, 5, 20, 20, 8, 15, 10);

        Integer result = numbers.stream()
            .distinct()
            .sorted(Comparator.reverseOrder())
            .skip(1)
            .findFirst()
            .orElse(null);

        System.out.println(result); // 15
        System.out.println();
    }

    // Q4: Count each word case-insensitively.
    private static void question4() {
        System.out.println("Q4: Word frequency");
        String sentence = "Java Spring Java Hibernate Spring Java";

        Map<String, Long> frequency = Arrays.stream(sentence.split("\\s+"))
            .map(String::toLowerCase)
            .collect(Collectors.groupingBy(
                word -> word,
                Collectors.counting()
            ));

        // TreeMap keeps output order readable and consistent.
        new java.util.TreeMap<>(frequency)
            .forEach((word, count) ->
                System.out.println(word + "=" + count));
        System.out.println();
    }

    // Q5: Find the longest string. maxBy's tie behavior is not guaranteed
    // to preserve first encounter, so use a reduction that does.
    private static void question5() {
        System.out.println("Q5: Longest string");
        List<String> words = Arrays.asList(
            "Java", "SpringBoot", "Microservices",
            "Hibernate", "Docker"
        );

        String result = words.stream()
            .reduce((first, next) ->
                next.length() > first.length() ? next : first)
            .orElse(null);

        System.out.println(result); // Microservices
        System.out.println();
    }

    // Q6: Group employees and calculate the average salary per department.
    private static void question6() {
        System.out.println("Q6: Average salary by department");
        List<Employee> employees = Arrays.asList(
            new Employee("Ahmed", "IT", 10000),
            new Employee("Mohamed", "IT", 15000),
            new Employee("Sara", "HR", 8000),
            new Employee("Omar", "HR", 12000),
            new Employee("Ali", "Finance", 20000)
        );

        Map<String, Double> averages = employees.stream()
            .collect(Collectors.groupingBy(
                e -> e.department,
                Collectors.averagingDouble(e -> e.salary)
            ));

        new java.util.TreeMap<>(averages)
            .forEach((department, average) ->
                System.out.println(department + " = " + average));
        System.out.println();
    }

    // Q7: Find the highest-paid employee in each department.
    private static void question7() {
        System.out.println("Q7: Highest-paid employee by department");
        List<Employee> employees = Arrays.asList(
            new Employee("Ahmed", "IT", 10000),
            new Employee("Mohamed", "IT", 18000),
            new Employee("Sara", "HR", 9000),
            new Employee("Omar", "HR", 14000),
            new Employee("Ali", "Finance", 20000)
        );

        Map<String, Employee> highestPaid = employees.stream()
            .collect(Collectors.toMap(
                e -> e.department,
                e -> e,
                (e1, e2) -> e1.salary >= e2.salary ? e1 : e2
            ));

        new java.util.TreeMap<>(highestPaid)
            .forEach((department, employee) ->
                System.out.println(department + " = " + employee.name));
        System.out.println();
    }

    // Q8: Find common distinct elements preserving list1 order.
    private static void question8() {
        System.out.println("Q8: Common distinct elements");
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8, 4);

        List<Integer> common = list1.stream()
            .filter(list2::contains)
            .distinct()
            .collect(Collectors.toList());

        System.out.println(common); // [4, 5, 6]
        System.out.println();
    }

    // Q9: Top three numbers by frequency desc, then numeric value asc.
    private static void question9() {
        System.out.println("Q9: Top 3 most frequent numbers");
        List<Integer> numbers = Arrays.asList(
            1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6
        );

        Map<Integer, Long> frequency = numbers.stream()
            .collect(Collectors.groupingBy(
                n -> n,
                Collectors.counting()
            ));

        frequency.entrySet().stream()
            .sorted(
                Comparator.<Map.Entry<Integer, Long>, Long>
                    comparing(Map.Entry::getValue)
                    .reversed()
                    .thenComparing(Map.Entry::getKey)
            )
            .limit(3)
            .forEach(entry ->
                System.out.println(entry.getKey() + " = "
                    + entry.getValue() + " occurrences"));
        System.out.println();
    }

    // Q10: Top 3 highest-paid employees per department.
    private static void question10() {
        System.out.println("Q10: Top 3 highest-paid employees per department");
        List<Employee> employees = Arrays.asList(
            new Employee("Ahmed", "IT", 10000),
            new Employee("Mohamed", "IT", 18000),
            new Employee("Ali", "IT", 15000),
            new Employee("Omar", "IT", 22000),
            new Employee("Sara", "HR", 12000),
            new Employee("Mona", "HR", 16000),
            new Employee("Nour", "HR", 14000),
            new Employee("Hana", "HR", 9000),
            new Employee("Khaled", "Finance", 20000),
            new Employee("Youssef", "Finance", 25000),
            new Employee("Mostafa", "Finance", 18000)
        );

        Map<String, List<Employee>> topByDepartment = employees.stream()
            .collect(Collectors.groupingBy(
                e -> e.department,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> list.stream()
                        .sorted(
                            Comparator.comparingDouble(
                                (Employee e) -> e.salary
                            ).reversed()
                            .thenComparing(e -> e.name)
                        )
                        .limit(3)
                        .collect(Collectors.toList())
                )
            ));

        new java.util.TreeMap<>(topByDepartment)
            .forEach((department, list) -> {
                System.out.println(department + ":");
                list.forEach(e ->
                    System.out.println(e.name + " " + (int) e.salary));
                System.out.println();
            });
    }
}
