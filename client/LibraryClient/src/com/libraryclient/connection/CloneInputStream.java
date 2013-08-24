package com.libraryclient.connection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CloneInputStream extends BufferedInputStream {

	private InputStream mInputStream;

	private OutputStream mClonerOutput;

	public CloneInputStream(InputStream is) {
		super(is);
		mInputStream = is;
	}

	public CloneInputStream(InputStream is, int size) {
		super(is, size);
		mInputStream = is;
	}

	public CloneInputStream(InputStream is, OutputStream cloneOutput) {
		super(is);
		mInputStream = is;
		mClonerOutput = cloneOutput;
	}

	public CloneInputStream(InputStream is, OutputStream cloneOutput, int size) {
		super(is, size);
		mInputStream = is;
		mClonerOutput = cloneOutput;
	}

	public void setCloneOutput(OutputStream os) {
		mClonerOutput = os;
	}

	@Override
	public synchronized int read() throws IOException {
		count = mInputStream.read();
		if (count < 0)
			return count;
		mClonerOutput.write(count);
		return count;
	}

	@Override
	public synchronized int read(byte[] buffer, int offset, int byteCount)
			throws java.io.IOException {
		count = mInputStream.read(buffer, offset, byteCount);
		if (count < 0)
			return count;
		mClonerOutput.write(buffer, offset, count);
		return count;
	}
}
