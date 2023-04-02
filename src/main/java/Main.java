import java.io.File;
import java.util.Scanner;


public class Main {

    static String[] products = new String[]{"Хлеб", "Яблоки", "Молоко"};
    static long[] prices = new long[]{100, 200, 300};

    static long[] quantity = new long[]{0,0,0};

    static File pom = new File("shop.xml");

    public static void main(String[] args) {

        int productNumber = 0;
        int productCount = 0;



        ClientLog log = new ClientLog();


        XMLSettings settings = new XMLSettings(new File("shop.xml"));

        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);

        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);




        for (int i = 0; i < basket.getGoods().length; i++) {
            System.out.println(i+1 + " " + basket.getGoods()[i] + " " + basket.getPrices()[i] + " руб/шт");
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                if(settings.isLog) {
                    log.exportAsCSV(logFile);
                }
                basket.printCart();
                log.exportAsCSV(logFile);
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length > 2 | parts.length == 1) {
                System.out.println("Введите два значения через пробел и нажмите Enter!");
                System.out.println();
                continue;
            }
            try {
                productNumber = Integer.parseInt(parts[0]);
                if (productNumber <= 0 | productNumber > basket.getGoods().length) {
                    System.out.println();
                    System.out.println("Введите нормальный номер товара");
                    System.out.println();
                    continue;

                }
                productCount = Integer.parseInt(parts[1]);
                if (productCount <= 0 | productCount > 100) {
                    System.out.println();
                    System.out.println("Введите нормальное количесвто товара!");
                    System.out.println();
                    continue;
                }
                basket.addToCart(productNumber, productCount);
                if (settings.isLog) {
                    log.log(productNumber,productCount);
                }
                if (settings.isSave){
                    switch (settings.saveFormat) {
                        case "json" -> basket.saveJSON(saveFile);
                        case "txt" -> basket.saveTxt(saveFile);
                    }
                }
                basket.saveJSON(saveFile);



            } catch (NumberFormatException e) {
                System.out.println("Вы ввели не число!");
            } finally {
                continue;
            }
        }
    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket;

        if (isLoad && loadFile.exists()) {
            switch (loadFormat) {
                case "json" -> basket = Basket.loadFromJSONFile(loadFile);
                case "txt" -> basket = Basket.loadFromTxtFile(loadFile);
                default -> basket = new Basket(products, prices);
            }
        } else {
            basket = new Basket(products,prices);
        }
        return basket;
    }
}
