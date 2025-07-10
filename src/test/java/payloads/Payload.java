package payloads;
import com.github.javafaker.Faker;
import pojo.Product;

import java.util.Random;

public class Payload {
   private static final Faker faker=new Faker();
    private static final String categories[]={"electronics", "furniture", "clothing", "books", "beauty"};
   private static final Random random=new Random();


   //Product


    public static Product productPayload()
   {
    String name=faker.commerce().productName();
    Double price=Double.parseDouble(faker.commerce().price());
    String description=faker.lorem().sentence();
    String imageUrl="https://i.pravtaar.cc/100";
    String category=categories[random.nextInt(categories.length)];
new Product(name,price,description,imageUrl,category);
  return new Product(name,price,description,imageUrl,category);
}

}
