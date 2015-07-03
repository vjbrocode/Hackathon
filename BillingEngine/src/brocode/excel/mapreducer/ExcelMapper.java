package brocode.excel.mapreducer;

import java.io.IOException;



import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;

 

public class ExcelMapper extends

		Mapper<LongWritable, Text, Text, Text> {

 

	 /**

     * Excel Spreadsheet is supplied in string form to the mapper.

     * We are simply emitting them for viewing on HDFS.

     */

	public void map(LongWritable key, Text value, Context context)

			throws InterruptedException, IOException {

		String line = value.toString();

		

		context.write(new Text(line), null);


	

	}

}


