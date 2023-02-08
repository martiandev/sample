package team.standalone.core.common.util

class ConstantUtil {

    companion object {
        const val GENDER_FEMALE = 1
        const val GENDER_MALE = 2
        const val GENDER_UNANSWERED = 3

        const val GENDER_LABEL_FEMALE = "女性"
        const val GENDER_LABEL_MALE = "男性"
        const val GENDER_LABEL_UNANSWERED = "未回答"

        const val FAILURE_RECEIPT_VERIFICATION = -1
        const val SERVER_NETWORK_ERROR = -2
        const val FIRESTORE_EXCEPTION = -3

        const val DATE_FORMAT_SHORT_DATE = "yyyy/MM/dd"

        const val SKU_ITEM = "membership_fumiya"

        const val BIRTHDATE_EMPTY = -1
        const val IMAGE_PREFIX = "data:image/png;base64,"

        const val SYS_LOCALE_JA = "ja"
    }
}