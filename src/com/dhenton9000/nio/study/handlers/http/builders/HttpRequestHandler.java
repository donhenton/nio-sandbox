 

package com.dhenton9000.nio.study.handlers.http.builders;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
 
public interface HttpRequestHandler {
/**
	 * Try to read a line.
         * @return 
         * @throws java.io.IOException
	 */
	String line() throws IOException;

	/**
	 * Get more data from the stream.
         * @param readableByteChannel
         * @throws java.io.IOException
	 */
	void read(ReadableByteChannel readableByteChannel) throws IOException;

	void sendResponse(WritableByteChannel writableByteChannel, HttpResponse response);

	StringBuilder getReadLines();
}
