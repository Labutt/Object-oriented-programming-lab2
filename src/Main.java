import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean exitFlag = false;
        long startTime;
        long endTime;
        long timeDiff;
        do {
            System.out.println("Введите полный путь до файла с адресами (или \"exit\" для выхода): ");
            Scanner sc = new Scanner(System.in);
            String filePath = sc.nextLine();
            if(filePath.endsWith(".csv")) {
                try {
                    startTime = System.currentTimeMillis(); //обработка csv файла
                    CSVProcessing.readCSV(filePath);
                    CSVProcessing.printDuplicates();
                    CSVProcessing.printFloors();
                    endTime = System.currentTimeMillis();
                    timeDiff = endTime - startTime;
                    System.out.println("Время выполнения обработки файла: " + timeDiff + " миллисекунд");
                }
                catch (Exception e){
                    System.err.println("Ошибка обработки файла" + e.getMessage());
                }
            }
            else if(filePath.endsWith(".xml")){
                try {
                    startTime = System.currentTimeMillis(); //обработка xml файла
                    XMLProcessing.readXML(filePath);
                    XMLProcessing.printDuplicates();
                    XMLProcessing.printFloors();
                    endTime = System.currentTimeMillis();
                    timeDiff = endTime - startTime;
                    System.out.println("Время выполнения обработки файла: " + timeDiff + " миллисекунд");
                }
                catch (Exception e){
                    System.err.println("Ошибка обработки файла" + e.getMessage());
                }
            }
            else if(filePath.equalsIgnoreCase("exit")){
                exitFlag = true;
            }
            else{
                System.out.println("Введен неверный формат.");
            }
            sc.close();
        }
        while(!exitFlag);

    }
}