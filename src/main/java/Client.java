import org.apache.spark.SparkConf;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yiwei on 2017/4/4.
 */
public class Client {
    public static void main(String[] args) throws IOException,InterruptedException{

        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("receive");
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(5));
        //JavaDStream<String> stream = javaStreamingContext.textFileStream("/home/yiwei/IdeaProjects/FPro/PubNub.txt").cache();
        JavaReceiverInputDStream<String> lines = javaStreamingContext.receiverStream(new MyReceiver("localhost",9999));

        lines.print();
        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }
    //
}
