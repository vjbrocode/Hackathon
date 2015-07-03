package brocode.excel.mapreducer;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

 

public class ExcelDriver {
	 /**

     * Main entry point for the example.

     *

     * @param args arguments

     * @throws Exception when something goes wrong

     */

	public static void main(String[] args) throws Exception {		


		

		Job job = new Job();

		job.setJarByClass(ExcelDriver.class);

		job.setJobName("Excel Record Reader");

		

		job.setMapperClass(ExcelMapper.class);

		job.setNumReduceTasks(0);

		

		FileInputFormat.addInputPath(job, new Path(args[0]));

		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		

		job.setInputFormatClass(ExcelInputFormat.class);

		

		job.waitForCompletion(true);

	}

 

}


