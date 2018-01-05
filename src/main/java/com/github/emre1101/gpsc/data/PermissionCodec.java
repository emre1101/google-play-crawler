package com.github.emre1101.gpsc.data;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.github.emre1101.gpsc.app.Permission;

public class PermissionCodec implements Codec<Permission> {
	@Override
	public void encode(final BsonWriter writer, final Permission value, final EncoderContext encoderContext) {
		writer.writeStartDocument();
		writer.writeName("permission");
		writer.writeString(value.permission);
		writer.writeName("description");
		writer.writeString(value.description);
		writer.writeEndDocument();
	}

	@Override
	public Permission decode(final BsonReader reader, final DecoderContext decoderContext) {
		Permission perm = new Permission();
		reader.readStartDocument();
		reader.readName();
		perm.permission = reader.readString();
		reader.readName();
		perm.description = reader.readString();
		reader.readEndDocument();
		return perm;
	}

	@Override
	public Class<Permission> getEncoderClass() {
		return Permission.class;
	}
}