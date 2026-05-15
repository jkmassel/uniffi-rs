/**
 * @suppress
 */
public object FfiConverterShort: FfiConverter<Short, Short> {
    override fun lift(value: Short): Short {
        // Canonicalize the low 16 bits. On armeabi-v7a, ART's AOT JNI bridge
        // fails to narrow ()S return values from JNA-registered methods, so
        // the operand-stack value can carry garbage in bits [31:16]. Masking
        // and re-narrowing produces the correct Java signed `short` regardless.
        // No-op on platforms without the bug.
        return (value.toInt() and 0xFFFF).toShort()
    }

    override fun read(buf: ByteBuffer): Short {
        return buf.getShort()
    }

    override fun lower(value: Short): Short {
        return value
    }

    override fun allocationSize(value: Short) = 2UL

    override fun write(value: Short, buf: ByteBuffer) {
        buf.putShort(value)
    }
}
