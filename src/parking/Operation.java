package parking;

import util.Date;
import util.Sort;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * User interface for the Parking Management System.
 * Reads commands and performs parking management operations.
 *
 * @author Hiya Savaliya & Monit Shah
 */
public class Operation {

    /** Number of characters required in a license plate. */
    private static final int PLATE_LENGTH = 7;

    /** Position of the hyphen in a license plate. */
    private static final int PLATE_HYPHEN_INDEX = 3;

    /** Number of tokens required for a single-value command. */
    private static final int SINGLE_DATA_TOKEN = 1;

    /** Number of remaining tokens required to open a new deck. */
    private static final int OPEN_DATA_TOKENS = 3;

    /** Number of tokens required in a date. */
    private static final int DATE_TOKENS = 3;

    /** Number of tokens required in a time. */
    private static final int TIME_TOKENS = 2;

    /** Number of data tokens required for an entry command. */
    private static final int ENTER_DATA_TOKENS = 4;

    /** Number of data tokens required for an exit command. */
    private static final int EXIT_DATA_TOKENS = 3;

    /** Minimum valid hour in 24-hour time. */
    private static final int MIN_HOUR = 0;

    /** Maximum valid hour in 24-hour time. */
    private static final int MAX_HOUR = 23;

    /** Minimum valid minute. */
    private static final int MIN_MINUTE = 0;

    /** Maximum valid minute. */
    private static final int MAX_MINUTE = 59;

    /** Maximum number of days allowed for one parking activity. */
    private static final int MAX_PARKING_DAYS = 2;

    /** Number of minutes in one hour. */
    private static final int MINUTES_PER_HOUR = 60;

    /** Number of days in a non-leap year. */
    private static final int DAYS_PER_YEAR = 365;

    /** Leap-year divisibility interval. */
    private static final int QUADRENNIAL = 4;

    /** Century divisibility interval. */
    private static final int CENTENNIAL = 100;

    /** Four-century divisibility interval. */
    private static final int QUATERCENTENNIAL = 400;

    /** February month number. */
    private static final int FEBRUARY = 2;

    /** April month number. */
    private static final int APRIL = 4;

    /** June month number. */
    private static final int JUNE = 6;

    /** September month number. */
    private static final int SEPTEMBER = 9;

    /** November month number. */
    private static final int NOVEMBER = 11;

    /** Number of days in February during a non-leap year. */
    private static final int FEBRUARY_DAYS = 28;

    /** Number of days in February during a leap year. */
    private static final int FEBRUARY_LEAP_DAYS = 29;

    /** Number of days in a short month. */
    private static final int SHORT_MONTH_DAYS = 30;

    /** Number of days in a long month. */
    private static final int LONG_MONTH_DAYS = 31;

    /** List of registered vehicles. */
    private VehicleList vehicleList;

    /** List of parking decks. */
    private DeckList deckList;

    /** Publisher for system notifications. */
    private Notification notification;

    /**
     * Creates the parking management system.
     */
    public Operation() {
        vehicleList = new VehicleList();
        deckList = new DeckList();
        notification = new Notification();
    }

    /**
     * Processes one command.
     *
     * @param command command code
     * @param tokens remaining command tokens
     */
    private void processCommand(String command, StringTokenizer tokens) {
        switch (command) {
            case "L":
                loadVehicles();
                break;
            case "A":
                registerVehicle(tokens);
                break;
            case "R":
                unregisterVehicle(tokens);
                break;
            case "O":
                openDeck(tokens);
                break;
            case "C":
                closeDeck(tokens);
                break;
            case "E":
                enterDeck(tokens);
                break;
            case "X":
                exitDeck(tokens);
                break;
            case "PP":
                printVehicles();
                break;
            case "PD":
                printDecks(tokens);
                break;
            case "PH":
                printHistory(tokens);
                break;
            case "PN":
                printNotifications(tokens);
                break;
            case "PF":
                deckList.printFees();
                break;
            default:
                System.out.println(command + " is an invalid command!");
        }
    }

    /**
     * Creates the vehicle type identified by a valid license plate.
     *
     * @param plate valid license plate
     * @return pickup, van, or sedan
     */
    private Vehicle createVehicle(String plate) {
        char firstLetter = Character.toUpperCase(plate.charAt(0));

        if (firstLetter == 'P') {
            return new Pickup(plate);
        }

        if (firstLetter == 'V') {
            return new Van(plate);
        }

        return new Sedan(plate);
    }

    /**
     * Checks whether a command has enough data tokens.
     *
     * @param tokens remaining command tokens
     * @param required minimum number of tokens
     * @param operation operation name used in the error message
     * @return true if enough tokens exist; false otherwise
     */
    private boolean hasRequiredTokens(StringTokenizer tokens,
                                      int required, String operation) {
        if (tokens.countTokens() < required) {
            System.out.println("Exception [" + operation
                    + "] - missing data tokens.");
            return false;
        }

        return true;
    }

    /**
     * Loads and subscribes vehicles from vehicles.txt.
     * Skips vehicles that are already registered.
     */
    private void loadVehicles() {
        try (Scanner fileScanner = new Scanner(new File("vehicles.txt"))) {
            while (fileScanner.hasNext()) {
                String plate = fileScanner.next();
                Vehicle vehicle = createVehicle(plate);

                if (!vehicleList.contains(vehicle)) {
                    vehicleList.add(vehicle);
                    notification.register(vehicle);
                }
            }

            System.out.println("vehicles loaded.");
        } catch (FileNotFoundException exception) {
            System.out.println("Exception [Load] - "
                    + exception.getMessage());
        }
    }

    /**
     * Parses an integer and reports invalid numeric input.
     *
     * @param text text to convert
     * @param operation operation name for the error message
     * @return parsed integer, or null if conversion fails
     */
    private Integer parseNumber(String text, String operation) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            System.out.println("Exception [" + operation + "] - "
                    + exception.getMessage());
            return null;
        }
    }

    /**
     * Registers a vehicle.
     *
     * @param tokens command tokens
     */
    private void registerVehicle(StringTokenizer tokens) {
        if (!hasRequiredTokens(tokens, SINGLE_DATA_TOKEN, "Register")) {
            return;
        }

        String plate = tokens.nextToken();

        if (!isValidPlate(plate)) {
            System.out.println(plate + " - invalid license plate format.");
            return;
        }

        Vehicle vehicle = createVehicle(plate);

        if (vehicleList.contains(vehicle)) {
            System.out.println(plate + " is already registered.");
            return;
        }

        vehicleList.add(vehicle);
        notification.register(vehicle);
        System.out.println(vehicle + " registered.");
    }

    /**
     * Unregisters a vehicle when permitted.
     *
     * @param tokens command tokens
     */
    private void unregisterVehicle(StringTokenizer tokens) {
        if (!hasRequiredTokens(tokens, SINGLE_DATA_TOKEN, "Unregister")) {
            return;
        }

        String plate = tokens.nextToken();

        if (!isValidPlate(plate)) {
            System.out.println(plate + " - invalid license plate format.");
            return;
        }

        Vehicle searchVehicle = createVehicle(plate);

        if (!vehicleList.contains(searchVehicle)) {
            System.out.println(plate
                    + " does not exist; cannot be unregistered.");
            return;
        }

        Vehicle vehicle = vehicleList.getVehicle(searchVehicle);

        if (deckList.findVehicleDeck(vehicle) != null) {
            System.out.println("Cannot be unregistered - " + vehicle
                    + " is in a deck.");
            return;
        }

        if (vehicle.hasHistory()) {
            System.out.println("Cannot be unregistered - " + vehicle
                    + " has parking history.");
            return;
        }

        vehicleList.remove(vehicle);
        notification.unregister(vehicle);
        System.out.println(vehicle + " unregistered.");
    }

    /**
     * Opens a new parking deck or reopens a closed deck.
     *
     * @param tokens command tokens
     */
    private void openDeck(StringTokenizer tokens) {
        if (!hasRequiredTokens(tokens, SINGLE_DATA_TOKEN, "Open")) {
            return;
        }

        String numberText = tokens.nextToken();
        Integer number = parseNumber(numberText, "Open");

        if (number == null) {
            return;
        }

        Deck existingDeck = deckList.getDeck(number);

        if (existingDeck != null) {
            reopenDeck(existingDeck);
            return;
        }

        if (!hasRequiredTokens(tokens, OPEN_DATA_TOKENS, "Open")) {
            return;
        }

        openNewDeck(number, tokens);
    }

    /**
     * Reopens a closed deck or reports that it is already open.
     *
     * @param deck existing deck
     */
    private void reopenDeck(Deck deck) {
        int number = deck.getNumber();

        if (deck.isOpen()) {
            System.out.println("Deck#" + number + " - is already open.");
            return;
        }

        deck.openDeck();
        System.out.println(
                "Deck#" + number + " - was closed, now reopened.");
    }

    /**
     * Validates data and opens a new deck.
     *
     * @param number deck number
     * @param tokens remaining deck data
     */
    private void openNewDeck(int number, StringTokenizer tokens) {
        String locationText = tokens.nextToken();
        String hourText = tokens.nextToken();
        String capacityText = tokens.nextToken();

        Location location = Location.findLocation(locationText);

        if (location == null) {
            System.out.println(locationText + " - invalid location.");
            return;
        }

        Hour hour = Hour.findHour(hourText);

        if (hour == null) {
            System.out.println(hourText + " - invalid operation hours");
            return;
        }

        Integer capacity = parseNumber(capacityText, "Open");

        if (capacity == null) {
            return;
        }

        if (capacity > Deck.MAXCAPACITY) {
            System.out.println(capacity
                    + " - exceeds the maximum deck capacity "
                    + Deck.MAXCAPACITY);
            return;
        }

        Deck deck = new Deck(number, location, hour, capacity);

        deckList.open(deck);
        System.out.println("Deck#" + number + " opened.");
    }

    /**
     * Closes an existing parking deck.
     *
     * @param tokens command tokens
     */
    private void closeDeck(StringTokenizer tokens) {
        if (!hasRequiredTokens(tokens, SINGLE_DATA_TOKEN, "Close")) {
            return;
        }

        String numberText = tokens.nextToken();
        Integer number = parseNumber(numberText, "Close");

        if (number == null) {
            return;
        }

        Deck deck = deckList.getDeck(number);

        if (deck == null) {
            System.out.println("Deck#" + number + " - does not exist.");
            return;
        }

        if (!deck.isOpen()) {
            System.out.println(
                    "Deck#" + number + " - is already closed.");
            return;
        }

        if (deck.getNumParked() > 0) {
            System.out.println("Deck#" + number
                    + " - cannot be closed; vehicle is in the deck.");
            return;
        }

        deckList.close(deck);
        notification.notify("Deck#" + number + " - closed.");
        System.out.println("Deck#" + number + " - closed.");
    }

    /**
     * Records a vehicle entering a deck.
     *
     * @param tokens command tokens
     */
    private void enterDeck(StringTokenizer tokens) {
        if (!hasRequiredTokens(tokens, ENTER_DATA_TOKENS, "Enter")) {
            return;
        }

        String numberText = tokens.nextToken();
        String plate = tokens.nextToken();
        String dateText = tokens.nextToken();
        String timeText = tokens.nextToken();

        Deck deck = getEntryDeck(numberText);

        if (deck == null) {
            return;
        }

        Vehicle vehicle = getEntryVehicle(plate);

        if (vehicle == null) {
            return;
        }

        Timestamp timestamp = validEntryTime(deck, dateText, timeText);

        if (timestamp == null) {
            return;
        }

        Parking parking = new Parking(vehicle, timestamp);
        deck.enter(parking);

        if (deck.isFull()) {
            notification.notify("Deck# " + deck.getNumber() + " is full.");
        }

        System.out.println(vehicle + " entered Deck#" + deck.getNumber()
                + " on " + timestamp);
    }

    /**
     * Builds an entry timestamp and checks the deck's operating hours.
     *
     * @param deck deck being entered
     * @param dateText date token
     * @param timeText time token
     * @return valid entry timestamp, or null if invalid
     */
    private Timestamp validEntryTime(
            Deck deck, String dateText, String timeText) {
        Timestamp timestamp = createTimestamp(dateText, timeText);

        if (timestamp == null) {
            return null;
        }

        if (!deck.getHour().isWithinOperatingHours(timestamp)) {
            System.out.println(
                    "Error entering - not within the operating hour: "
                            + deck.getHour());
            return null;
        }

        return timestamp;
    }

    /**
     * Validates the deck used by an entry command.
     *
     * @param numberText deck number token
     * @return valid open deck with space, or null if invalid
     */
    private Deck getEntryDeck(String numberText) {
        Integer number = parseNumber(numberText, "Enter");

        if (number == null) {
            return null;
        }

        Deck deck = deckList.getDeck(number);

        if (deck == null) {
            System.out.println("Deck#" + number + " - does not exist.");
            return null;
        }

        if (!deck.isOpen()) {
            System.out.println(
                    "Deck#" + number + " - is closed for parking.");
            return null;
        }

        if (deck.isFull()) {
            System.out.println("Deck#" + number + " - is full.");
            return null;
        }

        return deck;
    }

    /**
     * Validates the vehicle used by an entry command.
     *
     * @param plate license plate token
     * @return registered vehicle available to enter, or null if invalid
     */
    private Vehicle getEntryVehicle(String plate) {
        if (!isValidPlate(plate)) {
            System.out.println(plate + " - invalid license plate format.");
            return null;
        }

        Vehicle searchVehicle = createVehicle(plate);

        if (!vehicleList.contains(searchVehicle)) {
            System.out.println(searchVehicle + " - is not registered.");
            return null;
        }

        Vehicle vehicle = vehicleList.getVehicle(searchVehicle);

        if (deckList.findVehicleDeck(vehicle) != null) {
            System.out.println("Error entering - " + vehicle
                    + " is already in a deck.");
            return null;
        }

        return vehicle;
    }

    /**
     * Records a vehicle exiting a parking deck.
     *
     * @param tokens command tokens
     */
    private void exitDeck(StringTokenizer tokens) {
        if (!hasRequiredTokens(tokens, EXIT_DATA_TOKENS, "Exit")) {
            return;
        }

        String plate = tokens.nextToken();
        String dateText = tokens.nextToken();
        String timeText = tokens.nextToken();

        Deck deck = getExitDeck(plate);

        if (deck == null) {
            return;
        }

        Parking parking = deck.getParking(createVehicle(plate));
        Timestamp timestamp = createTimestamp(dateText, timeText);

        if (timestamp == null) {
            return;
        }

        if (!isValidExitTime(deck, parking, timestamp)) {
            return;
        }

        parking.setExit(timestamp);

        Vehicle vehicle = parking.getVehicle();
        int fee = vehicle.parkingFee(deck, parking.getEnter(), timestamp);

        deck.collectFee(fee);
        vehicle.addParking(parking, deck);
        deck.exit(parking);

        System.out.println(vehicle + " exited Deck#" + deck.getNumber()
                + " on " + timestamp);
        System.out.println("    * Parking Fee: $ " + fee
                + " [entered: " + parking.getEnter() + "]");
    }

    /**
     * Finds the deck used by an exit command.
     *
     * @param plate license plate token
     * @return deck containing the vehicle, or null if invalid
     */
    private Deck getExitDeck(String plate) {
        if (!isValidPlate(plate)) {
            System.out.println(plate + " - invalid license plate format.");
            return null;
        }

        Vehicle vehicle = createVehicle(plate);
        Deck deck = deckList.findVehicleDeck(vehicle);

        if (deck == null) {
            System.out.println(
                    "Error exiting - " + vehicle + " is not in a deck.");
            return null;
        }

        return deck;
    }

    /**
     * Validates an exit timestamp against deck and parking rules.
     *
     * @param deck deck containing the vehicle
     * @param parking parking activity being completed
     * @param timestamp proposed exit timestamp
     * @return true if the exit timestamp is valid; false otherwise
     */
    private boolean isValidExitTime(
            Deck deck, Parking parking, Timestamp timestamp) {
        if (!deck.getHour().isWithinOperatingHours(timestamp)) {
            System.out.println(
                    "Error exiting - not within the operating hour: "
                            + deck.getHour());
            return false;
        }

        if (timestamp.compareTo(parking.getEnter()) < 0) {
            System.out.println("Exiting time " + timestamp
                    + " before entering time " + parking.getEnter());
            return false;
        }

        if (exceedsTwoDays(parking.getEnter(), timestamp)) {
            System.out.println(
                    "Invalid exiting time - exceeds two days.");
            return false;
        }

        return true;
    }

    /**
     * Prints all registered vehicles.
     */
    private void printVehicles() {
        vehicleList.printByPlate();
    }

    /**
     * Processes the PD command.
     *
     * @param tokens command tokens
     */
    private void printDecks(StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            deckList.printByLocation();
            return;
        }

        String numberText = tokens.nextToken();

        if (!isDigits(numberText)) {
            System.out.println(numberText
                    + " - invalid deck number; it contains characters.");
            return;
        }

        Integer number = parseNumber(numberText, "Print");

        if (number == null) {
            return;
        }

        Deck deck = deckList.getDeck(number);

        if (deck == null) {
            System.out.println("Deck#" + number + " - does not exist.");
            return;
        }

        if (!deck.isOpen()) {
            System.out.println(
                    "Deck#" + number + " - is closed for parking.");
            return;
        }

        deckList.printVehicles(deck);
    }

    /**
     * Processes the PH command for all vehicles or a specified plate.
     *
     * @param tokens remaining command tokens
     */
    private void printHistory(StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            vehicleList.printHistory();
            return;
        }

        String plate = tokens.nextToken();

        if (!isValidPlate(plate)) {
            System.out.println(plate + " - invalid license plate format.");
            return;
        }

        Vehicle vehicle = vehicleList.getVehicle(createVehicle(plate));

        if (vehicle == null) {
            System.out.println(plate + " does not exist.");
            return;
        }

        if (!vehicle.hasHistory()) {
            System.out.println(plate + " - no parking history.");
            return;
        }

        System.out.println("** Parking history for " + vehicle + " **");

        Sort.byHistoryTimestamp(vehicle.getHistory());

        for (History record : vehicle.getHistory()) {
            System.out.println(record);
        }

        System.out.println("** end of list **");
    }

    /**
     * Displays notifications for all vehicles or a specified plate.
     *
     * @param tokens remaining command tokens
     */
    private void printNotifications(StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            vehicleList.printNotifications();
            return;
        }

        String plate = tokens.nextToken();

        if (!isValidPlate(plate)) {
            System.out.println(plate + " - invalid license plate format.");
            return;
        }

        Vehicle vehicle = vehicleList.getVehicle(createVehicle(plate));

        if (vehicle == null) {
            System.out.println(plate + " does not exist.");
            return;
        }

        if (vehicle.getNotifications().isEmpty()) {
            System.out.println(plate + " - no notifications.");
            return;
        }

        System.out.println("** Notifications for " + vehicle + " **");
        System.out.println("    ** notifications ** ");

        for (String message : vehicle.getNotifications()) {
            System.out.println("    " + message);
        }

        System.out.println("** end of list **");
    }

    /**
     * Creates a timestamp after validating its date and time.
     *
     * @param dateText date token
     * @param timeText time token
     * @return valid timestamp, or null if invalid
     */
    private Timestamp createTimestamp(String dateText, String timeText) {
        Date date = createDate(dateText);

        if (date == null) {
            return null;
        }

        StringTokenizer timeTokens =
                new StringTokenizer(timeText, ":");

        if (timeTokens.countTokens() != TIME_TOKENS) {
            return null;
        }

        String hourText = timeTokens.nextToken();
        String minuteText = timeTokens.nextToken();

        if (!isDigits(hourText) || !isDigits(minuteText)) {
            return null;
        }

        int hour = Integer.parseInt(hourText);
        int minute = Integer.parseInt(minuteText);

        if (hour < MIN_HOUR || hour > MAX_HOUR) {
            System.out.println(hour + " - invalid hour.");
            return null;
        }

        if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
            System.out.println(minute + " - invalid minute.");
            return null;
        }

        return new Timestamp(date, hour, minute);
    }

    /**
     * Creates and validates a calendar date from a date token.
     *
     * @param dateText date token
     * @return valid date, or null if invalid
     */
    private Date createDate(String dateText) {
        StringTokenizer dateTokens =
                new StringTokenizer(dateText, "-");

        if (dateTokens.countTokens() != DATE_TOKENS) {
            return null;
        }

        String yearText = dateTokens.nextToken();
        String monthText = dateTokens.nextToken();
        String dayText = dateTokens.nextToken();

        if (!isDigits(yearText)
                || !isDigits(monthText)
                || !isDigits(dayText)) {
            return null;
        }

        int year = Integer.parseInt(yearText);
        int month = Integer.parseInt(monthText);
        int day = Integer.parseInt(dayText);

        Date date = new Date(year, month, day);

        if (!date.isValid()) {
            System.out.println(date + " - invalid calendar date.");
            return null;
        }

        return date;
    }

    /**
     * Checks whether a license plate follows Xdd-XXX.
     *
     * @param plate license plate
     * @return true if valid; false otherwise
     */
    private boolean isValidPlate(String plate) {
        if (plate == null || plate.length() != PLATE_LENGTH) {
            return false;
        }

        if (!isLetter(plate.charAt(0))
                || plate.charAt(PLATE_HYPHEN_INDEX) != '-') {
            return false;
        }

        for (int index = 1; index < PLATE_HYPHEN_INDEX; index++) {
            if (!isDigit(plate.charAt(index))) {
                return false;
            }
        }

        for (int index = PLATE_HYPHEN_INDEX + 1;
             index < PLATE_LENGTH; index++) {
            if (!isLetter(plate.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether a string contains only digits.
     *
     * @param text text to check
     * @return true if all characters are digits
     */
    private boolean isDigits(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }

        for (int index = 0; index < text.length(); index++) {
            if (!isDigit(text.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether a character is an English letter.
     *
     * @param character character to check
     * @return true if the character is a letter
     */
    private boolean isLetter(char character) {
        return (character >= 'A' && character <= 'Z')
                || (character >= 'a' && character <= 'z');
    }

    /**
     * Checks whether a character is a digit.
     *
     * @param character character to check
     * @return true if the character is a digit
     */
    private boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }

    /**
     * Determines whether an exit is more than two days after entry.
     *
     * @param enter entering timestamp
     * @param exit exiting timestamp
     * @return true if the stay exceeds two days; false otherwise
     */
    private boolean exceedsTwoDays(Timestamp enter, Timestamp exit) {
        int dayDifference = dateValue(exit.getDate())
                - dateValue(enter.getDate());

        if (dayDifference > MAX_PARKING_DAYS) {
            return true;
        }

        if (dayDifference < MAX_PARKING_DAYS) {
            return false;
        }

        int enterMinutes = enter.getHour() * MINUTES_PER_HOUR
                + enter.getMinute();
        int exitMinutes = exit.getHour() * MINUTES_PER_HOUR
                + exit.getMinute();

        return exitMinutes > enterMinutes;
    }

    /**
     * Converts a date to a value for calculating day differences.
     *
     * @param date date to convert
     * @return numeric date value
     */
    private int dateValue(Date date) {
        int totalDays = date.getYear() * DAYS_PER_YEAR;

        for (int year = 1; year < date.getYear(); year++) {
            if (isLeapYear(year)) {
                totalDays++;
            }
        }

        for (int month = 1; month < date.getMonth(); month++) {
            totalDays += daysInMonth(date.getYear(), month);
        }

        totalDays += date.getDay();
        return totalDays;
    }

    /**
     * Returns the number of days in a month.
     *
     * @param year year of the date
     * @param month month of the date
     * @return number of days in the month
     */
    private int daysInMonth(int year, int month) {
        if (month == FEBRUARY) {
            return isLeapYear(year)
                    ? FEBRUARY_LEAP_DAYS : FEBRUARY_DAYS;
        }

        if (month == APRIL || month == JUNE
                || month == SEPTEMBER || month == NOVEMBER) {
            return SHORT_MONTH_DAYS;
        }

        return LONG_MONTH_DAYS;
    }

    /**
     * Determines whether a year is a leap year.
     *
     * @param year year to check
     * @return true if the year is a leap year; false otherwise
     */
    private boolean isLeapYear(int year) {
        return year % QUATERCENTENNIAL == 0
                || (year % QUADRENNIAL == 0
                && year % CENTENNIAL != 0);
    }

    /**
     * Runs the parking management system until uppercase Q is entered.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "Parking Management System is in operation.");
        System.out.println();

        boolean running = true;

        while (running && scanner.hasNextLine()) {
            String commandLine = scanner.nextLine().trim();

            if (commandLine.isEmpty()) {
                continue;
            }

            StringTokenizer tokens =
                    new StringTokenizer(commandLine);
            String command = tokens.nextToken();

            if (command.equals("Q")) {
                running = false;
            } else {
                processCommand(command, tokens);
            }
        }

        System.out.println(
                "Parking Management System is terminated.");
    }
}