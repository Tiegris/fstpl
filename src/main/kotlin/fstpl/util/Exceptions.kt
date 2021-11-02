package fstpl.util

sealed class FstplException(message: String?) : Exception(message)

class FstplIOException(message: String?) : Exception(message)

class FstplTemplateSyntaxException(message: String?) : FstplException(message)

class FstplModelException(message: String?) : FstplException(message)
