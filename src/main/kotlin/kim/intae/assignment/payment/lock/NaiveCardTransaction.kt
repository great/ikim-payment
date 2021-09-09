package kim.intae.assignment.payment.lock

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

// RDBMS를 사용하지만 redis 같은 K-V 저장소로 간주한다.
@Entity
data class NaiveCardTransaction(
    @Id
    @Column(length = 20)
    val cardNo: String,

    @Column(length = 20)
    val uniqueId: String,
)
