import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Intersection {

    int id;

    List<String> streets = new ArrayList<>();

    List<Street> streetList = null;

    public List<Street> getStreets() {
        if (streetList == null) {
            streetList = streets.stream().map(p -> Main.nameToStreet.get(p)).collect(Collectors.toList());

            streetList.sort(Comparator.comparing(Street::getCarsCount));
        }

        return streetList;
    }
}
