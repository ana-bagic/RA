package fer.zemris.moop;

import fer.zemris.moop.algorithm.Configuration;
import fer.zemris.moop.model.MOOPProblem2;
import fer.zemris.moop.operators.ArithmeticCrossover;
import fer.zemris.moop.operators.GaussMutation;
import fer.zemris.moop.operators.KTournamentSelection;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Util {

    public static final Random RAND = new Random();
    public static final Configuration CONFIG = new Configuration();

    public static void loadConfiguration(String path) {
        CONFIG.setCrossover(new ArithmeticCrossover());
        CONFIG.setProblem(new MOOPProblem2());
        CONFIG.setPlay(false);

        try(Scanner sc = new Scanner(new File(path))) {
            while(sc.hasNext()) {
                String line = sc.nextLine().trim();
                if(line.startsWith("#")) continue;

                String[] elements = line.split(": ");
                String key = elements[0];
                String value = elements[1];
                switch(key) {
                    case "PopulationSize": {
                        CONFIG.setPopulationSize(Integer.parseInt(value));
                        break;
                    }
                    case "Step": {
                        CONFIG.setStep(Double.parseDouble(value));
                        break;
                    }
                    case "TournamentSize": {
                        CONFIG.setSelection(new KTournamentSelection(Integer.parseInt(value)));
                        break;
                    }
                    case "MutationProbability": {
                        CONFIG.setMutation(new GaussMutation(Double.parseDouble(value)));
                        break;
                    }
                    default: throw new IllegalArgumentException("Parameter " + key + " is not supported.");
                }
            }
        } catch(FileNotFoundException e) {
            throw new RuntimeException("File " + path + " not found.");
        }
    }

    public static Set<Integer> generateNDifferentIntsInRange(int N, int lowerBound, int upperBound) {
        if(N > upperBound - lowerBound) {
            throw new IllegalArgumentException("Number of integers cannot be larger than range of integers");
        }

        Set<Integer> numbers = new HashSet<>();
        while(numbers.size() < N) {
            numbers.add(randI(lowerBound, upperBound));
        }
        return numbers;
    }

    public static int randI(int lower, int upper) {
        return RAND.nextInt(upper - lower) + lower;
    }

    public static double randD(double lower, double upper) {
        return RAND.nextDouble()*(upper - lower) + lower;
    }

    public static double getRealStep(double step) {
        double realStep = 1;

        if(step >= 1) {
            while(step / 10 >= 1) {
                realStep *= 10;
                step /= 10;
            }
        } else {
            while(step * 10 <= 10) {
                realStep /= 10;
                step *= 10;
            }
        }

        if(step / 5 >= 1) realStep *= 5;
        if(step / 2 >= 1) realStep *= 2;

        return realStep;
    }

    public static String doubleAsString(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(5, RoundingMode.HALF_UP);
        return String.valueOf(bd.doubleValue());
    }

}
