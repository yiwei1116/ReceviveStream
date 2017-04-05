import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yiwei on 2017/4/4.
 */
public class Reconstruct implements Serializable {
    public static List<Integer> reConstruct(String decode){
        int base = 200;
        int diff ;
        List<Integer> reNum = new ArrayList<Integer>();
        HashMap reConverse = new HashMap<String,Integer>();
        reConverse.put("A",1);
        reConverse.put("B",2);
        reConverse.put("C",3);
        reConverse.put("D",4);
        reConverse.put("E",5);
        reConverse.put("F",6);
        reConverse.put("G",7);
        reConverse.put("H",8);
        reConverse.put("I",9);
        reConverse.put("J",10);
        reConverse.put("K",11);
        reConverse.put("L",12);
        reConverse.put("M",13);
        reConverse.put("N",14);
        reConverse.put("O",15);
        reConverse.put("P",16);
        reConverse.put("Q",17);
        reConverse.put("R",18);
        reConverse.put("S",19);
        reConverse.put("T",20);
        reConverse.put("U",21);
        reConverse.put("V",22);
        reConverse.put("W",23);
        reConverse.put("X",24);
        reConverse.put("Y",25);
        reConverse.put("Z",0);



        reConverse.put("a",-1);
        reConverse.put("b",-2);
        reConverse.put("c",-3);
        reConverse.put("d",-4);
        reConverse.put("e",-5);
        reConverse.put("f",-6);
        reConverse.put("g",-7);
        reConverse.put("h",-8);
        reConverse.put("i",-9);
        reConverse.put("j",-10);
        reConverse.put("k",-11);
        reConverse.put("l",-12);
        reConverse.put("m",-13);
        reConverse.put("n",-14);
        reConverse.put("o",-15);
        reConverse.put("p",-16);
        reConverse.put("q",-17);
        reConverse.put("r",-18);
        reConverse.put("s",-19);
        reConverse.put("t",-20);
        reConverse.put("u",-21);
        reConverse.put("v",-22);
        reConverse.put("w",-23);
        reConverse.put("x",-24);
        reConverse.put("y",-25);
        for (int i = 0 ; i < decode.length(); i++){

            diff =  (Integer)(reConverse.get(String.valueOf(decode.charAt(i))));
            base += diff;
            reNum.add(base);
            //  Log.error("renum",String.valueOf(reNum.get(i)));
        }

        return reNum;
    }
}
