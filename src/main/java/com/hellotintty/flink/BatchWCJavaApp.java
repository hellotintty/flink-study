package com.hellotintty.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


/*
使用JAVA API开发flink的批处理应用程序
 */
public class BatchWCJavaApp {
    public static void main(String[] arge) throws Exception {

        String input = "D://hello.txt";
        //step1:获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //step2:read
        DataSource<String> text = env.readTextFile(input);
        text.print();
        //step3:transform
        text.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase().split("\t");
                for (String token:tokens){
                    if (token.length()>0){
                        collector.collect(new Tuple2<String,Integer>(token,1));
                    }
                }
            }
        }).groupBy(0).sum(1).print();
    }
}
