import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.collection.mutable.ArrayBuffer;
import org.apache.spark.streaming.mqtt.MQTTUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yiwei on 2017/4/4.
 */
public class Client {
    public static void main(String[] args) throws IOException,InterruptedException{
        List<Integer> compressList = new ArrayList<Integer>();
        String deString="";
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("receive");
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(5));
        //JavaDStream<String> stream = javaStreamingContext.textFileStream("/home/yiwei/IdeaProjects/FPro/PubNub.txt").cache();
     //   JavaReceiverInputDStream<String> lines = javaStreamingContext.receiverStream(new MyReceiver("localhost",9999));
        JavaDStream<String>StreamInput = MQTTUtils.createStream(javaStreamingContext,"tcp://localhost:1883","sensor", StorageLevel.MEMORY_AND_DISK_SER());

        JavaDStream<String> splitWord = StreamInput.flatMap(s -> Arrays.asList(s.split(" ")).iterator());
        JavaDStream<Integer> transferDec =splitWord.map(binary -> Integer.parseInt(binary,2));
        transferDec.print();
   /*    transferDec.foreachRDD(new VoidFunction<JavaRDD<Integer>>() {
           @Override
           public void call(JavaRDD<Integer> rdd) throws Exception {
               rdd.foreach(new VoidFunction<Integer>() {

                   @Override
                   public void call(Integer integer) throws Exception {
                       compressList.add(integer);


                   }
               });
           }

       });*/

        //transferDec.print();
        /*deString = Decode.decompress(compressList);
        System.out.println(deString);*/
        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }


    //
}
