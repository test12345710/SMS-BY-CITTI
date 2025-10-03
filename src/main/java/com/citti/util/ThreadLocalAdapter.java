package com.citti.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ThreadLocalAdapter extends TypeAdapter<ThreadLocal<?>> {

	@Override
	public void write(JsonWriter out, ThreadLocal<?> value) throws IOException {
		if (value == null || value.get() == null) {
			out.nullValue();
		} else {
			out.value(value.get().toString()); // Convert to string
		}
	}

	@Override
	public ThreadLocal<?> read(JsonReader in) throws IOException {
		String val = in.nextString();
		return ThreadLocal.withInitial(() -> {
			return val; // Restore as string
		});
	}
}
