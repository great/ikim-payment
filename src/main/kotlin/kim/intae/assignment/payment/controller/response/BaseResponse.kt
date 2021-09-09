package kim.intae.assignment.payment.controller.response

import kim.intae.assignment.payment.service.BaseResult

class BaseResponse(
    val uniqueId: String,
    val encryptedString: String,
) {
    constructor(r: BaseResult) : this(r.uniqueId, r.encryptedMessage)
}
