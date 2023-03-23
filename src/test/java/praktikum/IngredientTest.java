package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class IngredientTest {
    private final String name;
    private final float price;
    private final IngredientType type;
    Random random=new Random();

    public IngredientTest(String name, float price, IngredientType type){
        this.name=name;
        this.price=price;
        this.type=type;
    }

    @Parameterized.Parameters
    public static Object[][] getData(){
        return new Object[][] {
                {"",-1F, IngredientType.FILLING},
                {"      ",-0.00000000000000001F, IngredientType.SAUCE},
                {"I",0,IngredientType.FILLING},
                {"И н",0.0000000000000000000001F,IngredientType.SAUCE},
                {"ooops!",123F,IngredientType.FILLING},
                {"опаопаопаопаопопаопаопаопаопаопаопаопаопаопопаопаопаопаопаопаопаопаопаопопаопаопаопаопаопаопаопаопаопопаопаопаопаопаа",9999999999999999.99999999999999999999999F, IngredientType.SAUCE},
        };
    }

    @Test
    public void getPriceParameterized() {
        Ingredient ingredient=new Ingredient(IngredientType.SAUCE,"Name",price);
        assertTrue((price==ingredient.getPrice())&&ingredient.getPrice()>=0);
    }

    @Test
    public void getNameParameterized() {
        Ingredient ingredient=new Ingredient(IngredientType.FILLING,name,random.nextInt(1000));
        assertTrue(name.equals(ingredient.getName())&&(!ingredient.getName().trim().isEmpty()));
    }

    @Test
    public void getTypeParameterized() {
        Ingredient ingredient=new Ingredient(type,"Название",random.nextInt(1000)+ random.nextFloat());
        assertEquals(type,ingredient.getType());
    }
}