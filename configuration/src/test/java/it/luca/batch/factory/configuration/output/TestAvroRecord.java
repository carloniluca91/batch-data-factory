/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package it.luca.batch.factory.configuration.output;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class TestAvroRecord extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5974758310797965220L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestAvroRecord\",\"namespace\":\"it.luca.batch.factory.configuration.output\",\"fields\":[{\"name\":\"data_invio\",\"type\":[{\"type\":\"string\",\"avro.java.string\":\"String\"},\"null\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TestAvroRecord> ENCODER =
      new BinaryMessageEncoder<TestAvroRecord>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TestAvroRecord> DECODER =
      new BinaryMessageDecoder<TestAvroRecord>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<TestAvroRecord> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<TestAvroRecord> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<TestAvroRecord>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this TestAvroRecord to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a TestAvroRecord from a ByteBuffer. */
  public static TestAvroRecord fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String data_invio;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TestAvroRecord() {}

  /**
   * All-args constructor.
   * @param data_invio The new value for data_invio
   */
  public TestAvroRecord(java.lang.String data_invio) {
    this.data_invio = data_invio;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return data_invio;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: data_invio = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'data_invio' field.
   * @return The value of the 'data_invio' field.
   */
  public java.lang.String getDataInvio() {
    return data_invio;
  }

  /**
   * Sets the value of the 'data_invio' field.
   * @param value the value to set.
   */
  public void setDataInvio(java.lang.String value) {
    this.data_invio = value;
  }

  /**
   * Creates a new TestAvroRecord RecordBuilder.
   * @return A new TestAvroRecord RecordBuilder
   */
  public static it.luca.batch.factory.configuration.output.TestAvroRecord.Builder newBuilder() {
    return new it.luca.batch.factory.configuration.output.TestAvroRecord.Builder();
  }

  /**
   * Creates a new TestAvroRecord RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TestAvroRecord RecordBuilder
   */
  public static it.luca.batch.factory.configuration.output.TestAvroRecord.Builder newBuilder(it.luca.batch.factory.configuration.output.TestAvroRecord.Builder other) {
    if (other == null) {
      return new it.luca.batch.factory.configuration.output.TestAvroRecord.Builder();
    } else {
      return new it.luca.batch.factory.configuration.output.TestAvroRecord.Builder(other);
    }
  }

  /**
   * Creates a new TestAvroRecord RecordBuilder by copying an existing TestAvroRecord instance.
   * @param other The existing instance to copy.
   * @return A new TestAvroRecord RecordBuilder
   */
  public static it.luca.batch.factory.configuration.output.TestAvroRecord.Builder newBuilder(it.luca.batch.factory.configuration.output.TestAvroRecord other) {
    if (other == null) {
      return new it.luca.batch.factory.configuration.output.TestAvroRecord.Builder();
    } else {
      return new it.luca.batch.factory.configuration.output.TestAvroRecord.Builder(other);
    }
  }

  /**
   * RecordBuilder for TestAvroRecord instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TestAvroRecord>
    implements org.apache.avro.data.RecordBuilder<TestAvroRecord> {

    private java.lang.String data_invio;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(it.luca.batch.factory.configuration.output.TestAvroRecord.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.data_invio)) {
        this.data_invio = data().deepCopy(fields()[0].schema(), other.data_invio);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing TestAvroRecord instance
     * @param other The existing instance to copy.
     */
    private Builder(it.luca.batch.factory.configuration.output.TestAvroRecord other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.data_invio)) {
        this.data_invio = data().deepCopy(fields()[0].schema(), other.data_invio);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'data_invio' field.
      * @return The value.
      */
    public java.lang.String getDataInvio() {
      return data_invio;
    }

    /**
      * Sets the value of the 'data_invio' field.
      * @param value The value of 'data_invio'.
      * @return This builder.
      */
    public it.luca.batch.factory.configuration.output.TestAvroRecord.Builder setDataInvio(java.lang.String value) {
      validate(fields()[0], value);
      this.data_invio = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'data_invio' field has been set.
      * @return True if the 'data_invio' field has been set, false otherwise.
      */
    public boolean hasDataInvio() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'data_invio' field.
      * @return This builder.
      */
    public it.luca.batch.factory.configuration.output.TestAvroRecord.Builder clearDataInvio() {
      data_invio = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TestAvroRecord build() {
      try {
        TestAvroRecord record = new TestAvroRecord();
        record.data_invio = fieldSetFlags()[0] ? this.data_invio : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TestAvroRecord>
    WRITER$ = (org.apache.avro.io.DatumWriter<TestAvroRecord>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TestAvroRecord>
    READER$ = (org.apache.avro.io.DatumReader<TestAvroRecord>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
