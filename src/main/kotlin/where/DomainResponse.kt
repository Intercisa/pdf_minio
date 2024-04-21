package where

data class DomainSignal(
    val brandUrn: String
)

data class ApplyDomainResponse(
    val list: List<DomainSignal> = emptyList()
)

data class RemoveDomainResponse(
    val list: List<DomainSignal> = emptyList()
)

data class UpdateDomainResponse(
    val list: List<DomainSignal> = emptyList()
)

inline fun <reified T> processResponse(response: T) where T : PaymentResponse = response.getList()

inline fun <reified T> processResponse(response: T) =
    when (response) {
        is PaymentResponse -> response.getList()
        else -> throw IllegalArgumentException("Unsupported type")
    }


interface PaymentResponse {
    fun getList(): List<DomainSignal>
}


//data class ApplyPaymentResponse(
//     val list: List<DomainSignal> = emptyList()
//):PaymentResponse {
//    override fun getList(): List<DomainSignal> = list
//}
//
//fun ApplyDomainResponse.toSpecificPaymentResponse(): PaymentResponse {
//    return ApplyPaymentResponse(list)
//}

data class RemovePaymentResponse(
    val list: List<DomainSignal> = emptyList()
)

data class UpdatePaymentResponse(
    val list: List<DomainSignal> = emptyList()
)

fun ApplyDomainResponse.toPaymentResponse(): PaymentResponse {
    return object : PaymentResponse {
        override fun getList(): List<DomainSignal> = list
    }
}

fun RemoveDomainResponse.toPaymentResponse(): PaymentResponse {
    return object : PaymentResponse {
        override fun getList(): List<DomainSignal> = list
    }
}

fun UpdateDomainResponse.toPaymentResponse(): PaymentResponse {
    return object : PaymentResponse {
        override fun getList(): List<DomainSignal> = list
    }
}

fun test() {
    val updateDomainResponse = UpdateDomainResponse(
        list = listOf(
            DomainSignal("update")
        )
    )

    val applyDomainResponse = ApplyDomainResponse(
        list = listOf(
            DomainSignal("apply")
        )
    )

    val removeDomainResponse = RemoveDomainResponse(
        list = listOf(
            DomainSignal("remove")
        )
    )


    val updateResponse = updateDomainResponse.toPaymentResponse()
    val applyResponse = applyDomainResponse.toPaymentResponse()
    val removeResponse = removeDomainResponse.toPaymentResponse()

    listOf(
        processResponse(updateResponse),
        processResponse(applyResponse),
        processResponse(removeResponse)
    ).forEach { println(it) }
}

enum class GradeValue {
    A, B, C, D, E, F
}

data class AvgGrade(val points: Int) {
    operator fun plus(other: AvgGrade): GradeValue {
        val newPoints = this.points + other.points
        return when {
            (newPoints < 25) -> GradeValue.F
            (newPoints < 35) -> GradeValue.E
            (newPoints < 45) -> GradeValue.E
            (newPoints < 55) -> GradeValue.D
            (newPoints < 65) -> GradeValue.C
            (newPoints < 75) -> GradeValue.B
            else -> GradeValue.A
        }
    }
}

data class Student(
    val name: String,
    var grade: Int = 0,
    val birthDate: Long
)

fun main() {
    val joe = Student(name = "Joe", birthDate = 8767645687L)
    joe.apply {
        this.grade = 4
    }
    println(joe)

    val firstGrade = AvgGrade(25)
    val secondGrade = AvgGrade(40)

    println(firstGrade + secondGrade)


}