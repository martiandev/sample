package team.standalone.core.common.mapper

interface BaseMapper<in Input, out Output> {

    fun map(input: Input): Output
}