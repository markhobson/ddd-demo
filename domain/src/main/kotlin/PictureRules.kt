package marketplace.domain

fun Picture?.hasCorrectSize(): Boolean
    = this != null
        && this.size.width >= 800
        && this.size.height >= 600
