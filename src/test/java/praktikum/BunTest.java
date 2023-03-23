package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Random;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class BunTest {
    Random random=new Random();
    private final String name;
    private final float price;

    public BunTest(String name, float price){
        this.name=name;
        this.price=price;
    }

    @Parameterized.Parameters
    public static Object[][] getData(){
        return new Object[][] {
                {"",-0.0000000000001F},
                {" ",-1000.9999999999999F},
                {"Б",0.0000000000000000001F},
                {"Bu",0},
                {"b_r",2F},
                {"<бу>",21F},
                {"1Бург",100.99999999F},
                {"BUR GER",99999999999999999999F},
                {"БуpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppГеррррррррррррррррррррррррррррррррррррррррррррррррррррррррр",99999999999999999999.999999999999999999999999999999999F}
        };
    }

    @Test
    public void getNameParameterized() {
        Bun bun = new Bun(name, random.nextInt(1000));
        assertTrue(name.equals(bun.getName())&&(!bun.getName().trim().isEmpty()));
    }

    @Test
    public void getPriceParameterized() {
        Bun bun = new Bun("Вася,бургер", price);
        assertTrue(price==bun.getPrice()&&bun.getPrice()>=0);
    }
}