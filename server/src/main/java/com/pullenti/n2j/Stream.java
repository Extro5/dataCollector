package com.pullenti.n2j;


public class Stream implements AutoCloseable {
    public long length() throws java.io.IOException {
        return 0;
    }
    public void setLength(long p) throws java.io.IOException {
    }

    public long getPosition() throws java.io.IOException {
        return 0;
    }

    public long setPosition(long p) throws java.io.IOException {
        return p;
    }

    public long seek(long p, int dir) throws java.io.IOException {
		if(dir == 0) return setPosition(p);
		if(dir == 1) return setPosition(getPosition() + p);
        return setPosition(length() + p);
    }

	public boolean canSeek() {
	    return true;
	}
	public boolean canRead() {
	    return true;
	}
	public boolean canWrite() {
	    return true;
	}

    @Override
    public void close() throws java.io.IOException {
    }

    public void flush() throws java.io.IOException {
    }

    public int read() throws java.io.IOException {
        return -1;
    }

    public int read(byte[] buf, int off, int len) throws java.io.IOException {
        return -1;
    }

    public void write(byte b) throws java.io.IOException {
    }

    public void write(byte[] buf, int off, int len) throws java.io.IOException {
    }


}
