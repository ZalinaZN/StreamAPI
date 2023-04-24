import com.sun.jdi.IntegerValue;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        List<String> personStream = persons.stream()
                .filter(person -> (person.getAge() >= 18 && person.getAge() <= 27)
                        && person.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        List<Person> personWorking = persons.stream()
                .filter(age -> age.getAge() >= 18)
                .filter(education -> education.getEducation() == Education.HIGHER)
                .filter(Person -> {
                    if (Person.getSex() == Sex.MAN && Person.getAge() <= 65) {
                        return true;
                    } else if (Person.getSex() == Sex.WOMAN && Person.getAge() <= 60) {
                        return true;
                    } else return false;
                })
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        System.out.println();
        System.out.println("Количество несовершеннолетних: "
                + persons.stream().filter(person -> person.getAge() < 18).count() + "\n");

        System.out.println("Фамилии призывников: " + personStream + "\n");
        System.out.println("Список потенциально работоспособных людей: " + personWorking);


    }
}