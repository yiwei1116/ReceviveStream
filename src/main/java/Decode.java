import com.esotericsoftware.minlog.Log;

import java.io.Serializable;
import java.util.*;

/**
 * Created by yiwei on 2017/4/4.
 */
public class Decode implements Serializable {
    static Map<Integer,String> decodeDictionary = new HashMap<Integer,String>();
    static int dictionaryMaxSize = 1024;
    /** Decompress a list of output ks to a string. */
    public static String decompress(List<Integer> compressed) {
        // Build the decodeDictionary.

        LRUCache<Integer, String> lruCache = new LRUCache<Integer, String>(dictionaryMaxSize);
        String delruTable="";
        int dictSize = 52;

        /*for (int i = 0; i < 256; i++)
            decodeDictionary.put(i, "" + (char)i);*/
/**
 *  A~Z 0~25
 */
        for (int i = 65; i < 91; i++) {
            decodeDictionary.put((i-65), "" + (char) i);

        }

/**
 *  a~z 26~51
 */
        for (int i = 97; i < 123; i++) {
            decodeDictionary.put( (i-71), "" + (char) i);

        }
        String w = "" + decodeDictionary.get(compressed.remove(0));
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {

            String entry;

            if (decodeDictionary.containsKey(k)) {
                /**
                 * entry = output
                 */
                entry = decodeDictionary.get(k);

          /*      Log.error("k",String.valueOf(k));
                Log.error("entrychar",String.valueOf(entry.charAt(0)));*/

            }
            else if (k == dictSize) {
                entry = w + w.charAt(0);
            }
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);

            if(decodeDictionary.size()>dictionaryMaxSize){
                Integer leastFrequenceIndex = lruCache.getHead().getKey();
                String leastFrequenceCode = lruCache.getHead().getValue();
                //       Log.error("leastFrequenceIndex",String.valueOf(leastFrequenceIndex));
                //      Log.error("leastFrequenceCode",leastFrequenceCode);
                // Log.error("w",w);

                if(k==leastFrequenceIndex){
                    //  Log.error("replace",w+w.charAt(0));
                    lruCache.put(leastFrequenceIndex,w+w.charAt(0));
                    decodeDictionary.remove(leastFrequenceIndex,leastFrequenceCode);
                    decodeDictionary.put(leastFrequenceIndex,w+w.charAt(0));
                   /* for (Map.Entry<Integer, String> e : lruCache.getAll()){
                        if(decodeDictionary.get(k).contains(e.getValue()))
                            lruCache.put(e.getKey(),e.getValue());


                    }*/
                  /*  if( k > 255 )
                        lruCache.put(k, decodeDictionary.get(k));
*/

                    //     Log.error("w+w.charAt",w+w.charAt(0));
                    entry = decodeDictionary.get(k);
                }
                else {
                    //   Log.error("replace",w+entry.charAt(0));
                    lruCache.put(leastFrequenceIndex, w + entry.charAt(0));
                    decodeDictionary.remove(leastFrequenceIndex, leastFrequenceCode);
                    decodeDictionary.put(leastFrequenceIndex, w + entry.charAt(0));
                    /**
                     * hashmap 長度調整短字串先
                     */
                    Map<Integer,String> hashMap = new HashMap<>();

                    for (Map.Entry<Integer, String> e : lruCache.getAll()){
                        if(decodeDictionary.get(k).startsWith(e.getValue())) {
                            hashMap.put(e.getKey(),e.getValue());

                        /*    Log.error("kk", String.valueOf(e.getKey()));
                            Log.error("dd", String.valueOf(decodeDictionary.get(k)));
                            Log.error("ee", String.valueOf(e.getValue()));*/
                            //            Log.error("value",String.valueOf(e.getValue()));
                        }
                    }
                    List<Map.Entry> entryList = new ArrayList<>(hashMap.entrySet());
                    Comparator< Map.Entry> sortByValue = (e1,e2)->{
                        return ((String)e1.getValue()).compareTo( (String)e2.getValue());
                    };
                    Collections.sort(entryList, sortByValue );
                    for(Map.Entry e : entryList){

                        lruCache.put((Integer) e.getKey(), (String)e.getValue());


                    }

                    /*if( k > 255 )
                        lruCache.put(k, decodeDictionary.get(k));*/

                    //        Log.error("w+entry.charAt",w+entry.charAt(0));
                    entry = decodeDictionary.get(k);
                }

            }

            else{

                lruCache.put(dictSize,w +entry.charAt(0));
                //       Log.error("dictSize",String.valueOf(dictSize));
                //       Log.error("w +entry.charAt(0)",String.valueOf(w +entry.charAt(0)));
                decodeDictionary.put(dictSize, w + entry.charAt(0));
                Map<Integer,String> hashMap = new HashMap<>();
                for (Map.Entry<Integer, String> e : lruCache.getAll()){
                    if(decodeDictionary.get(k).startsWith(e.getValue())) {
                        hashMap.put(e.getKey(),e.getValue());

                 /*   Log.error("key",String.valueOf(e.getKey()));
                    Log.error("value",String.valueOf(e.getValue()));*/
                    }
                }
                List<Map.Entry> entryList = new ArrayList<>(hashMap.entrySet());
                Comparator< Map.Entry> sortByValue = (e1,e2)->{
                    return ((String)e1.getValue()).compareTo( (String)e2.getValue());
                };
                Collections.sort(entryList, sortByValue );
                for(Map.Entry e : entryList){

                    lruCache.put((Integer) e.getKey(), (String)e.getValue());


                }
         /* if( k > 255 )
                lruCache.put(k, decodeDictionary.get(k));*/

            }
            result.append(entry);

            dictSize++;

            w = entry;
        }
        for (Map.Entry<Integer, String> e : lruCache.getAll())
            delruTable=delruTable+"["+"Key:"+e.getKey()+" , "+"Code:"+e.getValue()+"]"+"\n";

        System.out.println("Decode Cache Table:");
        //      System.out.print(delruTable);


        System.out.println("********************************************************");
        System.out.println("Decode decodeDictionary Table:");
        for (Map.Entry<Integer,String>dic : decodeDictionary.entrySet()){
            Integer key =dic.getKey();
            String value = dic.getValue();

           /* if(key > 255)
                System.out.print("["+"Key:"+key+" , "+"Code:"+value+"]"+"\n");*/

        }
<<<<<<< HEAD
=======
        return result.toString();
    }
    public static ArrayList<Integer> binary12bitToInteger(String streaming){
       // streaming.subSequence()



        ArrayList<Integer>decodeNum = new ArrayList<Integer>();

        for (int j = 0 ; j < streaming.length();j+=12){

            decodeNum.add(Integer.parseInt(streaming.substring(j,j+12),2));
            Log.error("tt",String.valueOf(Integer.parseInt(streaming.substring(j,j+12),2)));
>>>>>>> 7a6095e17851302b7a70f60e7690d9441c345107

        }

<<<<<<< HEAD
        return result.toString();
=======
        return decodeNum;
>>>>>>> 7a6095e17851302b7a70f60e7690d9441c345107
    }




}
