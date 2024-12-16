import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class XMLProcessing {

    private static HashMap<String, Integer> addresses = new HashMap<String, Integer>(); //ключ - строка из адреса, значение - кол-во вхождений
    private static HashMap<String, int[]> floorCount = new HashMap<String, int[]>(); //ключ - строка из адреса, значение - массив с кол-вом зданий разных этажей

    public static void readXML(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //создание db для обработки xml
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item"); //список item из xml

            for (int temp = 0; temp < nList.getLength(); temp++) { //считывание адресов в addresses
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String city = eElement.getAttribute("city").replace("\"", "");
                    String street = eElement.getAttribute("street").replace("\"", "");
                    String house = eElement.getAttribute("house").replace("\"", "");
                    String floor = eElement.getAttribute("floor").replace("\"", "");

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
        } catch (Exception e) {
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
