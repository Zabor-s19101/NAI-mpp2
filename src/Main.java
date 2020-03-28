import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Iris> trainingIrises = new ArrayList<>();
        Scanner sc = new Scanner(new File("iris_training.txt"));
        readIrises(sc, trainingIrises);

        List<Iris> testIrises = new ArrayList<>();
        sc = new Scanner(new File("iris_test.txt"));
        readIrises(sc, testIrises);

        Perceptron perceptron = new Perceptron(trainingIrises.get(0).getNumbers().length);

        for (int i = 0; i < 50; i++) {
            for (Iris trainingIris : trainingIrises) {
                int target = trainingIris.getAttribute().equals("Iris-setosa") ? 1 : -1;
                perceptron.train(trainingIris.getNumbers(), target);
            }
        }

        int goodGuess = 0;
        int numOfSetosa = 0;
        for (Iris testIris : testIrises) {
            if (testIris.getAttribute().equals("Iris-setosa")) {
                numOfSetosa++;
                if (perceptron.guess(testIris.getNumbers()) == 1) {
                    goodGuess++;
                }
            }
        }

        System.out.println("Prawidłowo zaklasyfikowane przypadki: " + goodGuess + "/" + numOfSetosa + "\n" +
                "Dokładnośc eksperymentu: " + ((double)goodGuess / numOfSetosa * 100) + "%\n");

        System.out.println("Wprowadź atrybuty numeryczne by odtrzymać wynik klasyfikacji\n" +
                "(" + trainingIrises.get(0).getNumbers().length + " liczby z przecinkiem oddzielone białymi znakami)\n" +
                "jeśli chcesz zakończyć wpisz 0:");
        sc = new Scanner(System.in);
        String line;
        while (!(line = sc.nextLine()).equals("0")) {
            String[] data = line.replaceAll(",", ".").trim().split("\\s+");
            if (data.length != trainingIrises.get(0).getNumbers().length) {
                throw new IllegalArgumentException("Nieprawidłowa liczba atrybutów");
            }
            double[] numbers = new double[data.length];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Double.parseDouble(data[i]);
            }
            if (perceptron.guess(numbers) == 1) {
                System.out.println("Iris-setosa");
            } else {
                System.out.println("Inny");
            }
        }
    }

    private static void readIrises(Scanner sc, List<Iris> irises) {
        while (sc.hasNextLine()) {
            String[] data = sc.nextLine().replaceAll(",", ".").trim().split("\\s+");
            String attribute = data[data.length - 1];
            double[] numbers = new double[data.length - 1];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Double.parseDouble(data[i]);
            }
            irises.add(new Iris(attribute, numbers));
        }
    }
}
