import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static List<List<Integer>> graph;
    private static List<Intersection> intersections;
    public static Map<String, Street> nameToStreet = new HashMap<>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] firstLine = scan.nextLine().split(" ");
        int simulationDuration = Integer.parseInt(firstLine[0]);
        int numberOfIntersections = Integer.parseInt(firstLine[1]);
        int numberOfStreets  = Integer.parseInt(firstLine[2]);
        int numberOfCars = Integer.parseInt(firstLine[3]);
        int bonusForDestination = Integer.parseInt(firstLine[4]);

        List<Street> streets = new ArrayList<>();
        intersections = new ArrayList<>();

        for (int i = 0; i < numberOfStreets; i++) {
            String[] streetLine = scan.nextLine().split(" ");
            int startIntersection = Integer.parseInt(streetLine[0]);
            int endIntersection = Integer.parseInt(streetLine[1]);
            String streetName = streetLine[2];
            int streetRidingTime = Integer.parseInt(streetLine[3]);

            Street street = new Street(startIntersection, endIntersection, streetName, streetRidingTime);
            streets.add(street);
            nameToStreet.put(streetName, street);
        }

        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < numberOfCars; i++) {
            String[] carLine = scan.nextLine().split(" ");
            int numberOfStreetsToTravel = Integer.parseInt(carLine[0]);
            List<String> streetsToTravel = new ArrayList<>();
            for (int j = 0; j < numberOfStreetsToTravel; j++) {
                streetsToTravel.add(carLine[j+1]);
            }
            Car car = new Car(i, streetsToTravel);
            cars.add(car);
        }

        graph = new ArrayList<>();
        for (int i = 0; i < numberOfIntersections; i++) {
            graph.add(new ArrayList<>());

            Intersection intersection = new Intersection();
            intersection.id = i;
            intersections.add(intersection);
        }

        for (Street street : streets) {
            graph.get(street.startIntersection).add(street.endIntersection);

            Intersection intersection = intersections.get(street.endIntersection);
            intersection.streets.add(street.streetName);
        }


        cars.sort(Comparator.comparing(Car::getDuration));

        // wylaczamy to co nie dziala
        cars = cars.stream().limit((int) (numberOfCars * 0.995)).collect(Collectors.toList());

        for (Car car : cars) {
            for (String path : car.paths) {
                Street street = nameToStreet.get(path);
                street.carsCount += 1;
            }

           // System.out.println(car.getDuration());
        }

        String output = "";
        int countIntersection = 0;

        for (Intersection intersection : intersections) {
            long streetsToPrint = intersection.getStreets().stream().filter(c -> c.carsCount > 0).count();

            if (streetsToPrint > 0) {
                countIntersection++;

                output += intersection.id + "\n";
                output += streetsToPrint + "\n";
                long sum = intersection.getStreets().stream().map(c -> c.carsCount).reduce(0, Integer::sum).longValue();

                // Collections.shuffle(intersection.streets);

                for (Street street : intersection.getStreets()) {

                    if (street.carsCount > 0) {
                        output += street.streetName + " " + scaleCarsCount(street.carsCount, sum) +  "\n";
                        //output += streetString + " 1" +  "\n";
                    }
                }
            }
        }

        output = countIntersection + "\n" + output;

        System.out.println(output);
    }

    private static int scaleCarsCount(int c, long sum) {
        return  (int) (Math.ceil(((double) c)/sum * 2));
    }

}
