package marketplace.domain.classifiedAd

fun Picture?.hasCorrectSize(): Boolean =
    this != null
    && this.size.width >= 800
    && this.size.height >= 600
