import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yiwei on 2017/4/4.
 */
public class Client {
    public static void main(String[] args) throws IOException,InterruptedException{

        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("receive");
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(5));
        //JavaDStream<String> stream = javaStreamingContext.textFileStream("/home/yiwei/IdeaProjects/FPro/PubNub.txt").cache();
        JavaReceiverInputDStream<String> lines = javaStreamingContext.receiverStream(new MyReceiver("localhost",9999));

        JavaDStream<String> splitWord = lines.flatMap(s -> Arrays.asList(s.split(" ")).iterator());
        splitWord.foreachRDD(new Function<JavaRDD<String>, Void>() {
            public Void call(JavaRDD<String> rdd) throws Exception {
                List<Integer> DecNum = new ArrayList<Integer>();
                if (rdd != null) {
                    List<String> result = rdd.collect();


                    for (String s : result) {

                        //  DecNum.add(Integer.parseInt(s, 2));


                    }

                }

                //  return DecNum;
                return null;
            }}
        ;

        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }
    private   List<Integer> toDec(List<String> list){
        List<Integer> DecNum = new ArrayList<Integer>();
        for (String s : list){

            DecNum.add(Integer.parseInt(s,2));


        }

        System.out.println(DecNum);
        return DecNum;

    }

    //
}
