import java.util

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by steve02 on 2017/6/2.
  */
object MQTTWordCount {

  def main(args: Array[String]) {
    if (args.length < 2) {
      // scalastyle:off println
      System.err.println(
        "Usage: MQTTWordCount <MqttbrokerUrl> <topic>")
      // scalastyle:on println
      System.exit(1)
    }

    val Seq(brokerUrl, topic) = args.toSeq
    val sparkConf = new SparkConf().setAppName("MQTTWordCount")

    // check Spark configuration for master URL, set it to local if not configured
    if (!sparkConf.contains("spark.master")) {
      sparkConf.setMaster("local[2]")
    }

    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val lines = MQTTUtils.createStream(ssc, brokerUrl, topic, StorageLevel.MEMORY_ONLY_SER_2)
    val words = lines.flatMap(x => x.split(" "))
    val transform = words.map(binary => Integer.parseInt(binary, 2))
    val arr = new util.ArrayList[Integer]()
    transform.foreachRDD {
      arr +: _.collect() //you can now put it in an array or d w/e you want with it

    }
    while (arr.size()>=1){
    val  compress = transform.map(Decode.decompress(arr))
      compress.print()
    }
    transform.print()
    ssc.start()
    ssc.awaitTermination()
  }
}