/**
 * @suppress
 */
public object FfiConverterByte: FfiConverter<Byte, Byte> {
    override fun lift(value: Byte): Byte {
        // Canonicalize the low 8 bits. On armeabi-v7a, ART's AOT JNI bridge
        // fails to narrow ()B return values from JNA-registered methods, so
        // the operand-stack value can carry garbage in bits [31:8]. Masking
        // and re-narrowing produces the correct Java signed `byte` regardless.
        // No-op on platforms without the bug.
        return (value.toInt() and 0xFF).toByte()
    }

    override fun read(buf: ByteBuffer): Byte {
        return buf.get()
    }

    override fun lower(value: Byte): Byte {
        return value
    }

    override fun allocationSize(value: Byte) = 1UL

    override fun write(value: Byte, buf: ByteBuffer) {
        buf.put(value)
    }
}
