package com.opstty.job;

import com.opstty.mapper.DistrictTreesMapper;
import com.opstty.reducer.MaxTreesDistrictReducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class DistrictMostTrees {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: maxTreesDistrict <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "maxTreesDistrict");
        job.setJarByClass(DistrictMostTrees.class);
        job.setMapperClass(DistrictTreesMapper.class);
        job.setCombinerClass(MaxTreesDistrictReducer.class);
        job.setReducerClass(MaxTreesDistrictReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job,
                new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
