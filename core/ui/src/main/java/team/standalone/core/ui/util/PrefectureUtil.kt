package team.standalone.core.ui.util

import android.content.Context
import android.content.res.Resources
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.ui.R

object PrefectureUtil {

    /**translate the prefecture selected to kanji if the system locale is not [ConstantUtil.SYS_LOCALE_JA]*/
    fun translatePrefectureToKanji(context: Context, prefecture: String): String {
        var pref = prefecture
        val sysLocale = Resources.getSystem().configuration.locales.get(0)

        if (pref.isNotEmpty()) {
            if (sysLocale.language != ConstantUtil.SYS_LOCALE_JA) {
                val prefectureIndex = getPrefectureIndex(
                    prefecture = prefecture,
                    prefectureList = context.resources.getStringArray(R.array.prefecture_list)
                )
                pref = getPrefectureItem(
                    prefectureIndex = prefectureIndex,
                    prefectureList = context.resources.getStringArray(R.array.prefecture_ja_list)
                )
            }
        }
        return pref
    }

    /**translate the prefecture from kanji to english if the system locale is not [ConstantUtil.SYS_LOCALE_JA]*/
    fun translatePrefectureToEnglish(context: Context, prefecture: String): String {
        var pref = prefecture
        val sysLocale = Resources.getSystem().configuration.locales.get(0)

        if (pref.isNotEmpty()) {
            if (sysLocale.language != ConstantUtil.SYS_LOCALE_JA) {
                val prefectureIndex = getPrefectureIndex(
                    prefecture = prefecture,
                    prefectureList = context.resources.getStringArray(R.array.prefecture_ja_list)
                )
                pref = getPrefectureItem(
                    prefectureIndex = prefectureIndex,
                    prefectureList = context.resources.getStringArray(R.array.prefecture_list)
                )
            }
        }
        return pref
    }

    /**getting the index of "prefecture" from the prefectureList*/
    private fun getPrefectureIndex(prefecture: String, prefectureList: Array<String>): Int {
        var indexPrefecture = 0
        prefectureList.forEachIndexed { index, item ->
            if (item == prefecture) {
                indexPrefecture = index; return@forEachIndexed
            }
        }
        return indexPrefecture
    }

    /**getting the itemPrefecture using the prefectureIndex from the prefectureList*/
    private fun getPrefectureItem(prefectureIndex: Int, prefectureList: Array<String>): String {
        var itemPrefecture = ""
        prefectureList.forEachIndexed { index, item ->
            if (index == prefectureIndex) {
                itemPrefecture = item; return@forEachIndexed
            }
        }
        return itemPrefecture
    }
}