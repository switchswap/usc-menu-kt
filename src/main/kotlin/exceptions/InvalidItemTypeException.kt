package exceptions

import java.lang.IllegalArgumentException

class InvalidItemTypeException(message: String = "") : IllegalArgumentException(message)