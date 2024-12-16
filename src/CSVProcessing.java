import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVProcessing {

    private static HashMap<String, Integer> addresses = new HashMap<String, Integer>(); //ключ - строка из адреса, значение - кол-во вхождений
    private static HashMap<String, int[]> floorCount = new HashMap<String, int[]>(); //ключ - строка из адреса, значение - массив с кол-вом зданий разных этажей

    public static void readCSV(String filePath){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) { //считывание адресов в addresses
                String[] values = line.split(";");

                if (values.length == 4){
                    String city = values[0].replace("\"", "");
                    String street = values[1].replace("\"", "");
                    String house = values[2].replace("\"", "");
                    String floor = values[3].replace("\"", "");
                    String address = city + " " + street + " " + house + " " + floor;
                    addresses.put(address, addresses.getOrDefault(address, 0) + 1); //добавление адресов и количеств вхождений в хэш-карту

                    int floorNumber;

                    try {
                        floorNumber = Integer.parseInt(floor);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    floorCount.putIfAbsent(city, new int[5]); //добавление города в хэш-карту

                    if (floorNumber >= 1 && floorNumber <= 5) { //обновление счетчика сданий
                        floorCount.get(city)[floorNumber - 1]++;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void printDuplicates(){
        System.out.println("Дубликаты адресов:");
        boolean hasDuplicates = false;
        for (String address : addresses.keySet()) {
            if (addresses.get(address) > 1) { //если кол-во вхождений больше 1
                System.out.println(address + " - " + addresses.get(address) + " раз(а)");
                hasDuplicates = true;
            }
        }
        if (!hasDuplicates) {
            System.out.println("Дубликатов не найдено.");
        }
    }
    public static void printFloors(){
        System.out.println("\nКоличество этажей в каждом городе:");
        for (String city : floorCount.keySet()) {
            int[] floors = floorCount.get(city);
            System.out.println(city + ": 1 этаж - " + floors[0] + ", 2 этажа - " + floors[1] +
                    ", 3 этажа - " + floors[2] + ", 4 этажа - " + floors[3] +
                    ", 5 этажей - " + floors[4]);
        }
    }
}
