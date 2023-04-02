
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;
public class Basket implements Serializable {

    private long[] prices;
    private String[] goods;
    private long[] quantity;

    public static final long serialVersionUID = 1L;


    public Basket() {
        goods = new String[]{"Хлеб", "Яблоки", "Молоко"};
        prices = new long[]{100, 200, 300};
        quantity = new long[]{0,0,0};
    }
    public Basket(String[] goods, long[] prices) {
        this.goods = goods;
        this.prices = prices;
        quantity = new long[]{0,0,0};;
    }

    public String[] getGoods() {
        return this.goods;
    }

    public long[] getPrices() {
        return this.prices;
    }

    public void addToCart(long productNum, long amount) {
        this.quantity[(int) (productNum-1)] = amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина");

        long sum = 0;
        for (int i = 0; i < this.goods.length; i++) {
            sum = sum + this.prices[i] * this.quantity[i];
            if (this.quantity[i] != 0) {
                System.out.println(this.goods[i] + " цена: "
                        + this.prices[i]
                        +" рублей, всего " + this.quantity[i] + " шт., итого: "
                        + this.quantity[i]*this.prices[i] + " рублей");
            }

        }
        System.out.println();
        System.out.println("Общая стоимость " + sum + " рублей");
    }



    public void saveJSON(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
//            Gson gson = GsonBuilder().setPrettyPrinting().create;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            writer.print(json);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJSONFile(File file) {
        Basket basket;
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public static Basket loadFromTxtFile(File txtFile) {
        Basket basket;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(txtFile));

            basket = new Basket();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void saveTxt(File textFile) throws RuntimeException {
        try(PrintWriter out = new PrintWriter(textFile.getPath())) {

            for (String good: this.goods) {
                out.print(good + " ");
            }
            out.println();

            for (long price: this.prices) {
                out.print(price + " ");
            }
            out.println();

            for (long amount: this.quantity) {
                out.print(amount + " ");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}