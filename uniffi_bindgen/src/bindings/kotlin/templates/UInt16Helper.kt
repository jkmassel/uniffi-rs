/**
 * @suppress
 */
public object FfiConverterUShort: FfiConverter<UShort, Short> {
    override fun lift(value: Short): UShort {
        // See FfiConverterShort.lift — canonicalize to handle armeabi-v7a
        // AOT JNI narrowing bug.
        return (value.toInt() and 0xFFFF).toShort().toUShort()
    }

    override fun read(buf: ByteBuffer): UShort {
        return lift(buf.getShort())
    }

    override fun lower(value: UShort): Short {
        return value.toShort()
    }

    override fun allocationSize(value: UShort) = 2UL

    override fun write(value: UShort, buf: ByteBuffer) {
        buf.putShort(value.toShort())
    }
}
