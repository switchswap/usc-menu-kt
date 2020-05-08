package exceptions

import java.lang.IllegalArgumentException

class InvalidHallTypeException(message: String = "") : IllegalArgumentException(message)