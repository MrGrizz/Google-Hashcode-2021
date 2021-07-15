public class Street {
	int startIntersection;
	int endIntersection;
	String streetName;
	int streetRidingTime;

	int carsCount = 0;

	public Street(int startIntersection, int endIntersection, String streetName, int streetRidingTime) {
		this.startIntersection = startIntersection;
		this.endIntersection = endIntersection;
		this.streetName = streetName;
		this.streetRidingTime = streetRidingTime;
	}

	public int getCarsCount() {
		return carsCount;
	}
}
