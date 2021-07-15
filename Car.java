import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Car {
	public long i;
	public List<String> paths;
	public List<Street> streets;

	public Car(int i, List<String> streetsToTravel) {
		this.i = i;
		this.paths = streetsToTravel;
	}

	public List<Street> getStreets() {
		if (streets == null) {
			streets = paths.stream().map(p -> Main.nameToStreet.get(p)).collect(Collectors.toList());

			streets.sort(Comparator.comparing(Street::getCarsCount));
		}

		return streets;
	}

	public long getDuration() {
		return getStreets().stream().map(s -> s.streetRidingTime).reduce(0, Integer::sum);
	}
}
