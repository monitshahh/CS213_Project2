package parking;

/**
 * Represents the supported locations of parking decks.
 * Each location stores its county and ZIP code.
 *
 * @author Hiya Savaliya
 */
public enum Location {

    /** Bridgewater in Somerset County with ZIP code 08807. */
    BRIDGEWATER("Somerset", "08807"),

    /** Piscataway in Middlesex County with ZIP code 08854. */
    PISCATAWAY("Middlesex", "08854"),

    /** Edison in Middlesex County with ZIP code 08817. */
    EDISON("Middlesex", "08817"),

    /** Princeton in Mercer County with ZIP code 08542. */
    PRINCETON("Mercer", "08542"),

    /** Morristown in Morris County with ZIP code 07960. */
    MORRISTOWN("Morris", "07960"),

    /** Clark in Union County with ZIP code 07066. */
    CLARK("Union", "07066");

    /** County associated with this location. */
    private final String county;

    /** ZIP code associated with this location. */
    private final String zipCode;

    /**
     * Creates a location with its county and ZIP code.
     *
     * @param county county where the location is located
     * @param zipCode ZIP code of the location
     */
    Location(String county, String zipCode) {
        this.county = county;
        this.zipCode = zipCode;
    }

    /**
     * Returns the county of this location.
     *
     * @return county name
     */
    public String getCounty() {
        return county;
    }

    /**
     * Returns the ZIP code of this location.
     *
     * @return ZIP code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Finds a supported location using a city name.
     * The city comparison is not case-sensitive.
     *
     * @param cityName city name to search for
     * @return matching location, or null if the city is unsupported
     */
    public static Location findLocation(String cityName) {
        if (cityName == null) {
            return null;
        }

        for (Location currentLocation : Location.values()) {
            if (currentLocation.name().equalsIgnoreCase(cityName)) {
                return currentLocation;
            }
        }

        return null;
    }
}