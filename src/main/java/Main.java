
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        File textFile = new File("./src/main/resources.basket.bin");

        int productNumber = 0;
        int productCount = 0;
        Basket basket = null;

        if (textFile.exists()) {
            basket = Basket.loadFromBinFile(textFile);
        } else {
            basket = new Basket();
        }

        for (int i = 0; i < basket.getGoods().length; i++) {
            System.out.println(i+1 + " " + basket.getGoods()[i] + " " + basket.getPrices()[i] + " руб/шт");
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                basket.printCart();
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
                ClientLog.log(productNumber,productCount);

            } catch (NumberFormatException e) {
                System.out.println("Вы ввели не число!");
            } finally {
                continue;
            }
        }
    }
}
