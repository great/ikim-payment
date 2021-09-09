package kim.intae.assignment.payment.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "card_refund_log")
class CardRefundLog(
    @Id
    @Column(length = 20)
    val uniqueId: String,

    @Column(length = 20)
    val paidUniqueId: String,

    @CreatedDate
    val createdAt: Instant,

    @LastModifiedDate
    var updatedAt: Instant
)
