package me.switchswap.diningmenu.parser

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A custom serializer for the [LocalDate] class that converts a [LocalDate] object to and
 * from a string representation using the `BASIC_ISO_DATE` format (eg: `20231225`).
 */
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = LocalDate.Format {
        year()
        monthNumber(Padding.ZERO)
        day(Padding.ZERO)
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(
            "me.switchswap.diningmenu.parser.LocalDate",
            PrimitiveKind.STRING
        )

    override fun serialize(
        encoder: Encoder,
        value: LocalDate
    ) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}