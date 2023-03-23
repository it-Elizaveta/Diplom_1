package praktikum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BurgerTest {
    @Mock
    Bun bunMock;
    @Mock
    Ingredient ingredientMock;
    @Mock
    List<Ingredient> ingredientsMock;
    @Spy
    Burger burgerSpy;

    private final Random random= new Random();
    private final String bunName="black bun";
    private final Bun bun = new Bun(bunName,100);
    private final String ingredientName="ingriname";
    private final Ingredient ingredient = new Ingredient(IngredientType.SAUCE,ingredientName,100);
    private final Ingredient anotherIngredient = new Ingredient(IngredientType.FILLING,ingredientName,200);
    private final int index;
    private final int newIndex;
    private final float burgerPrice;
    private final float ingredientPrice;
    private final float expectedPrice;

    public BurgerTest(int index,int newIndex,float burgerPrice, float ingredientPrice,float expectedPrice){
        this.index=index;
        this.newIndex=newIndex;
        this.burgerPrice=burgerPrice;
        this.ingredientPrice=ingredientPrice;
        this.expectedPrice=expectedPrice;
    }

    @Parameterized.Parameters
    public static Object[][] getData(){
        return new Object[][] {
                {0,3,100F,200F,600F},
                {1,2,0F,0F,0F},
                {1,1,1000F,1000F,4000F},
                {3,1,1.1F,1.1F,4.4F},
                {2,0,0.25F,0.25F,1F}
        };
    }

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void setBunsSuccessfully() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        Assert.assertEquals(bun,burger.bun);
    }

    @Test
    public void addIngredientSuccessfully() {
        Burger burger= new Burger();
        int size= burger.ingredients.size();
        burger.addIngredient(ingredient);
        Assert.assertTrue(ingredient==burger.ingredients.get(burger.ingredients.size()-1)&&(burger.ingredients.size()==size+1));
    }

    @Test
    public void removeSingleIngredientSuccessfully() {
        Burger burger=new Burger();
        burger.addIngredient(ingredient);
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    public void removeNotSingleIngredientSuccessfully() {
        Burger burger=new Burger();
        burger.addIngredient(ingredient);
        burger.addIngredient(anotherIngredient);
        burger.addIngredient(ingredient);
        burger.addIngredient(anotherIngredient);
        burger.removeIngredient(index);

        List<Ingredient> ingredientsExpected= new ArrayList<Ingredient>();
        ingredientsExpected.add(ingredient);
        ingredientsExpected.add(anotherIngredient);
        ingredientsExpected.add(ingredient);
        ingredientsExpected.add(anotherIngredient);
        ingredientsExpected.remove(index);
        assertEquals(ingredientsExpected, burger.ingredients);
    }

    @Test
    public void moveIngredientSuccessfully() {
        Burger burger=new Burger();
        burger.addIngredient(ingredient);
        burger.addIngredient(anotherIngredient);
        burger.addIngredient(ingredient);
        burger.addIngredient(anotherIngredient);
        burger.moveIngredient(index,newIndex);

        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(ingredient);
        expectedIngredients.add(anotherIngredient);
        expectedIngredients.add(ingredient);
        expectedIngredients.add(anotherIngredient);
        Ingredient removedElement=expectedIngredients.remove(index);
        expectedIngredients.add(newIndex,removedElement);
        assertEquals(expectedIngredients, burger.ingredients);
    }

    @Test
    public void getPriceCorrect() {
        Burger burger=new Burger();
        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);
        burger.addIngredient(ingredientMock);
        Mockito.when(bunMock.getPrice()).thenReturn(burgerPrice);
        Mockito.when(ingredientMock.getPrice()).thenReturn(ingredientPrice);
        assertTrue(burger.getPrice()==expectedPrice);
    }

    @Test
    public void getReceiptShouldPrintCorrectInfo() {
        burgerSpy.setBuns(bunMock);
        burgerSpy.addIngredient(ingredientMock);
        burgerSpy.addIngredient(ingredientMock);
        Mockito.when(bunMock.getName()).thenReturn(bunName);
        Mockito.when(ingredientMock.getName()).thenReturn(ingredientName);
        Mockito.when(ingredientMock.getType()).thenReturn(IngredientType.FILLING);
        int price= random.nextInt(1000);
        Float priceF=(float)price;
        Mockito.doReturn(priceF).when(burgerSpy).getPrice();
        String actualReceipt = burgerSpy.getReceipt();
        String expectedReceipt=String.format("(==== %s ====)\r\n= %s %s =\r\n= %s %s =\r\n(==== %s ====)\r\n\r\nPrice: %d,000000\r\n"
                ,bunName, IngredientType.FILLING.name().toLowerCase(),ingredientName
                ,IngredientType.FILLING.name().toLowerCase(),ingredientName,bunName
                ,price);
        assertEquals(expectedReceipt, actualReceipt);
    }
}